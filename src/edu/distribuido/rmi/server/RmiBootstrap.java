package edu.distribuido.rmi.server;

import edu.distribuido.rmi.common.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiBootstrap {
    public static void main(String[] args) throws Exception {
        // Usa la IP externa de tu host si estás dentro de contenedor o Minikube
        System.setProperty("java.rmi.server.hostname", "192.168.193.238");

        // Crea el registro RMI en el puerto 1099
        Registry registry = LocateRegistry.createRegistry(1099);

        // Registra los servicios con nombres simples
        registry.rebind("LoginService", new LoginServiceImpl());
        registry.rebind("Inscripciones", new InscripcionServiceImpl());
        registry.rebind("NotificacionService", new NotificacionServiceImpl());

        System.out.println("✅ Servicios RMI registrados en puerto 1099");
    }
}
