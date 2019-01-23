Create database TAMECON;
Use TAMECON;
Create Table Vehiculo(
	Placa varchar(12) primary key,
    Modelo varchar(50),
    Color varchar(20),
    NombreCliente varchar(80)
);
Create Table Ayudante(
	Id int auto_increment primary key,
    Nombre varchar(35),
    Estado CHAR(1)
);
Create table IngresoVehiculo(
	Id int Auto_Increment primary key,
    Placa varchar(12),
    PC int,
    FechaIngreso datetime,
    FechaIngreso1 varchar(12),
    FechaSalida datetime,
    FechaSalida1 varchar(12),
    foreign key (Placa) references Vehiculo(Placa),
    Foreign key (PC) references Ayudante(Id)
);
Create table Inventario(
	Codigo varchar(15)primary key,
    
    Material varchar(100),
    PrecioCompra float,
    PrecioVenta float,
    Unidad varchar(10)
);
CREATE TABLE `inventario` (
  `Codigo` varchar(15) NOT NULL,
  `CodMat` varchar(50) NOT NULL,
  `Material` varchar(100) DEFAULT NULL,
  `PrecioCompra` float DEFAULT NULL,
  `PrecioVenta` float DEFAULT NULL,
  `Unidad` varchar(10) DEFAULT NULL
); 
create table DepositoTotal(
	Codigo varchar(15)primary key,
    CEntrada float,
    CSalida float,
    CSaldo float,
	Foreign key (Codigo) references Inventario(Codigo)
);
INSERT INTO Usuario VALUES('TAMECON',SHA1('TAMECON'),'TAMECON',now(),'Administrador');

Create table Deposito(
	Codigo varchar(15),
    CEntrada float,
    CSalida float,
    CSaldo float,
    Fecha datetime,
    Foreign key (Codigo) references Inventario(Codigo)
);

Create Table EntregaMateriales(
	Id int auto_Increment primary key,
	Codigo varchar(15),
    IdV int,
    Cantidad float,
    PrecioVenta float,
    Fecha datetime,
    User varchar(25),
    Descuento float,
    Foreign key (User) references Usuario(User),
    Foreign key(Codigo) references Inventario(Codigo),
    Foreign key (IdV) references IngresoVehiculo(Id)
);


Create table NroVenta(
	Id int auto_Increment Primary key,
    Nombre varchar(50),
    Descuento float,
    User varchar(25),
     Foreign key (User) references Usuario(User)
);

Create table Venta(
	I int Auto_Increment primary key,
	Id int,
	Codigo varchar(15),
    Cantidad float,
    PrecioVenta float,
    FechaCancelacion datetime,
    Foreign key(Id)references NroVenta(Id),
    Foreign key(Codigo) references Inventario(Codigo)
);
Create Table Usuario(
	User varchar(25) primary key,
    Password varchar(60),
    NombreUsuario varchar(50),
    FechaIngreso datetime,
    Cargo varchar(25)
);
Insert into Usuario Values('Nelvy',SHA1('keyli02'),'NELVY',NOW(),'Manager');
create table Proveedor(
	Id int auto_increment primary key,
    Nombre varchar(50),
    Telefono varchar(20),
    Direccion varchar(100),
    Detalles varchar(100)
);

Create table Bitacora(
	User varchar(25),
    FechaIngreso datetime,
    Foreign key (User) references Usuario(User)
);
delimiter $
CREATE PROCEDURE `InsertarNuevoMaterial` (IN `Cod` VARCHAR(15), IN `CM` VARCHAR(50), IN `M` VARCHAR(100), IN `PC` FLOAT, IN `PV` FLOAT, IN `U` VARCHAR(10), IN `C` FLOAT)
  begin
	insert Into Inventario(Codigo,CodMat,Material,PrecioCompra,PrecioVenta,Unidad) Values(Cod,CM,M,PC,PV,U);
    Insert into Deposito(Codigo,CEntrada,CSalida,Fecha) values(Cod,C,0,now());
    Insert Into DepositoTotal(Codigo,CEntrada,CSalida,CSaldo) values(Cod,C,0,C);
		
end $


delimiter $
create procedure ModificarMaterial(IN Cod VARCHAR(15), IN CM VARCHAR(50), IN M VARCHAR(100), IN PC FLOAT, IN PV FLOAT, IN U VARCHAR(10), IN C FLOAT)
	begin
	UPDATE Inventario SET CodMat=CM,Material=M,PrecioCompra= PC,PrecioVenta = PV,Unidad=U where Codigo=Cod;
    UPDATE DepositoTotal SET CEntrada=C,CSaldo=C,CSalida=0 where Codigo=Cod;
end $

DELIMITER $
CREATE PROCEDURE InsertarDeposito (IN `Cod` VARCHAR(15), IN `CE` FLOAT, IN `CS` FLOAT) 
 begin
