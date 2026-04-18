package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // 1. Cargar la vista de Login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
        
        // 2. Definir la escena (Tamaño automático basado en el FXML)
        Scene scene = new Scene(loader.load()); 

        // 3. ✅ Aplicar Tema por defecto (light.css en la carpeta view)
        URL cssResource = getClass().getResource("/view/light.css");
        if (cssResource != null) {
            scene.getStylesheets().add(cssResource.toExternalForm());
        } else {
            System.err.println("⚠️ ALERTA: No se encontró /view/light.css");
        }

        // 4. Configurar la ventana
        stage.setTitle("Acceso al Sistema - Gestión de Postulantes");
        stage.setScene(scene);
        stage.setResizable(false); 
        stage.centerOnScreen(); 
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
