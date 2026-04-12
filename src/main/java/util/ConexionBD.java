package util;

import java.sql.*;

public class ConexionBD {

	private static final String URL =
		    "jdbc:postgresql://localhost:5433/postulantes_db";
		private static final String USER = "postgres";
		private static final String PASSWORD = "prueba";
		

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

/*75712404*/