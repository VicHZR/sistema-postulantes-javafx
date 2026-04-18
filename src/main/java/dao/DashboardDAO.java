package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import util.ConexionBD;

public class DashboardDAO {

    public int totalPostulantes() throws Exception {
        String sql = "SELECT COUNT(*) FROM postulante";
        try (Connection cn = ConexionBD.getConexion();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public int totalPorSexo(String sexo) throws Exception {
        String sql = "SELECT COUNT(*) FROM postulante WHERE sexo = ?";
        try (Connection cn = ConexionBD.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, sexo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    public int totalUbigeosUsados() throws Exception {
        String sql = "SELECT COUNT(DISTINCT ubigeo) FROM postulante";
        try (Connection cn = ConexionBD.getConexion();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}
