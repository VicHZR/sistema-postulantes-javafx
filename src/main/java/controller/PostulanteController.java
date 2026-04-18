package controller;

import dao.DashboardDAO;
import dao.PostulanteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import model.Postulante;
import model.Usuario;
import util.Validador;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class PostulanteController {

    // ========= ELEMENTOS FXML: FORMULARIO REGISTRO Y EDICIÓN =========
    @FXML 
    private TextField txtNombre;

    @FXML 
    private TextField txtApellidoP;

    @FXML 
    private TextField txtApellidoM;

    @FXML 
    private TextField txtDni;

    @FXML 
    private TextField txtCorreo;

    @FXML 
    private TextField txtCelular;

    @FXML 
    private TextField txtUbigeo;

    @FXML 
    private TextField txtNuevoCorreo;

    @FXML 
    private ComboBox<String> cmbSexo;

    @FXML 
    private Button btnGuardar;

    @FXML 
    private Label lblMensaje;

    // ========= ELEMENTOS FXML: LISTADO Y TABLA =========
    @FXML 
    private TableView<Postulante> tblPostulantes;

    @FXML 
    private TableColumn<Postulante, Integer> colId;

    @FXML 
    private TableColumn<Postulante, String> colNombre;

    @FXML 
    private TableColumn<Postulante, String> colApellidoP;

    @FXML 
    private TableColumn<Postulante, String> colDni;

    @FXML 
    private TableColumn<Postulante, String> colCorreo;

    @FXML 
    private TableColumn<Postulante, String> colSexo;

    // ========= ELEMENTOS FXML: FILTROS =========
    @FXML 
    private TextField txtFiltroDni;

    @FXML 
    private TextField txtFiltroNombre;

    @FXML 
    private ComboBox<String> cmbFiltroSexo;

    @FXML 
    private ComboBox<String> cmbFiltroUbigeo;

    // ========= ELEMENTOS FXML: BOTONES DE ACCIÓN =========
    @FXML 
    private Button btnVerDetalle;

    @FXML 
    private Button btnEditar;

    @FXML 
    private Button btnEliminar;

    @FXML 
    private Button btnExportar;

    @FXML 
    private Button btnEditarCorreo;

    // ========= ELEMENTOS FXML: DASHBOARD =========
    @FXML 
    private Label lblTotalPostulantes;

    @FXML 
    private Label lblTotalMasculino;

    @FXML 
    private Label lblTotalFemenino;

    @FXML 
    private Label lblTotalUbigeos;

    // ========= ELEMENTOS FXML: FICHA DE DETALLE =========
    @FXML 
    private Label lblNombre;

    @FXML 
    private Label lblDni;

    @FXML 
    private Label lblCorreo;

    @FXML 
    private Label lblTelefono;

    @FXML 
    private Label lblSexo;

    @FXML 
    private Label lblUbigeo;

    // ========= PROPIEDADES DE LÓGICA Y ESTADO =========
    private final DashboardDAO dashboardDAO = new DashboardDAO();
    private final PostulanteDAO dao = new PostulanteDAO();
    private static Postulante seleccionado; 
    private FilteredList<Postulante> filteredData;

    private static boolean modoEdicion = false;
    private static int idPostulanteEdicion;

    private Usuario usuarioActivo;

    // ========= MÉTODOS DE SEGURIDAD Y SESIÓN =========
    public void setUsuarioActivo(Usuario u) {
        this.usuarioActivo = u;
        aplicarPermisos();
    }

    private void aplicarPermisos() {
        if (usuarioActivo == null) return;

        // Solo el rol ADMIN puede realizar cambios (Guardar, Editar, Eliminar)
        boolean esAdmin = "ADMIN".equals(usuarioActivo.getRol());

        if (btnEditar != null) {
            btnEditar.setDisable(!esAdmin);
        }
        if (btnEliminar != null) {
            btnEliminar.setDisable(!esAdmin);
        }
        if (btnGuardar != null) {
            btnGuardar.setDisable(!esAdmin);
        }
    }

    // ========= INICIALIZACIÓN =========
    @FXML
    public void initialize() {
        // 1. Configurar ComboBox de Sexo
        if (cmbSexo != null) {
            cmbSexo.getItems().setAll("M", "F");
        }

        // 2. Configurar Listado si la tabla existe
        if (tblPostulantes != null) {
            cargarTabla();
            configurarEstadoBotonesListado();
        }

        // 3. Configurar Validaciones si es el formulario
        if (btnGuardar != null) {
            configurarValidacionesVisuales();
        }

        // 4. Cargar Dashboard si es la pantalla principal
        if (lblTotalPostulantes != null) {
            cargarDashboard();
        }

        // 5. Cargar datos si es la ventana de detalle
        if (lblNombre != null && seleccionado != null) {
            mostrarDatosEnDetalle();
        }
    }

    // ========= LÓGICA DE APARIENCIA =========
    @FXML
    private void temaClaro() {
        cambiarTema("/view/light.css");
    }

    @FXML
    private void temaOscuro() {
        cambiarTema("/view/dark.css");
    }

    private void cambiarTema(String rutaCss) {
        Scene scene = null;
        if (lblTotalPostulantes != null) {
            scene = lblTotalPostulantes.getScene();
        } else if (tblPostulantes != null) {
            scene = tblPostulantes.getScene();
        }

        if (scene != null) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource(rutaCss).toExternalForm());
        }
    }

    // ========= VALIDACIONES VISUALES =========
    private void configurarValidacionesVisuales() {
        if (txtNombre != null) {
            txtNombre.textProperty().addListener((obs, old, nuevo) -> validarFormulario());
            txtApellidoP.textProperty().addListener((obs, old, nuevo) -> validarFormulario());
            txtDni.textProperty().addListener((obs, old, nuevo) -> validarFormulario());
            txtCorreo.textProperty().addListener((obs, old, nuevo) -> validarFormulario());
            cmbSexo.valueProperty().addListener((obs, old, nuevo) -> validarFormulario());
        }
        validarFormulario();
    }

    private void validarFormulario() {
        if (btnGuardar == null) return;

        boolean nomVal = txtNombre.getText().trim().length() >= 3;
        boolean apeVal = txtApellidoP.getText().trim().length() >= 3;
        boolean dniVal = txtDni.getText().matches("\\d{8}");
        boolean correoVal = txtCorreo.getText().contains("@");
        boolean sexoVal = cmbSexo.getValue() != null;

        marcarCampo(txtNombre, nomVal);
        marcarCampo(txtApellidoP, apeVal);
        marcarCampo(txtDni, dniVal);
        marcarCampo(txtCorreo, correoVal);

        boolean esAdmin = usuarioActivo != null && "ADMIN".equals(usuarioActivo.getRol());
        btnGuardar.setDisable(!(nomVal && apeVal && dniVal && correoVal && sexoVal) || !esAdmin);
    }

    private void marcarCampo(Control c, boolean valido) {
        if (valido) {
            c.setStyle("-fx-border-color: green; -fx-border-width: 1px;");
        } else {
            c.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        }
    }

    // ========= LÓGICA DE TABLA Y FILTROS =========
    public void cargarTabla() {
        try {
            ObservableList<Postulante> data = FXCollections.observableArrayList(dao.listarTabla());
            filteredData = new FilteredList<>(data, p -> true);

            colId.setCellValueFactory(new PropertyValueFactory<>("idPostulante"));
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colApellidoP.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
            colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
            colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
            colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));

            configurarListenersFiltros();

            SortedList<Postulante> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tblPostulantes.comparatorProperty());
            tblPostulantes.setItems(sortedData);
        } catch (Exception e) {
            alerta("Error", "No se pudo cargar la tabla: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void configurarListenersFiltros() {
        if (cmbFiltroSexo != null) {
            cmbFiltroSexo.getItems().setAll("Todos", "M", "F");
            cmbFiltroSexo.setValue("Todos");
            cmbFiltroSexo.valueProperty().addListener((o, old, n) -> aplicarFiltros());
        }
        if (txtFiltroDni != null) {
            txtFiltroDni.textProperty().addListener((o, old, n) -> aplicarFiltros());
        }
        if (txtFiltroNombre != null) {
            txtFiltroNombre.textProperty().addListener((o, old, n) -> aplicarFiltros());
        }
    }

    private void aplicarFiltros() {
        if (filteredData == null) return;
        filteredData.setPredicate(p -> {
            if (txtFiltroDni != null && !txtFiltroDni.getText().isEmpty() && !p.getDni().contains(txtFiltroDni.getText())) {
                return false;
            }
            if (txtFiltroNombre != null && !txtFiltroNombre.getText().isEmpty()) {
                String filtro = txtFiltroNombre.getText().toLowerCase();
                if (!p.getNombre().toLowerCase().contains(filtro) && !p.getApellidoPaterno().toLowerCase().contains(filtro)) {
                    return false;
                }
            }
            if (cmbFiltroSexo != null && !"Todos".equals(cmbFiltroSexo.getValue()) && !p.getSexo().equals(cmbFiltroSexo.getValue())) {
                return false;
            }
            return true;
        });
    }

    @FXML
    private void limpiarFiltros() {
        if (txtFiltroDni != null) txtFiltroDni.clear();
        if (txtFiltroNombre != null) txtFiltroNombre.clear();
        if (cmbFiltroSexo != null) cmbFiltroSexo.setValue("Todos");
    }

    private void configurarEstadoBotonesListado() {
        if (tblPostulantes == null) return;
        
        btnVerDetalle.setDisable(true);
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);

        tblPostulantes.getSelectionModel().selectedItemProperty().addListener((obs, ant, nuevo) -> {
            boolean haySeleccion = nuevo != null;
            btnVerDetalle.setDisable(!haySeleccion);
            
            boolean esAdmin = usuarioActivo != null && "ADMIN".equals(usuarioActivo.getRol());
            btnEditar.setDisable(!haySeleccion || !esAdmin);
            btnEliminar.setDisable(!haySeleccion || !esAdmin);
        });
    }

    // ========= FUNCIONALIDADES CRUD =========
    @FXML
    private void guardarPostulante() {
        try {
            Postulante p = new Postulante();
            p.setNombre(txtNombre.getText());
            p.setApellidoPaterno(txtApellidoP.getText());
            p.setApellidoMaterno(txtApellidoM.getText());
            p.setDni(txtDni.getText());
            p.setCorreo(txtCorreo.getText());
            p.setTelefonoCelular(txtCelular.getText());
            p.setSexo(cmbSexo.getValue());
            p.setUbigeo(txtUbigeo.getText());

            Validador.dni(p.getDni());

            if (modoEdicion) {
                p.setIdPostulante(idPostulanteEdicion);
                dao.actualizar(p);
                alerta("Éxito", "Postulante actualizado correctamente.", Alert.AlertType.INFORMATION);
                modoEdicion = false;
                cerrarVentanaActual(txtNombre);
            } else {
                if (dao.existeDni(p.getDni())) {
                    alerta("Aviso", "El DNI ingresado ya existe.", Alert.AlertType.WARNING);
                    return;
                }
                dao.insertar(p);
                alerta("Éxito", "Postulante registrado correctamente.", Alert.AlertType.INFORMATION);
            }
            limpiarFormulario();
        } catch (Exception e) {
            alerta("Error", "Fallo en la operación: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void editarPostulante() throws Exception {
        seleccionado = tblPostulantes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            alerta("Aviso", "Debe seleccionar un postulante.", Alert.AlertType.WARNING);
            return;
        }
        abrirVentanaCompleta("/view/PostulanteForm.fxml", "Editar Postulante");
    }

    public void cargarFormularioEdicion(Postulante p) {
        modoEdicion = true;
        idPostulanteEdicion = p.getIdPostulante();

        txtNombre.setText(p.getNombre());
        txtApellidoP.setText(p.getApellidoPaterno());
        txtApellidoM.setText(p.getApellidoMaterno());
        txtDni.setText(p.getDni());
        txtDni.setDisable(true);
        txtCorreo.setText(p.getCorreo());
        txtCelular.setText(p.getTelefonoCelular());
        cmbSexo.setValue(p.getSexo());
        txtUbigeo.setText(p.getUbigeo());
        
        if (btnGuardar != null) btnGuardar.setText("Actualizar Datos");
    }

    @FXML
    private void eliminarPostulante() {
        Postulante p = tblPostulantes.getSelectionModel().getSelectedItem();
        if (p == null) return;

        if (confirmar("¿Está seguro de eliminar a " + p.getNombre() + "?")) {
            try {
                dao.eliminar(p.getIdPostulante());
                cargarTabla();
                alerta("Éxito", "Registro eliminado.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                alerta("Error", "No se pudo eliminar: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    // ========= EXPORTACIÓN Y CONSULTAS =========
    @FXML
    private void exportarCSV() {
        if (tblPostulantes.getItems().isEmpty()) {
            alerta("Aviso", "No hay datos para exportar.", Alert.AlertType.WARNING);
            return;
        }
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Guardar CSV");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo CSV", "*.csv"));
            File file = fc.showSaveDialog(tblPostulantes.getScene().getWindow());
            
            if (file == null) return;

            List<Postulante> lista = tblPostulantes.getItems();
            FileWriter writer = new FileWriter(file);
            writer.write("ID,Nombre,Apellido,DNI,Correo,Sexo,Ubigeo\n");

            for (Postulante pos : lista) {
                writer.write(pos.getIdPostulante() + "," + pos.getNombre() + "," + pos.getApellidoPaterno() + "," + 
                             pos.getDni() + "," + pos.getCorreo() + "," + pos.getSexo() + "," + pos.getUbigeo() + "\n");
            }
            writer.close();
            alerta("Éxito", "Archivo exportado con éxito.", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            alerta("Error", "Fallo al exportar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void consultarUbigeos() {
        try {
            List<String> ubis = dao.obtenerUbigeosValidos();
            int limite = Math.min(ubis.size(), 15);
            String mensaje = String.join("\n", ubis.subList(0, limite));
            alerta("Ubigeos Válidos", "Muestra de códigos aceptados:\n" + mensaje, Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            alerta("Error", "No se pudo obtener ubigeos.", Alert.AlertType.ERROR);
        }
    }

    // ========= GESTIÓN DE VENTANAS Y CIERRES =========
    @FXML
    private void verDetallePostulante() throws Exception {
        seleccionado = tblPostulantes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            alerta("Aviso", "Seleccione un postulante.", Alert.AlertType.WARNING);
            return;
        }
        abrirVentanaCompleta("/view/PostulanteDetalle.fxml", "Ficha de Detalle");
    }

    private void mostrarDatosEnDetalle() {
        lblNombre.setText(seleccionado.getNombre() + " " + seleccionado.getApellidoPaterno());
        lblDni.setText(seleccionado.getDni());
        lblCorreo.setText(seleccionado.getCorreo());
        lblTelefono.setText(seleccionado.getTelefonoCelular());
        lblSexo.setText(seleccionado.getSexo());
        lblUbigeo.setText(seleccionado.getUbigeo());
    }

    @FXML private void abrirFormularioRegistro() throws Exception { modoEdicion = false; abrirVentanaCompleta("/view/PostulanteForm.fxml", "Registro"); }
    @FXML private void abrirListado() throws Exception { abrirVentanaCompleta("/view/PostulanteListView.fxml", "Listado"); }

    @FXML
    private void mostrarActualizarCorreo() throws Exception {
        seleccionado = tblPostulantes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            abrirVentanaCompleta("/view/ActualizarCorreo.fxml", "Actualizar Correo");
        }
    }

    @FXML
    private void confirmarActualizarCorreo() {
        try {
            if (txtNuevoCorreo.getText().isEmpty()) {
                alerta("Error", "El correo no puede estar vacío.", Alert.AlertType.ERROR);
                return;
            }
            dao.actualizarCorreo(seleccionado.getIdPostulante(), txtNuevoCorreo.getText());
            alerta("Éxito", "Correo actualizado.", Alert.AlertType.INFORMATION);
            cerrarModal();
            cargarTabla(); 
        } catch (Exception e) {
            alerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void abrirVentanaCompleta(String ruta, String titulo) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
        Parent root = loader.load();
        PostulanteController c = loader.getController();
        c.setUsuarioActivo(this.usuarioActivo);
        abrirEscenaEnStage(root, titulo);
    }

    private void abrirEscenaEnStage(Parent root, String titulo) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/view/light.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(titulo);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML private void cerrarModal() { cerrarVentanaActual(txtNuevoCorreo); }
    @FXML private void cerrarDetalle() { cerrarVentanaActual(lblNombre); }
    @FXML private void cerrarModalListado() { cerrarVentanaActual(tblPostulantes); }
    @FXML private void cerrarAplicacion() { System.exit(0); }

    private void cerrarVentanaActual(Control c) {
        if (c != null && c.getScene() != null) {
            ((Stage) c.getScene().getWindow()).close();
        }
    }

    // ========= DASHBOARD Y ALERTAS =========
    private void cargarDashboard() {
        try {
            lblTotalPostulantes.setText(String.valueOf(dashboardDAO.totalPostulantes()));
            lblTotalMasculino.setText(String.valueOf(dashboardDAO.totalPorSexo("M")));
            lblTotalFemenino.setText(String.valueOf(dashboardDAO.totalPorSexo("F")));
            lblTotalUbigeos.setText(String.valueOf(dashboardDAO.totalUbigeosUsados()));
        } catch (Exception e) {
            System.err.println("Dashboard error.");
        }
    }

    private void alerta(String t, String m, Alert.AlertType tipo) {
        Alert a = new Alert(tipo);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }

    private boolean confirmar(String m) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, m, ButtonType.YES, ButtonType.NO);
        return a.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }

    @FXML
    private void limpiarFormulario() {
        if (txtNombre != null) {
            txtNombre.clear();
            txtApellidoP.clear();
            txtDni.clear();
            txtCorreo.clear();
            txtUbigeo.clear();
            if (cmbSexo != null) cmbSexo.setValue(null);
            txtDni.setDisable(false);
            validarFormulario();
        }
    }
}
