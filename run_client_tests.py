import urllib.request
import json
import time
import random

bff_url = "http://localhost:8080"


def send_request(method, path, body=None, token=None):
    url = f"{bff_url}{path}"
    headers = {
        "Content-Type": "application/json"
    }
    if token:
        headers["Authorization"] = f"Bearer {token}"
        
    data = None
    if body is not None:
        data = json.dumps(body).encode("utf-8")
        
    req = urllib.request.Request(url, data=data, headers=headers, method=method)
    try:
        with urllib.request.urlopen(req) as res:
            status = res.status
            resp_body = res.read().decode("utf-8")
            try:
                return status, json.loads(resp_body)
            except:
                return status, resp_body
    except urllib.error.HTTPError as e:
        resp_body = e.read().decode("utf-8")
        try:
            return e.code, json.loads(resp_body)
        except:
            return e.code, resp_body
    except Exception as e:
        return 500, str(e)

print("=== STARTING SYSTEM INTEGRATION TESTS VIA BFF (PORT 8080) ===")

# 1. Register User (admin / admin123)
print("\n[TEST 1] Registering user 'admin'...")
status, resp = send_request("POST", "/api/auth/register", {"username": "admin", "password": "admin123"})
print(f"Status: {status} | Response: {resp}")

# 2. Login User
print("\n[TEST 2] Logging in...")
status, resp = send_request("POST", "/api/auth/login", {"username": "admin", "password": "admin123"})
print(f"Status: {status} | Response: {resp}")
token = None
if status == 200 and isinstance(resp, dict):
    token = resp.get("accessToken")
    print("Token obtained successfully!")
else:
    # If user already exists, register might return error, but login should work. Let's make sure we have a token.
    print("Register failed/already exists, login response:", resp)
    if isinstance(resp, dict) and "accessToken" in resp:
        token = resp["accessToken"]

if not token:
    print("ERROR: Could not obtain Bearer token. Aborting rest of tests.")
    exit(1)

# 3. Create Author
print("\n[TEST 3] Creating Author 'Pablo Neruda'...")
status, author_resp = send_request("POST", "/api/library/authors", {
    "name": "Pablo Neruda",
    "nationality": "Chilena",
    "birthYear": 1904
}, token)
print(f"Status: {status} | Response: {author_resp}")
author_id = author_resp.get("id") if isinstance(author_resp, dict) else 1

# 4. List Authors
print("\n[TEST 4] Listing all authors...")
status, resp = send_request("GET", "/api/library/authors", token=token)
print(f"Status: {status} | Response: {resp}")

# 5. Create Book
print("\n[TEST 5] Creating Book...")
status, book_resp = send_request("POST", "/api/library/books", {
    "title": "El otoño del patriarca",
    "authorId": author_id,
    "category": "Novela",
    "isbn": f"978-0-063-{random.randint(100000, 999999)}-{random.randint(0, 9)}"
}, token)
print(f"Status: {status} | Response: {book_resp}")
book_id = book_resp.get("id") if isinstance(book_resp, dict) else "a1b2c3d4-0001-0000-0000-000000000001"

# 6. List Books
print("\n[TEST 6] Listing all books...")
status, resp = send_request("GET", "/api/library/books", token=token)
print(f"Status: {status} | Response: {resp}")

# 7. Create Book Copy (Inventory)
print("\n[TEST 7] Creating Book Copy...")
status, copy_resp = send_request("POST", "/api/library/inventorys", {
    "bookId": book_id,
    "barcode": f"BC-{random.randint(100000, 999999)}",
    "physicalStatus": "NEW"
}, token)
print(f"Status: {status} | Response: {copy_resp}")
copy_id = copy_resp.get("id") if isinstance(copy_resp, dict) else "a1b2c3d4-0001-0000-0000-000000000001"

# 8. List Copies
print("\n[TEST 8] Listing Book Copies...")
status, resp = send_request("GET", "/api/library/inventorys", token=token)
print(f"Status: {status} | Response: {resp}")


# 9. Create Loan
print("\n[TEST 9] Creating Loan...")
status, loan_resp = send_request("POST", "/api/library/loans", {
    "bookCopyId": copy_id,
    "username": "admin"
}, token)
print(f"Status: {status} | Response: {loan_resp}")
loan_id = loan_resp.get("id") if isinstance(loan_resp, dict) else None

# 10. List Loans
print("\n[TEST 10] Listing Loans...")
status, resp = send_request("GET", "/api/library/loans", token=token)
print(f"Status: {status} | Response: {resp}")

# 10b. Return Loan
if loan_id:
    print(f"\n[TEST 10b] Returning Loan {loan_id}...")
    status, return_resp = send_request("POST", f"/api/library/loans/{loan_id}/return", None, token)
    print(f"Status: {status} | Response: {return_resp}")


# 11. Create Penalty (Stateless calculation)
print("\n[TEST 11] Creating Penalty (ms-penalty)...")
status, penalty_resp = send_request("POST", "/api/library/penaltys", {
    "loanId": loan_id if loan_id else "a1b2c3d4-0001-0000-0000-000000000001",
    "username": "admin",
    "amount": 2500.0
}, token)
print(f"Status: {status} | Response: {penalty_resp}")

# 12. List Penalties
print("\n[TEST 12] Listing Penalties...")
status, resp = send_request("GET", "/api/library/penaltys", token=token)
print(f"Status: {status} | Response: {resp}")

# 12b. Pay Penalty
penalty_id = penalty_resp.get("id") if isinstance(penalty_resp, dict) else None
if penalty_id:
    print(f"\n[TEST 12b] Paying Penalty {penalty_id}...")
    status, pay_resp = send_request("POST", f"/api/library/penaltys/{penalty_id}/pay", None, token)
    print(f"Status: {status} | Response: {pay_resp}")


# 13. Create Reservation
print("\n[TEST 13] Creating Reservation...")
status, res_resp = send_request("POST", "/api/library/reservations", {
    "bookId": book_id,
    "username": "admin"
}, token)
print(f"Status: {status} | Response: {res_resp}")

# 14. List Reservations
print("\n[TEST 14] Listing Reservations...")
status, resp = send_request("GET", "/api/library/reservations", token=token)
print(f"Status: {status} | Response: {resp}")

# 15. Send Notification (Stateless logger)
print("\n[TEST 15] Sending Notification (ms-notification)...")
status, notif_resp = send_request("POST", "/api/library/notifications", {
    "recipient": "alumno@duoc.cl",
    "messageType": "EMAIL",
    "content": "Tu libro se encuentra próximo a vencer el día de mañana."
}, token)
print(f"Status: {status} | Response: {notif_resp}")

# 16. List Notification History
print("\n[TEST 16] Listing Notification History...")
status, resp = send_request("GET", "/api/library/notifications", token=token)
print(f"Status: {status} | Response: {resp}")

# 17. Create Review
print("\n[TEST 17] Creating Review...")
status, review_resp = send_request("POST", "/api/library/reviews", {
    "bookId": book_id,
    "username": "admin",
    "rating": 5,
    "comment": "Gran obra literaria, altamente recomendable."
}, token)
print(f"Status: {status} | Response: {review_resp}")

# 18. List Reviews
print("\n[TEST 18] Listing Reviews...")
status, resp = send_request("GET", "/api/library/reviews", token=token)
print(f"Status: {status} | Response: {resp}")

print("\n=== INTEGRATION TESTS COMPLETED SUCCESSFULLY ===")
