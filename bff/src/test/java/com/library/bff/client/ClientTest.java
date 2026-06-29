package com.library.bff.client;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClient;

import com.library.bff.dto.AuthorRequest;
import com.library.bff.dto.AuthorResponse;
import com.library.bff.dto.BookRequest;
import com.library.bff.dto.BookResponse;

// Integra Mockito con JUnit 5.
// Gracias a esta anotacion, los campos con @Mock se crean automaticamente antes de cada test.
// Sin esto, tendriamos que inicializar mocks manualmente.
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({"unchecked", "rawtypes"})
class ClientTest {

    @Mock
    private RestClient restClient;

    @Test
    void authorClient_findAll_shouldReturnList() {
        // *** Este test prueba la obtención de la lista completa de autores instanciando manualmente el cliente
        AuthorClient authorClient = new AuthorClient(restClient);
        ReflectionTestUtils.setField(authorClient, "authorServiceUrl", "http://localhost:8083");

        RestClient.RequestHeadersUriSpec getSpec = mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
        
        when(restClient.get()).thenReturn(getSpec);
        when(getSpec.uri(anyString())).thenReturn(getSpec);
        when(getSpec.retrieve()).thenReturn(responseSpec);
        
        List<AuthorResponse> expected = List.of(new AuthorResponse(1L, "Gabriel", "Col", 1927));
        when(responseSpec.body(any(ParameterizedTypeReference.class))).thenReturn(expected);

        List<AuthorResponse> result = authorClient.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Gabriel", result.get(0).name());
    }

    @Test
    void authorClient_findById_shouldReturnAuthor() {
        // *** Este test prueba la búsqueda de un autor por su ID instanciando manualmente el cliente
        AuthorClient authorClient = new AuthorClient(restClient);
        ReflectionTestUtils.setField(authorClient, "authorServiceUrl", "http://localhost:8083");

        RestClient.RequestHeadersUriSpec getSpec = mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
        
        when(restClient.get()).thenReturn(getSpec);
        when(getSpec.uri(anyString())).thenReturn(getSpec);
        when(getSpec.retrieve()).thenReturn(responseSpec);
        
        AuthorResponse expected = new AuthorResponse(1L, "Gabriel", "Col", 1927);
        when(responseSpec.body(AuthorResponse.class)).thenReturn(expected);

        AuthorResponse result = authorClient.findById(1L);

        assertNotNull(result);
        assertEquals("Gabriel", result.name());
    }

