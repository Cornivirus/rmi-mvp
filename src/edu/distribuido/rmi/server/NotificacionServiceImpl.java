
package edu.distribuido.rmi.server;

import edu.distribuido.rmi.common.NotificacionService;
import edu.distribuido.rmi.common.dto.NotificacionDTO;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class NotificacionServiceImpl extends UnicastRemoteObject implements NotificacionService {
    private static final long serialVersionUID = 1L;

    public NotificacionServiceImpl() throws RemoteException { super(); }

    @Override
    public void enviarSMS(NotificacionDTO n) throws RemoteException {
        System.out.printf("[SMS] -> %s : %s%n", n.getDestino(), n.getCuerpo());
    }

    @Override
    public void enviarCorreo(NotificacionDTO n) throws RemoteException {
        System.out.printf("[MAIL] -> %s : (%s) %s%n", n.getDestino(), n.getAsunto(), n.getCuerpo());
    }
}
