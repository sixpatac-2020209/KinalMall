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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.sduardixpatac.bean.Administracion;
import org.sduardixpatac.bean.Cliente;
import org.sduardixpatac.bean.CuentasPorCobrar;
import org.sduardixpatac.bean.Local;
import org.sduardixpatac.db.Conexion;
import org.sduardixpatac.system.Principal;

public class CuentasPorCobrarController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR,CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<CuentasPorCobrar> listaCuentasPorCobrar;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<Cliente> listaCliente;
    private ObservableList<Local> listaLocales;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoCuentaPorCobrar;
    @FXML private TextField txtCodigoFactura;
    @FXML private TextField txtAnio;
    @FXML private TextField txtMes;
    @FXML private TextField txtValorNetoPago;
    @FXML private TextField txtEstadoPago;
    @FXML private ComboBox cmbCodigoAdministracion;
    @FXML private ComboBox cmbCodigoCliente;
    @FXML private ComboBox cmbCodigoLocal;
    @FXML private TableView tblCuentasPorCobrar;
    @FXML private TableColumn colCodigoCuentaPorCobrar;
    @FXML private TableColumn colCodigoFactura;
    @FXML private TableColumn colAnio;
    @FXML private TableColumn colMes;
    @FXML private TableColumn colValorNetoPago;
    @FXML private TableColumn colEstadoPago;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private TableColumn colCodigoCliente;
    @FXML private TableColumn colCodigoLocal;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       cargarDatos();
    }

    public void cargarDatos(){
        tblCuentasPorCobrar.setItems(getCuentasPorCobrar());
        colCodigoCuentaPorCobrar.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, Integer>("codigoCuentaPorCobrar"));
        colCodigoFactura.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, String>("codigoFactura"));
        colAnio.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, Integer>("anio"));
        colMes.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, Integer>("mes"));
        colValorNetoPago.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, Double>("valorNetoPago"));
        colEstadoPago.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, String>("estadoPago"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, Integer>("codigoAdministracion"));
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, Integer>("codigoCliente"));
        colCodigoLocal.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar, Integer>("codigoLocal"));
        
        cmbCodigoAdministracion.setItems(getAdministracion());
        cmbCodigoCliente.setItems(getCliente());
        cmbCodigoLocal.setItems(getLocales());
    }
    
    public ObservableList<CuentasPorCobrar> getCuentasPorCobrar(){
        ArrayList<CuentasPorCobrar> lista = new ArrayList <CuentasPorCobrar>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarCuentasPorCobrar()");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new CuentasPorCobrar(resultado.getInt("codigoCuentaPorCobrar"),
                                               resultado.getString("codigoFactura"),
                                               resultado.getInt("anio"),
                                               resultado.getInt("mes"),
                                               resultado.getDouble("valorNetoPago"),
                                               resultado.getString("estadoPago"),
                                               resultado.getInt("codigoAdministracion"),
                                               resultado.getInt("codigoCliente"),
                                               resultado.getInt("codigoLocal")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaCuentasPorCobrar = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Administracion> getAdministracion(){
        ArrayList<Administracion> lista = new ArrayList<Administracion>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarAdministracion()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Administracion(resultado.getInt("codigoAdministracion"),
                                                resultado.getString("direccion"),
                                                resultado.getString("telefono")));
            }
        }catch(Exception e){
            e.printStackTrace();
            }
        return listaAdministracion = FXCollections.observableArrayList(lista);
    }
    
    public Administracion buscarAdministracion(int codigoAdministracion){
        Administracion resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance(). getConexion().prepareCall("{call sp_BuscarAdministracion(?)}");
            procedimiento.setInt(1, codigoAdministracion);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                 resultado = new Administracion(registro.getInt("codigoAdministracion"),
                                            registro.getString("direccion"),
                                            registro.getString("telefono"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public ObservableList<Cliente> getCliente(){
        ArrayList<Cliente> lista = new ArrayList<Cliente>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cliente(resultado.getInt("codigoCliente"),
                                        resultado.getString("nombresCliente"),
                                        resultado.getString("apellidosCliente"),
                                        resultado.getString("telefonoCliente"),
                                        resultado.getString("direccionCliente"),
                                        resultado.getString("email"),
                                        resultado.getInt("codigoLocal"),
                                        resultado.getInt("codigoAdministracion"),
                                        resultado.getInt("codigoTipoCliente")));      
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaCliente = FXCollections.observableArrayList(lista);
    }
    
    public Cliente buscarCliente(int codigoCliente){
        Cliente resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarClientes(?)");
            procedimiento.setInt(1, codigoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Cliente(registro.getInt("codigoCliente"),
                                        registro.getString("nombresCliente"),
                                        registro.getString("apellidosCliente"),
                                        registro.getString("telefonoCliente"),
                                        registro.getString("direccionCliente"),
                                        registro.getString("email"),
                                        registro.getInt("codigoLocal"),
                                        registro.getInt("codigoAdministracion"),
                                        registro.getInt("codigoTipoCliente"));
            
            }
        }catch(Exception e){
            e.printStackTrace();
        
        }
        return resultado;
    }
    
    public ObservableList<Local> getLocales(){
        ArrayList<Local>  lista = new ArrayList<Local>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarLocales()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Local(resultado.getInt("codigoLocal"),
                                        resultado.getDouble("saldoFavor"),
                                        resultado.getDouble("saldoContra"),
                                        resultado.getInt("mesesPendientes"),
                                        resultado.getBoolean("disponibilidad"),
                                        resultado.getDouble("valorLocal"),
                                        resultado.getDouble("valorAdministracion")));      
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaLocales = FXCollections.observableArrayList(lista);
    }
    
    public Local buscarLocal(int codigoLocal){
        Local resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarLocales(?)}");
            procedimiento.setInt(1, codigoLocal);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Local(registro.getInt("codigoLocal"),
                                            registro.getDouble("saldoFavor"),
                                            registro.getDouble("saldoContra"),
                                            registro.getInt("mesesPendientes"),
                                            registro.getBoolean("disponibilidad"),
                                            registro.getDouble("valorLocal"),
                                            registro.getDouble("valorAdministracion"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }                
        return resultado;
    }
    
    public void seleccionarElemento(){
        if(tblCuentasPorCobrar.getSelectionModel().getSelectedItem() != null){
            txtCodigoCuentaPorCobrar.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorCobrar()));
            txtCodigoFactura.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoFactura()));
            txtAnio.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getAnio()));
            txtMes.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getMes()));
            txtValorNetoPago.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getValorNetoPago()));
            txtEstadoPago.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getEstadoPago()));
            
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
            cmbCodigoCliente.getSelectionModel().select(buscarCliente(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCliente()));
            cmbCodigoLocal.getSelectionModel().select(buscarLocal(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoLocal()));
        }
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                activarComboBox();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgNuevo.setImage(new Image("/org/sduardixpatac/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/sduardixpatac/images/cancelar.png"));
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
                imgNuevo.setImage(new Image("/org/sduardixpatac/images/Add.png"));
                imgEliminar.setImage(new Image("/org/sduardixpatac/images/Delete.png"));
                tipoDeOperacion= operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void guardar(){
        CuentasPorCobrar registro = new CuentasPorCobrar();
        registro.setCodigoFactura(txtCodigoFactura.getText());
        registro.setAnio(Integer.parseInt(txtAnio.getText()));
        registro.setMes(Integer.parseInt(txtMes.getText()));
        registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
        registro.setEstadoPago(txtEstadoPago.getText());
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        registro.setCodigoCliente(((Cliente)cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setCodigoLocal(((Local)cmbCodigoLocal.getSelectionModel().getSelectedItem()).getCodigoLocal());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCuentasPorCobrar(?,?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getCodigoFactura());
            procedimiento.setInt(2, registro.getAnio());
            procedimiento.setInt(3, registro.getMes());
            procedimiento.setDouble(4, registro.getValorNetoPago());
            procedimiento.setString(5, registro.getEstadoPago());
            procedimiento.setInt(6, registro.getCodigoAdministracion());
            procedimiento.setInt(7, registro.getCodigoCliente());
            procedimiento.setInt(8, registro.getCodigoLocal());
            procedimiento.execute();
            listaCuentasPorCobrar.add(registro);
//            cargarDatos();
//            tipoDeOperacion = operaciones.NINGUNO;
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
                imgNuevo.setImage(new Image("/org/sduardixpatac/images/Add.png"));
                imgEliminar.setImage(new Image("/org/sduardixpatac/images/Delete.png"));
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblCuentasPorCobrar.getSelectionModel().getSelectedItem()!= null){
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?","Eliminar Cuentas Por Cobrar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                       try{
                           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCuentasPorCobrar(?)}");
                           procedimiento.setInt(1,((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorCobrar()); 
                           procedimiento.execute();
                           listaCuentasPorCobrar.remove(tblCuentasPorCobrar.getSelectionModel().getSelectedIndex());
                           limpiarControles();
                       }catch(Exception e){
                           e.printStackTrace();
                       }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
            }
        }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblCuentasPorCobrar.getSelectionModel().getSelectedItem() != null){
                 btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/sduardixpatac/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/sduardixpatac/images/cancelar.png"));
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                
                }else{JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
                
                }
                break;
                
            case ACTUALIZAR:
                actualizar();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/sduardixpatac/images/Edit.png"));
                imgReporte.setImage(new Image("/org/sduardixpatac/images/Report.png"));
                limpiarControles();
                desactivarControles();
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void generarReporte(){
        switch (tipoDeOperacion){
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
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCuentasPorCobrar(?,?,?,?,?,?)}");
            CuentasPorCobrar registro = (CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem();
            registro.setCodigoFactura(txtCodigoFactura.getText());
            registro.setAnio(Integer.parseInt(txtAnio.getText()));
            registro.setMes(Integer.parseInt(txtMes.getText()));
            registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
            registro.setEstadoPago(txtEstadoPago.getText());
            procedimiento.setInt(1, registro.getCodigoCuentaPorCobrar());
            procedimiento.setString(2, registro.getCodigoFactura());
            procedimiento.setInt(3, registro.getAnio());
            procedimiento.setInt(4, registro.getMes());
            procedimiento.setDouble(5, registro.getValorNetoPago());
            procedimiento.setString(6, registro.getEstadoPago());
            procedimiento.execute();
            listaCuentasPorCobrar.add(registro);
            cargarDatos();
            tipoDeOperacion = operaciones.NINGUNO;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
        txtCodigoCuentaPorCobrar.setEditable(false);
        txtCodigoFactura.setEditable(false);
        txtAnio.setEditable(false);
        txtMes.setEditable(false);
        txtValorNetoPago.setEditable(false);
        txtEstadoPago.setEditable(false);
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoCliente.setDisable(true);
        cmbCodigoLocal.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoCuentaPorCobrar.setEditable(true);
        txtCodigoFactura.setEditable(true);
        txtAnio.setEditable(true);
        txtMes.setEditable(true);
        txtValorNetoPago.setEditable(true);
        txtEstadoPago.setEditable(true);
    }
    
    public void activarComboBox(){
        cmbCodigoAdministracion.setDisable(false);
        cmbCodigoCliente.setDisable(false);
        cmbCodigoLocal.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoCuentaPorCobrar.clear();
        txtCodigoFactura.clear();
        txtAnio.clear();
        txtMes.clear();
        txtValorNetoPago.clear();
        txtEstadoPago.clear();
        cmbCodigoAdministracion.setValue(null);
        cmbCodigoCliente.setValue(null);
        cmbCodigoLocal.setValue(null);
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
    public void ventanaLocal(){
        escenarioPrincipal.ventanaLocal();
    }
}
