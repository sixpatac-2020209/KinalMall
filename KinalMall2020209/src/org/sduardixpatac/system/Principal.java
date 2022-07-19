/**
 * Nombre del Programador: Sduard Alejandro Ixpatac Sipaque
 * Codigo Técnico: IN5AV
 * Carné: 2020209
 * Fecha de Creación:
    05-05-2021
    06-05-2021
    09-05-2021
    12-05-2021
    13-05-2021
    26-05-2021
    02-06-2021
    03-06-2021
    04-06-2021
    08-06-2021
    09-06-2021
    10-06-2021
    15-06-2021
    16-06-2021
    17-06-2021
    06-07-2021
    07-07-2021
    08-07-2021
    09-07-2021
    10-07-2021
 */
package org.sduardixpatac.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.sduardixpatac.controller.AdministracionController;
import org.sduardixpatac.controller.CargoController;
import org.sduardixpatac.controller.ClienteController;
import org.sduardixpatac.controller.CuentaPorPagarController;
import org.sduardixpatac.controller.CuentasPorCobrarController;
import org.sduardixpatac.controller.DepartamentoController;
import org.sduardixpatac.controller.EmpleadoController;
import org.sduardixpatac.controller.HorarioController;
import org.sduardixpatac.controller.LocalController;
import org.sduardixpatac.controller.LoginController;
import org.sduardixpatac.controller.MenuPrincipalController;
import org.sduardixpatac.controller.ProgramadorController;
import org.sduardixpatac.controller.ProveedorController;
import org.sduardixpatac.controller.TipoClienteController;
import org.sduardixpatac.controller.UsuarioController;


public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/sduardixpatac/view/"; //Ruta de las vistas
    private Stage escenarioPrincipal;
    private Scene escena;
    @Override
    public void start(Stage escenarioPrincipal) throws IOException  {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Mall 2021");
        escenarioPrincipal.getIcons().add(new Image("/org/sduardixpatac/images/Kinal Mall Icono.png"));
        //Parent root = FXMLLoader.load(getClass().getResource("/org/sduardixpatac/view/MenuPrincipalView.fxml"));
        //Scene escena = new Scene(root);
        //escenarioPrincipal.setScene(escena);
        ventanaLogin();
        escenarioPrincipal.show();
        
    }
    public void menuPrincipal(){
        try{
            MenuPrincipalController menuPrincipal =  (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml", 684 , 444 );//(nombre vista y extencion, ancho y alto)
            menuPrincipal.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProgramador(){
        try{
            ProgramadorController programador = (ProgramadorController)cambiarEscena("ProgramadorView.fxml", 848,525);
            programador.setEscenarioPrincipal(this);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaCliente(){
        try{
            ClienteController cliente = (ClienteController)cambiarEscena("ClienteView.fxml", 1500, 633);
            cliente.setEscenarioPrincipal(this);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaProveedor(){
        try{
            ProveedorController proveedor = (ProveedorController)cambiarEscena("ProveedoresView.fxml", 1298,523);
            proveedor.setEscenarioPrincipal(this);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaAdministracion(){
        try{
            AdministracionController administracion = (AdministracionController)cambiarEscena("AdministracionView.fxml", 848,525);
            administracion.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaDepartamento(){
        try {
            DepartamentoController departamento = (DepartamentoController)cambiarEscena("DepartamentosView.fxml", 792, 489);
            departamento.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ventanaLocal(){
        try{
            LocalController local = (LocalController)cambiarEscena("LocalesView.fxml", 1177, 601);
            local.setEscenarioPrincipal(this);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaCargo(){
        try {
            CargoController cargo = (CargoController)cambiarEscena("CargosView.fxml", 792, 489);
            cargo.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ventanaHorario(){
        try {
            HorarioController horario = (HorarioController)cambiarEscena("HorariosView.fxml", 955, 650);
            horario.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ventanaTipoCliente(){
        try {
            TipoClienteController tipoCliente = (TipoClienteController)cambiarEscena("TipoClientesView.fxml", 792, 489);
            tipoCliente.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     public void ventanaCuentaPorPagar(){
        try {
            CuentaPorPagarController cuentaPorPagar = (CuentaPorPagarController)cambiarEscena("CuentasPorPagarView.fxml", 1297, 522);
            cuentaPorPagar.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     public void ventanaUsuario(){
         try{
             
         }catch(Exception e){
             e.printStackTrace();
         }
    }
    public void ventanaEmpleado(){
        try {
            EmpleadoController empleado = (EmpleadoController)cambiarEscena("EmpleadosView.fxml", 1112,475 );
            empleado.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ventanaCuentaPorCobrar(){
        try {
            CuentasPorCobrarController cuentaPorCobrar = (CuentasPorCobrarController)cambiarEscena("CuentasPorCobrarView.fxml", 1181, 522);
            cuentaPorCobrar.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ventaUsuario(){
         try{
            UsuarioController usuario = (UsuarioController)cambiarEscena("UsuarioView.fxml", 538, 400);
            usuario.setEscenarioPrincipal(this);
         }catch(Exception e){
             e.printStackTrace();
         }
     }
    public void ventanaLogin(){
         try{
            LoginController login = (LoginController)cambiarEscena("LoginView.fxml", 593, 470);
            login.setEscenarioPrincipal(this);
         }catch(Exception e){
             e.printStackTrace();
         }
     }
    public static void main(String[] args) {
        launch(args);
    }
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws IOException{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo), ancho, alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }
}
