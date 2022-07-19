/*	Nombre del Programador: Sduard Alejandro Ixpatac Sipaque
    Codigo Tecnico: IN5AV
    Carné: 2020209
    Fecha de Creación:
		28-04-2021
	Modificaciones:
		28-04-2021
*/
drop database if exists DBKinalMall2020209;
create database DBKinalMall2020209;

Use DBKinalMall2020209;

-- Modulo sin Foreing Key
create table Departamentos(
	codigoDepartamento int not null auto_increment,
    nombreDepartamento varchar(45) not null,
    primary key PK_codigoDepartamento(codigoDepartamento)
);

create table Cargos(
	codigoCargo int not null auto_increment,
    nombreCargo varchar(45) not null,
    primary key PK_codigoCargo (codigoCargo)
);

create table Horarios(
	codigoHorario int not null auto_increment,
    horarioEntrada varchar(5) not null,
    horarioSalida varchar(5) not null,
    lunes boolean,
    martes boolean,
    miercoles boolean,
    jueves boolean,
    viernes boolean,
    primary key PK_codigoHorario (codigoHorario)
);

Create table Administracion(
	codigoAdministracion int not null auto_increment,
    direccion varchar(45) not null,
    telefono varchar(8) not null,
    primary key PK_codigoAdministracion (codigoAdministracion)
);

create table TipoClientes(
	codigoTipoCliente int not null auto_increment,
    descripcion varchar (45) not null,
    primary key PK_codigoTipoCliente (codigoTipoCliente)
);

create table Locales(
	codigoLocal int not null auto_increment,
    saldoFavor double(11,2) not null default 0.00,
    saldoContra double(11,2) not null default 0.00,
    mesesPendientes int default 0,
    disponibilidad boolean not null,
    valorLocal double (11,2) not null,
    valorAdministracion double (11,2) not null,
    primary key PK_codigoLocal (codigoLocal)
);

-- Modulo con Foreing Key
create table Empleados(
	codigoEmpleado int not null auto_increment,
    nombreEmpleado varchar(45) not null,
    apellidoEmpleado varchar(45) not null,
    correoElectronico varchar(45) not null,
    telefono varchar(8) not null,
    fechaContratacion date not null,
    sueldo double (11,2) not null default 0.00,
    primary key PK_codigoEmpleado (codigoEmpleado),
    codigoDepartamento int not null,
    codigoCargo int not null,
    codigoHorario int not null,
    codigoAdministracion int not null,
	constraint FK_Empleados_Departamentos foreign key (codigoDepartamento)
		references Departamentos (codigoDepartamento),
	constraint FK_Empleados_Cargos foreign key (codigoCargo)
		references Cargos(codigoCargo),
	constraint FK_Empleados_Horarios foreign key (codigoHorario)
		references Horarios(codigoHorario),
	constraint FK_Empleados_Administracion foreign key (codigoAdministracion)
		references Administracion(codigoAdministracion)
);

create table Proveedores(
	codigoProveedor int not null auto_increment,
    NITProveedor varchar(45) not null,
    servicioPrestado varchar(75) not null,
    telefonoProveedor varchar(8) not null,
    direccionProveedor varchar(45) not null,
    saldoFavor double (11,2) not null,
    saldoContra double (11,2) not null,
    primary key PK_codigoProveedor (codigoProveedor),
    codigoAdministracion int not null,
    constraint FK_Proveedores_Administracion foreign key (codigoAdministracion)
		references Administracion(codigoAdministracion)
);

create table CuentasPorPagar(
	codigoCuentaPorPagar int not null auto_increment,
    numeroFactura varchar(45) not null,
    fechaLimitePago date not null,
    estadoPago varchar (45) not null,
    valorNetoPago double (11,2) not null,
    primary key PK_codigoCuentaPorPagar(codigoCuentaPorPagar),
    codigoAdministracion int not null,
    codigoProveedor int not null,
    constraint FK_CuentasPorPagar_Administracion foreign key (codigoAdministracion)
		references Administracion(codigoAdministracion),
	constraint FK_CuentasPorPagar_Proveedor foreign key (codigoProveedor)
		references Proveedores(codigoProveedor)
);

create table Clientes(
	codigoCliente int not null auto_increment,
    nombresCliente varchar(60) not null,
    apellidosCliente varchar(60) not null,
    telefonoCliente varchar(8) not null,
    direccionCliente varchar(60) not null,
    email varchar(45) not null,
    primary key PK_codigoCliente (codigoCliente),
    codigoLocal int not null,
    codigoAdministracion int not null,
    codigoTipoCliente int not null,
    constraint FK_Clientes_Locales foreign key (codigoLocal)
		references Locales (codigoLocal),
	constraint FK_Clientes_Administracion foreign key (codigoAdministracion)
		references Administracion (codigoAdministracion),
	constraint FK_Clientes_TipoClientes foreign key (codigoTipoCliente)
		references TipoClientes (codigoTipoCliente)
);

