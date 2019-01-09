-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-01-2019 a las 20:16:55
-- Versión del servidor: 10.1.32-MariaDB
-- Versión de PHP: 5.6.36

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tamecon`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `AgregarRebaja` (IN `Id` INT, IN `Des` FLOAT)  begin
Update EntregaMateriales SEt Descuento=Des where IdV=Id;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `AgregarVenta` (`Cod` VARCHAR(15), `Cant` FLOAT, `PV` FLOAT, `Us` VARCHAR(25), `Nomb` VARCHAR(50))  begin
insert into Venta(Codigo,Cantidad,PrecioVenta,FechaCancelacion,User,Nombre)values(Cod,Cant,PV,'1111-11-11 00:00:00',Us,Nomb);
CALL InsertarDeposito(Cod,0,Cant);
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Entrega` (IN `Cod` VARCHAR(15), IN `Id` INT, IN `Cant` FLOAT, IN `Us` VARCHAR(25), IN `Des` FLOAT, IN `PV` FLOAT)  begin
insert into EntregaMateriales(Codigo,IdV,Cantidad,PrecioVenta,Fecha,User,Descuento)values(Cod,Id,Cant,PV,NOW(),Us,Des);
CALL InsertarDeposito(Cod,0,Cant);
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `IngresoVehiculo` (IN `placa` VARCHAR(12), IN `Nomb` VARCHAR(35), IN `fecha` VARCHAR(12))  begin
set @Id=(Select Id from Ayudante where Nombre=Nomb);
Insert Into IngresoVehiculo(Placa,PC,FechaIngreso1,FechaIngreso,FechaSalida1) values(placa,@Id,fecha,NOW(),'null');
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertarDeposito` (IN `Cod` VARCHAR(15), IN `CE` FLOAT, IN `CS` FLOAT)  begin
Set @CE1=(Select CEntrada from DepositoTotal where Codigo=Cod);
Set @CS1=(Select CSalida from DepositoTotal where Codigo=Cod);

	insert Into Deposito(Codigo,CEntrada,CSalida,Fecha) values(Cod,CE,CS,Now());
    Update DepositoTotal SET CEntrada=CE+@CE1, CSalida=CS+@CS1, CSaldo=(CE+@CE1)-(CS+@CS1) where Codigo=Cod;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertarNuevoMaterial` (IN `Cod` VARCHAR(15), IN `CM` VARCHAR(50), IN `M` VARCHAR(100), IN `PC` FLOAT, IN `PV` FLOAT, IN `U` VARCHAR(10), IN `C` FLOAT)  begin
	insert Into Inventario(Codigo,CodMat,Material,PrecioCompra,PrecioVenta,Unidad) Values(Cod,CM,M,PC,PV,U);
    Insert into Deposito(Codigo,CEntrada,CSalida,Fecha) values(Cod,C,0,now());
    Insert Into DepositoTotal(Codigo,CEntrada,CSalida,CSaldo) values(Cod,C,0,C);
    
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertarProveedor` (IN `Nomb` VARCHAR(50), IN `Telef` VARCHAR(20), IN `Direc` VARCHAR(100), IN `Det` VARCHAR(100))  begin
Insert Into Proveedor(Nombre,Telefono,Direccion,Detalles)values(Nomb,Telef,Direc,Det);
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ModificarAyudante` (IN `I` INT, IN `Nomb` VARCHAR(35), IN `St` CHAR(1))  begin
	UPDATE Ayudante SET Estado=St, Nombre=Nomb Where Id=I;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ModificarIngresoVehiculo` (IN `placa` VARCHAR(12), IN `Nomb` VARCHAR(35), IN `I` INT)  begin
set @Id=(Select Id from Ayudante where Nombre=Nomb);
Update IngresoVehiculo SET Placa=placa, PC=@Id,FechaIngreso=now() Where Id=I;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ModificarMaterial` (IN `Cod` VARCHAR(15), IN `CM` VARCHAR(50), IN `M` VARCHAR(100), IN `PC` FLOAT, IN `PV` FLOAT, IN `U` VARCHAR(10), IN `C` FLOAT)  begin
	UPDATE Inventario SET CodMat=CM,Material=M,PrecioCompra= PC,PrecioVenta = PV,Unidad=U where Codigo=Cod;
    UPDATE DepositoTotal SET CEntrada=C,CSaldo=C,CSalida=0 where Codigo=Cod;
	end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ModificarProveedor` (IN `Nomb` VARCHAR(50), IN `Telef` VARCHAR(20), IN `Direc` VARCHAR(100), IN `Det` VARCHAR(100), IN `I` INT)  begin
    Update Proveedor Set Nombre=Nomb,Telefono=Telef,Direccion=Direc,Detalles=Det Where Id=I;
    End$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Quitar` (IN `I` INT, IN `Cod` VARCHAR(15), IN `Cant` FLOAT)  begin
