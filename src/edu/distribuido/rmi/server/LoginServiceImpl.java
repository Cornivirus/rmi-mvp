package edu.distribuido.rmi.server;

import edu.distribuido.rmi.common.LoginService;
import edu.distribuido.rmi.common.db.PostgresConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginServiceImpl extends UnicastRemoteObject implements LoginService {
    public LoginServiceImpl() throws RemoteException {}

    @Override
    public boolean autenticar(String correo, String contraseña) throws RemoteException {
        try (Connection conn = PostgresConnection.getConnection()) {
            System.out.println("Intentando autenticar a: " + correo);
            String sql = "SELECT * FROM Usuario WHERE correo = ? AND contraseña = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, correo);
            stmt.setString(2, contraseña);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Resultado autenticación: " + rs);
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String obtenerRol(String correo) throws RemoteException {
        try (Connection conn = PostgresConnection.getConnection()) {
            String sql = "SELECT rol FROM Usuario WHERE correo = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("rol");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
