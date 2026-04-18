package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Usuario;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblMensaje;

    @FXML
    private void login() {
        String user = txtUsuario.getText().trim();
        String pass = txtPassword.getText().trim();

        // Validación de credenciales
        if (user.equals("admin") && pass.equals("1234")) {
            ingresarAlSistema(user, "ADMIN");
        } else if (user.equals("invitado") && pass.equals("invitado")) {
            ingresarAlSistema(user, "USER");
        } else {
            lblMensaje.setText("Usuario o contraseña incorrectos.");
            txtPassword.clear();
        }
    }

    private void ingresarAlSistema(String username, String rol) {
        try {
            // 1. Crear el objeto Usuario usando tus métodos específicos
            Usuario session = new Usuario();
            session.setUsername(username);
            session.setRol(rol);

            // 2. Cargar MainView
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
            Parent root = loader.load();
            
            // 3. Pasar el usuario al PostulanteController para aplicar permisos
            PostulanteController mainCtrl = loader.getController();
            mainCtrl.setUsuarioActivo(session);

            // 4. Configurar y mostrar la ventana principal
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/view/app.css").toExternalForm());
            
            stage.setTitle("Sistema de Gestión de Postulantes");
            stage.setScene(scene);
            stage.show();

            // 5. Cerrar la ventana de Login
            ((Stage) txtUsuario.getScene().getWindow()).close();

        } catch (Exception e) {
            lblMensaje.setText("Error al entrar: " + e.getMessage());
        }
    }
}