Set @C=(Select CSalida from DepositoTotal Where Codigo=Cod);
Set @CE=(Select CEntrada from DepositoTotal Where Codigo=Cod);
Set @CS=(Select CSaldo from DepositoTotal Where Codigo=Cod);
Delete From EntregaMateriales Where Id=I;
Update DepositoTotal Set CSalida=@C-Cant,CSaldo=@C-Cant+@CE+Cant Where Codigo=Cod;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `QuitarVenta` (`Cod` VARCHAR(15), `Cant` FLOAT, IN `id` INT)  begin
Set @C=(Select CSalida from DepositoTotal Where Codigo=Cod);
Set @CE=(Select CEntrada from DepositoTotal Where Codigo=Cod);
Set @CS=(Select CSaldo from DepositoTotal Where Codigo=Cod);
Delete From Venta  Where Id=id;
Update DepositoTotal Set CSalida=@C-Cant,CSaldo=@C-Cant+@CE+Cant Where Codigo=Cod;
end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ayudante`
--

CREATE TABLE `ayudante` (
  `Id` int(11) NOT NULL,
  `Nombre` varchar(35) DEFAULT NULL,
  `Estado` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ayudante`
--

INSERT INTO `ayudante` (`Id`, `Nombre`, `Estado`) VALUES
(1, 'Mario', 'H'),
(57, 'Pablo', 'H'),
(58, 'Vargas', 'D'),
(59, 'ALEX', 'H');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bitacora`
--

CREATE TABLE `bitacora` (
  `User` varchar(25) DEFAULT NULL,
  `FechaIngreso` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `bitacora`
--

INSERT INTO `bitacora` (`User`, `FechaIngreso`) VALUES
('AccesoRoot', '2018-12-30 18:16:54'),
('AccesoRoot', '2018-12-30 18:17:00'),
('AccesoRoot', '2018-12-30 18:17:02'),
('AccesoRoot', '2018-12-30 18:29:05'),
('AccesoRoot', '2018-12-30 20:34:41'),
('AccesoRoot', '2018-12-30 20:39:40'),
('AccesoRoot', '2018-12-31 02:11:02'),
('AccesoRoot', '2018-12-31 02:15:49'),
('AccesoRoot', '2018-12-31 02:17:58'),
('AccesoRoot', '2018-12-31 02:28:09'),
('AccesoRoot', '2018-12-31 02:32:55'),
('AccesoRoot', '2018-12-31 02:34:01'),
('AccesoRoot', '2018-12-31 02:38:02'),
('AccesoRoot', '2019-01-02 05:08:13'),
('AccesoRoot', '2019-01-02 05:08:16'),
('AccesoRoot', '2019-01-02 05:09:05'),
('AccesoRoot', '2019-01-02 05:10:05'),
('AccesoRoot', '2019-01-02 05:10:14'),
('AccesoRoot', '2019-01-02 05:10:43'),
('AccesoRoot', '2019-01-02 15:16:13'),
('AccesoRoot', '2019-01-02 15:16:26'),
('AccesoRoot', '2019-01-02 15:16:58'),
('AccesoRoot', '2019-01-02 15:17:20'),
('AccesoRoot', '2019-01-02 15:17:28'),
('AccesoRoot', '2019-01-02 15:17:34'),
('AccesoRoot', '2019-01-02 15:17:52'),
('AccesoRoot', '2019-01-02 15:19:31'),
('AccesoRoot', '2019-01-02 15:29:54'),
('AccesoRoot', '2019-01-02 15:29:57'),
('AccesoRoot', '2019-01-02 15:30:23'),
('AccesoRoot', '2019-01-02 15:31:26'),
('AccesoRoot', '2019-01-02 15:42:23'),
('AccesoRoot', '2019-01-02 15:42:27'),
('AccesoRoot', '2019-01-02 15:42:47'),
('AccesoRoot', '2019-01-02 16:43:07'),
('AccesoRoot', '2019-01-02 18:10:07'),
('AccesoRoot', '2019-01-02 18:24:44'),
('AccesoRoot', '2019-01-02 18:29:37'),
('AccesoRoot', '2019-01-02 18:31:46'),
('AccesoRoot', '2019-01-02 18:33:15'),
('AccesoRoot', '2019-01-02 18:34:49'),
('AccesoRoot', '2019-01-02 20:00:48'),
('AccesoRoot', '2019-01-02 22:58:05'),
('AccesoRoot', '2019-01-02 22:59:37'),
('AccesoRoot', '2019-01-02 23:00:49'),
('AccesoRoot', '2019-01-02 23:08:29'),
('AccesoRoot', '2019-01-02 23:09:14'),
('AccesoRoot', '2019-01-02 23:39:36'),
('AccesoRoot', '2019-01-05 01:52:17'),
('AccesoRoot', '2019-01-05 05:29:32'),
('AccesoRoot', '2019-01-05 05:30:02'),
('AccesoRoot', '2019-01-05 05:32:16'),
('AccesoRoot', '2019-01-05 05:33:19'),
('AccesoRoot', '2019-01-05 05:33:48'),
('AccesoRoot', '2019-01-05 05:34:58'),
('AccesoRoot', '2019-01-05 05:38:57'),
('AccesoRoot', '2019-01-05 05:40:08'),
('AccesoRoot', '2019-01-05 05:50:47'),
('AccesoRoot', '2019-01-05 05:52:31'),
('AccesoRoot', '2019-01-05 05:53:59'),
('AccesoRoot', '2019-01-05 05:54:04'),
('AccesoRoot', '2019-01-05 05:54:11'),
('AccesoRoot', '2019-01-05 05:54:28'),
('AccesoRoot', '2019-01-05 05:54:28'),
('AccesoRoot', '2019-01-05 05:54:28'),
('AccesoRoot', '2019-01-05 05:54:29'),
('AccesoRoot', '2019-01-05 05:56:13'),
('Pablo', '2019-01-05 06:35:49'),
('AccesoRoot', '2019-01-05 06:38:42'),
('Pablo', '2019-01-05 06:39:04'),
('Pablo', '2019-01-05 06:39:28'),
('Pablo', '2019-01-05 06:39:37'),
('AccesoRoot', '2019-01-05 07:17:39'),
('AccesoRoot', '2019-01-05 07:19:46'),
('AccesoRoot', '2019-01-05 07:20:36'),
('AccesoRoot', '2019-01-05 07:23:20'),
('Pablo2', '2019-01-05 07:26:21'),
('Pablo', '2019-01-05 07:26:30'),
('AccesoRoot', '2019-01-05 07:26:40'),
('AccesoRoot', '2019-01-05 08:17:44'),
('AccesoRoot', '2019-01-05 08:28:04'),
('Pablo', '2019-01-05 08:28:14'),
('Pablo', '2019-01-05 08:28:29'),
('AccesoRoot', '2019-01-05 11:48:02'),
('AccesoRoot', '2019-01-05 11:50:19'),
('AccesoRoot', '2019-01-05 11:50:33'),
('AccesoRoot', '2019-01-05 12:01:20'),
('AccesoRoot', '2019-01-05 12:08:17'),
('AccesoRoot', '2019-01-05 12:11:00'),
('AccesoRoot', '2019-01-05 12:15:16'),
('AccesoRoot', '2019-01-05 12:24:33'),
('AccesoRoot', '2019-01-05 12:27:59'),
('AccesoRoot', '2019-01-05 12:31:55'),
('AccesoRoot', '2019-01-05 12:34:03'),
('AccesoRoot', '2019-01-05 12:41:00'),
('AccesoRoot', '2019-01-05 12:47:23'),
('AccesoRoot', '2019-01-05 12:49:14'),
('AccesoRoot', '2019-01-05 14:56:54'),
('AccesoRoot', '2019-01-05 14:58:40'),
('AccesoRoot', '2019-01-05 15:02:18'),
('AccesoRoot', '2019-01-05 15:08:02'),
('AccesoRoot', '2019-01-05 15:21:56'),
('AccesoRoot', '2019-01-05 17:21:29'),
('AccesoRoot', '2019-01-05 17:37:32'),
('AccesoRoot', '2019-01-05 17:57:45'),
('AccesoRoot', '2019-01-05 18:07:57'),
('AccesoRoot', '2019-01-05 18:24:55'),
('AccesoRoot', '2019-01-05 18:25:32'),
('AccesoRoot', '2019-01-05 18:27:03'),
('AccesoRoot', '2019-01-05 18:29:43'),
('Pablo2', '2019-01-05 18:30:44'),
('AccesoRoot', '2019-01-05 18:33:47'),
('AccesoRoot', '2019-01-05 18:36:45'),
('AccesoRoot', '2019-01-05 18:38:00'),
('AccesoRoot', '2019-01-05 18:39:05'),
('AccesoRoot', '2019-01-05 22:04:54'),
('AccesoRoot', '2019-01-05 22:14:14'),
('AccesoRoot', '2019-01-05 23:51:19'),
('AccesoRoot', '2019-01-05 23:52:56'),
('AccesoRoot', '2019-01-06 00:02:04'),
('AccesoRoot', '2019-01-06 00:05:20'),
('AccesoRoot', '2019-01-06 00:07:23'),
('AccesoRoot', '2019-01-06 00:19:54'),
('AccesoRoot', '2019-01-06 00:31:59'),
('AccesoRoot', '2019-01-06 14:54:43'),
('AccesoRoot', '2019-01-06 15:14:56'),
('AccesoRoot', '2019-01-06 15:18:25'),
('AccesoRoot', '2019-01-06 15:22:16'),
('AccesoRoot', '2019-01-06 15:22:49'),
('AccesoRoot', '2019-01-06 15:23:51'),
('AccesoRoot', '2019-01-06 15:25:26'),
('AccesoRoot', '2019-01-06 19:18:46'),
('AccesoRoot', '2019-01-06 19:21:11'),
('AccesoRoot', '2019-01-06 19:32:22'),
('AccesoRoot', '2019-01-06 19:37:05'),
('AccesoRoot', '2019-01-06 20:12:13'),
('AccesoRoot', '2019-01-06 20:15:06'),
('AccesoRoot', '2019-01-06 20:27:50'),
('AccesoRoot', '2019-01-06 23:19:57'),
('AccesoRoot', '2019-01-06 23:22:48'),
('AccesoRoot', '2019-01-06 23:25:22'),
('AccesoRoot', '2019-01-06 23:26:05'),
('AccesoRoot', '2019-01-07 00:04:57'),
('AccesoRoot', '2019-01-07 00:14:16'),
('AccesoRoot', '2019-01-07 00:17:39'),
('AccesoRoot', '2019-01-07 00:20:23'),
('AccesoRoot', '2019-01-07 00:24:58'),
('AccesoRoot', '2019-01-07 00:28:39'),
('AccesoRoot', '2019-01-07 00:32:59'),
('AccesoRoot', '2019-01-07 01:48:50'),
('AccesoRoot', '2019-01-07 02:13:17'),
('AccesoRoot', '2019-01-07 02:19:46'),
('AccesoRoot', '2019-01-07 02:20:31'),
('AccesoRoot', '2019-01-07 03:49:59'),
('AccesoRoot', '2019-01-07 03:51:30'),
('AccesoRoot', '2019-01-07 03:53:27'),
('AccesoRoot', '2019-01-07 03:56:08'),
('AccesoRoot', '2019-01-07 03:58:32'),
('AccesoRoot', '2019-01-07 04:01:54'),
('AccesoRoot', '2019-01-07 04:03:17'),
('AccesoRoot', '2019-01-07 04:04:36'),
('AccesoRoot', '2019-01-07 04:07:11'),
('AccesoRoot', '2019-01-07 04:08:57'),
('AccesoRoot', '2019-01-07 04:14:52'),
('AccesoRoot', '2019-01-07 04:18:47'),
('AccesoRoot', '2019-01-07 04:20:00'),
('AccesoRoot', '2019-01-07 04:24:14'),
('AccesoRoot', '2019-01-07 04:32:09'),
('AccesoRoot', '2019-01-07 04:33:32'),
('AccesoRoot', '2019-01-07 04:34:14'),
('AccesoRoot', '2019-01-07 05:10:06'),
('AccesoRoot', '2019-01-07 05:11:28'),
('AccesoRoot', '2019-01-07 05:14:00'),
('AccesoRoot', '2019-01-07 05:16:39'),
('AccesoRoot', '2019-01-07 05:18:48'),
('AccesoRoot', '2019-01-07 06:34:47'),
('AccesoRoot', '2019-01-07 06:37:41'),
('AccesoRoot', '2019-01-07 06:38:24'),
('AccesoRoot', '2019-01-07 06:41:01'),
('AccesoRoot', '2019-01-07 06:41:56'),
('AccesoRoot', '2019-01-07 06:42:59'),
('AccesoRoot', '2019-01-07 06:44:15'),
('AccesoRoot', '2019-01-07 06:45:38'),
('AccesoRoot', '2019-01-07 07:04:25'),
('AccesoRoot', '2019-01-07 07:05:45'),
('AccesoRoot', '2019-01-07 07:07:34'),
('AccesoRoot', '2019-01-07 07:09:48'),
('AccesoRoot', '2019-01-07 07:11:18'),
('AccesoRoot', '2019-01-07 07:12:28'),
('AccesoRoot', '2019-01-07 07:17:01'),
('AccesoRoot', '2019-01-07 07:19:51'),
('AccesoRoot', '2019-01-07 07:21:04'),
('AccesoRoot', '2019-01-07 10:13:29'),
('AccesoRoot', '2019-01-07 16:37:44'),
('AccesoRoot', '2019-01-07 16:39:23'),
('AccesoRoot', '2019-01-07 16:40:23'),
('AccesoRoot', '2019-01-07 16:42:38'),
('AccesoRoot', '2019-01-07 17:05:17'),
('AccesoRoot', '2019-01-07 18:01:55'),
('AccesoRoot', '2019-01-07 23:07:47'),
('AccesoRoot', '2019-01-07 23:13:09'),
('AccesoRoot', '2019-01-07 23:26:47'),
('AccesoRoot', '2019-01-07 23:30:22'),
('AccesoRoot', '2019-01-07 23:32:16'),
('AccesoRoot', '2019-01-07 23:33:49'),
('AccesoRoot', '2019-01-07 23:34:59'),
('AccesoRoot', '2019-01-07 23:40:40'),
('AccesoRoot', '2019-01-08 00:11:13'),
('AccesoRoot', '2019-01-08 00:12:06'),
('Pablo2', '2019-01-08 13:16:22'),
('AccesoRoot', '2019-01-08 13:16:43'),
('AccesoRoot', '2019-01-08 13:18:30'),
('Pablo', '2019-01-08 13:19:23'),
('Pablo2', '2019-01-08 13:20:01'),
('AccesoRoot', '2019-01-08 17:57:43'),
('AccesoRoot', '2019-01-08 18:07:08'),
('AccesoRoot', '2019-01-08 18:51:03'),
('AccesoRoot', '2019-01-08 18:57:18'),
('AccesoRoot', '2019-01-08 18:58:08'),
('AccesoRoot', '2019-01-08 18:59:15'),
('AccesoRoot', '2019-01-08 19:10:43'),
('AccesoRoot', '2019-01-09 01:29:55'),
('AccesoRoot', '2019-01-09 01:31:22'),
('AccesoRoot', '2019-01-09 01:32:54'),
('AccesoRoot', '2019-01-09 15:57:57');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `deposito`
--

CREATE TABLE `deposito` (
  `Codigo` varchar(15) DEFAULT NULL,
  `CEntrada` float DEFAULT NULL,
  `CSalida` float DEFAULT NULL,
  `CSaldo` float DEFAULT NULL,
  `Fecha` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `deposito`
--

INSERT INTO `deposito` (`Codigo`, `CEntrada`, `CSalida`, `CSaldo`, `Fecha`) VALUES
('TMC-001', 200, 0, 200, '2018-12-31 02:15:18'),
('TMC-001', 0, 50, 150, '2018-12-31 02:27:33'),
('TMC-002', 20, 0, 0, '2018-12-31 02:37:46'),
('TMC-002', 0, 4.5, 0, '2018-12-31 02:37:46'),
('TMC-004', 25, NULL, 0, '2019-01-02 15:30:23'),
('TMC-005', 1, 0, NULL, '2019-01-02 15:42:48'),
('TMC-006', 100, 0, NULL, '2019-01-02 18:35:47'),
('TMC-007', 1000, 0, NULL, '2019-01-02 23:40:09'),
('TMC-008', 100, 0, NULL, '2019-01-05 01:52:57'),
('TMC-009', 45, 0, NULL, '2019-01-06 00:07:55'),
('TMC-010', 24, 0, NULL, '2019-01-06 00:22:47'),
('TMC-007', 15, 10, NULL, '2019-01-06 18:11:27'),
('TMC-007', 0, 50, NULL, '2019-01-06 18:13:09'),
('TMC-007', NULL, 10, NULL, '2019-01-06 18:19:42'),
('TMC-007', 0, 50, NULL, '2019-01-06 19:01:25'),
('TMC-007', 0, 50, NULL, '2019-01-06 19:01:57'),
('TMC-009', 0, 50, NULL, '2019-01-06 19:02:38'),
('TMC-011', 100, 0, NULL, '2019-01-06 19:10:40'),
('TMC-012', 205, 98, NULL, '2019-01-06 19:21:50'),
('TMC-013', 100, 0, NULL, '2019-01-06 19:37:51'),
('TMC-007', 50, 0, NULL, '2019-01-06 22:59:56'),
('TMC-007', 50, 0, NULL, '2019-01-06 23:00:19'),
('TMC-007', 0, 50, NULL, '2019-01-06 23:18:12'),
('TMC-011', 100, 0, NULL, '2019-01-07 00:29:15'),
('TMC-012', 333, 98, NULL, '2019-01-07 00:33:41'),
('TMC-012', 60, 98, NULL, '2019-01-07 00:34:01'),
('TMC-007', 45, 0, NULL, '2019-01-07 00:36:45'),
('TMC-009', 20, 0, NULL, '2019-01-07 01:49:04'),
('TMC-014', 50, 0, NULL, '2019-01-07 04:11:04'),
('TMC-015', 42, 0, NULL, '2019-01-07 04:11:23'),
('TMC-007', 0, 150, NULL, '2019-01-07 08:56:08'),
('TMC-007', 0, 150, NULL, '2019-01-07 08:58:36'),
('TMC-007', 0, 200, NULL, '2019-01-07 18:04:22'),
('TMC-013', 0, 50, NULL, '2019-01-07 18:05:02'),
('TMC-007', 0, 3, NULL, '2019-01-07 23:28:00'),
('TMC-008', 0, 50, NULL, '2019-01-07 23:41:39'),
('TMC-013', 0, 25, NULL, '2019-01-07 23:42:02'),
('TMC-012', 0, 98, NULL, '2019-01-07 23:44:31'),
('TMC-012', 0, 98, NULL, '2019-01-07 23:47:08'),
('TMC-008', 50, 0, NULL, '2019-01-08 13:48:42'),
('TMC-013', 25, 0, NULL, '2019-01-08 13:51:09'),
('TMC-011', 0, 50, NULL, '2019-01-08 17:58:15'),
('TMC-012', 0, 98, NULL, '2019-01-08 17:58:24'),
('TMC-012', 0, 92, NULL, '2019-01-08 18:16:56'),
('TMC-012', 50, 0, NULL, '2019-01-08 18:18:03'),
('TMC-012', 0, 20, NULL, '2019-01-08 18:24:23'),
('TMC-013', 0, 50, NULL, '2019-01-08 18:25:31'),
('TMC-012', 0, 30, NULL, '2019-01-08 18:52:34'),
('TMC-007', 0, 97, NULL, '2019-01-09 13:47:47');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `depositototal`
--

CREATE TABLE `depositototal` (
  `Codigo` varchar(15) NOT NULL,
  `CEntrada` float DEFAULT NULL,
  `CSalida` float DEFAULT NULL,
  `CSaldo` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `depositototal`
--

INSERT INTO `depositototal` (`Codigo`, `CEntrada`, `CSalida`, `CSaldo`) VALUES
('TMC-007', 1000, 600, 400),
('TMC-008', 550, 50, 500),
('TMC-009', 15, 0, 15),
('TMC-010', 500, 0, 500),
('TMC-011', 250, 0, 250),
('TMC-012', 942, 112, 1084),
('TMC-013', 125, 125, 0),
('TMC-014', 50, 0, 50),
('TMC-015', 42, 0, 42);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entregamateriales`
--

