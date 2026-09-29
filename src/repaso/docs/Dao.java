package repaso.docs;

import java.util.List;
import java.util.Optional;

public interface Dao<T, K> {
    Optional<T> buscarPorId(K id);
    List<T> buscarTodos();
    void guardar(T entidad);
    void eliminar(K id);
}

// Y después, una implementación concreta:
// public class AlumnoDao implements Dao<Alumno, Integer> { ... }