create table CuentasPorCobrar(
	codigoCuentaPorCobrar int not null auto_increment,
    codigoFactura varchar(45),
    anio year(4) not null,
    mes int(2) not null,
    valorNetoPago double (11,2) not null default 0.00,
    estadoPago varchar(45) not null,
    primary key PK_codigoCuentaPorCobrar (codigoCuentaPorCobrar),
    codigoAdministracion int not null,
    codigoCliente int not null,
    codigoLocal int not null,
    constraint FK_CuentasPorCobrar_Administracion foreign key(codigoAdministracion)
		references Administracion (codigoAdministracion),
	constraint FK_CuentasPorCobrar_Clientes foreign key(codigoCliente)
		references Clientes (codigoCliente),
	constraint FK_CuentasPorCobrar_Locales foreign key(codigoLocal)
		references Locales (codigoLocal)
);


-- ------------------Departamentos------------------------- 
--  Insertar --
	Delimiter $$
		create procedure sp_AgregarDepartamentos (in nom_Departamento varchar(45))
		Begin
			insert into Departamentos(nombreDepartamento) values (nom_Departamento);
		End $$
		
	Delimiter ;

-- Listar
	Delimiter $$
		create procedure sp_ListarDepartamentos()
			Begin
				select 
					D.codigoDepartamento,
					D.nombreDepartamento
				from Departamentos D;
		End $$
	Delimiter ;
call sp_ListarDepartamentos();
-- Eliminar
	Delimiter $$
			create procedure sp_EliminarDepartamentos(in cod_Departamento int)
				Begin
					delete from Departamentos where codigoDepartamento = cod_Departamento;
				end$$
		Delimiter ;
		call sp_EliminarDepartamentos()
		
-- Buscar
	Delimiter $$
		create procedure sp_BuscarDepartamentos (in cod_Departamento int)
			begin
				select
					D.codigoDepartamento,
					D.nombreDepartamento
				from Departamentos D where cod_Departamento=codigoDepartamento;
			End $$
	delimiter ;
	
-- Editar
	Delimiter $$
		create procedure sp_EditarDepartamentos (in cod_Departamento int, in nom_Departamento varchar(45))
			Begin 
				Update Departamentos
					Set
						nombreDepartamento = nom_Departamento
						where codigoDepartamento=cod_Departamento;
            End $$
    Delimiter ;

    
-- ------------------Cargos------------------------- 
--  Insertar --
	Delimiter $$
		create procedure sp_AgregarCargos(in nom_Cargo varchar(45))
		Begin
			insert into Cargos(nombreCargo) values (nom_Cargo);
		End $$
		
	Delimiter ;


-- Listar
	Delimiter $$
		create procedure sp_ListarCargos()
			Begin
				select 
					C.codigoCargo,
					C.nombreCargo
				from Cargos C;
		End $$
	Delimiter ;

-- Eliminar
	Delimiter $$
			create procedure sp_EliminarCargos(in cod_Cargo int)
				Begin
					delete from Cargos where codigoCargo = cod_Cargo;
				end$$
		Delimiter ;
		
-- Buscar
	Delimiter $$
		create procedure sp_BuscarCargos (in cod_Cargo int)
			begin
				select
					C.codigoCargo,
					C.nombreCargo
				from Cargos C where cod_Cargo = codigoCargo;
			End $$
	delimiter ;
	
-- Editar
	Delimiter $$
		create procedure sp_EditarCargos (in cod_Cargo int, in nom_Cargo varchar(45))
			Begin 
				Update Cargos
					Set
						nombreCargo = nom_Cargo
						where codigoCargo = cod_Cargo;
            End $$
    Delimiter ;
    
-- ------------------Horarios------------------------- 
--  Insertar --
	Delimiter $$
		create procedure sp_AgregarHorarios(in hora_Entrada varchar(5), in hora_Salida varchar(5), in bo_Lunes boolean,
			in bo_Martes boolean, in bo_Miercoles boolean, in bo_Jueves boolean, in bo_Viernes boolean)
		Begin
			insert into Horarios(horarioEntrada, horarioSalida, lunes, martes, miercoles, jueves, viernes)
				values (hora_Entrada, hora_Salida, bo_Lunes, bo_Martes, bo_Miercoles, bo_Jueves, bo_Viernes);
		End $$
		
	Delimiter ;

-- Listar
	Delimiter $$
		create procedure sp_ListarHorarios()
			Begin
				select 
					H.codigoHorario,
					H.horarioEntrada, 
                    H.horarioSalida, 
                    H.lunes, 
                    H.martes, 
                    H.miercoles, 
                    H.jueves, 
                    H.viernes
				from Horarios H;
		End $$
	Delimiter ;
	call sp_ListarHorarios();

-- Eliminar
	Delimiter $$
			create procedure sp_EliminarHorarios(in cod_Horario int)
				Begin
					delete from Horarios where codigoHorario = cod_Horario;
				end$$
		Delimiter ;
		
-- Buscar
	Delimiter $$
		create procedure sp_BuscarHorarios (in cod_Horario int)
			begin
				select
					H.codigoHorario,
					H.horarioEntrada, 
                    H.horarioSalida, 
                    H.lunes, 
                    H.martes, 
                    H.miercoles, 
                    H.jueves, 
                    H.viernes
				from Horarios H where codigoHorario = cod_Horario;
			End $$
	delimiter ;

