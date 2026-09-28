package escuela.dao;

import escuela.db.Conexion;
import escuela.modelo.Materia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Operaciones CRUD de la tabla "materia". */
public class MateriaDAO {

    private static final String COLUMNAS = "id_materia, nombre, ciclo, clave_de_materia, total_de_horas";

    /** Inserta la materia y le asigna el id generado por la base de datos. */
    public void insertar(Materia m) throws SQLException {
        String sql = "INSERT INTO materia (nombre, ciclo, clave_de_materia, total_de_horas) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getCiclo());
            ps.setString(3, m.getClave());
            ps.setInt(4, m.getTotalHoras());
            ps.executeUpdate();
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    m.setIdMateria(claves.getInt(1));
                }
            }
        }
    }

    public List<Materia> listar() throws SQLException {
        List<Materia> lista = new ArrayList<>();
        String sql = "SELECT " + COLUMNAS + " FROM materia ORDER BY ciclo, nombre";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(leer(rs));
            }
        }
        return lista;
    }

    /** Busca una materia. Si no existe devuelve Optional.empty() en lugar de null. */
    public Optional<Materia> obtenerPorId(int idMateria) throws SQLException {
        String sql = "SELECT " + COLUMNAS + " FROM materia WHERE id_materia = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMateria);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(leer(rs)) : Optional.empty();
            }
        }
    }

    /** Devuelve true si la materia existía y se actualizó. */
    public boolean actualizar(Materia m) throws SQLException {
        String sql = "UPDATE materia SET nombre = ?, ciclo = ?, clave_de_materia = ?, total_de_horas = ? "
                + "WHERE id_materia = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getCiclo());
            ps.setString(3, m.getClave());
            ps.setInt(4, m.getTotalHoras());
            ps.setInt(5, m.getIdMateria());
            return ps.executeUpdate() > 0;
        }
    }

    /** Devuelve true si la materia existía y se eliminó. */
    public boolean eliminar(int idMateria) throws SQLException {
        String sql = "DELETE FROM materia WHERE id_materia = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMateria);
            return ps.executeUpdate() > 0;
        }
    }

    private static Materia leer(ResultSet rs) throws SQLException {
        return new Materia(
                rs.getInt("id_materia"),
                rs.getString("nombre"),
                rs.getInt("ciclo"),
                rs.getString("clave_de_materia"),
                rs.getInt("total_de_horas"));
    }
}
