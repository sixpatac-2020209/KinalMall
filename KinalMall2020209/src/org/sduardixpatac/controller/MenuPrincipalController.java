package org.sduardixpatac.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.sduardixpatac.system.Principal;

public class MenuPrincipalController implements Initializable {
    private Principal escenarioPrincipal;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    public void ventanaProgramador(){
        escenarioPrincipal.ventanaProgramador();
    }
    public void ventanaAdministracion(){
        escenarioPrincipal.ventanaAdministracion();
    }
    public void ventanaDepartamento(){
        escenarioPrincipal.ventanaDepartamento();
    }
    public void ventanaTipoCliente(){
        escenarioPrincipal.ventanaTipoCliente();
    }
    public void ventanaLocal(){
        escenarioPrincipal.ventanaLocal();
    }
    public void ventanaCargo(){
        escenarioPrincipal.ventanaCargo();
    }
    public void ventanaHorario(){
        escenarioPrincipal.ventanaHorario();
    }
    public void ventanaCliente(){
        escenarioPrincipal.ventanaCliente();
    }
    public void ventanaProveedor(){
        escenarioPrincipal.ventanaProveedor();
    }
    public void ventanaCuentaPorPagar(){
        escenarioPrincipal.ventanaCuentaPorPagar();
    }
    public void ventanaEmpleado(){
        escenarioPrincipal.ventanaEmpleado();
    }
    public void ventanaCuentaPorCobrar(){
        escenarioPrincipal.ventanaCuentaPorCobrar();
    }
    public void ventanaUsuario(){
        escenarioPrincipal.ventanaUsuario();
    }
    public void ventanaLogin(){
        escenarioPrincipal.ventanaLogin();
    }
}