-- Editar
	Delimiter $$
		create procedure sp_EditarHorarios (in hora_Entrada varchar(5), in hora_Salida varchar(5), in bo_Lunes boolean,
			in bo_Martes boolean, in bo_Miercoles boolean, in bo_Jueves boolean, in bo_Viernes boolean)
			Begin 
				Update Horarios
					Set
						horarioEntrada = hora_Entrada, 
                        horarioSalida = hora_Salida, 
                        lunes = bo_Lunes, 
                        martes = bo_Martes, 
                        miercoles = bo_Miercoles, 
                        jueves = bo_Jueves, 
                        viernes = bo_Viernes
						where codigoHorario = cod_Horario;
            End $$
    Delimiter ;
    
-- ------------------Administracion------------------------- 
--  Insertar --
	Delimiter $$
		create procedure sp_AgregarAdministracion(in ad_Direccion varchar(45), in ad_Telefono varchar(8))
		Begin
			insert into Administracion(direccion, telefono) values (ad_Direccion, ad_Telefono);
		End $$
		
	Delimiter ;

-- Listar
	Delimiter $$
		create procedure sp_ListarAdministracion()
			Begin
				select 
					A.codigoAdministracion,
					A.direccion,
					A.telefono
				from Administracion A;
		End $$
	Delimiter ;

-- Eliminar
	Delimiter $$
			create procedure sp_EliminarAdministracion(in cod_Administracion int)
				Begin
					delete from Administracion where codigoAdministracion = cod_Administracion;
				end$$
		Delimiter ;

		
-- Buscar
	Delimiter $$
		create procedure sp_BuscarAdministracion (in codAdministracion int)
			begin
				select
					A.codigoAdministracion,
					A.direccion,
					A.telefono
				from Administracion A where codigoAdministracion = codAdministracion;
			End $$
	delimiter ;
call sp_BuscarAdministracion(2);
-- Editar
	Delimiter $$
		create procedure sp_EditarAdministracion (in cod_Administracion int, in ad_Direccion varchar (45), in ad_Telefono varchar (8))
			Begin 
				Update Administracion
					Set
						direccion = ad_Direccion,
						telefono = ad_Telefono
						where codigoAdministracion = cod_Administracion;
            End $$
    Delimiter ;

-- ------------------TipoClientes------------------------- 
--  Insertar --
	Delimiter $$
		create procedure sp_AgregarTipoClientes(tc_Descripcion varchar(45))
		Begin
			insert into TipoClientes(descripcion) values (tc_Descripcion);
		End $$
		
	Delimiter ;


-- Listar
	Delimiter $$
		create procedure sp_ListarTipoClientes()
			Begin
				select 
					TC.codigoTipoCliente,
					TC.descripcion
				from TipoClientes TC;
		End $$
	Delimiter ;

-- Eliminar
	Delimiter $$
			create procedure sp_EliminarTipoClientes(in cod_TipoCliente int)
				Begin
					delete from TipoClientes where codigoTipoCliente = cod_TipoCliente;
				end$$
		Delimiter ;
		
-- Buscar
	Delimiter $$
		create procedure sp_BuscarTipoClientes (in cod_TipoCliente int)
			begin
				select
					TC.codigoTipoCliente,
					TC.descripcion
				from TipoClientes TC where codigoTipoCliente = cod_TipoCliente;
			End $$
	delimiter ;
	
-- Editar
	Delimiter $$
		create procedure sp_EditarTipoClientes (in cod_TipoCliente int, in tc_Descripcion varchar(45))
			Begin 
				Update TipoClientes
					Set
						descripcion = tc_Descripcion
						where codigoTipoCliente = cod_TipoCliente;
            End $$
    Delimiter ;
    
-- ------------------Locales------------------------- 
--  Insertar --
delimiter $$
	create procedure sp_AgregarLocal(in ag_disponibilidad boolean, in ag_valorLocal double(11,2), in ag_valorAdministracion double(11,2))
	begin
		insert into Locales(disponibilidad, valorLocal, valorAdministracion)
			values (ag_disponibilidad,ag_valorLocal, ag_valorAdministracion);
    end$$
delimiter ;

-- Listar
	delimiter $$
	create procedure sp_ListarLocales()
	begin
		select 
			L.codigoLocal,
            L.saldoFavor,
            L.saldoContra,
            L.mesesPendientes,
            L.disponibilidad,
            L.valorLocal,
            L.valorAdministracion
				from Locales L;
    end$$
delimiter ;

-- Eliminar
	delimiter $$
	create procedure sp_EliminarLocales(in el_codigoLocal int)
	begin
		delete from Locales where codigoLocal = el_codigoLocal;
	end$$
	delimiter ;
		
-- Buscar
	delimiter $$
	create procedure sp_BuscarLocales(in bu_codigoLocal int)
	begin
		select 
			L.codigoLocal,
            L.saldoFavor,
            L.saldoContra,
            L.mesesPendientes,
            L.disponibilidad,
            L.valorLocal,
            L.valorAdministracion
            from Locales L
				where codigoLocal = bu_codigoLocal;
    end$$
	delimiter ;
	
