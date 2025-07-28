
package edu.distribuido.rmi.common.dto;
import java.io.Serializable;

public class NotificacionDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String destino;
    private String asunto;
    private String cuerpo;

    public NotificacionDTO() {}

    public NotificacionDTO(String destino, String asunto, String cuerpo) {
        this.destino = destino;
        this.asunto = asunto;
        this.cuerpo = cuerpo;
    }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getCuerpo() { return cuerpo; }
    public void setCuerpo(String cuerpo) { this.cuerpo = cuerpo; }
}
