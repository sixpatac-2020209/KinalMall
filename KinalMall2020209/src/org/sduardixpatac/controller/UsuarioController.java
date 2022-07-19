
package org.sduardixpatac.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.sduardixpatac.bean.Usuario;
import org.sduardixpatac.db.Conexion;
import org.sduardixpatac.system.Principal;

public class UsuarioController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones {NUEVO, GUARDAR, NINGUNO, CANCELAR};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    
    @FXML private TextField txtNombreUsuario;
    @FXML private TextField txtApellidoUsuario;
    @FXML private TextField txtUsuario;
    @FXML private TextField txtPassword;
    @FXML private Button btnNuevo;
    @FXML private ImageView imgNuevo;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    public void nuevo(){
        switch (tipoDeOperacion){
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnNuevo.setText ("Guardar");
                imgNuevo.setImage(new Image("/org/sduardixpatac/images/guardar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText ("Nuevo");
                imgNuevo.setImage(new Image("/org/sduardixpatac/images/Add.png"));
                tipoDeOperacion = operaciones.NINGUNO;
        }
    }
     
    public void guardar(){
        Usuario registro = new Usuario();
        registro.setNombreUsuario(txtNombreUsuario.getText());
        registro.setApellidoUsuario(txtApellidoUsuario.getText());
        registro.setUsuarioLogin(txtUsuario.getText());
        registro.setContrasena(txtPassword.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarUsuarios(?,?,?,?)}");
            procedimiento.setString(1,registro.getNombreUsuario());
            procedimiento.setString(2,registro.getApellidoUsuario());
            procedimiento.setString(3,registro.getUsuarioLogin());
            procedimiento.setString(4,registro.getContrasena());
            procedimiento.execute();
            ventanaLogin();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void Cancelar(){
        switch (tipoDeOperacion){
            case CANCELAR:
                desactivarControles();
                limpiarControles();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            case NINGUNO:
                ventanaLogin();
                break;
        }
    }
    public void desactivarControles(){
        txtNombreUsuario.setEditable(false);
        txtApellidoUsuario.setEditable(false);
        txtUsuario.setEditable(false);
        txtPassword.setEditable(false);
    }
    public void activarControles(){
        txtNombreUsuario.setEditable(true);
        txtApellidoUsuario.setEditable(true);
        txtUsuario.setEditable(true);
        txtPassword.setEditable(true);        
    }
    public void limpiarControles(){
        txtNombreUsuario.clear();
        txtApellidoUsuario.clear();
        txtUsuario.clear();
        txtPassword.clear();
    }
    public void ventanaLogin(){
        escenarioPrincipal.ventanaLogin();
    }
}