-- Editar
	delimiter $$
	create procedure sp_EditarLocales(in ed_codigoLocal int, in ed_disponibilidad boolean, in ed_valorLocal double(11,2), in ed_valorAdministracion double(11,2))
	begin
		update Locales
			set
                disponibilidad = ed_disponibilidad,
                valorLocal = ed_valorLocal,
                valorAdministracion = ed_valorAdministracion
					where codigoLocal = ed_codigoLocal;
	end$$
	delimiter ;
    
-- ----------------------------Empleado ---------------------------------- --
--  Insertar --
	Delimiter $$
		create procedure sp_AgregarEmpleados (in e_nombreEmpleado varchar(45), in e_apellidoEmpleado varchar(45), in e_correoElectronico varchar(45), in e_telefono varchar(8),
												in e_fechaContratacion date, in e_sueldo double (11,2), in e_codigoDepartamento int, in e_codigoCargo int, in e_codigoHorario int, in e_codigoAdministracion int)
		Begin
			insert into Empleados (nombreEmpleado, apellidoEmpleado, correoElectronico, telefono, fechaContratacion, sueldo, codigoDepartamento, codigoCargo, codigoHorario, codigoAdministracion) 
				values (e_nombreEmpleado, e_apellidoEmpleado, e_correoElectronico, e_telefono, e_fechaContratacion, e_sueldo, e_codigoDepartamento, e_codigoCargo, e_codigoHorario, e_codigoAdministracion);
		End $$
		
	Delimiter ;
    
-- Listar
	Delimiter $$
		create procedure sp_ListarEmpleados()
			Begin
				select 
					E.codigoEmpleado,
                    E.nombreEmpleado,
					E.apellidoEmpleado,
					E.correoElectronico,
					E.telefono,
					E.fechaContratacion,
					E.sueldo,
					E.codigoDepartamento,
					E.codigoCargo,
					E.codigoHorario,
					E.codigoAdministracion
				from Empleados E;
		End $$
	Delimiter ;
	call sp_ListarEmpleados();

-- Eliminar
	Delimiter $$
			create procedure sp_EliminarEmpleados(in e_codigoEmpleado int)
				Begin
					delete from Empleados where codigoEmpleado = e_codigoEmpleado;
				end$$
		Delimiter ;
        
-- Buscar
	Delimiter $$
		create procedure sp_BuscarEmpleados (in cod_Empleado int)
			begin
				select
                    E.nombreEmpleado,
					E.apellidoEmpleado,
					E.correoElectronico,
					E.telefono,
					E.fechaContratacion,
					E.sueldo,
					E.codigoDepartamento,
					E.codigoCargo,
					E.codigoHorario,
					E.codigoAdministracion
				from Empleados E where cod_Empleado = codigoEmpleado;
			End $$
	Delimiter ;
	
-- Editar
	Delimiter $$
		create procedure sp_EditarEmpleados (in e_codigoEmpleado int, in e_nombreEmpleado varchar(45), in e_apellidoEmpleado varchar(45), in e_correoElectronico varchar(45), in e_telefono varchar(8),
												in e_fechaContratacion date, in e_sueldo double (11,2))
			Begin 
				Update Empleados
					Set
						nombreEmpleado = e_nombreEmpleado,
						apellidoEmpleado = e_apellidoEmpleado,
						correoElectronico = e_correoElectronico,
						telefono = e_telefono,
						fechaContratacion = e_fechaContratacion,
						sueldo = e_sueldo
						where codigoEmpleado = e_codigoEmpleado;
            End $$
    Delimiter ;
    
    
-- ----------------------------Proveedores ---------------------------------- --
--  Insertar --
delimiter $$
	create procedure sp_AgregarProveedores(in ag_NITProveedor varchar(45), in ag_servicioPrestado varchar(45),
		in ag_telefonoProveedor varchar(8), in ag_direccionProveedor varchar (60), in ag_saldoFavor double (11,2),
			in ag_saldoContra double (11,2), in ag_codigoAdministracion int)
	Begin 
		insert into Proveedores(NITProveedor, servicioPrestado, telefonoProveedor, direccionProveedor, saldoFavor,
			saldoContra, codigoAdministracion)
			values (ag_NITProveedor, ag_servicioPrestado, ag_telefonoProveedor, ag_direccionProveedor,
				ag_saldoFavor, ag_saldoContra, ag_codigoAdministracion);
    end$$
delimiter ;
-- Listar
	Delimiter $$
		create procedure sp_ListarProveedores()
			Begin
				select 
					P.codigoProveedor,
					P.NITProveedor, 
                    P.servicioPrestado, 
                    P.telefonoProveedor, 
                    P.direccionProveedor, 
                    P.saldoFavor, 
                    P.SaldoContra, 
                    P.codigoAdministracion
				from Proveedores P;
		End $$
	Delimiter ;
	call sp_ListarProveedores();

-- Eliminar
	Delimiter $$
			create procedure sp_EliminarProveedor(in cod_Proveedor int)
				Begin
					delete from Proveedores where codigoProveedor = cod_Proveedor;
				end$$
		Delimiter ;
        
