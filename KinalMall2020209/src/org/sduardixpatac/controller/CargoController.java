package org.sduardixpatac.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import org.sduardixpatac.bean.Cargo;
import org.sduardixpatac.db.Conexion;
import org.sduardixpatac.system.Principal;

public class CargoController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones {NUEVO,GUARDAR,ELIMINAR,ACTUALIZAR,CANCELAR,NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Cargo> listaCargos;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoCargo;
    @FXML private TextField txtNombreCargo;
    @FXML private TableView tblCargos;
    @FXML private TableColumn colCodigoCargo;
    @FXML private TableColumn colNombreCargo;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    public void cargarDatos(){
        tblCargos.setItems(getCargo());
        colCodigoCargo.setCellValueFactory(new PropertyValueFactory<Cargo, Integer>("codigoCargo"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargo, String>("nombreCargo"));
    }
    public ObservableList<Cargo>getCargo(){
        ArrayList<Cargo> lista = new ArrayList<Cargo>();
        try {
                PreparedStatement procedimiento  = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargos()}");
                ResultSet resultado = procedimiento.executeQuery();
                while (resultado.next()){
                    lista.add(new Cargo(resultado.getInt("codigoCargo"), 
                                        resultado.getString("nombreCargo")));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return listaCargos = FXCollections.observableArrayList(lista);
    }
    public void seleccionarElemento(){
        if (tblCargos.getSelectionModel().getSelectedItem() != null) {
            txtCodigoCargo.setText(String.valueOf(((Cargo)tblCargos.getSelectionModel().getSelectedItem()).getCodigoCargo()));
            txtNombreCargo.setText(((Cargo)tblCargos.getSelectionModel().getSelectedItem()).getNombreCargo());
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
        Cargo registro = new Cargo();
        registro.setNombreCargo(txtNombreCargo.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCargos(?)}");
            procedimiento.setString(1, registro.getNombreCargo());
            procedimiento.execute();
            listaCargos.add(registro);
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
                if(tblCargos.getSelectionModel().getSelectedItem()!= null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar Cargo", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCargos(?)}");
                            procedimiento.setInt(1,((Cargo) tblCargos.getSelectionModel().getSelectedItem()).getCodigoCargo());
                            procedimiento.execute();
                            listaCargos.remove(tblCargos.getSelectionModel().getSelectedIndex());
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
                if(tblCargos.getSelectionModel().getSelectedItem() != null){
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarDepartamentos(?,?)}");
            Cargo registro = (Cargo)tblCargos.getSelectionModel().getSelectedItem();
            registro.setNombreCargo(txtNombreCargo.getText());
            procedimiento.setInt(1, registro.getCodigoCargo());
            procedimiento.setString(2, registro.getNombreCargo());
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
                    //Informe
                    break;
            }
        }
    public void desactivarControles(){
        txtCodigoCargo.setEditable(false);
        txtNombreCargo.setEditable(false);
    }
    public void activarControles(){
        txtCodigoCargo.setEditable(false);
        txtNombreCargo.setEditable(true);
    }
    public void limpiarControles(){
        txtCodigoCargo.clear();
        txtCodigoCargo.clear();
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
    public void ventanaEmpleado(){
        escenarioPrincipal.ventanaEmpleado();
    }
}
