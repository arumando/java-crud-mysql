package escuela.modelo;

public class Materia {

    private int idMateria;
    private String nombre;
    private int ciclo;
    private String clave;
    private int totalHoras;

    public Materia() {
    }

    /** Para crear una materia nueva: el id lo asigna la base de datos. */
    public Materia(String nombre, int ciclo, String clave, int totalHoras) {
        this(0, nombre, ciclo, clave, totalHoras);
    }

    public Materia(int idMateria, String nombre, int ciclo, String clave, int totalHoras) {
        this.idMateria = idMateria;
        this.nombre = nombre;
        this.ciclo = ciclo;
        this.clave = clave;
        this.totalHoras = totalHoras;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(int totalHoras) {
        this.totalHoras = totalHoras;
    }

    @Override
    public String toString() {
        return String.format("%-4d %-30s ciclo %-3d %-10s %d h", idMateria, nombre, ciclo, clave, totalHoras);
    }
}
