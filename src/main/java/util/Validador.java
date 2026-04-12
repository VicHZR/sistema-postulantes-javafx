package util;

public class Validador {

    public static void textoObligatorio(String txt, String campo) throws Exception {
        if (txt == null || txt.length() < 3 || txt.length() > 60) {
            throw new Exception(campo + " debe tener entre 3 y 60 caracteres");
        }
    }

    public static void dni(String dni) throws Exception {
        if (!dni.matches("\\d{8}")) {
            throw new Exception("DNI debe tener exactamente 8 dígitos");
        }
    }

    public static void correo(String correo) throws Exception {
        if (!correo.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            throw new Exception("Correo inválido");
        }
    }

    public static void sexo(String sexo) throws Exception {
        if (!sexo.equalsIgnoreCase("M") && !sexo.equalsIgnoreCase("F")) {
            throw new Exception("Sexo solo puede ser M o F");
        }
    }
}
