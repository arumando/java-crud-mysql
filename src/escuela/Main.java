package escuela;

import escuela.dao.MateriaDAO;
import escuela.dao.UsuarioDAO;
import escuela.modelo.Materia;
import escuela.modelo.Usuario;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/** Menú de consola para administrar usuarios y materias. */
public class Main {

    private static final Scanner ENTRADA = new Scanner(System.in);
    private static final UsuarioDAO USUARIOS = new UsuarioDAO();
    private static final MateriaDAO MATERIAS = new MateriaDAO();

    /** Operación sobre la base de datos que puede fallar. */
    private interface Operacion {
        void ejecutar() throws SQLException;
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("""

                    ===== ESCUELA =====
                     1. Listar usuarios      5. Listar materias
                     2. Agregar usuario      6. Agregar materia
                     3. Actualizar usuario   7. Actualizar materia
                     4. Eliminar usuario     8. Eliminar materia
                     0. Salir""");
            int opcion = leerEntero("Opción: ");
            switch (opcion) {
                case 1 -> ejecutar(Main::listarUsuarios);
                case 2 -> ejecutar(Main::agregarUsuario);
                case 3 -> ejecutar(Main::actualizarUsuario);
                case 4 -> ejecutar(Main::eliminarUsuario);
                case 5 -> ejecutar(Main::listarMaterias);
                case 6 -> ejecutar(Main::agregarMateria);
                case 7 -> ejecutar(Main::actualizarMateria);
                case 8 -> ejecutar(Main::eliminarMateria);
                case 0 -> {
                    System.out.println("¡Hasta luego!");
                    return;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    /** Ejecuta la operación y muestra un mensaje claro si la base de datos falla. */
    private static void ejecutar(Operacion operacion) {
        try {
            operacion.ejecutar();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("No se guardó: ya existe un registro con ese correo o clave.");
        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
        }
    }

    // ---------- Usuarios ----------

    private static void listarUsuarios() throws SQLException {
        imprimir(USUARIOS.listar(), "No hay usuarios registrados.");
    }

    private static void agregarUsuario() throws SQLException {
        Usuario u = new Usuario(leerTexto("Nombre: "), leerCorreo("Correo: "));
        USUARIOS.insertar(u);
        System.out.println("Usuario agregado con id " + u.getId() + ".");
    }

    private static void actualizarUsuario() throws SQLException {
        int id = leerEntero("Id del usuario: ");
        Optional<Usuario> encontrado = USUARIOS.obtenerPorId(id);
        if (encontrado.isEmpty()) {
            System.out.println("No existe un usuario con id " + id + ".");
            return;
        }
        Usuario u = encontrado.get();
        System.out.println("Actual: " + u);
        u.setNombre(leerTexto("Nuevo nombre: "));
        u.setEmail(leerCorreo("Nuevo correo: "));
        USUARIOS.actualizar(u);
        System.out.println("Usuario actualizado.");
    }

    private static void eliminarUsuario() throws SQLException {
        int id = leerEntero("Id del usuario: ");
        System.out.println(USUARIOS.eliminar(id)
                ? "Usuario eliminado."
                : "No existe un usuario con id " + id + ".");
    }

    // ---------- Materias ----------

    private static void listarMaterias() throws SQLException {
        imprimir(MATERIAS.listar(), "No hay materias registradas.");
    }

    private static void agregarMateria() throws SQLException {
        Materia m = new Materia(
                leerTexto("Nombre: "),
                leerPositivo("Ciclo: "),
                leerTexto("Clave: "),
                leerPositivo("Total de horas: "));
        MATERIAS.insertar(m);
        System.out.println("Materia agregada con id " + m.getIdMateria() + ".");
    }

    private static void actualizarMateria() throws SQLException {
        int id = leerEntero("Id de la materia: ");
        Optional<Materia> encontrada = MATERIAS.obtenerPorId(id);
        if (encontrada.isEmpty()) {
            System.out.println("No existe una materia con id " + id + ".");
            return;
        }
        Materia m = encontrada.get();
        System.out.println("Actual: " + m);
        m.setNombre(leerTexto("Nuevo nombre: "));
        m.setCiclo(leerPositivo("Nuevo ciclo: "));
        m.setClave(leerTexto("Nueva clave: "));
        m.setTotalHoras(leerPositivo("Nuevo total de horas: "));
        MATERIAS.actualizar(m);
        System.out.println("Materia actualizada.");
    }

    private static void eliminarMateria() throws SQLException {
        int id = leerEntero("Id de la materia: ");
        System.out.println(MATERIAS.eliminar(id)
                ? "Materia eliminada."
                : "No existe una materia con id " + id + ".");
    }

    // ---------- Lectura de datos ----------

    private static void imprimir(List<?> registros, String mensajeVacio) {
        if (registros.isEmpty()) {
            System.out.println(mensajeVacio);
        }
        registros.forEach(System.out::println);
    }

    private static String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (!ENTRADA.hasNextLine()) {
                System.out.println();
                System.exit(0); // se cerró la entrada (por ejemplo con Ctrl+Z / Ctrl+D)
            }
            String texto = ENTRADA.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("Este dato no puede quedar vacío.");
        }
    }

    private static String leerCorreo(String mensaje) {
        while (true) {
            String correo = leerTexto(mensaje);
            if (correo.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")) {
                return correo;
            }
            System.out.println("Escribe un correo válido, por ejemplo nombre@dominio.com");
        }
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Escribe un número entero.");
            }
        }
    }

    private static int leerPositivo(String mensaje) {
        while (true) {
            int numero = leerEntero(mensaje);
            if (numero > 0) {
                return numero;
            }
            System.out.println("El número debe ser mayor a 0.");
        }
    }
}
