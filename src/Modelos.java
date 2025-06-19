/**
 * Clase contenedora de modelos de datos para el sistema de gestión escolar.
 * Define las estructuras de datos utilizadas en el sistema.
 * 
 * @author Sistema de Gestión Escolar
 * @version 1.0
 */
public class Modelos {
    /**
     * Clase que representa a un alumno en el sistema.
     */
    public static class Alumno {
        private int idAlumno;
        private String nombre;
        private String apellidoPaterno;
        private String apellidoMaterno;

        /**
         * Constructor completo para la clase Alumno
         * 
         * @param idAlumno        Identificador único del alumno
         * @param nombre          Nombre del alumno
         * @param apellidoPaterno Apellido paterno del alumno
         * @param apellidoMaterno Apellido materno del alumno
         */
        public Alumno(int idAlumno, String nombre, String apellidoPaterno, String apellidoMaterno) {
            this.idAlumno = idAlumno;
            this.nombre = nombre;
            this.apellidoPaterno = apellidoPaterno;
            this.apellidoMaterno = apellidoMaterno;
        }

        // Getters
        public int getIdAlumno() {
            return idAlumno;
        }

        public String getNombre() {
            return nombre;
        }

        public String getApellidoPaterno() {
            return apellidoPaterno;
        }

        public String getApellidoMaterno() {
            return apellidoMaterno;
        }

        /**
         * Obtiene el nombre completo del alumno formateado
         * 
         */
        public String getNombreCompleto() {
            return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
        }
    }

    /**
     * Clase que representa una asignatura en el sistema.
     */
    public static class Asignatura {
        private int idAsignatura;
        private String descripcion;

        /**
         * Constructor completo para la clase Asignatura
         * 
         * @param idAsignatura Identificador único de la asignatura
         * @param descripcion  Nombre o descripción de la asignatura
         */
        public Asignatura(int idAsignatura, String descripcion) {
            this.idAsignatura = idAsignatura;
            this.descripcion = descripcion;
        }

        // Getters
        public int getIdAsignatura() {
            return idAsignatura;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }

    /**
     * Clase que representa una calificación de alumno en una asignatura
     */
    public static class Calificacion {
        private int idAlumnoAsignatura;
        private int unidad;
        private int calificacion;
        private int idAsignatura;
        private int idAlumno;

        /**
         * Constructor completo para la clase Calificacion
         * 
         * @param idAlumnoAsignatura Identificador único de la relación
         *                           alumno-asignatura
         * @param unidad             Número de unidad (1, 2 o 3)
         * @param calificacion       Valor numérico de la calificación
         * @param idAsignatura       Identificador de la asignatura
         * @param idAlumno           Identificador del alumno
         */
        public Calificacion(int idAlumnoAsignatura, int unidad, int calificacion, int idAsignatura, int idAlumno) {
            this.idAlumnoAsignatura = idAlumnoAsignatura;
            this.unidad = unidad;
            this.calificacion = calificacion;
            this.idAsignatura = idAsignatura;
            this.idAlumno = idAlumno;
        }

        // Getters
        public int getIdAlumnoAsignatura() {
            return idAlumnoAsignatura;
        }

        public int getUnidad() {
            return unidad;
        }

        public int getCalificacion() {
            return calificacion;
        }

        public int getIdAsignatura() {
            return idAsignatura;
        }

        public int getIdAlumno() {
            return idAlumno;
        }
    }

    /**
     * Clase que representa los indicadores de rendimiento de un alumno
     */
    public static class IndicadorRendimiento {
        private int idIndicadorRendimiento;
        private int cantidadParciales;
        private int cantidadSemestrales;
        private int idAlumno;

        /**
         * Constructor completo para la clase IndicadorRendimiento
         * 
         * @param idIndicadorRendimiento Identificador único del indicador
         * @param cantidadParciales      Número de exámenes parciales a presentar
         * @param cantidadSemestrales    Número de exámenes semestrales a presentar
         * @param idAlumno               Identificador del alumno
         */
        public IndicadorRendimiento(int idIndicadorRendimiento, int cantidadParciales, int cantidadSemestrales,
                int idAlumno) {
            this.idIndicadorRendimiento = idIndicadorRendimiento;
            this.cantidadParciales = cantidadParciales;
            this.cantidadSemestrales = cantidadSemestrales;
            this.idAlumno = idAlumno;
        }

        // Getters
        public int getIdIndicadorRendimiento() {
            return idIndicadorRendimiento;
        }

        public int getCantidadParciales() {
            return cantidadParciales;
        }

        public int getCantidadSemestrales() {
            return cantidadSemestrales;
        }

        public int getIdAlumno() {
            return idAlumno;
        }
    }

    /**
     * Clase que representa los ingresos por exámenes de un alumno
     */
    public static class Ingreso {
        private int idIngreso;
        private float costoParciales;
        private float costoSemestrales;
        private float costoTotal;
        private int idAlumno;

        /**
         * Constructor completo para la clase Ingreso
         * 
         * @param idIngreso        Identificador único del ingreso
         * @param costoParciales   Costo total por exámenes parciales
         * @param costoSemestrales Costo total por exámenes semestrales
         * @param costoTotal       Costo total (suma de parciales y semestrales)
         * @param idAlumno         Identificador del alumno
         */
        public Ingreso(int idIngreso, float costoParciales, float costoSemestrales, float costoTotal, int idAlumno) {
            this.idIngreso = idIngreso;
            this.costoParciales = costoParciales;
            this.costoSemestrales = costoSemestrales;
            this.costoTotal = costoTotal;
            this.idAlumno = idAlumno;
        }

        // Getters
        public int getIdIngreso() {
            return idIngreso;
        }

        public float getCostoParciales() {
            return costoParciales;
        }

        public float getCostoSemestrales() {
            return costoSemestrales;
        }

        public float getCostoTotal() {
            return costoTotal;
        }

        public int getIdAlumno() {
            return idAlumno;
        }
    }

    /**
     * Clase que representa la competencia de un alumno en una asignatura
     */
    public static class Competencia {
        private int idCompetencia;
        private float promedio;
        private String acronimo;
        private int idAsignatura;
        private int idAlumno;

        /**
         * Constructor completo para la clase Competencia
         * 
         * @param idCompetencia Identificador único de la competencia
         * @param promedio      Promedio del alumno en la asignatura
         * @param acronimo      Acrónimo que representa el nivel de competencia
         * @param idAsignatura  Identificador de la asignatura
         * @param idAlumno      Identificador del alumno
         */
        public Competencia(int idCompetencia, float promedio, String acronimo, int idAsignatura, int idAlumno) {
            this.idCompetencia = idCompetencia;
            this.promedio = promedio;
            this.acronimo = acronimo;
            this.idAsignatura = idAsignatura;
            this.idAlumno = idAlumno;
        }

        // Getters
        public int getIdCompetencia() {
            return idCompetencia;
        }

        public float getPromedio() {
            return promedio;
        }

        public String getAcronimo() {
            return acronimo;
        }

        public int getIdAsignatura() {
            return idAsignatura;
        }

        public int getIdAlumno() {
            return idAlumno;
        }
    }
}