    @Test
    void authorClient_create_shouldReturnAuthor() {
        // *** Este test prueba la creación de un nuevo autor instanciando manualmente el cliente
        AuthorClient authorClient = new AuthorClient(restClient);
        ReflectionTestUtils.setField(authorClient, "authorServiceUrl", "http://localhost:8083");

        RestClient.RequestBodyUriSpec postSpec = mock(RestClient.RequestBodyUriSpec.class);
        RestClient.RequestBodySpec bodySpec = mock(RestClient.RequestBodySpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
        
        when(restClient.post()).thenReturn(postSpec);
        when(postSpec.uri(anyString())).thenReturn(postSpec);
        when(postSpec.contentType(any(MediaType.class))).thenReturn(postSpec);
        when(postSpec.body(any(AuthorRequest.class))).thenReturn(bodySpec);
        when(bodySpec.retrieve()).thenReturn(responseSpec);
        
        AuthorResponse expected = new AuthorResponse(1L, "Gabriel", "Col", 1927);
        when(responseSpec.body(AuthorResponse.class)).thenReturn(expected);

        AuthorResponse result = authorClient.create(new AuthorRequest("Gabriel", "Col", 1927));

        assertNotNull(result);
        assertEquals("Gabriel", result.name());
    }

    @Test
    void authorClient_update_shouldReturnAuthor() {
        // *** Este test prueba la actualización de un autor instanciando manualmente el cliente
        AuthorClient authorClient = new AuthorClient(restClient);
        ReflectionTestUtils.setField(authorClient, "authorServiceUrl", "http://localhost:8083");

        RestClient.RequestBodyUriSpec putSpec = mock(RestClient.RequestBodyUriSpec.class);
        RestClient.RequestBodySpec bodySpec = mock(RestClient.RequestBodySpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
        
        when(restClient.put()).thenReturn(putSpec);
        when(putSpec.uri(anyString())).thenReturn(putSpec);
        when(putSpec.contentType(any(MediaType.class))).thenReturn(putSpec);
        when(putSpec.body(any(AuthorRequest.class))).thenReturn(bodySpec);
        when(bodySpec.retrieve()).thenReturn(responseSpec);
        
        AuthorResponse expected = new AuthorResponse(1L, "Gabriel Updated", "Col", 1927);
        when(responseSpec.body(AuthorResponse.class)).thenReturn(expected);

        AuthorResponse result = authorClient.update(1L, new AuthorRequest("Gabriel Updated", "Col", 1927));

        assertNotNull(result);
        assertEquals("Gabriel Updated", result.name());
    }

    @Test
    void authorClient_delete_shouldInvokeEndpoint() {
        // *** Este test prueba la eliminación de un autor por ID instanciando manualmente el cliente
        AuthorClient authorClient = new AuthorClient(restClient);
        ReflectionTestUtils.setField(authorClient, "authorServiceUrl", "http://localhost:8083");

        RestClient.RequestHeadersUriSpec deleteSpec = mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
        
        when(restClient.delete()).thenReturn(deleteSpec);
        when(deleteSpec.uri(anyString())).thenReturn(deleteSpec);
        when(deleteSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toBodilessEntity()).thenReturn(ResponseEntity.noContent().build());

        authorClient.delete(1L);

        verify(responseSpec).toBodilessEntity();
    }

    @Test
    void bookClient_findById_shouldReturnBook() {
        // *** Este test prueba la obtención de un libro por su ID único instanciando manualmente el cliente
        BookClient bookClient = new BookClient(restClient);
        ReflectionTestUtils.setField(bookClient, "bookServiceUrl", "http://localhost:8082");

        RestClient.RequestHeadersUriSpec getSpec = mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
        
        when(restClient.get()).thenReturn(getSpec);
        when(getSpec.uri(anyString())).thenReturn(getSpec);
        when(getSpec.retrieve()).thenReturn(responseSpec);
        
        UUID uuid = UUID.randomUUID();
        BookResponse expected = new BookResponse(uuid, "Title", 1L, "Category", "123-456");
        when(responseSpec.body(BookResponse.class)).thenReturn(expected);

        BookResponse result = bookClient.findById(uuid);

        assertNotNull(result);
        assertEquals("Title", result.title());
    }

    @Test
    void bookClient_findAll_shouldReturnList() {
        // *** Este test prueba el listado de libros con filtros instanciando manualmente el cliente
        BookClient bookClient = new BookClient(restClient);
        ReflectionTestUtils.setField(bookClient, "bookServiceUrl", "http://localhost:8082");

        RestClient.RequestHeadersUriSpec getSpec = mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
        
        when(restClient.get()).thenReturn(getSpec);
        when(getSpec.uri(anyString())).thenReturn(getSpec);
        when(getSpec.retrieve()).thenReturn(responseSpec);
        
        UUID uuid = UUID.randomUUID();
        List<BookResponse> expected = List.of(new BookResponse(uuid, "Title", 1L, "Category", "123-456"));
        when(responseSpec.body(any(ParameterizedTypeReference.class))).thenReturn(expected);

        List<BookResponse> result = bookClient.findAll("Title", "Category");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).title());
    }

