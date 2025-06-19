CREATE DATABASE IF NOT EXISTS gestion_escolar;
USE gestion_escolar;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for alumnos
-- ----------------------------
DROP TABLE IF EXISTS `alumnos`;
CREATE TABLE `alumnos` (
  `id_alumno` int(11) NOT NULL,
  `nombre` varchar(90) DEFAULT NULL,
  `apellido_paterno` varchar(90) DEFAULT NULL,
  `apellido_materno` varchar(90) DEFAULT NULL,
  PRIMARY KEY (`id_alumno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of alumnos
-- ----------------------------
INSERT INTO `alumnos` VALUES ('1', 'LUIS ANGEL', 'PEREZ', 'LOPEZ');
INSERT INTO `alumnos` VALUES ('2', 'MARTIN', 'SOSA', 'BARRERA');
INSERT INTO `alumnos` VALUES ('3', 'ENRIQUE', 'SANDOVAL', 'HERRERA');

-- ----------------------------
-- Table structure for alumnos_asignaturas
-- ----------------------------
DROP TABLE IF EXISTS `alumnos_asignaturas`;
CREATE TABLE `alumnos_asignaturas` (
  `id_alumnos_asignaturas` int(11) DEFAULT NULL,
  `unidad` tinyint(4) DEFAULT NULL,
  `calificacion` tinyint(4) DEFAULT NULL,
  `id_asignatura` int(11) DEFAULT NULL,
  `id_alumno` int(11) DEFAULT NULL,
  KEY `id_asignatura` (`id_asignatura`),
  KEY `id_alumno` (`id_alumno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of alumnos_asignaturas
-- ----------------------------
INSERT INTO `alumnos_asignaturas` VALUES ('1', '1', '47', '1', '1');
INSERT INTO `alumnos_asignaturas` VALUES ('2', '2', '62', '1', '1');
INSERT INTO `alumnos_asignaturas` VALUES ('3', '3', '86', '1', '1');
INSERT INTO `alumnos_asignaturas` VALUES ('4', '1', '46', '2', '1');
INSERT INTO `alumnos_asignaturas` VALUES ('5', '2', '77', '2', '1');
INSERT INTO `alumnos_asignaturas` VALUES ('6', '3', '99', '2', '1');
INSERT INTO `alumnos_asignaturas` VALUES ('7', '1', '95', '3', '1');
INSERT INTO `alumnos_asignaturas` VALUES ('8', '2', '94', '3', '1');
INSERT INTO `alumnos_asignaturas` VALUES ('9', '3', '71', '3', '1');
INSERT INTO `alumnos_asignaturas` VALUES ('10', '1', '70', '4', '1');
INSERT INTO `alumnos_asignaturas` VALUES ('11', '2', '100', '4', '1');
INSERT INTO `alumnos_asignaturas` VALUES ('12', '3', '52', '4', '1');
INSERT INTO `alumnos_asignaturas` VALUES ('13', '1', '74', '5', '1');
INSERT INTO `alumnos_asignaturas` VALUES ('14', '2', '52', '5', '1');
INSERT INTO `alumnos_asignaturas` VALUES ('15', '3', '73', '5', '1');
INSERT INTO `alumnos_asignaturas` VALUES ('16', '1', '50', '1', '2');
INSERT INTO `alumnos_asignaturas` VALUES ('17', '2', '58', '1', '2');
INSERT INTO `alumnos_asignaturas` VALUES ('18', '3', '96', '1', '2');
INSERT INTO `alumnos_asignaturas` VALUES ('19', '1', '75', '2', '2');
INSERT INTO `alumnos_asignaturas` VALUES ('20', '2', '47', '2', '2');
INSERT INTO `alumnos_asignaturas` VALUES ('21', '3', '58', '2', '2');
INSERT INTO `alumnos_asignaturas` VALUES ('22', '1', '95', '3', '2');
INSERT INTO `alumnos_asignaturas` VALUES ('23', '2', '81', '3', '2');
INSERT INTO `alumnos_asignaturas` VALUES ('24', '3', '69', '3', '2');
INSERT INTO `alumnos_asignaturas` VALUES ('25', '1', '70', '4', '2');
INSERT INTO `alumnos_asignaturas` VALUES ('26', '2', '91', '4', '2');
INSERT INTO `alumnos_asignaturas` VALUES ('27', '3', '89', '4', '2');
INSERT INTO `alumnos_asignaturas` VALUES ('28', '1', '81', '5', '2');
INSERT INTO `alumnos_asignaturas` VALUES ('29', '2', '50', '5', '2');
INSERT INTO `alumnos_asignaturas` VALUES ('30', '3', '70', '5', '2');
INSERT INTO `alumnos_asignaturas` VALUES ('31', '1', '48', '1', '3');
INSERT INTO `alumnos_asignaturas` VALUES ('32', '2', '54', '1', '3');
INSERT INTO `alumnos_asignaturas` VALUES ('33', '3', '85', '1', '3');
INSERT INTO `alumnos_asignaturas` VALUES ('34', '1', '92', '2', '3');
INSERT INTO `alumnos_asignaturas` VALUES ('35', '2', '57', '2', '3');
INSERT INTO `alumnos_asignaturas` VALUES ('36', '3', '48', '2', '3');
INSERT INTO `alumnos_asignaturas` VALUES ('37', '1', '59', '3', '3');
INSERT INTO `alumnos_asignaturas` VALUES ('38', '2', '61', '3', '3');
INSERT INTO `alumnos_asignaturas` VALUES ('39', '3', '91', '3', '3');
INSERT INTO `alumnos_asignaturas` VALUES ('40', '1', '51', '4', '3');
INSERT INTO `alumnos_asignaturas` VALUES ('41', '2', '90', '4', '3');
INSERT INTO `alumnos_asignaturas` VALUES ('42', '3', '42', '4', '3');
INSERT INTO `alumnos_asignaturas` VALUES ('43', '1', '87', '5', '3');
INSERT INTO `alumnos_asignaturas` VALUES ('44', '2', '48', '5', '3');
INSERT INTO `alumnos_asignaturas` VALUES ('45', '3', '63', '5', '3');

-- ----------------------------
-- Table structure for asignaturas
-- ----------------------------
DROP TABLE IF EXISTS `asignaturas`;
CREATE TABLE `asignaturas` (
  `id_asignatura` int(11) NOT NULL,
  `descripcion` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id_asignatura`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of asignaturas
-- ----------------------------
INSERT INTO `asignaturas` VALUES ('1', 'BASE DE DATOS');
INSERT INTO `asignaturas` VALUES ('2', 'ENRUTAMIENTO AVANZADO');
INSERT INTO `asignaturas` VALUES ('3', 'PROGRAMACION');
INSERT INTO `asignaturas` VALUES ('4', 'INGENIERIA DEL SOFTWARE');
INSERT INTO `asignaturas` VALUES ('5', 'FUNDAMENTOS DE IA');

-- ----------------------------
-- Table structure for competencias
-- ----------------------------
DROP TABLE IF EXISTS `competencias`;
CREATE TABLE `competencias` (
  `id_competencia` int(11) NOT NULL,
  `promedio` float DEFAULT NULL,
  `acronimo` varchar(2) DEFAULT NULL,
  `id_asignatura` int(11) DEFAULT NULL,
  `id_alumno` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_competencia`),
  KEY `id_asignatura` (`id_asignatura`),
  KEY `id_alumno` (`id_alumno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of competencias
-- ----------------------------

-- ----------------------------
-- Table structure for indicadores_rendimiento
-- ----------------------------
DROP TABLE IF EXISTS `indicadores_rendimiento`;
CREATE TABLE `indicadores_rendimiento` (
  `id_indicadores_rendimiento` int(11) NOT NULL,
  `cantidad_parciales` tinyint(4) DEFAULT NULL,
  `cantidad_semestrales` tinyint(4) DEFAULT NULL,
  `id_alumno` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_indicadores_rendimiento`),
  KEY `id_alumno` (`id_alumno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of indicadores_rendimiento
-- ----------------------------

-- ----------------------------
-- Table structure for ingresos
-- ----------------------------
DROP TABLE IF EXISTS `ingresos`;
CREATE TABLE `ingresos` (
  `id_ingresos` int(11) NOT NULL,
  `costo_total_parciales` float DEFAULT NULL,
  `cost_total_semestrales` float DEFAULT NULL,
  `id_alumno` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_ingresos`),
  KEY `id_alumno` (`id_alumno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ingresos
-- ----------------------------

-- ----------------------------
-- Table structure for promedios_asignaturas
-- ----------------------------
DROP TABLE IF EXISTS `promedios_asignaturas`;
CREATE TABLE `promedios_asignaturas` (
  `id_promedio_asignatura` int(11) NOT NULL,
  `promedio` float DEFAULT NULL,
  `id_asignatura` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_promedio_asignatura`),
  KEY `id_asignatura` (`id_asignatura`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of promedios_asignaturas
-- ----------------------------
