import java.sql.*;

/**
 * Clase que gestiona la conexión a la base de datos del sistema de gestión
 * escolar.
 * Implementa el patrón Singleton para asegurar una única instancia de conexión.
 * 
 * @author Sistema de Gestión Escolar
 * @version 1.0
 */
public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_escolar";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    private static Connection conexion = null;

    /**
     * Constructor privado para evitar instanciación directa (patrón Singleton)
     */
    private ConexionDB() {
    }

    /**
     * Establece una conexión con la base de datos si no existe previamente
     * 
     * @return Connection objeto de conexión a la base de datos
     * @throws SQLException si ocurre un error al conectar
     */
    public static Connection obtenerConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                // Carga del driver JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establecimiento de la conexión
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                System.out.println("Conexión establecida con éxito");
            } catch (ClassNotFoundException e) {
                System.err.println("Error al cargar el driver JDBC: " + e.getMessage());
                throw new SQLException("Driver JDBC no encontrado", e);
            } catch (SQLException e) {
                System.err.println("Error al conectar con la base de datos: " + e.getMessage());
                throw e;
            }
        }
        return conexion;
    }

    /**
     * Cierra la conexión con la base de datos de forma segura
     */
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                    System.out.println("Conexión cerrada exitosamente");
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            } finally {
                conexion = null;
            }
        }
    }

    /**
     * Cierra recursos de base de datos (PreparedStatement, Statement, ResultSet)
     * 
     * @param recursos Recursos a cerrar (PreparedStatement, Statement, ResultSet)
     */
    public static void cerrarRecursos(AutoCloseable... recursos) {
        for (AutoCloseable recurso : recursos) {
            if (recurso != null) {
                try {
                    recurso.close();
                } catch (Exception e) {
                    System.err.println("Error al cerrar recurso: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Ejecuta un procedimiento almacenado que no devuelve resultados
     * 
     * @param procedimiento Nombre del procedimiento almacenado
     * @param parametros    Parámetros para el procedimiento almacenado (opcional)
     * @throws SQLException si ocurre un error al ejecutar el procedimiento
     */
    public static void ejecutarProcedimiento(String procedimiento, Object... parametros) throws SQLException {
        Connection conn = null;
        CallableStatement cs = null;

        try {
            conn = obtenerConexion();

            // Construir la llamada al procedimiento
            StringBuilder sql = new StringBuilder("{CALL " + procedimiento + "(");

            if (parametros.length > 0) {
                sql.append("?");
                for (int i = 1; i < parametros.length; i++) {
                    sql.append(",?");
                }
            }

            sql.append(")}");

            cs = conn.prepareCall(sql.toString());

            // Establecer los parámetros
            for (int i = 0; i < parametros.length; i++) {
                cs.setObject(i + 1, parametros[i]);
            }

            cs.execute();

        } catch (SQLException e) {
            System.err.println("Error al ejecutar procedimiento almacenado: " + e.getMessage());
            throw e;
        } finally {
            cerrarRecursos(cs);
        }
    }

    /**
     * Ejecuta un procedimiento almacenado que devuelve un ResultSet
     * 
     * @param procedimiento Nombre del procedimiento almacenado
     * @param parametros    Parámetros para el procedimiento almacenado (opcional)
     * @return ResultSet con los resultados del procedimiento
     * @throws SQLException si ocurre un error al ejecutar el procedimiento
     */
    public static ResultSet ejecutarProcedimientoConResultado(String procedimiento, Object... parametros)
            throws SQLException {
        Connection conn = null;
        CallableStatement cs = null;

        try {
            conn = obtenerConexion();

            // Construir la llamada al procedimiento
            StringBuilder sql = new StringBuilder("{CALL " + procedimiento + "(");

            if (parametros.length > 0) {
                sql.append("?");
                for (int i = 1; i < parametros.length; i++) {
                    sql.append(",?");
                }
            }

            sql.append(")}");

            cs = conn.prepareCall(sql.toString());

            // Establecer los parámetros
            for (int i = 0; i < parametros.length; i++) {
                cs.setObject(i + 1, parametros[i]);
            }

            return cs.executeQuery();

        } catch (SQLException e) {
            System.err.println("Error al ejecutar procedimiento almacenado: " + e.getMessage());
            throw e;
        }
    }
}