CREATE TABLE `entregamateriales` (
  `Id` int(11) NOT NULL,
  `Codigo` varchar(15) DEFAULT NULL,
  `IdV` int(11) DEFAULT NULL,
  `Cantidad` float DEFAULT NULL,
  `PrecioVenta` float DEFAULT NULL,
  `Fecha` datetime DEFAULT NULL,
  `User` varchar(25) DEFAULT NULL,
  `Descuento` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `entregamateriales`
--

INSERT INTO `entregamateriales` (`Id`, `Codigo`, `IdV`, `Cantidad`, `PrecioVenta`, `Fecha`, `User`, `Descuento`) VALUES
(7, 'TMC-012', 8, 92, 25, '2019-01-08 18:16:56', 'AccesoRoot', 50),
(8, 'TMC-013', 8, 50, 15, '2019-01-08 18:25:31', 'AccesoRoot', 50);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ingresovehiculo`
--

CREATE TABLE `ingresovehiculo` (
  `Id` int(11) NOT NULL,
  `Placa` varchar(12) DEFAULT NULL,
  `PC` int(11) DEFAULT NULL,
  `FechaIngreso` datetime DEFAULT NULL,
  `FechaIngreso1` varchar(12) DEFAULT NULL,
  `FechaSalida` datetime DEFAULT NULL,
  `FechaSalida1` varchar(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ingresovehiculo`
--

INSERT INTO `ingresovehiculo` (`Id`, `Placa`, `PC`, `FechaIngreso`, `FechaIngreso1`, `FechaSalida`, `FechaSalida1`) VALUES
(1, 'a', 58, '2019-01-07 06:46:07', '2019-01-05 1', '2019-01-05 11:54:55', '2019-01-05 1'),
(2, 'a2', 1, '2019-01-07 05:53:14', '2019-01-07', NULL, NULL),
(3, 'a', 57, '2019-01-07 06:35:26', '07-01-2019', NULL, NULL),
(4, '683-PUA', 57, '2019-01-07 07:07:54', '07-01-2019', NULL, NULL),
(5, 'a', 1, '2019-01-07 07:17:16', '03-01-2019', NULL, NULL),
(6, '683-PUA', 1, '2019-01-07 07:19:13', '07-01-2019', NULL, 'null'),
(7, 'asdasd', 57, '2019-01-07 23:40:58', '11-01-2019', NULL, 'null'),
(8, '1234ab', 59, '2019-01-08 13:23:22', '08-01-2019', NULL, 'null');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventario`
--

CREATE TABLE `inventario` (
  `Codigo` varchar(15) NOT NULL,
  `CodMat` varchar(50) NOT NULL,
  `Material` varchar(100) DEFAULT NULL,
  `PrecioCompra` float DEFAULT NULL,
  `PrecioVenta` float DEFAULT NULL,
  `Unidad` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `inventario`
--

INSERT INTO `inventario` (`Codigo`, `CodMat`, `Material`, `PrecioCompra`, `PrecioVenta`, `Unidad`) VALUES
('TMC-001', '', 'Aceite', 35.5, 40, 'Litros'),
('TMC-002', '', 'Lija', 2, 2.5, 'Metros'),
('TMC-003', '', 'Liquido de Freno', 40.2, 45, 'Litros'),
('TMC-004', '', 'Martillo', 12.5, 15, 'Unidad'),
('TMC-005', '', 'Camion', 500, 600, 'Unidad'),
('TMC-006', '', 'Placa', 500, 800, 'Piezas'),
('TMC-007', 'aaa', 'Prueba', 15, 20, 'Metros'),
('TMC-008', 'assa45', 'Lijas', 2.6, 3.5, 'Metros'),
('TMC-009', 'aaa', 'asdj', 65, 65, 'Unidad'),
('TMC-010', '123A', 'ACEITE', 20, 25, 'Unidad'),
('TMC-011', '263asd', 'CARPICOLA', 11.5, 15, 'Unidad'),
('TMC-012', '123AS', 'ACEITE', 15.9, 25, 'Litros'),
('TMC-013', '263asd', 'PINTURA', 11.5, 15, 'Unidad'),
('TMC-014', 'asds', 'adasd', 55, 25, 'Litros'),
('TMC-015', 'ssfds', 'sdf', 25, 24, 'Metros');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `Id` int(11) NOT NULL,
  `Nombre` varchar(50) DEFAULT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Direccion` varchar(100) DEFAULT NULL,
  `Detalles` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `proveedor`
--

INSERT INTO `proveedor` (`Id`, `Nombre`, `Telefono`, `Direccion`, `Detalles`) VALUES
(1, 'Jose', '78670128', 'AV JAPON', 'Aceites');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `User` varchar(25) NOT NULL,
  `Password` varchar(60) DEFAULT NULL,
  `NombreUsuario` varchar(50) DEFAULT NULL,
  `FechaIngreso` datetime NOT NULL,
  `Cargo` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`User`, `Password`, `NombreUsuario`, `FechaIngreso`, `Cargo`) VALUES
