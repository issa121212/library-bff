package com.library.book;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class BookApplicationTest {

    @Test
    void main_shouldInvokeSpringApplicationRun() {
        try (var mocked = Mockito.mockStatic(SpringApplication.class)) {
            mocked.when(() -> SpringApplication.run(BookApplication.class, new String[]{}))
                  .thenReturn(null);

            BookApplication.main(new String[]{});

            mocked.verify(() -> SpringApplication.run(BookApplication.class, new String[]{}));
        }

        BookApplication app = new BookApplication();
        assertNotNull(app);
    }
}
