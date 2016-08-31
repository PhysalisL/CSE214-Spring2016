
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller class that handles information travel between GUI and
 * database
 */
public class Controller implements Initializable {
    private NeoDataBase dataBase = new NeoDataBase();

    @FXML
    private Button addPageButton;
    @FXML
    private TextField addPageTextField;
    @FXML
    private TableColumn<NearEarthObject, Integer> id;
    @FXML
    private TableColumn<NearEarthObject, String> name;
    @FXML
    private TableColumn<NearEarthObject, Double> mag;
    @FXML
    private TableColumn<NearEarthObject, Double> dia;
    @FXML
    private TableColumn<NearEarthObject, Boolean> danger;
    @FXML
    private TableColumn<NearEarthObject, Date> date;
    @FXML
    private TableColumn<NearEarthObject, Double> miss;
    @FXML
    private TableColumn<NearEarthObject, String> body;
    @FXML
    private TableView<NearEarthObject> tableView;
    @FXML
    private Text errorReport;

    private ObservableList<NearEarthObject> data =
            FXCollections.observableArrayList();

    /**
     * Initalize the GUI functions
     *
     * @param fxmlFileLocation
     * FXML file location
     * @param src
     * Source
     */
    public void initialize(URL fxmlFileLocation, ResourceBundle src){
        id.setCellValueFactory(new PropertyValueFactory<>("referenceID"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        mag.setCellValueFactory(
                new PropertyValueFactory<>("absoluteMagnitude"));
        dia.setCellValueFactory(
                new PropertyValueFactory<>("averageDiameter"));
        danger.setCellValueFactory(
                new PropertyValueFactory<>("isDangerous"));
        date.setCellValueFactory(
                new PropertyValueFactory<>("closestApproachDate"));
        miss.setCellValueFactory(new PropertyValueFactory<>("missDistance"));
        body.setCellValueFactory(new PropertyValueFactory<>("orbitingBody"));
        tableView.setItems(data);

        addPageButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                if(addPageTextField.getText().length()>0) {
                    try {
                        errorReport.setText("Loading....");
                        int pageNumber = Integer.parseInt(
                                addPageTextField.getText().trim());
                        dataBase.addAll(dataBase.buildQueryURL(pageNumber));
                        data.clear();
                        data.addAll(dataBase);
                        errorReport.setText("Page Loaded");
                    }catch(Exception e1){
                        errorReport.setText("Failed load. "+e1.getMessage());
                    }
                }
            }
        });
    }
}
