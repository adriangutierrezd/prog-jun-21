package prog.jun.prog_jun_2021;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import prog.jun.prog_jun_2021.bbdd.ConnectionDB;
import java.net.URL;
import prog.jun.prog_jun_2021.utils.Dates;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ListadoEntradasController implements Initializable {


    @FXML
    ComboBox playsCB;

    @FXML
    Text playsError;

    @FXML
    Text startDateError;

    @FXML
    Text endDateError;

    @FXML
    DatePicker startDateField;
    @FXML
    DatePicker endDateField;
    @FXML
    TableView<ModelTable> dataTable;

    @FXML
    TableColumn<ModelTable, String> nif;

    @FXML
    TableColumn<ModelTable, String> name;

    @FXML
    TableColumn<ModelTable, String> surname;

    @FXML
    TableColumn<ModelTable, String> date;

    @FXML
    TableColumn<ModelTable, String> price;


    static Map<String, String> errors = new HashMap<String, String>();
    static Map<String, Node> errorAlerts = new HashMap<String, Node>();


    /**
     * Poblamos el ComboBox de obras y guardamos los elementos de error
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        /* Iniciamos la BBDD */
        ConnectionDB.openConnection();
        /* Cargamos las obras */
        playsCB.setItems(FXCollections.observableArrayList(ConnectionDB.Operations.getPlays()));
        /* Guardamos los elementos de error en el HashMap */
        errorAlerts.put("startDate", startDateError);
        errorAlerts.put("endDate", endDateError);
        errorAlerts.put("plays", playsError);

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

        if(startDateField.getValue() == null) errors.put("start-date", "Campo obligatorio");
        if(endDateField.getValue() == null) errors.put("end-date", "Campo obligatorio");
        if(playsCB.getValue() == null) errors.put("plays", "Debes seleccionar una obra");

        return errors.isEmpty();
    }


    /**
     * Muestra los errores obtenidos al comprobar el formulario
     */
    private void showErrorForms(){
        errors.forEach((key, value) -> {
            switch (key){
                case "plays":
                    playsError.setText(value);
                    break;
                case "start-date":
                    startDateError.setText(value);
                    break;
                case "end-date":
                    endDateError.setText(value);
                    break;
            }
        });
    }

    /**
     * Carga los datos seleccionados en el TableView
     */
    @FXML
    public void loadData(){
        if(!checkFields()){
            showErrorForms();
        }else{
            String play = playsCB.getValue().toString();
            Date startDate = Dates.getDate(startDateField.getValue());
            Date endtDate = Dates.getDate(endDateField.getValue());

            nif.setCellValueFactory(new PropertyValueFactory<>("nif"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));

            dataTable.setItems(ConnectionDB.Operations.getTickets(play, startDate, endtDate));
        }
    }



}
