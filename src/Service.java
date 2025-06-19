import java.sql.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Servicio que implementa la lógica de negocio para el sistema de gestión
 * escolar.
 * Esta clase se encarga de calcular promedios, indicadores de rendimiento,
 * ingresos
 * y competencias según los requisitos establecidos.
 * 
 * @author Sistema de Gestión Escolar
 * @version 1.0
 */
public class Service {

    // Constantes del negocio
    private static final int CALIFICACION_MINIMA_APROBATORIA = 80;
    private static final float COSTO_EXAMEN_SEMESTRAL = 400.0f;
    private static final float COSTO_EXAMEN_PARCIAL = 150.0f;

    /**
     * Calcula y almacena los promedios de cada alumno por asignatura
     * utilizando procedimientos almacenados para mayor eficiencia.
     * 
     * @throws SQLException si ocurre un error en la base de datos
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
     * Calcula y almacena los promedios por asignatura
     * utilizando procedimientos almacenados para mayor eficiencia.
     * 
     * @throws SQLException si ocurre un error en la base de datos
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
     * Calcula y almacena los indicadores de rendimiento para cada alumno
     * utilizando procedimientos almacenados para mayor eficiencia.
     * 
     * @throws SQLException si ocurre un error en la base de datos
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
     * Calcula y almacena los ingresos para cada alumno
     * utilizando procedimientos almacenados para mayor eficiencia.
     * 
     * @throws SQLException si ocurre un error en la base de datos
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
     * 
     * @throws SQLException si ocurre un error en la base de datos
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

            System.out.println("\n===== PROMEDIO POR ASIGNATURA =====");
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
            ConexionDB.cerrarRecursos(rs, stmt);
        }
    }

    /**
     * Muestra los alumnos con sus calificaciones y competencias ordenados por
     * apellido paterno
     * 
     * @throws SQLException si ocurre un error en la base de datos
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
                            "as.descripcion as asignatura, c.promedio, c.acronimo " +
                            "FROM competencias c " +
                            "JOIN alumnos a ON c.id_alumno = a.id_alumno " +
                            "JOIN asignaturas as ON c.id_asignatura = as.id_asignatura " +
                            "ORDER BY a.apellido_paterno, a.apellido_materno, a.nombre, as.descripcion");

            System.out.println("\n===== COMPETENCIAS POR ALUMNO =====");
            System.out.printf("%-40s | %-30s | %-8s | %s\n",
                    "NOMBRE COMPLETO", "ASIGNATURA", "PROMEDIO", "COMPETENCIA");
            System.out.println("----------------------------------------------------------------------");

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
            ConexionDB.cerrarRecursos(rs, stmt);
        }
    }

    /**
     * Muestra el total a pagar por cada alumno
     * 
     * @throws SQLException si ocurre un error en la base de datos
     */
    public void mostrarTotalPagosPorAlumno() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionDB.obtenerConexion();
            stmt = conn.createStatement();

            rs = stmt.executeQuery(
                    "SELECT a.nombre, a.apellido_paterno, a.apellido_materno, " +
                            "i.costo_parciales, i.costo_semestrales, i.costo_total " +
                            "FROM ingresos i " +
                            "JOIN alumnos a ON i.id_alumno = a.id_alumno " +
                            "ORDER BY a.apellido_paterno, a.apellido_materno, a.nombre");

            System.out.println("\n===== PAGOS POR ALUMNO =====");
            System.out.printf("%-40s | %-15s | %-15s | %s\n",
                    "NOMBRE COMPLETO", "PARCIALES", "SEMESTRALES", "TOTAL");
            System.out.println("-------------------------------------------------------------------");

            float granTotalParciales = 0;
            float granTotalSemestrales = 0;
            float granTotal = 0;

            while (rs.next()) {
                String nombreCompleto = rs.getString("nombre") + " " +
                        rs.getString("apellido_paterno") + " " +
                        rs.getString("apellido_materno");
                float costoParciales = rs.getFloat("costo_parciales");
                float costoSemestrales = rs.getFloat("costo_semestrales");
                float total = rs.getFloat("costo_total");

                granTotalParciales += costoParciales;
                granTotalSemestrales += costoSemestrales;
                granTotal += total;

                System.out.printf("%-40s | $%-14.2f | $%-14.2f | $%.2f\n",
                        nombreCompleto, costoParciales, costoSemestrales, total);
            }

            System.out.println("-------------------------------------------------------------------");
            System.out.printf("%-40s | $%-14.2f | $%-14.2f | $%.2f\n",
                    "TOTAL", granTotalParciales, granTotalSemestrales, granTotal);

        } catch (SQLException e) {
            System.err.println("Error al mostrar pagos por alumno: " + e.getMessage());
            throw e;
        } finally {
            ConexionDB.cerrarRecursos(rs, stmt);
        }
    }
}