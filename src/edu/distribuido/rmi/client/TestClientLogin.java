package edu.distribuido.rmi.client;

import edu.distribuido.rmi.common.*;
import edu.distribuido.rmi.common.dto.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class TestClientLogin {
    public static void main(String[] args) throws Exception {
        String host = System.getenv().getOrDefault("RMI_HOST", "localhost");
        Registry registry = LocateRegistry.getRegistry(host, 1099);

        LoginService login = (LoginService) registry.lookup("LoginService");

        // Credenciales hardcodeadas (puedes reemplazar por entrada con Scanner si quieres)
        String username = "juan.perez@email.com";
        String password = "pass123";

        if (login.autenticar(username, password)) {
            System.out.println("✅ Login exitoso");
            String rol = login.obtenerRol(username);
            System.out.println("🔑 Rol: " + rol);

            if (!"alumno".equalsIgnoreCase(rol)) {
                System.out.println("🔒 No tienes acceso al módulo de inscripciones.");
                return;
            }

            InscripcionService inscripcionService = (InscripcionService) registry.lookup("Inscripciones");

            boolean continuar = true;

            while (continuar) {
                System.out.println("\n📋 Menú de Inscripción:");
                System.out.println("1. Ver inscripciones actuales");
                System.out.println("2. Inscribirme a una nueva materia");
                System.out.println("3. Salir");
                System.out.print("Elige una opción: ");
                String opcion = "1";

                switch (opcion) {
                    case "1":
                        List<InscripcionDTO> lista = inscripcionService.listarInscripciones(username);
                        if (lista.isEmpty()) {
                            System.out.println("📭 No tienes inscripciones actuales.");
                        } else {
                            System.out.println("📚 Inscripciones actuales:");
                            for (InscripcionDTO ins : lista) {
                                System.out.println(" - Materia: " + ins.getNombreMateria() + " | Grupo: " + ins.getIdGrupo());
                            }
                        }
                        break;

                    case "2":
                        System.out.print("Ingrese nombre de la materia: ");
                        String materia = "MATEMATICAS RMI";
                        System.out.print("Ingrese grupo: ");
                        String grupo = "605 A";

                        InscripcionDTO nueva = new InscripcionDTO(username, grupo);
                        nueva.setNombreMateria(materia); // Asegúrate que exista el setter
                        boolean ok = inscripcionService.inscribirAlumno(nueva);
                        System.out.println(ok ? "✅ Inscripción exitosa." : "❌ Ya estás inscrito en esa materia.");
                        break;

                    case "3":
                        continuar = false;
                        System.out.println("👋 Hasta luego.");
                        break;

                    default:
                        System.out.println("⚠️ Opción inválida.");
                        break;
                }
            }
        } else {
            System.out.println("❌ Credenciales incorrectas.");
        }

    }
}