Set @CE1=(Select CEntrada from DepositoTotal where Codigo=Cod);
Set @CS1=(Select CSalida from DepositoTotal where Codigo=Cod);

	insert Into Deposito(Codigo,CEntrada,CSalida,Fecha) values(Cod,CE,CS,Now());
    Update DepositoTotal SET CEntrada=CE+@CE1, CSalida=CS+@CS1, CSaldo=(CE+@CE1)-(CS+@CS1) where Codigo=Cod;
end$
delimiter $
create procedure IngresoVehiculo(in placa varchar(12),in Nomb varchar(35),in fecha varchar(12))
begin
set @Id=(Select Id from Ayudante where Nombre=Nomb);
Insert Into IngresoVehiculo(Placa,PC,FechaIngreso1,FechaIngreso,FechaSalida1) values(placa,@Id,fecha,NOW(),'null');
end $

delimiter $
create procedure ModificarIngresoVehiculo(in placa varchar(12),in Nomb varchar(35),in I int)
begin
set @Id=(Select Id from Ayudante where Nombre=Nomb);
Update IngresoVehiculo SET Placa=placa, PC=@Id,FechaIngreso=now() Where Id=I;
end $


delimiter $
create procedure Entrega(in Cod varchar(15),in Id int,in Cant float,in Us varchar(25),in Des float,in PV float)
begin
insert into EntregaMateriales(Codigo,IdV,Cantidad,PrecioVenta,Fecha,User,Descuento)values(Cod,Id,Cant,PV,NOW(),Us,Des);
CALL InsertarDeposito(Cod,0,Cant);
end $


delimiter $
Create Procedure Quitar(in I int,in Cod Varchar(15),in Cant float)
begin
Set @C=(Select CSalida from DepositoTotal Where Codigo=Cod);
Set @CE=(Select CEntrada from DepositoTotal Where Codigo=Cod);
Delete From EntregaMateriales Where Id=I;
Update DepositoTotal Set CSalida=@C-Cant,CSaldo=@CE-(@C-Cant) Where Codigo=Cod;
end $

delimiter $
Create Procedure AgregarRebaja(in Id int,in Des float)
begin
Update EntregaMateriales SEt Descuento=Des where IdV=Id;
end$

delimiter $
Create Procedure AgregarVenta(in Ide int,Cod varchar(15),
    Cant float,
    PV float)
begin
insert into Venta(Id,Codigo,Cantidad,PrecioVenta,FechaCancelacion)values(Ide,Cod,Cant,PV,'1111-11-11 00:00:00');
CALL InsertarDeposito(Cod,0,Cant);
end$

delimiter $
Create Procedure QuitarVenta(in Cod varchar(15),
    in Cant float,
    in iss int)
begin
Set @C=(Select CSalida from DepositoTotal Where Codigo=Cod);
Set @CE=(Select CEntrada from DepositoTotal Where Codigo=Cod);
Delete From Venta  Where I=iss;
Update DepositoTotal Set CSalida=@C-Cant,CSaldo=@CE-(@C-Cant) Where Codigo=Cod;
end$

delimiter $
create procedure InsertarDescuento(in Des float,in iss int)
begin
	Update NroVenta Set Descuento=Des Where Id=iss;
end $

delimiter $
Create Procedure FechaS(in FS varchar(12),in d int)
begin
UPDATE IngresoVehiculo SET FechaSalida1=FS,FechaSalida=now() where Id=d;
end $
Select * From Venta;
Select * From NroVenta;
delimiter $
Create Procedure FeS(in iss int)
begin
UPDATE Venta SET FechaCancelacion=now() where Id=iss;
end $

delimiter $
Create Procedure RVenta(in Nomb varchar(50),in Us varchar(25))
begin
insert into NroVenta(Nombre,User,Descuento) values(Nomb,Us,0);
end $


DELIMITER $$
--
-- Procedimientos
--
CREATE  PROCEDURE AgregarRebaja (IN Id INT, IN Des FLOAT)  begin
Update EntregaMateriales SEt Descuento=Des where IdV=Id;
end$$

CREATE  PROCEDURE AgregarVenta (IN Ide INT, Cod VARCHAR(15), Cant FLOAT, PV FLOAT)  begin
insert into Venta(Id,Codigo,Cantidad,PrecioVenta,FechaCancelacion)values(Ide,Cod,Cant,PV,'1111-11-11 00:00:00');
CALL InsertarDeposito(Cod,0,Cant);
end$$

CREATE PROCEDURE Entrega (IN Cod VARCHAR(15), IN Id INT, IN Cant FLOAT, IN Us VARCHAR(25), IN Des FLOAT, IN PV FLOAT)  begin
insert into EntregaMateriales(Codigo,IdV,Cantidad,PrecioVenta,Fecha,User,Descuento)values(Cod,Id,Cant,PV,NOW(),Us,Des);
CALL InsertarDeposito(Cod,0,Cant);
end$$

CREATE PROCEDURE FechaS (IN FS VARCHAR(12), IN d INT)  begin
UPDATE IngresoVehiculo SET FechaSalida1=FS,FechaSalida=now() where Id=d;
end$$

CREATE PROCEDURE FeS (IN iss INT)  begin
UPDATE Venta SET FechaCancelacion=now() where Id=iss;
end$$

