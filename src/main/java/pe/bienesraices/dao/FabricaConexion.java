package pe.bienesraices.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Abstraccion para obtener conexiones JDBC. Los DAO dependen de esta interfaz y
 * no de un driver concreto (principio de inversion de dependencias), lo que
 * permite usar MySQL en produccion y H2 en las pruebas.
 */
@FunctionalInterface
public interface FabricaConexion {

    Connection obtener() throws SQLException;
}
