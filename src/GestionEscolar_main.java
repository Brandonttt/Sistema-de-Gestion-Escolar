import java.sql.SQLException;

public class GestionEscolar_main {
    /**
     * Método principal que inicia la ejecución del sistema
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        Service service = new Service();

        try {
            System.out.println("=======================================================");
            System.out.println("====== SISTEMA DE GESTIÓN ESCOLAR UNIVERSITARIO =======");
            System.out.println("=====================Banregio==========================");

            // Crear procedimientos almacenados en la base de datos
            // System.out.println("\n[Iniciando] Creación de procedimientos
            // almacenados...");
            Procedimientos.crearProcedimientosAlmacenados();

            // 1. Calcular promedios por alumno y asignatura, y asignar competencias
            // System.out.println("\n[Proceso 1] Cálculo de promedios por alumno y
            // asignatura...");
            service.calcularPromediosPorAlumnoAsignatura();

            // 2. Calcular promedios por asignatura
            // System.out.println("\n[Proceso 2] Cálculo de promedios por asignatura...");
            service.calcularPromediosPorAsignatura();

            // 3. Calcular indicadores de rendimiento
            // System.out.println("\n[Proceso 3] Cálculo de indicadores de rendimiento...");
            service.calcularIndicadoresRendimiento();

            // 4. Calcular ingresos
            // System.out.println("\n[Proceso 4] Cálculo de ingresos...");
            service.calcularIngresos();

            // Mostrar reportes
            // System.out.println("\n[Generando reportes]");
            service.mostrarPromedioPorAsignatura();
            service.mostrarCompetenciasPorAlumno();
            service.mostrarTotalPagosPorAlumno();

            System.out.println("\n[Completado] Proceso finalizado con éxito.");

        } catch (SQLException e) {
            System.err.println("\n[ERROR] Error en el sistema: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("\n[Cerrando] Cerrando conexiones...");
            ConexionDB.cerrarConexion();
            System.out.println("=======================================================");
        }
    }
}