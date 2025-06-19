import java.sql.*;

/**
 * Clase para crear y gestionar los procedimientos almacenados en la base de
 * datos.
 */
public class Procedimientos {

    /**
     * Crea todos los procedimientos almacenados necesarios en la base de datos
     * 
     * @throws SQLException si ocurre un error al crear los procedimientos
     */
    public static void crearProcedimientosAlmacenados() throws SQLException {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = ConexionDB.obtenerConexion();
            stmt = conn.createStatement();

            // SP para calcular promedios por alumno y asignatura
            String spCalcularPromediosPorAlumnoAsignatura = "CREATE PROCEDURE IF NOT EXISTS sp_calcular_promedios_alumno_asignatura()\n"
                    +
                    "BEGIN\n" +
                    "    -- Elimina registros previos\n" +
                    "    DELETE FROM competencias;\n" +
                    "    \n" +
                    "    -- Inserta nuevos registros\n" +
                    "    INSERT INTO competencias (id_competencia, promedio, acronimo, id_asignatura, id_alumno)\n" +
                    "    SELECT\n" +
                    "        ROW_NUMBER() OVER () AS id_competencia,\n" +
                    "        ROUND(AVG(aa.calificacion), 1) AS promedio,\n" +
                    "        CASE\n" +
                    "            WHEN ROUND(AVG(aa.calificacion), 1) >= 95 THEN 'SA'\n" +
                    "            WHEN ROUND(AVG(aa.calificacion), 1) >= 85 THEN 'DE'\n" +
                    "            WHEN ROUND(AVG(aa.calificacion), 1) >= 76 THEN 'BU'\n" +
                    "            WHEN ROUND(AVG(aa.calificacion), 1) >= 70 THEN 'SU'\n" +
                    "            ELSE 'NA'\n" +
                    "        END AS acronimo,\n" +
                    "        aa.id_asignatura,\n" +
                    "        aa.id_alumno\n" +
                    "    FROM\n" +
                    "        alumnos_asignaturas aa\n" +
                    "    GROUP BY\n" +
                    "        aa.id_alumno, aa.id_asignatura;\n" +
                    "END";

            // SP para calcular promedios por asignatura
            String spCalcularPromediosPorAsignatura = "CREATE PROCEDURE IF NOT EXISTS sp_calcular_promedios_asignatura()\n"
                    +
                    "BEGIN\n" +
                    "    -- Elimina registros previos\n" +
                    "    DELETE FROM promedios_asignaturas;\n" +
                    "    \n" +
                    "    -- Inserta nuevos registros\n" +
                    "    INSERT INTO promedios_asignaturas (id_promedio_asignatura, promedio, id_asignatura)\n" +
                    "    SELECT\n" +
                    "        ROW_NUMBER() OVER () AS id_promedio_asignatura,\n" +
                    "        ROUND(AVG(aa.calificacion), 1) AS promedio,\n" +
                    "        aa.id_asignatura\n" +
                    "    FROM\n" +
                    "        alumnos_asignaturas aa\n" +
                    "    GROUP BY\n" +
                    "        aa.id_asignatura;\n" +
                    "END";

            // SP para calcular indicadores de rendimiento
            String spCalcularIndicadoresRendimiento = "CREATE PROCEDURE IF NOT EXISTS sp_calcular_indicadores_rendimiento()\n"
                    +
                    "BEGIN\n" +
                    "    -- Variables para almacenar datos temporales\n" +
                    "    DECLARE alumno_id INT;\n" +
                    "    DECLARE asignatura_id INT;\n" +
                    "    DECLARE promedio FLOAT;\n" +
                    "    DECLARE unidades_reprobadas INT;\n" +
                    "    DECLARE parciales_count INT DEFAULT 0;\n" +
                    "    DECLARE semestrales_count INT DEFAULT 0;\n" +
                    "    DECLARE done INT DEFAULT FALSE;\n" +
                    "    DECLARE contador INT DEFAULT 1;\n" +
                    "    \n" +
                    "    -- Cursores para iterar sobre alumnos y asignaturas\n" +
                    "    DECLARE cur_alumnos CURSOR FOR SELECT DISTINCT id_alumno FROM alumnos;\n" +
                    "    DECLARE cur_asignaturas CURSOR FOR SELECT DISTINCT id_asignatura FROM alumnos_asignaturas WHERE id_alumno = alumno_id;\n"
                    +
                    "    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;\n" +
                    "    \n" +
                    "    -- Elimina registros previos\n" +
                    "    DELETE FROM indicadores_rendimiento;\n" +
                    "    \n" +
                    "    -- Itera sobre cada alumno\n" +
                    "    OPEN cur_alumnos;\n" +
                    "    alumnos_loop: LOOP\n" +
                    "        FETCH cur_alumnos INTO alumno_id;\n" +
                    "        IF done THEN\n" +
                    "            LEAVE alumnos_loop;\n" +
                    "        END IF;\n" +
                    "        \n" +
                    "        SET parciales_count = 0;\n" +
                    "        SET semestrales_count = 0;\n" +
                    "        SET done = FALSE;\n" +
                    "        \n" +
                    "        -- Itera sobre las asignaturas del alumno\n" +
                    "        OPEN cur_asignaturas;\n" +
                    "        asignaturas_loop: LOOP\n" +
                    "            FETCH cur_asignaturas INTO asignatura_id;\n" +
                    "            IF done THEN\n" +
                    "                LEAVE asignaturas_loop;\n" +
                    "            END IF;\n" +
                    "            \n" +
                    "            -- Calcula el promedio de la asignatura para este alumno\n" +
                    "            SELECT ROUND(AVG(calificacion), 1) INTO promedio\n" +
                    "            FROM alumnos_asignaturas\n" +
                    "            WHERE id_alumno = alumno_id AND id_asignatura = asignatura_id;\n" +
                    "            \n" +
                    "            -- Verifica si el promedio es menor a 80\n" +
                    "            IF promedio < 80 THEN\n" +
                    "                SET semestrales_count = semestrales_count + 1;\n" +
                    "            ELSE\n" +
                    "                -- Verifica si tiene unidades reprobadas\n" +
                    "                SELECT COUNT(*) INTO unidades_reprobadas\n" +
                    "                FROM alumnos_asignaturas\n" +
                    "                WHERE id_alumno = alumno_id AND id_asignatura = asignatura_id AND calificacion < 80;\n"
                    +
                    "                \n" +
                    "                SET parciales_count = parciales_count + unidades_reprobadas;\n" +
                    "            END IF;\n" +
                    "        END LOOP asignaturas_loop;\n" +
                    "        CLOSE cur_asignaturas;\n" +
                    "        \n" +
                    "        -- Inserta los indicadores de rendimiento para este alumno\n" +
                    "        INSERT INTO indicadores_rendimiento (id_indicadores_rendimiento, cantidad_parciales, cantidad_semestrales, id_alumno)\n"
                    +
                    "        VALUES (contador, parciales_count, semestrales_count, alumno_id);\n" +
                    "        \n" +
                    "        SET contador = contador + 1;\n" +
                    "        SET done = FALSE;\n" +
                    "    END LOOP alumnos_loop;\n" +
                    "    CLOSE cur_alumnos;\n" +
                    "END";

            // SP para calcular ingresos - CORREGIDO
            String spCalcularIngresos = "CREATE PROCEDURE IF NOT EXISTS sp_calcular_ingresos()\n" +
                    "BEGIN\n" +
                    "    DECLARE costo_parcial FLOAT DEFAULT 100.0;\n" +
                    "    DECLARE costo_semestral FLOAT DEFAULT 350.0;\n" +
                    "    \n" +
                    "    -- Elimina registros previos\n" +
                    "    DELETE FROM ingresos;\n" +
                    "    \n" +
                    "    -- Inserta nuevos registros\n" +
                    "    INSERT INTO ingresos (id_ingresos, costo_total_parciales, cost_total_semestrales, id_alumno)\n"
                    +
                    "    SELECT\n" +
                    "        ROW_NUMBER() OVER () AS id_ingresos,\n" +
                    "        ir.cantidad_parciales * costo_parcial AS costo_total_parciales,\n" +
                    "        ir.cantidad_semestrales * costo_semestral AS cost_total_semestrales,\n" +
                    "        ir.id_alumno\n" +
                    "    FROM\n" +
                    "        indicadores_rendimiento ir;\n" +
                    "END";

            // Elimina procedimientos si ya existen
            stmt.execute("DROP PROCEDURE IF EXISTS sp_calcular_promedios_alumno_asignatura");
            stmt.execute("DROP PROCEDURE IF EXISTS sp_calcular_promedios_asignatura");
            stmt.execute("DROP PROCEDURE IF EXISTS sp_calcular_indicadores_rendimiento");
            stmt.execute("DROP PROCEDURE IF EXISTS sp_calcular_ingresos");

            // Crea los procedimientos
            stmt.execute(spCalcularPromediosPorAlumnoAsignatura);
            stmt.execute(spCalcularPromediosPorAsignatura);
            stmt.execute(spCalcularIndicadoresRendimiento);
            stmt.execute(spCalcularIngresos);

            System.out.println("Procedimientos almacenados creados correctamente");

        } catch (SQLException e) {
            System.err.println("Error al crear procedimientos almacenados: " + e.getMessage());
            throw e;
        } finally {
            ConexionDB.cerrarRecursos(stmt);
        }
    }
}