-- Buscar
	Delimiter $$
		create procedure sp_BuscarProveedor (in cod_Proveedor int)
			begin
				select
					P.codigoProveedor,
					P.NITProveedor, 
                    P.servicioPrestado, 
                    P.telefonoProveedor, 
                    P.direccionProveedor, 
                    P.saldoFavor, 
                    P.SaldoContra, 
                    P.codigoAdministracion
				from Proveedores P where codigoProveedor = cod_Proveedor;
			End $$
	Delimiter ;
	
-- Editar
	Delimiter $$
		create procedure sp_EditarProveedor (in cod_Proveedor int, in p_NITProveedor varchar(11), in p_servicioPrestado varchar(45),
			in p_telefonoProveedor varchar(8), in p_direccionProveedor varchar(45), in p_saldoFavor double (11,2) , in p_saldoContra double (11,2))
			Begin 
				Update Proveedores
					Set
						NITProveedor = p_NITProveedor,
                        servicioPrestado = p_servicioPrestado, 
                        telefonoProveedor = p_telefonoProveedor, 
                        direccionProveedor = p_DireccionProveedor, 
                        saldoFavor = p_saldoFavor, 
                        saldoContra = p_saldoContra
						where codigoProveedor = cod_Proveedor;
            End $$
    Delimiter ;
    
-- ---------------------------------CuentasPorPagar---------------------------------------------
--  Insertar --
	Delimiter $$
		create procedure sp_AgregarCuentasPorPagar (in cp_numeroFactura varchar(45), in cp_fechaLimitePago date,
			in cp_estadoPago varchar (45), in cp_valorNetoPago double (11,2),in cp_codigoAdministracion int, in cp_codigoProveedor int)
		Begin
			insert into CuentasPorPagar (numeroFactura, fechalimitePago, estadoPago, valorNetoPago, codigoAdministracion, codigoProveedor) 
				values (cp_numeroFactura, cp_fechalimitePago, cp_estadoPago, cp_valorNetoPago, cp_codigoAdministracion, cp_codigoProveedor);
		End $$
		
	Delimiter ;

-- Listar
	Delimiter $$
		create procedure sp_ListarCuentasPorPagar()
			Begin
				select 
					CU.codigoCuentaPorPagar,
					CU.numeroFactura, 
                    CU.fechalimitePago, 
                    CU.estadoPago, 
                    CU.valorNetoPago,
                    CU.codigoAdministracion, 
                    CU.codigoProveedor
				from CuentasPorPagar CU;
		End $$
	Delimiter ;
	call sp_ListarCuentasPorPagar();

-- Eliminar
	Delimiter $$
			create procedure sp_EliminarCuentasPorPagar(in cod_CuentaPorPagar int)
				Begin
					delete from CuentasPorPagar where codigoCuentaPorPagar = cod_CuentaPorPagar;
				end$$
		Delimiter ;
		
-- Buscar
	Delimiter $$
		create procedure sp_BuscarCuentasPorPagar (in cod_CuentaPorPagar int)
			begin
				select
					CU.numeroFactura, 
                    CU.fechalimitePago, 
                    CU.estadoPago, 
                    CU.valorNetoPago,
                    CU.codigoAdministracion, 
                    CU.codigoProveedor
				from CuentasPorPagar CU where cod_CuentaPorPagar = codigoCuentaPorPagar;
			End $$
	delimiter ;
	
-- Editar
	Delimiter $$
		create procedure sp_EditarCuentasPorPagar(in cod_cuentaPorPagar int, in cp_numeroFactura varchar(45), in cp_fechaLimitePago date,
			in cp_estadoPago varchar (45), in cp_valorNetoPago double (11,2))
			Begin 
				Update cuentasPorPagar
					Set
					numeroFactura = cp_numeroFactura, 
                    fechalimitePago = cp_fechaLimitePago, 
                    estadoPago = cp_estadoPago, 
                    valorNetoPago = cp_valorNetoPago
						where codigoCuentaPorPagar = cod_cuentaPorPagar;
            End $$
    Delimiter ;

-- ----------------------------Clientes ---------------------------------- --
--  Insertar --
	Delimiter $$
		create procedure sp_AgregarClientes (in c_nombresCliente varchar(60) , in c_apellidosCliente varchar(60) , in c_telefonoCliente varchar(8) , in c_direccionCliente varchar(60),
				in c_email varchar(45), in c_codigoLocal int, in c_codigoAdministracion int, in c_codigoTipoCliente int)
		Begin
			insert into Clientes (nombresCliente, apellidosCliente, telefonoCliente, direccionCliente, email, codigoLocal, codigoAdministracion, codigoTipoCliente) 
				values (c_nombresCliente, c_apellidosCliente, c_telefonoCliente, c_direccionCliente, c_email, c_codigoLocal, c_codigoAdministracion, c_codigoTipoCliente);
		End $$
		
	Delimiter ;
    
