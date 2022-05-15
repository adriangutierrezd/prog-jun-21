package prog.jun.prog_jun_2021;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController{
    private Stage stage;
    private Scene scene;
    private Parent root;


    /**
     * Cambia a la escena d eventa de entradas
     * @param event
     * @throws IOException
     */
    public void toSceneVentaEntrada(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("venta-entrada.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1200, 800);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cambia a la escena de listado de entradas
     * @param event
     * @throws IOException
     */
    public void toSceneListadoEntradas(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("listado-entradas.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1200, 800);
        stage.setScene(scene);
        stage.show();
    }




}