package util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ValidadorTest {

    @Test
    void nombreCorrecto_noLanzaError() {
        assertDoesNotThrow(() -> {
            Validador.textoObligatorio("Carlos", "Nombre");
        });
    }

    @Test
    void nombreCorto_lanzaError() {
        Exception ex = assertThrows(Exception.class, () -> {
            Validador.textoObligatorio("Al", "Nombre");
        });
        assertEquals("Nombre debe tener entre 3 y 60 caracteres", ex.getMessage());
    }

    @Test
    void dniValido() {
        assertDoesNotThrow(() -> {
            Validador.dni("12345678");
        });
    }

    @Test
    void dniInvalido_lanzaError() {
        Exception ex = assertThrows(Exception.class, () -> {
            Validador.dni("1234");
        });
        assertEquals("DNI debe tener exactamente 8 dígitos", ex.getMessage());
    }

    @Test
    void correoValido() {
        assertDoesNotThrow(() -> {
            Validador.correo("test@gmail.com");
        });
    }

    @Test
    void correoInvalido_lanzaError() {
        Exception ex = assertThrows(Exception.class, () -> {
            Validador.correo("correo.com");
        });
        assertEquals("Correo inválido", ex.getMessage());
    }

    @Test
    void sexoValido() {
        assertDoesNotThrow(() -> {
            Validador.sexo("M");
            Validador.sexo("F");
        });
    }

    @Test
    void sexoInvalido_lanzaError() {
        Exception ex = assertThrows(Exception.class, () -> {
            Validador.sexo("X");
        });
        assertEquals("Sexo solo puede ser M o F", ex.getMessage());
    }
}