-- Listar
	Delimiter $$
		create procedure sp_ListarClientes()
			Begin
				select 
					C.codigoCliente,
					C.nombresCliente,
					C.apellidosCliente,
					C.telefonoCliente,
					C.direccionCliente,
					C.email,
					C.codigoLocal,
					C.codigoAdministracion,
					C.codigoTipoCliente
				from Clientes C;
		End $$
	Delimiter ;
	call sp_ListarClientes();

-- Eliminar
	Delimiter $$
			create procedure sp_EliminarClientes(in c_codigoCliente int)
				Begin
					delete from Clientes where codigoCliente = c_codigoCliente;
				end$$
		Delimiter ;
        
-- Buscar
	Delimiter $$
		create procedure sp_BuscarClientes (in cod_Cliente int)
			begin
				select
					C.codigoCliente,
                    C.nombresCliente,
					C.apellidosCliente,
					C.telefonoCliente,
					C.direccionCliente,
					C.email,
					C.codigoLocal,
					C.codigoAdministracion,
					C.codigoTipoCliente
				from Clientes C where cod_Cliente = codigoCliente;
			End $$
	Delimiter ;
	
-- Editar
	Delimiter $$
		create procedure sp_EditarClientes (in c_codigoCliente int, in c_nombresCliente varchar(60) , in c_apellidosCliente varchar(60) , in c_telefonoCliente varchar(8) , in c_direccionCliente varchar(60),
				in c_email varchar(45))
			Begin 
				Update Clientes
					Set
						nombresCliente = c_nombresCliente,
						apellidosCliente = c_apellidosCliente,
						telefonoCliente = c_telefonoCliente,
						direccionCliente = c_direccionCliente,
						email = c_email
						where codigoCliente = c_codigoCliente;
            End $$
    Delimiter ;

-- ----------------------------CuentasPorCobrar ---------------------------------- --
--  Insertar --
	Delimiter $$
		create procedure sp_AgregarCuentasPorCobrar ( in cpc_codigoFactura varchar(45), in cpc_anio year(4), in cpc_mes int(2), in cpc_valorNetoPago double (11,2), in cpc_estadoPago varchar(45),
					in cpc_codigoAdministracion int, in cpc_codigoCliente int, in cpc_codigoLocal int)
		Begin
			insert into CuentasPorCobrar ( codigoFactura, anio, mes, valorNetoPago, estadoPago, codigoAdministracion, codigoCliente, codigoLocal) 
				values (cpc_codigoFactura, cpc_anio, cpc_mes, cpc_valorNetoPago, cpc_estadoPago, cpc_codigoAdministracion, cpc_codigoCliente, cpc_codigoLocal);
		End $$
		
	Delimiter ;
    
-- Listar
	Delimiter $$
		create procedure sp_ListarCuentasPorCobrar()
			Begin
				select 
					CPC.codigoCuentaPorCobrar, 
                    CPC.codigoFactura, 
                    CPC.anio, 
                    CPC.mes, 
                    CPC.valorNetoPago, 
                    CPC.estadoPago, 
                    CPC.codigoAdministracion, 
                    CPC.codigoCliente, 
                    CPC.codigoLocal
				from CuentasPorCobrar CPC;
		End $$
	Delimiter ;
call sp_ListarCuentasPorCobrar();

-- Eliminar
	Delimiter $$
			create procedure sp_EliminarCuentasPorCobrar(in cpc_codigoCuentaPorCobrar int)
				Begin
					delete from CuentasPorCobrar where codigoCuentaPorCobrar = cpc_codigoCuentaPorCobrar;
				end$$
		Delimiter ;
        
-- Buscar
	Delimiter $$
		create procedure sp_BuscarCuentasPorCobrar (in cod_CuentaPorCobrar int)
			begin
				select
                    CPC.codigoFactura, 
                    CPC.anio, 
                    CPC.mes, 
                    CPC.valorNetoPago, 
                    CPC.estadoPago, 
                    CPC.codigoAdministracion, 
                    CPC.codigoCliente, 
                    CPC.codigoLocal
				from CuentasPorCobrar CPC where cod_CuentaPorCobrar = codigoCuentaPorPagar;
			End $$
	Delimiter ;
	
-- Editar
	Delimiter $$
		create procedure sp_EditarCuentasPorCobrar (in cpc_codigoCuentaPorCobrar int, in cpc_codigoFactura varchar(45), in cpc_anio year(4), in cpc_mes int(2), 
														in cpc_valorNetoPago double (11,2), in cpc_estadoPago varchar(45))
			Begin 
				Update CuentasPorCobrar
					Set
						C.codigoCuentaPorCobrar = cpc_codigoFactura, 
						C.codigoFactura = cpc_anio, 
                        C.anio = cpc_anio, 
						C.mes = cpc_mes, 
						C.valorNetoPago = cpc_valorNetoPago, 
						C.estadoPago = cpc_estadoPago
						where codigoCuentaPorCobrar = cpc_codigoCuentaPorCobrar;
            End $$
Delimiter ;


delimiter $$
	create procedure sp_CalcularLiquido(in cal_codigoLocal int)
		begin
			select
            L.saldoFavor-L.saldoContra as 'liquido'
            from Locales L where cal_codigoLocal = codigoLocal;
		end$$