CREATE PROCEDURE IngresoVehiculo (IN placa VARCHAR(12), IN Nomb VARCHAR(35), IN fecha VARCHAR(12))  begin
set @Id=(Select Id from Ayudante where Nombre=Nomb);
Insert Into IngresoVehiculo(Placa,PC,FechaIngreso1,FechaIngreso,FechaSalida1) values(placa,@Id,fecha,NOW(),'null');
end$$

CREATE PROCEDURE InsertarDeposito (IN Cod VARCHAR(15), IN CE FLOAT, IN CS FLOAT)  begin
Set @CE1=(Select CEntrada from DepositoTotal where Codigo=Cod);
Set @CS1=(Select CSalida from DepositoTotal where Codigo=Cod);

	insert Into Deposito(Codigo,CEntrada,CSalida,Fecha) values(Cod,CE,CS,Now());
    Update DepositoTotal SET CEntrada=CE+@CE1, CSalida=CS+@CS1, CSaldo=(CE+@CE1)-(CS+@CS1) where Codigo=Cod;
end$$

CREATE PROCEDURE InsertarDescuento (IN Des FLOAT, IN iss INT)  begin
	Update NroVenta Set Descuento=Des Where Id=iss;
end$$

CREATE PROCEDURE InsertarNuevoMaterial (IN Cod VARCHAR(15), IN CM VARCHAR(50), IN M VARCHAR(100), IN PC FLOAT, IN PV FLOAT, IN U VARCHAR(10), IN C FLOAT)  begin
	insert Into Inventario(Codigo,CodMat,Material,PrecioCompra,PrecioVenta,Unidad) Values(Cod,CM,M,PC,PV,U);
    Insert into Deposito(Codigo,CEntrada,CSalida,Fecha) values(Cod,C,0,now());
    Insert Into DepositoTotal(Codigo,CEntrada,CSalida,CSaldo) values(Cod,C,0,C);
    
end$$

CREATE  PROCEDURE InsertarProveedor (IN Nomb VARCHAR(50), IN Telef VARCHAR(20), IN Direc VARCHAR(100), IN Det VARCHAR(100))  begin
Insert Into Proveedor(Nombre,Telefono,Direccion,Detalles)values(Nomb,Telef,Direc,Det);
end$$

CREATE PROCEDURE ModificarAyudante (IN I INT, IN Nomb VARCHAR(35), IN St CHAR(1))  begin
	UPDATE Ayudante SET Estado=St, Nombre=Nomb Where Id=I;
end$$

CREATE PROCEDURE ModificarIngresoVehiculo (IN placa VARCHAR(12), IN Nomb VARCHAR(35), IN I INT)  begin
set @Id=(Select Id from Ayudante where Nombre=Nomb);
Update IngresoVehiculo SET Placa=placa, PC=@Id,FechaIngreso=now() Where Id=I;
end$$

CREATE PROCEDURE ModificarMaterial (IN Cod VARCHAR(15), IN CM VARCHAR(50), IN M VARCHAR(100), IN PC FLOAT, IN PV FLOAT, IN U VARCHAR(10), IN C FLOAT)  begin
	UPDATE Inventario SET CodMat=CM,Material=M,PrecioCompra= PC,PrecioVenta = PV,Unidad=U where Codigo=Cod;
    UPDATE DepositoTotal SET CEntrada=C,CSaldo=C,CSalida=0 where Codigo=Cod;
	end$$

CREATE PROCEDURE ModificarProveedor (IN Nomb VARCHAR(50), IN Telef VARCHAR(20), IN Direc VARCHAR(100), IN Det VARCHAR(100), IN I INT)  begin
    Update Proveedor Set Nombre=Nomb,Telefono=Telef,Direccion=Direc,Detalles=Det Where Id=I;
    End$$

CREATE PROCEDURE Quitar (IN I INT, IN Cod VARCHAR(15), IN Cant FLOAT)  begin
Set @C=(Select CSalida from DepositoTotal Where Codigo=Cod);
Set @CE=(Select CEntrada from DepositoTotal Where Codigo=Cod);
Set @CS=(Select CSaldo from DepositoTotal Where Codigo=Cod);
Delete From EntregaMateriales Where Id=I;
Update DepositoTotal Set CSalida=@C-Cant,CSaldo=@C-Cant+@CE+Cant Where Codigo=Cod;
end$$

CREATE PROCEDURE QuitarVenta (IN Cod VARCHAR(15), IN Cant FLOAT, IN iss INT)  begin
Set @C=(Select CSalida from DepositoTotal Where Codigo=Cod);
Set @CE=(Select CEntrada from DepositoTotal Where Codigo=Cod);
Delete From Venta  Where I=iss;
Update DepositoTotal Set CSalida=@C-Cant,CSaldo=@CE-(@C-Cant) Where Codigo=Cod;
end$$

CREATE PROCEDURE RVenta (IN Nomb VARCHAR(50), IN Us VARCHAR(25))  begin
insert into NroVenta(Nombre,User,Descuento) values(Nomb,Us,0);
end$$

DELIMITER ;