package edu.distribuido.rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginService extends Remote {
    boolean autenticar(String correo, String contraseña) throws RemoteException;
    String obtenerRol(String correo) throws RemoteException; // ← debe existir esta línea
}
