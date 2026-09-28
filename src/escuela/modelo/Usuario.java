package escuela.modelo;

public class Usuario {

    private int id;
    private String nombre;
    private String email;

    public Usuario() {
    }

    /** Para crear un usuario nuevo: el id lo asigna la base de datos. */
    public Usuario(String nombre, String email) {
        this(0, nombre, email);
    }

    public Usuario(int id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("%-4d %-25s %s", id, nombre, email);
    }
}
