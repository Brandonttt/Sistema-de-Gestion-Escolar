import java.sql.*;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_escolar";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    private static Connection conexion = null;

    /**
     * Establece una conexión con la base de datos ejecutar el .sql que se encuentra
     * en la carpeta
     */
    public static Connection obtenerConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                // Cargar el driver JDBC para MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");

                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                System.out.println("Conexión establecida con éxito");
            } catch (ClassNotFoundException e) {
                System.err.println("Error al cargar el driver: " + e.getMessage());
                throw new SQLException("Error al cargar el driver JDBC", e);
            } catch (SQLException e) {
                System.err.println("Error al conectar con la base de datos: " + e.getMessage());
                throw e;
            }
        }
        return conexion;
    }

    /**
     * Cierra la conexión con la base de datos
     */
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada exitosamente");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    /**
     * Cierra recursos de base de datos (ResultSet, Statement, PreparedStatement)
     * 
     * @param recursos Los recursos a cerrar
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
}
