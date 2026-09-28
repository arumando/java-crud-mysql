package escuela.dao;

import escuela.db.Conexion;
import escuela.modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Operaciones CRUD de la tabla "usuario".
 *
 * Los métodos lanzan SQLException en lugar de ocultar el error, para que
 * quien los llame decida qué mostrarle al usuario.
 */
public class UsuarioDAO {

    /** Inserta el usuario y le asigna el id generado por la base de datos. */
    public void insertar(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuario (nombre, email) VALUES (?, ?)";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.executeUpdate();
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    u.setId(claves.getInt(1));
                }
            }
        }
    }

    public List<Usuario> listar() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, email FROM usuario ORDER BY id";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(leer(rs));
            }
        }
        return lista;
    }

    /** Busca un usuario. Si no existe devuelve Optional.empty() en lugar de null. */
    public Optional<Usuario> obtenerPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, email FROM usuario WHERE id = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(leer(rs)) : Optional.empty();
            }
        }
    }

    /** Devuelve true si el usuario existía y se actualizó. */
    public boolean actualizar(Usuario u) throws SQLException {
        String sql = "UPDATE usuario SET nombre = ?, email = ? WHERE id = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.setInt(3, u.getId());
            return ps.executeUpdate() > 0;
        }
    }

    /** Devuelve true si el usuario existía y se eliminó. */
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private static Usuario leer(ResultSet rs) throws SQLException {
        return new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"));
    }
}