('AccesoRoot', '7a7d800377e8c8797b2f7d94f77a6cf6667ac648', 'TAMECON', '2019-01-09 15:57:57', 'Manager'),
('Pablo', 'ce7169ba6c7dea1ca07fdbff5bd508d4bb3e5832', 'Pablo', '2019-01-08 13:19:23', 'Administrador'),
('Pablo2', '51b24cb745061f0a8239cccb233377553bac8b51', 'Pablo2', '2019-01-08 13:20:01', 'Supervisor'),
('Root', 'e96857c58f716104caead648ee6aa61ab8e41cdc', 'OMAR', '2019-01-05 18:34:54', 'Supervisor');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculo`
--

CREATE TABLE `vehiculo` (
  `Placa` varchar(12) NOT NULL,
  `Modelo` varchar(50) DEFAULT NULL,
  `Color` varchar(20) DEFAULT NULL,
  `NombreCliente` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `vehiculo`
--

INSERT INTO `vehiculo` (`Placa`, `Modelo`, `Color`, `NombreCliente`) VALUES
('1234ab', 'Nissan', 'Blanco', 'Paulino'),
('683-PUA', 'ISUZUa', 'PLOMOa', 'PABLOa'),
('a', '1a', 'a2', 'a3'),
('a2', 'a3', 'a4', 'a5'),
('asdasd', 'ad', 'as', '6644-as');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `Id` int(11) NOT NULL,
  `Codigo` varchar(15) DEFAULT NULL,
  `Cantidad` float DEFAULT NULL,
  `PrecioVenta` float DEFAULT NULL,
  `FechaCancelacion` datetime DEFAULT NULL,
  `User` varchar(25) DEFAULT NULL,
  `Nombre` varchar(50) DEFAULT NULL,
  `Descuento` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `ayudante`
--
ALTER TABLE `ayudante`
  ADD PRIMARY KEY (`Id`);

--
-- Indices de la tabla `bitacora`
--
ALTER TABLE `bitacora`
  ADD KEY `User` (`User`);

--
-- Indices de la tabla `deposito`
--
ALTER TABLE `deposito`
  ADD KEY `Codigo` (`Codigo`);

--
-- Indices de la tabla `depositototal`
--
ALTER TABLE `depositototal`
  ADD PRIMARY KEY (`Codigo`);

--
-- Indices de la tabla `entregamateriales`
--
ALTER TABLE `entregamateriales`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `User` (`User`),
  ADD KEY `Codigo` (`Codigo`),
  ADD KEY `IdV` (`IdV`);

--
-- Indices de la tabla `ingresovehiculo`
--
ALTER TABLE `ingresovehiculo`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Placa` (`Placa`),
  ADD KEY `PC` (`PC`);

