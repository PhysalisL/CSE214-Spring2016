import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * The Gui Class starts the SceneController and SceneFXML
 * which is a SlideList manager class with user interface
 * 
 * @author Yixiu Liu 110602460 
 * yixiu.liu@stonbybrook.edu 
 * CSE 214-R03 Daniel Scanteianu 
 */
public class GUI extends Application {

    /**
     * Runs the javafx gui
     */
    public void start(Stage primaryStage) throws Exception{
        Parent base = FXMLLoader.load(
        		getClass().
        		getResource("SceneView.fxml"));
        primaryStage.setTitle("Slide List Manager");
        primaryStage.setScene(new Scene(base, 800, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
