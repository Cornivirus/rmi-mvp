
package edu.distribuido.rmi.common.dto;
import java.io.Serializable;

public class AlumnoDTO implements Serializable {
    private int id;
    private String rol;

    private static final long serialVersionUID = 1L;
    private String matricula;
    private String nombre;

    public AlumnoDTO() {}

    public AlumnoDTO(String matricula, String nombre) {
        this.matricula = matricula;
        this.nombre = nombre;
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getId() {
        return id;
    }

    public String getRol() {
        return rol;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
