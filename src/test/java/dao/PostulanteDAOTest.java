package dao;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.Postulante;

public class PostulanteDAOTest {

    @Test
    void insertarPostulante_correcto() {
        PostulanteDAO dao = new PostulanteDAO();
        Postulante p = new Postulante();

        p.setNombre("Test");
        p.setApellidoPaterno("JUnit");
        p.setApellidoMaterno("Demo");
        p.setDni("99887766");          // Usa un DNI que no exista
        p.setCorreo("junit@test.com"); // Usa un correo que no exista
        p.setTelefonoCelular("987654321");
        p.setSexo("M");
        p.setUbigeo("150122"); // Ubigeo REAL existente

        assertDoesNotThrow(() -> {
            dao.insertar(p);
        });
    }
}