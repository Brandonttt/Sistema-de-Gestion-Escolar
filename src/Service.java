import java.sql.*;

/**
 * Servicio que implementa la lógica de negocio para el sistema de gestión
 * escolar.
 */
public class Service {

    // Constantes del negocio
    private static final int CALIFICACION_MINIMA_APROBATORIA = 80;
    private static final float COSTO_EXAMEN_SEMESTRAL = 350.0f;
    private static final float COSTO_EXAMEN_PARCIAL = 150.0f;

    /**
     * Calcula y almacena los promedios de cada alumno por asignatura utilizando
     * procedimientos almacenados p.
     * 
     */
    public void calcularPromediosPorAlumnoAsignatura() throws SQLException {
        try {
            // Ejecuta el procedimiento almacenado
            ConexionDB.ejecutarProcedimiento("sp_calcular_promedios_alumno_asignatura");
            System.out.println("Promedios por alumno y asignatura calculados correctamente");
        } catch (SQLException e) {
            System.err.println("Error al calcular promedios por alumno y asignatura: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Calculamos y almacenamos los promedios por cada asignatura utilizando
     * procedimientos almacenados .
     */
    public void calcularPromediosPorAsignatura() throws SQLException {
        try {
            // Ejecuta el procedimiento almacenado
            ConexionDB.ejecutarProcedimiento("sp_calcular_promedios_asignatura");
            System.out.println("Promedios por asignatura calculados correctamente");
        } catch (SQLException e) {
            System.err.println("Error al calcular promedios por asignatura: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Calcula y almacena los indicadores de rendimiento para cada alumno utilizando
     * procedimientos almacenados .
     * 
     */
    public void calcularIndicadoresRendimiento() throws SQLException {
        try {
            // Ejecuta el procedimiento almacenado
            ConexionDB.ejecutarProcedimiento("sp_calcular_indicadores_rendimiento");
            System.out.println("Indicadores de rendimiento calculados correctamente");
        } catch (SQLException e) {
            System.err.println("Error al calcular indicadores de rendimiento: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Calcula y almacena los ingresos para cada alumno utilizando procedimientos
     * almacenados para mayor eficiencia.
     */
    public void calcularIngresos() throws SQLException {
        try {
            // Ejecuta el procedimiento almacenado
            ConexionDB.ejecutarProcedimiento("sp_calcular_ingresos");
            System.out.println("Ingresos calculados correctamente");
        } catch (SQLException e) {
            System.err.println("Error al calcular ingresos: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Muestra el promedio de cada asignatura en consola
     */
    public void mostrarPromedioPorAsignatura() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionDB.obtenerConexion();
            stmt = conn.createStatement();

            rs = stmt.executeQuery(
                    "SELECT a.descripcion, pa.promedio " +
                            "FROM promedios_asignaturas pa " +
                            "JOIN asignaturas a ON pa.id_asignatura = a.id_asignatura " +
                            "ORDER BY a.descripcion");

            System.out.println("\n======== PROMEDIO POR CADA ASIGNATURA ========");
            System.out.printf("%-30s | %s\n", "ASIGNATURA", "PROMEDIO");
            System.out.println("----------------------------------------------");

            while (rs.next()) {
                String descripcion = rs.getString("descripcion");
                float promedio = rs.getFloat("promedio");

                System.out.printf("%-30s | %.1f\n", descripcion, promedio);
            }

        } catch (SQLException e) {
            System.err.println("Error al mostrar promedios por asignatura: " + e.getMessage());
            throw e;
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Muestra los alumnos con sus calificaciones y competencias ordenados por
     * apellido paterno
     * 
     */
    public void mostrarCompetenciasPorAlumno() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionDB.obtenerConexion();
            stmt = conn.createStatement();

            rs = stmt.executeQuery(
                    "SELECT a.nombre, a.apellido_paterno, a.apellido_materno, " +
                            "asig.descripcion as asignatura, c.promedio, c.acronimo " +
                            "FROM competencias c " +
                            "JOIN alumnos a ON c.id_alumno = a.id_alumno " +
                            "JOIN asignaturas asig ON c.id_asignatura = asig.id_asignatura " +
                            "ORDER BY a.apellido_paterno, a.apellido_materno, a.nombre, asig.descripcion");

            System.out.println("\n===== DESGLOSE GENERAL DE ALUMNOS =====");
            System.out.printf("%-40s | %-30s | %-8s | %s\n",
                    "NOMBRE COMPLETO", "ASIGNATURA", "PROMEDIO", "COMPETENCIA");
            System.out.println(
                    "------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                String nombreCompleto = rs.getString("nombre") + " " +
                        rs.getString("apellido_paterno") + " " +
                        rs.getString("apellido_materno");
                String asignatura = rs.getString("asignatura");
                float promedio = rs.getFloat("promedio");
                String acronimo = rs.getString("acronimo");

                System.out.printf("%-40s | %-30s | %-8.1f | %s\n",
                        nombreCompleto, asignatura, promedio, acronimo);
            }

        } catch (SQLException e) {
            System.err.println("Error al mostrar competencias por alumno: " + e.getMessage());
            throw e;
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * Muestra el total a pagar por cada alumno
     */
    public void mostrarTotalPagosPorAlumno() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionDB.obtenerConexion();
            stmt = conn.createStatement();

            // Consulta que obtiene la cantidad de exámenes
            rs = stmt.executeQuery(
                    "SELECT a.nombre, a.apellido_paterno, a.apellido_materno, " +
                            "ir.cantidad_parciales, ir.cantidad_semestrales, " +
                            "i.costo_total_parciales, i.cost_total_semestrales, " +
                            "(i.costo_total_parciales + i.cost_total_semestrales) as costo_total " +
                            "FROM ingresos i " +
                            "JOIN alumnos a ON i.id_alumno = a.id_alumno " +
                            "JOIN indicadores_rendimiento ir ON a.id_alumno = ir.id_alumno " +
                            "ORDER BY a.apellido_paterno, a.apellido_materno, a.nombre");

            // Definir los costos unitarios para mostrar en el encabezado
            int costoParcial = 100;
            int costoSemestral = 350;

            // Formato de tabla con costos unitarios en el encabezado
            System.out.println("\n===== PAGOS POR ALUMNO =====");
            System.out.println(
                    "--------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-30s | %-10s | %-15s | %-10s | %-15s | %-15s\n",
                    "ALUMNOS", "PARCIALES",
                    "COSTO PARCIAL ($" + costoParcial + ")",
                    "SEMESTRALES",
                    "COSTO SEMESTRAL ($" + costoSemestral + ")",
                    "TOTAL");
            System.out.println(
                    "---------------------------------------------------------------------------------------------------------------");

            float granTotalParciales = 0;
            float granTotalSemestrales = 0;
            float granTotal = 0;

            while (rs.next()) {
                String nombreCompleto = rs.getString("nombre") + " " +
                        rs.getString("apellido_paterno") + " " +
                        rs.getString("apellido_materno");
                int cantidadParciales = rs.getInt("cantidad_parciales");
                int cantidadSemestrales = rs.getInt("cantidad_semestrales");
                float costoParciales = rs.getFloat("costo_total_parciales");
                float costoSemestrales = rs.getFloat("cost_total_semestrales");
                float total = rs.getFloat("costo_total");

                granTotalParciales += costoParciales;
                granTotalSemestrales += costoSemestrales;
                granTotal += total;

                System.out.printf("%-30s | %-10d | $%-14.2f      | %-10d  | $%-14.2f        | $%.2f\n",
                        nombreCompleto, cantidadParciales, costoParciales,
                        cantidadSemestrales, costoSemestrales, total);
            }

            System.out.println(
                    "--------------------------------------------------------------------------------------------------------------------");
            // No mostramos totales de cantidades de exámenes, solo los costos
            // System.out.printf("%-30s | %-10s | $%-14.2f | %-10s | $%-14.2f | $%.2f\n",
            // "TOTAL", "", granTotalParciales, "", granTotalSemestrales, granTotal);

        } catch (SQLException e) {
            System.err.println("Error al mostrar pagos por alumno: " + e.getMessage());
            throw e;
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
    }
}