--
-- Indices de la tabla `inventario`
--
ALTER TABLE `inventario`
  ADD PRIMARY KEY (`Codigo`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`Id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`User`);

--
-- Indices de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  ADD PRIMARY KEY (`Placa`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `User` (`User`),
  ADD KEY `Codigo` (`Codigo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `ayudante`
--
ALTER TABLE `ayudante`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- AUTO_INCREMENT de la tabla `entregamateriales`
--
ALTER TABLE `entregamateriales`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `ingresovehiculo`
--
ALTER TABLE `ingresovehiculo`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `venta`
--
ALTER TABLE `venta`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `bitacora`
--
ALTER TABLE `bitacora`
  ADD CONSTRAINT `bitacora_ibfk_1` FOREIGN KEY (`User`) REFERENCES `usuario` (`User`);

--
-- Filtros para la tabla `deposito`
--
ALTER TABLE `deposito`
  ADD CONSTRAINT `deposito_ibfk_1` FOREIGN KEY (`Codigo`) REFERENCES `inventario` (`Codigo`);

--
-- Filtros para la tabla `depositototal`
--
ALTER TABLE `depositototal`
  ADD CONSTRAINT `depositototal_ibfk_1` FOREIGN KEY (`Codigo`) REFERENCES `inventario` (`Codigo`);

--
-- Filtros para la tabla `entregamateriales`
--
ALTER TABLE `entregamateriales`
  ADD CONSTRAINT `entregamateriales_ibfk_1` FOREIGN KEY (`User`) REFERENCES `usuario` (`User`),
  ADD CONSTRAINT `entregamateriales_ibfk_2` FOREIGN KEY (`Codigo`) REFERENCES `inventario` (`Codigo`),
  ADD CONSTRAINT `entregamateriales_ibfk_3` FOREIGN KEY (`IdV`) REFERENCES `ingresovehiculo` (`Id`);

--
-- Filtros para la tabla `ingresovehiculo`
--
ALTER TABLE `ingresovehiculo`
  ADD CONSTRAINT `ingresovehiculo_ibfk_1` FOREIGN KEY (`Placa`) REFERENCES `vehiculo` (`Placa`),
  ADD CONSTRAINT `ingresovehiculo_ibfk_2` FOREIGN KEY (`PC`) REFERENCES `ayudante` (`Id`);

--
-- Filtros para la tabla `venta`
--
ALTER TABLE `venta`
  ADD CONSTRAINT `venta_ibfk_1` FOREIGN KEY (`User`) REFERENCES `usuario` (`User`),
  ADD CONSTRAINT `venta_ibfk_2` FOREIGN KEY (`Codigo`) REFERENCES `inventario` (`Codigo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