    @Test
    void bookClient_findAll_onlyCategory_shouldReturnList() {
        // *** Este test prueba la consulta de libros filtrando únicamente por categoría instanciando manualmente el cliente
        BookClient bookClient = new BookClient(restClient);
        ReflectionTestUtils.setField(bookClient, "bookServiceUrl", "http://localhost:8082");

        RestClient.RequestHeadersUriSpec getSpec = mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
        
        when(restClient.get()).thenReturn(getSpec);
        when(getSpec.uri(anyString())).thenReturn(getSpec);
        when(getSpec.retrieve()).thenReturn(responseSpec);
        
        UUID uuid = UUID.randomUUID();
        List<BookResponse> expected = List.of(new BookResponse(uuid, "Title", 1L, "Category", "123-456"));
        when(responseSpec.body(any(ParameterizedTypeReference.class))).thenReturn(expected);

        List<BookResponse> result = bookClient.findAll(null, "Category");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).title());
    }

    @Test
    void bookClient_create_shouldReturnBook() {
        // *** Este test prueba la creación de un nuevo libro instanciando manualmente el cliente
        BookClient bookClient = new BookClient(restClient);
        ReflectionTestUtils.setField(bookClient, "bookServiceUrl", "http://localhost:8082");

        RestClient.RequestBodyUriSpec postSpec = mock(RestClient.RequestBodyUriSpec.class);
        RestClient.RequestBodySpec bodySpec = mock(RestClient.RequestBodySpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
        
        when(restClient.post()).thenReturn(postSpec);
        when(postSpec.uri(anyString())).thenReturn(postSpec);
        when(postSpec.contentType(any(MediaType.class))).thenReturn(postSpec);
        when(postSpec.body(any(BookRequest.class))).thenReturn(bodySpec);
        when(bodySpec.retrieve()).thenReturn(responseSpec);
        
        UUID uuid = UUID.randomUUID();
        BookResponse expected = new BookResponse(uuid, "Title", 1L, "Category", "123-456");
        when(responseSpec.body(BookResponse.class)).thenReturn(expected);

        BookResponse result = bookClient.create(new BookRequest("Title", 1L, "Category", "123-456"));

        assertNotNull(result);
        assertEquals("Title", result.title());
    }

    @Test
    void bookClient_update_shouldReturnBook() {
        // *** Este test prueba la actualización de un libro existente instanciando manualmente el cliente
        BookClient bookClient = new BookClient(restClient);
        ReflectionTestUtils.setField(bookClient, "bookServiceUrl", "http://localhost:8082");

        RestClient.RequestBodyUriSpec putSpec = mock(RestClient.RequestBodyUriSpec.class);
        RestClient.RequestBodySpec bodySpec = mock(RestClient.RequestBodySpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
        
        when(restClient.put()).thenReturn(putSpec);
        when(putSpec.uri(anyString())).thenReturn(putSpec);
        when(putSpec.contentType(any(MediaType.class))).thenReturn(putSpec);
        when(putSpec.body(any(BookRequest.class))).thenReturn(bodySpec);
        when(bodySpec.retrieve()).thenReturn(responseSpec);
        
        UUID uuid = UUID.randomUUID();
        BookResponse expected = new BookResponse(uuid, "Title Updated", 1L, "Category", "123-456");
        when(responseSpec.body(BookResponse.class)).thenReturn(expected);

        BookResponse result = bookClient.update(uuid, new BookRequest("Title Updated", 1L, "Category", "123-456"));

        assertNotNull(result);
        assertEquals("Title Updated", result.title());
    }

    @Test
    void bookClient_delete_shouldInvokeEndpoint() {
        // *** Este test prueba la eliminación de un libro por su ID único instanciando manualmente el cliente
        BookClient bookClient = new BookClient(restClient);
        ReflectionTestUtils.setField(bookClient, "bookServiceUrl", "http://localhost:8082");

        RestClient.RequestHeadersUriSpec deleteSpec = mock(RestClient.RequestHeadersUriSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
        
        when(restClient.delete()).thenReturn(deleteSpec);
        when(deleteSpec.uri(anyString())).thenReturn(deleteSpec);
        when(deleteSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toBodilessEntity()).thenReturn(ResponseEntity.noContent().build());

        UUID uuid = UUID.randomUUID();
        bookClient.delete(uuid);

        verify(responseSpec).toBodilessEntity();
    }
}
