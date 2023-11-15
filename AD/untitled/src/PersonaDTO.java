

import java.io.Serializable;

public class PersonaDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private String apellidos;
    private int puntos;


    public PersonaDTO(String nombre, String apellidos, int puntos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.puntos = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", puntos=" + puntos +
                '}';
    }
}
