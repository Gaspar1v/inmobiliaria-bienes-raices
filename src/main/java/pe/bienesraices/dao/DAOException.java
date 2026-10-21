package pe.bienesraices.dao;

/** Error de acceso a datos. Envuelve la SQLException para no exponer detalles al usuario. */
public class DAOException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DAOException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public DAOException(String mensaje) {
        super(mensaje);
    }
}
