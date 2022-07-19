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
import org.sduardixpatac.bean.Horario;
import org.sduardixpatac.db.Conexion;
import org.sduardixpatac.system.Principal;

public class HorarioController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Horario> listaHorarios;
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoHorario;
    @FXML private TextField txtHorarioEntrada;
    @FXML private TextField txtHorarioSalida;
    @FXML private TextField txtLunes;
    @FXML private TextField txtMartes;
    @FXML private TextField txtMiercoles;
    @FXML private TextField txtJueves;
    @FXML private TextField txtViernes;
    @FXML private TableView tblHorarios;
    @FXML private TableColumn colCodigoHorario;
    @FXML private TableColumn colHorarioEntrada;
    @FXML private TableColumn colHorarioSalida;
    @FXML private TableColumn colLunes;
    @FXML private TableColumn colMartes;
    @FXML private TableColumn colMiercoles;
    @FXML private TableColumn colJueves;
    @FXML private TableColumn colViernes;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
     public void cargarDatos(){
        tblHorarios.setItems(getHorario());
        colCodigoHorario.setCellValueFactory(new PropertyValueFactory<Horario,Integer>("codigoHorario"));
        colHorarioEntrada.setCellValueFactory(new PropertyValueFactory<Horario,String>("horarioEntrada"));
        colHorarioSalida.setCellValueFactory(new PropertyValueFactory<Horario,String>("horarioSalida"));
        colLunes.setCellValueFactory(new PropertyValueFactory<Horario,Boolean>("lunes"));
        colMartes.setCellValueFactory(new PropertyValueFactory<Horario,Boolean>("martes"));
        colMiercoles.setCellValueFactory(new PropertyValueFactory<Horario,Boolean>("miercoles"));
        colJueves.setCellValueFactory(new PropertyValueFactory<Horario,Boolean>("jueves"));
        colViernes.setCellValueFactory(new PropertyValueFactory<Horario,Boolean>("viernes"));
        
    }
    public ObservableList<Horario>getHorario(){
        ArrayList<Horario> lista = new ArrayList<Horario>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarHorarios()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Horario(resultado.getInt("codigoHorario"),
                                        resultado.getString("horarioEntrada"),
                                        resultado.getString("horarioSalida"),
                                        resultado.getBoolean("lunes"),
                                        resultado.getBoolean("martes"),
                                        resultado.getBoolean("miercoles"),
                                        resultado.getBoolean("jueves"),
                                        resultado.getBoolean("viernes")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaHorarios =FXCollections.observableArrayList(lista);
    }
    public void seleccionarElemento(){
        if (tblHorarios.getSelectionModel().getSelectedItem() != null) {
            txtCodigoHorario.setText(String.valueOf(((Horario)tblHorarios.getSelectionModel().getSelectedItem()).getCodigoHorario()));
            txtHorarioEntrada.setText(((Horario)tblHorarios.getSelectionModel().getSelectedItem()).getHorarioEntrada());
            txtHorarioSalida.setText(((Horario)tblHorarios.getSelectionModel().getSelectedItem()).getHorarioSalida());
            txtLunes.setText(String.valueOf(((Horario)tblHorarios.getSelectionModel().getSelectedItem()).isLunes()));
            txtMartes.setText(String.valueOf(((Horario)tblHorarios.getSelectionModel().getSelectedItem()).isMartes()));
            txtMiercoles.setText(String.valueOf(((Horario)tblHorarios.getSelectionModel().getSelectedItem()).isMiercoles()));
            txtJueves.setText(String.valueOf(((Horario)tblHorarios.getSelectionModel().getSelectedItem()).isJueves()));
            txtViernes.setText(String.valueOf(((Horario)tblHorarios.getSelectionModel().getSelectedItem()).isViernes()));
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
        Horario registro = new Horario();
        registro.setHorarioEntrada(txtHorarioEntrada.getText());
        registro.setHorarioSalida(txtHorarioSalida.getText());
        registro.setLunes(Boolean.valueOf(txtLunes.getText()));
        registro.setMartes(Boolean.valueOf(txtMartes.getText()));
        registro.setMiercoles(Boolean.valueOf(txtMiercoles.getText()));
        registro.setJueves(Boolean.valueOf(txtJueves.getText()));
        registro.setViernes(Boolean.valueOf(txtViernes.getText()));
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarHorarios(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getHorarioEntrada());
            procedimiento.setString(2, registro.getHorarioSalida());
            procedimiento.setBoolean(3, registro.isLunes());
            procedimiento.setBoolean(4, registro.isMartes());
            procedimiento.setBoolean(5, registro.isMiercoles());
            procedimiento.setBoolean(6, registro.isJueves());
            procedimiento.setBoolean(7, registro.isViernes());
            procedimiento.execute();
            listaHorarios.add(registro);
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
                if(tblHorarios.getSelectionModel().getSelectedItem()!= null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar Horarios", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarDepartamentos(?)}");
                            procedimiento.setInt(1,((Horario) tblHorarios.getSelectionModel().getSelectedItem()).getCodigoHorario());
                            procedimiento.execute();
                            listaHorarios.remove(tblHorarios.getSelectionModel().getSelectedIndex());
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
                if(tblHorarios.getSelectionModel().getSelectedItem() != null){
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarHorarios(?,?,?,?,?,?,?)}");
            Horario registro = (Horario)tblHorarios.getSelectionModel().getSelectedItem();
            registro.setHorarioEntrada(txtHorarioEntrada.getText());
            registro.setHorarioSalida(txtHorarioSalida.getText());
            registro.setLunes(Boolean.valueOf(txtLunes.getText()));
            registro.setMartes(Boolean.valueOf(txtMartes.getText()));
            registro.setMiercoles(Boolean.valueOf(txtMiercoles.getText()));
            registro.setJueves(Boolean.valueOf(txtJueves.getText()));
            registro.setViernes(Boolean.valueOf(txtViernes.getText()));
            procedimiento.setString(1, registro.getHorarioEntrada());
            procedimiento.setString(2, registro.getHorarioSalida());
            procedimiento.setBoolean(3, registro.isLunes());
            procedimiento.setBoolean(4, registro.isMartes());
            procedimiento.setBoolean(5, registro.isMiercoles());
            procedimiento.setBoolean(6, registro.isJueves());
            procedimiento.setBoolean(7, registro.isViernes());
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
        txtCodigoHorario.setEditable(false);
        txtHorarioEntrada.setEditable(false);
        txtHorarioSalida.setEditable(false);
        txtLunes.setEditable(false);
        txtMartes.setEditable(false);
        txtMiercoles.setEditable(false);
        txtJueves.setEditable(false);
        txtViernes.setEditable(false);
    }
    public void activarControles(){
        txtCodigoHorario.setEditable(false);
        txtHorarioEntrada.setEditable(true);
        txtHorarioSalida.setEditable(true);
        txtLunes.setEditable(true);
        txtMartes.setEditable(true);
        txtMiercoles.setEditable(true);
        txtJueves.setEditable(true);
        txtViernes.setEditable(true);
    }
    public void limpiarControles(){
        txtCodigoHorario.clear();
        txtHorarioEntrada.clear();
        txtHorarioSalida.clear();
        txtLunes.clear();
        txtMartes.clear();
        txtMiercoles.clear();
        txtJueves.clear();
        txtViernes.clear();
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
}
