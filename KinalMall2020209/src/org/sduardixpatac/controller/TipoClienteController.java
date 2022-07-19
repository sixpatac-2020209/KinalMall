
package org.sduardixpatac.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.sduardixpatac.bean.Departamento;
import org.sduardixpatac.bean.TipoCliente;
import org.sduardixpatac.db.Conexion;
import org.sduardixpatac.report.GenerarReporte;
import org.sduardixpatac.system.Principal;

public class TipoClienteController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<TipoCliente> listaTipoCliente;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoTipoCliente;
    @FXML private TextField txtDescipcion;
    @FXML private TableView tblTipoClientes;
    @FXML private TableColumn colCodigoTipoCliente;
    @FXML private TableColumn colDescripcion;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    public void cargarDatos(){
        tblTipoClientes.setItems(getTipoCliente());
        colCodigoTipoCliente.setCellValueFactory(new PropertyValueFactory<TipoCliente, Integer>("codigoTipoCliente"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoCliente, String>("descripcion"));
    }
    public ObservableList<TipoCliente>getTipoCliente(){
        ArrayList<TipoCliente> lista = new ArrayList<TipoCliente>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoCliente(resultado.getInt("codigoTipoCliente"),
                                            resultado.getString("descripcion")));   
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTipoCliente = FXCollections.observableArrayList(lista);
    }
    public void seleccionarElemento(){
        if (tblTipoClientes.getSelectionModel().getSelectedItem() != null){
            txtCodigoTipoCliente.setText(String.valueOf(((TipoCliente)tblTipoClientes.getSelectionModel().getSelectedItem()).getCodigoTipoCliente()));
            txtDescipcion.setText(((TipoCliente)tblTipoClientes.getSelectionModel().getSelectedItem()).getDescripcion());
        }
    }
   public void nuevo(){
        switch (tipoDeOperacion){
            case NINGUNO:
                activarControles();
                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image("/org/sduardixpatac/images/guardar.png"));
                btnEliminar.setText("Cancelar");
                imgEliminar.setImage(new Image("/org/sduardixpatac/images/cancelar.png"));
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image ("/org/sduardixpatac/images/Add.png"));
                imgEliminar.setImage(new Image ("/org/sduardixpatac/images/Delete.png"));
                tipoDeOperacion= operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    public void guardar(){
        TipoCliente registro = new TipoCliente();
        registro.setDescripcion(txtDescipcion.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoClientes(?)}");
            procedimiento.setString(1, registro.getDescripcion());
            procedimiento.execute();
            listaTipoCliente.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                imgNuevo.setImage(new Image ("/org/sduardixpatac/images/Add.png"));
                imgEliminar.setImage(new Image ("/org/sduardixpatac/images/Delete.png"));
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblTipoClientes.getSelectionModel().getSelectedItem()!= null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar Tipo Cliente", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoClientes(?)}");
                            procedimiento.setInt(1,((TipoCliente) tblTipoClientes.getSelectionModel().getSelectedItem()).getCodigoTipoCliente());
                            procedimiento.execute();
                            listaTipoCliente.remove(tblTipoClientes.getSelectionModel().getSelectedIndex());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
                }
        }
    }
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblTipoClientes.getSelectionModel().getSelectedItem() != null){
                    activarControles();
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/sduardixpatac/images/Update.png"));
                    imgReporte.setImage(new Image("/org/sduardixpatac/images/cancelar.png"));
                    tipoDeOperacion= operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
                }
                break;    
            case ACTUALIZAR:
                actualizar();
                limpiarControles();
                desactivarControles();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/sduardixpatac/images/Edit.png"));
                imgReporte.setImage(new Image("/org/sduardixpatac/images/Report.png"));
                tipoDeOperacion= operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarTipoClientes(?,?)}");
            TipoCliente registro = (TipoCliente)tblTipoClientes.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescipcion.getText());
            procedimiento.setInt(1, registro.getCodigoTipoCliente());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
        public void reporte(){
            switch(tipoDeOperacion){
                case ACTUALIZAR:
                    desactivarControles();
                    limpiarControles();
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    imgEditar.setImage(new Image("/org/sduardixpatac/images/Edit.png"));
                    imgReporte.setImage(new Image("/org/sduardixpatac/images/Report.png"));
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    tipoDeOperacion = operaciones.NINGUNO;
                    break;
                case NINGUNO:
                    imprimirReporte();
                    break;
            }
        }
    public void imprimirReporte(){
        Map parametros = new HashMap();
        int codTC = ((TipoCliente)tblTipoClientes.getSelectionModel().getSelectedItem()).getCodigoTipoCliente();
        parametros.put("codTC", codTC );
        GenerarReporte.mostrarReporte("ReporteTipoCliente.jasper","Reporte TipoCliente" , parametros);
    }
    public void desactivarControles(){
        txtCodigoTipoCliente.setEditable(false);
        txtDescipcion.setEditable(false);
    }
    public void activarControles(){
        txtCodigoTipoCliente.setEditable(false);
        txtDescipcion.setEditable(true);
    }
    public void limpiarControles(){
        txtCodigoTipoCliente.clear();
        txtDescipcion.clear();
    }
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    public void ventanaCliente(){
        escenarioPrincipal.ventanaCliente();
    }
}
