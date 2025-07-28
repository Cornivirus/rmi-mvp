
package edu.distribuido.rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import edu.distribuido.rmi.common.dto.InscripcionDTO;

public interface InscripcionService extends Remote {
    boolean inscribirAlumno(InscripcionDTO inscripcion) throws RemoteException;
    List<InscripcionDTO> listarInscripciones(String matricula) throws RemoteException;

    List<InscripcionDTO> obtenerInscripciones(int idAlumno) throws RemoteException;
    boolean inscribirAlumno(int idAlumno, int idMateria) throws RemoteException;
}
