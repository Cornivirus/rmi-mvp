package edu.distribuido.rmi.server;

import edu.distribuido.rmi.common.InscripcionService;
import edu.distribuido.rmi.common.dto.InscripcionDTO;
import edu.distribuido.rmi.common.db.PostgresConnection;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class InscripcionServiceImpl extends UnicastRemoteObject implements InscripcionService {
    private static final long serialVersionUID = 1L;
    private final Map<String, List<InscripcionDTO>> bd = new HashMap<>();

    public InscripcionServiceImpl() throws RemoteException { super(); }

    @Override
    public synchronized boolean inscribirAlumno(InscripcionDTO inscripcion) throws RemoteException {
        bd.computeIfAbsent(inscripcion.getMatricula(), k -> new ArrayList<>());
        List<InscripcionDTO> lista = bd.get(inscripcion.getMatricula());
        for (InscripcionDTO i : lista) {
            if (i.getIdGrupo().equals(inscripcion.getIdGrupo())) return false;
        }
        lista.add(inscripcion);
        return true;
    }

    @Override
    public List<InscripcionDTO> listarInscripciones(String matricula) throws RemoteException {
        return bd.getOrDefault(matricula, Collections.emptyList());
    }

    @Override
    public List<InscripcionDTO> obtenerInscripciones(int idAlumno) throws RemoteException {
        List<InscripcionDTO> lista = new ArrayList<>();
        try {
            Connection conn = PostgresConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT i.id_materia, m.nombre FROM inscripcion i JOIN materia m ON i.id_materia = m.id WHERE i.id_alumno = ?"
            );
            stmt.setInt(1, idAlumno);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                InscripcionDTO ins = new InscripcionDTO();
                ins.setIdMateria(rs.getInt("id_materia"));
                ins.setNombreMateria(rs.getString("nombre"));
                lista.add(ins);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public boolean inscribirAlumno(int idAlumno, int idMateria) throws RemoteException {
        try {
            Connection conn = PostgresConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO inscripcion (id_alumno, id_materia) VALUES (?, ?)"
            );
            stmt.setInt(1, idAlumno);
            stmt.setInt(2, idMateria);
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
