
package edu.distribuido.rmi.common.dto;
import java.io.Serializable;

public class InscripcionDTO implements Serializable {
    private int idMateria;
    private String nombreMateria;

    private static final long serialVersionUID = 1L;
    private String matricula;
    private String idGrupo;

    public InscripcionDTO() {}

    public InscripcionDTO(String matricula, String idGrupo) {
        this.matricula = matricula;
        this.idGrupo = idGrupo;
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getIdGrupo() { return idGrupo; }
    public void setIdGrupo(String idGrupo) { this.idGrupo = idGrupo; }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }
}
