package com.docente.msauth;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class MsAuthApplicationTest {

    @Test
    void main_shouldInvokeSpringApplicationRun() {
        try (var mocked = Mockito.mockStatic(SpringApplication.class)) {
            mocked.when(() -> SpringApplication.run(MsAuthApplication.class, new String[]{}))
                  .thenReturn(null);

            MsAuthApplication.main(new String[]{});

            mocked.verify(() -> SpringApplication.run(MsAuthApplication.class, new String[]{}));
        }

        MsAuthApplication app = new MsAuthApplication();
        assertNotNull(app);
    }
}
