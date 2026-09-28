package escuela.db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Abre conexiones a MySQL usando los datos de config/db.properties.
 *
 * Los datos de acceso no están escritos en el código: así la contraseña
 * no termina en GitHub y cada quien usa la de su propio servidor.
 */
public final class Conexion {

    private static final Path ARCHIVO_CONFIG = Path.of("config", "db.properties");

    private static Properties config;

    private Conexion() {
    }

    /**
     * Devuelve una conexión nueva. Nunca devuelve null: si algo falla
     * lanza SQLException con un mensaje que explica qué revisar.
     */
    public static Connection obtener() throws SQLException {
        Properties datos = cargarConfig();
        return DriverManager.getConnection(
                datos.getProperty("url"),
                datos.getProperty("usuario"),
                datos.getProperty("contrasena", ""));
    }

    private static synchronized Properties cargarConfig() throws SQLException {
        if (config != null) {
            return config;
        }
        if (!Files.exists(ARCHIVO_CONFIG)) {
            throw new SQLException("No se encontró " + ARCHIVO_CONFIG
                    + ". Copia config/db.properties.example como config/db.properties y escribe tus datos.");
        }
        Properties datos = new Properties();
        try (InputStream entrada = Files.newInputStream(ARCHIVO_CONFIG)) {
            datos.load(entrada);
        } catch (IOException e) {
            throw new SQLException("No se pudo leer " + ARCHIVO_CONFIG, e);
        }
        for (String clave : new String[] {"url", "usuario"}) {
            if (datos.getProperty(clave, "").isBlank()) {
                throw new SQLException("Falta la propiedad '" + clave + "' en " + ARCHIVO_CONFIG);
            }
        }
        config = datos;
        return config;
    }
}