delimiter ; 
call sp_CalcularLiquido(1);
    
Delimiter $$
	create procedure sp_CalcularLiquidoLocal(in cal_codigoLocal int)
		begin
			select 
            L.codigoLocal,
            L.saldoFavor,
            L.saldoContra,
            L.mesesPendientes,
            L.disponibilidad,
            L.valorLocal,
            L.valorAdministracion,
            L.valorLocal * L.mesesPendientes as 'MesesPendientes',
            L.saldoFavor - L.saldoContra as 'Liquido',
            mesesPendientes-saldoFavor as 'Deuda'
            from Locales L;
		end$$
Delimiter ;
call sp_CalcularLiquidoLocal(1); -- Sduard Ixpatac

call sp_AgregarAdministracion ('3ra calle zona 6','8912746');
call sp_AgregarAdministracion ('5ta calle zona 10','80182376');		 
call sp_ListarAdministracion();

call sp_AgregarDepartamentos('Contabilidad');
call sp_AgregarDepartamentos('Servicio al cliente');
call sp_AgregarDepartamentos('Gerencia');
call sp_AgregarDepartamentos('Servicio técnico');
call sp_ListarDepartamentos();

call sp_AgregarTipoClientes('Frecuente');
call sp_AgregarTipoClientes('Nuevo');
call sp_ListarTipoClientes();

call sp_AgregarLocal(True, 550.90, 299.99);
call sp_AgregarLocal(False, 699.99, 349.90);
call sp_ListarLocales();

call sp_AgregarCargos('Gerente');
call sp_AgregarCargos('Subgerente');
call sp_ListarCargos();

call sp_AgregarHorarios('08:00','17:00',true,true,false,false,true);
call sp_AgregarHorarios('07:00','15:00',false,true,true,true,false);
call sp_ListarHorarios();

call sp_AgregarClientes('Sduard Alejandro','Ixpatac Sipaque','92456781','Guatemala, Guatemala', 'sduard.sipaque@gmail.com', 1,1,1);
call sp_AgregarClientes('Karina Samara','Rodriguez Estrada', '89102738','Flores, Petén','karina.rodriguez@gmail.com',2,2,2);
call sp_AgregarClientes('Samuel Antonio','Cabrera España','78916283','Chinautla, Guatemala','samuel_cabrera.2021@gmail.com','1','2','1');
call sp_ListarClientes();

call sp_AgregarLocal(false, 100.60, 299.99); -- Sduard Ixpatac
call sp_AgregarLocal(true, 800.10, 349.90); -- Sduard Ixpatac
call sp_AgregarLocal(True, 540.55, 300.80); -- Sduard Ixpatac
call sp_AgregarLocal(False, 699.99, 450.90); -- Sduard Ixpatac
call sp_AgregarLocal(True, 125.99, 100.85); -- Sduard Ixpatac
call sp_AgregarLocal(False, 135.00, 55.15); -- Sduard Ixpatac
call sp_AgregarLocal(True, 200.90, 125.10); -- Sduard Ixpatac
call sp_AgregarLocal(true, 300.98, 159.90); -- Sduard Ixpatac
call sp_AgregarLocal(True, 100.90, 25.86); -- Sduard Ixpatac
call sp_AgregarLocal(False, 450.50, 200.70); -- Sduard Ixpatac
call sp_ListarLocales();

-- ----------------------------------PROVEEDORES
-- Sduard Ixpatac
call sp_AgregarProveedores('7125183-8','Mantenimiento de edificio','78192763','3ra calle lote 91 zona 10 Guatemala',155.50,100.25,1); -- Sduard Ixpatac
call sp_AgregarProveedores('8912637-5','Servicio Técnico','78291672','3ra Av. 5ta. calle29-5 Chinautla, Guatemala',455.20,450.25,2); -- Sduard Ixpatac
call sp_AgregarProveedores('7819263-2','Agua','17892534','5ta avenida 8-65zona 1 Guatemala',1000.99,1200.50,1); -- Sduard Ixpatac
call sp_AgregarProveedores('7182965-4','Luz','78192734','5ta calle 8-95 zona 10 Guatemala',190.99,30.50,2); -- Sduard Ixpatac
call sp_AgregarProveedores('6172936-5','Servicio de Internet','89120712','5aveniza 56-90 Flores, Petén',2500.00,2555.50,1); -- Sduard Ixpatac
call sp_AgregarProveedores('6182634-8','Señal de cable','87192563','9calle 4ta av 78-9 zona 3 Guatemala',835.00,676.97,1); -- Sduard Ixpatac
call sp_AgregarProveedores('0182634-0','Mantenimiento general','78129734','3ra calle 4-18 zona 7 Guatemala',957.90,1000.90,2);-- Sduard Ixpatac
call sp_AgregarProveedores('8125375-2','Mantenimiento de Computadoras','67192653','2da ave 5-98 zona 2 Guatemala',825.85,569.15,1); -- Sduard Ixpatac
call sp_AgregarProveedores('7182654-1','Equipo de limpieza','76192673','1ra calle 5 ave zona 3 Guatemala',998.45,723.89,2); -- Sduard Ixpatac
call sp_AgregarProveedores('9102537-1','Maquinaria','78102863','6ta calle 1ra ave zona 2 Izabal',210.90,100.85,1); -- Sduard Ixpatac
call sp_ListarProveedores();

