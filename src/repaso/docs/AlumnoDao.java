package repaso.docs;

import java.sql.Connection;
import java.sql.SQLException;

public class AlumnoDao implements AutoCloseable {

    private final Connection conexion;

    public AlumnoDao(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void close() throws SQLException {
        conexion.close();
    }
}

