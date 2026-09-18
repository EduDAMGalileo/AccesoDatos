package persistencia;

import java.util.List;
import java.util.Optional;

public interface AlumnoDao {
    Optional<Alumno> buscarPorId(int id);
    List<Alumno> buscarPorMunicipio(String municipio);
    void guardar(Alumno alumno);
    void eliminar(int id);
}
