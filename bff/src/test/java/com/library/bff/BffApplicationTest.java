package com.library.bff;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

/**
 * Pruebas unitarias para la clase principal de la aplicación BFF.
 */
class BffApplicationTest {

    /**
     * Valida que el método main invoque correctamente a SpringApplication.run.
     * Utiliza mockStatic de Mockito para interceptar y verificar la ejecución
     * sin necesidad de levantar todo el contexto de Spring.
     */
    @Test
    void main_shouldInvokeSpringApplicationRun() {
        try (var mocked = Mockito.mockStatic(SpringApplication.class)) {
            mocked.when(() -> SpringApplication.run(BffApplication.class, new String[]{}))
                  .thenReturn(null);

            BffApplication.main(new String[]{});

            mocked.verify(() -> SpringApplication.run(BffApplication.class, new String[]{}));
        }
        
        // Cubre el constructor por defecto de la clase de aplicación
        BffApplication app = new BffApplication();
        assertNotNull(app);
    }
}
