
package edu.distribuido.rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import edu.distribuido.rmi.common.dto.NotificacionDTO;

public interface NotificacionService extends Remote {
    void enviarSMS(NotificacionDTO n) throws RemoteException;
    void enviarCorreo(NotificacionDTO n) throws RemoteException;
}
