package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Postulante;
import util.ConexionBD;

public class PostulanteDAO {

    /**
     * Inserta un nuevo postulante usando el procedimiento almacenado (9 parámetros).
     */
    public void insertar(Postulante p) throws Exception {
        String sql = "CALL sp_insertar_postulante(?,?,?,?,?,?,?,?,?)"; 
        try (Connection cn = ConexionBD.getConexion();
             CallableStatement cs = cn.prepareCall(sql)) {
            cs.setString(1, p.getNombre());
            cs.setString(2, p.getApellidoPaterno());
            cs.setString(3, p.getApellidoMaterno());
            cs.setString(4, p.getDni());
            cs.setString(5, p.getCorreo());
            cs.setString(6, ""); // Teléfono fijo (parámetro vacío según SP)
            cs.setString(7, p.getTelefonoCelular());
            cs.setString(8, p.getSexo());
            cs.setString(9, p.getUbigeo());
            cs.execute();
        }
    }

    /**
     * Verifica si un DNI ya existe para evitar errores de duplicidad.
     */
    public boolean existeDni(String dni) throws Exception {
        String sql = "SELECT COUNT(*) FROM postulante WHERE dni = ?";
        try (Connection cn = ConexionBD.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    /**
     * Actualiza de forma integral los datos de un postulante.
     */
    public void actualizar(Postulante p) throws Exception {
        String sql = """
            UPDATE postulante SET
                nombre = ?,
                apellido_paterno = ?,
                apellido_materno = ?,
                correo = ?,
                telefono_celular = ?,
                sexo = ?,
                ubigeo = ?
            WHERE id_postulante = ?
        """;
        try (Connection cn = ConexionBD.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellidoPaterno());
            ps.setString(3, p.getApellidoMaterno());
            ps.setString(4, p.getCorreo());
            ps.setString(5, p.getTelefonoCelular());
            ps.setString(6, p.getSexo());
            ps.setString(7, p.getUbigeo());
            ps.setInt(8, p.getIdPostulante());
            ps.executeUpdate();
        }
    }

    /**
     * Actualiza específicamente el correo electrónico.
     */
    public void actualizarCorreo(int id, String correo) throws Exception {
        String sql = "CALL sp_actualizar_correo(?, ?)";
        try (Connection cn = ConexionBD.getConexion();
             CallableStatement cs = cn.prepareCall(sql)) {
            cs.setInt(1, id);
            cs.setString(2, correo);
            cs.execute();
        }
    }

    /**
     * Obtiene una lista de ubigeos válidos (Top 30).
     */
    public List<String> obtenerUbigeosValidos() throws Exception {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT ubigeo, distrito FROM ubigeo ORDER BY distrito LIMIT 30";
        try (Connection cn = ConexionBD.getConexion();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(rs.getString("ubigeo") + " - " + rs.getString("distrito"));
            }
        }
        return lista;
    }

    /**
     * Retorna la lista completa para el TableView.
     */
    public List<Postulante> listarTabla() throws Exception {
        List<Postulante> lista = new ArrayList<>();
        String sql = "SELECT id_postulante, nombre, apellido_paterno, apellido_materno, dni, correo, sexo, ubigeo FROM postulante ORDER BY id_postulante";
        try (Connection cn = ConexionBD.getConexion();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Postulante p = new Postulante();
                p.setIdPostulante(rs.getInt("id_postulante"));
                p.setNombre(rs.getString("nombre"));
                p.setApellidoPaterno(rs.getString("apellido_paterno"));
                p.setApellidoMaterno(rs.getString("apellido_materno"));
                p.setDni(rs.getString("dni"));
                p.setCorreo(rs.getString("correo"));
                p.setSexo(rs.getString("sexo"));
                p.setUbigeo(rs.getString("ubigeo"));
                lista.add(p);
            }
        }
        return lista;
    }

    /**
     * Elimina un registro permanentemente.
     */
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM postulante WHERE id_postulante = ?";
        try (Connection cn = ConexionBD.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /**
     * Lista nombres en consola para compatibilidad con Main.java.
     */
    public void listar() throws Exception {
        for (Postulante p : listarTabla()) {
            System.out.println(p.getIdPostulante() + " - " + p.getNombre() + " " + p.getApellidoPaterno());
        }
    }
}
