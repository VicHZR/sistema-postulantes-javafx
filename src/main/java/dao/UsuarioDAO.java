package dao;

import java.sql.*;
import model.Usuario;
import util.ConexionBD;

public class UsuarioDAO {

    public Usuario login(String user, String pass) throws Exception {

        Connection cn = ConexionBD.getConexion();

        PreparedStatement ps = cn.prepareStatement(
            "SELECT * FROM usuario WHERE username = ? AND password = ?"
        );

        ps.setString(1, user);
        ps.setString(2, pass);

        ResultSet rs = ps.executeQuery();

        Usuario u = null;

        if (rs.next()) {
            u = new Usuario();
            u.setId(rs.getInt("id_usuario"));
            u.setUsername(rs.getString("username"));
            u.setRol(rs.getString("rol"));
        }

        rs.close();
        ps.close();
        cn.close();

        return u;
    }
}