package com.library.author;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class AuthorApplicationTest {

    @Test
    void main_shouldInvokeSpringApplicationRun() {
        try (var mocked = Mockito.mockStatic(SpringApplication.class)) {
            mocked.when(() -> SpringApplication.run(AuthorApplication.class, new String[]{}))
                  .thenReturn(null);

            AuthorApplication.main(new String[]{});

            mocked.verify(() -> SpringApplication.run(AuthorApplication.class, new String[]{}));
        }

        AuthorApplication app = new AuthorApplication();
        assertNotNull(app);
    }
}
