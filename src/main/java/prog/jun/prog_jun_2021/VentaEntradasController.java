package prog.jun.prog_jun_2021;

import prog.jun.prog_jun_2021.bbdd.ConnectionDB;
import prog.jun.prog_jun_2021.utils.Dates;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class VentaEntradasController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    ComboBox clientsCB;
    @FXML
    ComboBox playsCB;
    @FXML
    TextField priceField;
    @FXML
    DatePicker dateField;

    /* Campos de error */
    @FXML
    Text clientsError;
    @FXML
    Text playsError;
    @FXML
    Text dateError;
    @FXML
    Text priceError;


    /* Contiene los errores de los formularios: Campo | Error */
    static Map<String, String> errors = new HashMap<String, String>();
    /** Contiene las etiquetas de error del formulario: Campo | Node
     *
     * Este HashMap no es 100% necesario, pero de este modo podemos limpiar los
     * errores de una forma más cómoda, pues si hubiese 40 campos, hacerlo uno a uno
     * sería poco eficiente
     *
     * **/
    static Map<String, Node> errorAlerts = new HashMap<String, Node>();
    /************* MÉTODOS ***************/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        /* Iniciamos la BBDD */
        ConnectionDB.openConnection();
        /* Cargamos los clientes*/
        clientsCB.setItems(FXCollections.observableArrayList(ConnectionDB.Operations.getClientes()));
        /* Cargamos las obras */
        playsCB.setItems(FXCollections.observableArrayList(ConnectionDB.Operations.getPlays()));

        /* Introducimos los campos de error en el HashMap */
        errorAlerts.put("clients", clientsError);
        errorAlerts.put("date", dateError);
        errorAlerts.put("plays", playsError);
        errorAlerts.put("price", priceError);
    }


    /**
     * Comprueba que no haya errores en ningún campo. Si los hay, los almacena en un HashMap
     * @return true si está libre de errores, false si contiene algún error
     */
    private boolean checkFields(){
        /* Limpiamos los errores de la pantalla */
        errorAlerts.forEach((key, value) -> {
            Text tmpText = (Text)value;
            tmpText.setText("");
        });
        /* Eliminamos los errores anteriores */
        errors.clear();

        /* Buscamos los nuevos errores */
        if(clientsCB.getValue() == null) errors.put("clients", "Debes seleccionar un cliente");
        if(dateField.getValue() == null) errors.put("date", "Debes introducir una fecha");
        if(playsCB.getValue() == null) errors.put("plays", "Debes seleccionar una obra");
        if(priceField.getText().trim().isEmpty()){
            errors.put("price", "Debes introducir el precio");
        }else if(!priceField.getText().replace(",", ".").matches("\\d*\\.?\\d*")){
            errors.put("price", "Formato no válido (N*.N*)");
        }

        /* Si no hay errores, retorna true, si hay errores, false */
        return errors.isEmpty();
    }

    /**
     * Muestra los errores obtenidos al comprobar el formulario
     */
    private void showErrorForms(){
        errors.forEach((key, value) -> {
            switch (key){
                case "clients":
                    clientsError.setText(value);
                    break;
                case "date":
                    dateError.setText(value);
                    break;
                case "plays":
                    playsError.setText(value);
                    break;
                case "price":
                    priceError.setText(value);
                    break;
            }
        });
    }

    /**
     * Obtiene los datos necesarios y crea una entrada en la BBDD
     * @param ActionEvent
     */
    @FXML
    public void sellTicket(ActionEvent ActionEvent){
        if(!checkFields()){
            showErrorForms();
        }else{

            Date date = Dates.getDate(dateField.getValue());
            Double price = Double.valueOf(priceField.getText().replace(",", "."));
            ConnectionDB.Operations.insertTicket(playsCB.getValue().toString(), clientsCB.getValue().toString(), date, price);

            /* Cerramos la conexión */
            ConnectionDB.closeConnection();

            /* Cambiamos de escena */
            try{
                toSceneIndex(ActionEvent);
            }catch(IOException ioe){
                ioe.printStackTrace();
            }

        }

    }

    /**
     * Cambia a la escena principal
     * @param event
     * @throws IOException
     */
    public void toSceneIndex(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("index.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1200, 800);
        stage.setScene(scene);
        stage.show();
    }


}

