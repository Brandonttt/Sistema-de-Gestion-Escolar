USE gestion_escolar;


--1.-
-- Consulta para mostrarPromedioPorAsignatura()
SELECT a.descripcion, pa.promedio 
FROM promedios_asignaturas pa 
JOIN asignaturas a ON pa.id_asignatura = a.id_asignatura 
ORDER BY a.descripcion;


-- Consulta para mostrarCompetenciasPorAlumno()
SELECT a.nombre, a.apellido_paterno, a.apellido_materno, 
       asig.descripcion as asignatura, c.promedio, c.acronimo 
FROM competencias c 
JOIN alumnos a ON c.id_alumno = a.id_alumno 
JOIN asignaturas asig ON c.id_asignatura = asig.id_asignatura 
ORDER BY a.apellido_paterno, a.apellido_materno, a.nombre, asig.descripcion;

-- Consulta para mostrarRendimientoPorAlumno()-- Consulta para mostrarTotalPagosPorAlumno()
SELECT a.nombre, a.apellido_paterno, a.apellido_materno, 
       ir.cantidad_parciales, ir.cantidad_semestrales, 
       i.costo_total_parciales, i.cost_total_semestrales, 
       (i.costo_total_parciales + i.cost_total_semestrales) as costo_total 
FROM ingresos i 
JOIN alumnos a ON i.id_alumno = a.id_alumno 
JOIN indicadores_rendimiento ir ON a.id_alumno = ir.id_alumno 
ORDER BY a.apellido_paterno, a.apellido_materno, a.nombre;