call sp_AgregarCuentasPorPagar('C-00001','2021-01-31','Pagado',1200.00,1,2);
call sp_AgregarCuentasPorPagar('C-00002','2021-02-1','No Pagado',600.90,2,1);
call sp_AgregarCuentasPorPagar('C-00003','2021-03-28','No Pagado',1700.50,2,10);
call sp_ListarCuentasPorPagar();

call sp_AgregarEmpleados("Carlos","Yuman","carlos.yuman0901@hotmail.com","56947965","2020-02-15",3000.00,1,2,1,2);
call sp_AgregarEmpleados("Damaris","Orellana","damarisorellana290@gmail.com","34210946","2015-06-25",4500.00,2,1,2,1);
call sp_AgregarEmpleados("Roxana","Ramirez","roxana_ramirez10@hotmail.com","67819203","2017-10-12",3500.00,1,1,1,1);
call sp_ListarEmpleados();
delimiter $$

call sp_AgregarCuentasPorCobrar("C-00002","2021","09",1200.50,"Pagado", 1,2,3);
call sp_AgregarCuentasPorCobrar("C-00005","2021","01",3450.10,"Pagado", 2,2,1);
call sp_AgregarCuentasPorCobrar("C-00010","2021","08",4000.00,"Pagado", 1,1,1);
call sp_ListarCuentasPorCobrar();

	create procedure sp_CalcularLiquidoProv(in cal_codigoProveedor int) -- Sduard Ixpatac
		begin
			select
            P.codigoProveedor, -- Sduard Ixpatac
			P.NITProveedor, -- Sduard Ixpatac
            P.servicioPrestado, -- Sduard Ixpatac
            P.telefonoProveedor, -- Sduard Ixpatac
            P.direccionProveedor, -- Sduard Ixpatac
            P.saldoFavor, -- Sduard Ixpatac
            P.saldoContra, -- Sduard Ixpatac
            P.codigoAdministracion, -- Sduard Ixpatac
            P.saldoFavor-P.saldoContra as liquidoProveedor
            from Proveedores P where cal_codigoProveedor = codigoProveedor; -- Sduard Ixpatac
		end$$
delimiter ; 
call sp_CalcularLiquidoProv(3);


Delimiter $$
	create procedure sp_ContarLocales()
		Begin
		select count(codigoLocal) as Locales from Locales;     
		End$$
Delimiter ;
call sp_ContarLocales(); -- Sduard Ixpatac

Delimiter $$
	create procedure sp_LocalesDisponibles ()
		Begin
			select sum(disponibilidad = true) as LocalesDisponibles from Locales;
		End$$
Delimiter ;
call sp_LocalesDisponibles(); -- Sduard Ixpatac


Delimiter $$
	create procedure sp_LocalesNoDisponibles ()
		Begin
			select sum(disponibilidad = false) as LocalesNoDisponibles from Locales;
		End$$
Delimiter ;
call sp_LocalesNoDisponibles (); -- Sduard Ixpatac

ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password By 'admin';

call sp_ListarClientes();

select * from TipoClientes TC inner join Clientes C on
	TC.codigoTipoCliente = C.codigoTipoCliente
		where TC.descripcion='Frecuente';

Create table Usuario(
	codigoUsuario int not null auto_increment,
    nombreUsuario varchar(100) not null,
    apellidoUsuario varchar(100),
    usuarioLogin varchar(50),
    contrasena varchar (50),
    primary key PK_codigoUsuario(codigoUsuario)
    );
    
    Delimiter $$
    create procedure sp_AgregarUsuario(in nombreUsuario varchar(100), in apellidoUsuario varchar(100),
		in usuarioLogin varchar(50), in contrasena varchar(50))
	Begin
    Insert into Usuario(nombreUsuario, apellidoUsuario, usuarioLogin,contrasena)
		values(nombreUsuario, apellidoUsuario, usuarioLogin, contrasena);
    End$$
    Delimiter ;
    
    Delimiter $$
    create procedure sp_ListarUsuarios()
		begin
			Select
				U.codigoUsuario,
                U.nombreUsuario,
                U.apellidoUsuario,
                U.usuarioLogin,
                U.contrasena
			from Usuario U;
		end$$
    Delimiter ;
    
    create table Login(
		usuarioMaster varchar (50) not null,
        PaswordLogin varchar(50) not null,
        primary key PK_usuarioMaster(usuarioMaster)
    );
    
    call sp_AgregarUsuario('Sduard','Ixpatac','sixpatac','1234567');
    call sp_ListarUsuarios();

     
     select *  from Clientes C
	inner join TipoClientes TP inner join Locales L inner join CuentasPorCobrar CP
	on C.codigoTipoCliente=TP.codigoTipoCliente and C.codigoLocal=L.codigoLocal and C.codigoCliente=CP.codigoCliente 
	 where C.codigoCliente=1;
	