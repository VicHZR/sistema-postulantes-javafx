package dao;

import java.sql.*;
import model.Postulante;
import util.ConexionBD;

public class PostulanteDAO {

    public void insertar(Postulante p) throws Exception {
        Connection cn = ConexionBD.getConexion();
        CallableStatement cs =
            cn.prepareCall("CALL sp_insertar_postulante(?,?,?,?,?,?,?,?,?)");

        cs.setString(1, p.getNombre());
        cs.setString(2, p.getApellidoPaterno());
        cs.setString(3, p.getApellidoMaterno());
        cs.setString(4, p.getDni());
        cs.setString(5, p.getCorreo());
        cs.setString(6, p.getTelefonoFijo());
        cs.setString(7, p.getTelefonoCelular());
        cs.setString(8, p.getSexo());
        cs.setString(9, p.getUbigeo());

        cs.execute();
        cs.close();
        cn.close();
    }

    public void listar() throws Exception {
        Connection cn = ConexionBD.getConexion();
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(
            "SELECT id_postulante, nombre, apellido_paterno, dni FROM postulante"
        );

        while (rs.next()) {
            System.out.println(
                rs.getInt("id_postulante") + " - " +
                rs.getString("nombre") + " " +
                rs.getString("apellido_paterno") +
                " | DNI: " + rs.getString("dni")
            );
        }

        rs.close();
        st.close();
        cn.close();
    }

    public void actualizarCorreo(int id, String correo) throws Exception {
        Connection cn = ConexionBD.getConexion();
        CallableStatement cs =
            cn.prepareCall("CALL sp_actualizar_correo(?, ?)");

        cs.setInt(1, id);
        cs.setString(2, correo);
        cs.execute();

        cs.close();
        cn.close();
    }

    public void eliminar(int id) throws Exception {
        Connection cn = ConexionBD.getConexion();
        PreparedStatement ps =
            cn.prepareStatement("DELETE FROM postulante WHERE id_postulante = ?");
        ps.setInt(1, id);
        ps.executeUpdate();

        ps.close();
        cn.close();
    }
    
    public void buscarDistrito(String distrito) throws Exception {
        Connection cn = ConexionBD.getConexion();

        PreparedStatement ps = cn.prepareStatement(
            "SELECT ubigeo, distrito, provincia, departamento " +
            "FROM ubigeo " +
            "WHERE distrito ILIKE ?"
        );

        ps.setString(1, "%" + distrito + "%");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            System.out.println(
                "Ubigeo: " + rs.getString("ubigeo") + " | " +
                rs.getString("distrito") + " - " +
                rs.getString("provincia") + " - " +
                rs.getString("departamento")
            );
        }

        rs.close();
        ps.close();
        cn.close();
    }
    
    public void buscarPorUbigeo(String codigo) throws Exception {
        Connection cn = ConexionBD.getConexion();

        PreparedStatement ps = cn.prepareStatement(
            "SELECT ubigeo, distrito, provincia, departamento " +
            "FROM ubigeo " +
            "WHERE ubigeo LIKE ?"
        );

        // Permite 4 o 6 dígitos
        if (codigo.length() == 4) {
            ps.setString(1, codigo + "%");
        } else {
            ps.setString(1, codigo);
        }

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            System.out.println(
                "Ubigeo: " + rs.getString("ubigeo") + " | " +
                rs.getString("distrito") + " - " +
                rs.getString("provincia") + " - " +
                rs.getString("departamento")
            );
        }

        rs.close();
        ps.close();
        cn.close();
    }
    
    
}