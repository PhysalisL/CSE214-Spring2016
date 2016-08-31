

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The GUI class extends Application
 * This class is the alternative way of running TeamManager
 * 
 * @author Yixiu Liu
 *
 */
public class GUI extends Application{
	private TeamManager teamManager = new TeamManager();
	private Stage stage;
	private Scene scene;
	
	//Pane
	private HBox paneContainer = new HBox(); //Contains all panes
	private VBox paneOption = new VBox(); //Menu
	private VBox paneContent = new VBox(); //Where use can enter inputs
	private ScrollPane scrollPane = new ScrollPane(); //For scrolling
	
	//Menu Buttons
	private Button btnAddPlayer = new Button("Add Player");
	private Button btnGetPlayerStat = new Button("Get Stats");
	private Button btnGetLeader = new Button("Get Leader");
	private Button btnRemovePlayer = new Button("Remove Player");
	private Button btnPrintAllPlayer = new Button("Display All Players"); 
	private Button btnSize = new Button("Team Size");                     
	private Button btnSelectTeam = new Button("Select Team");
	private Button btnClone = new Button("Clone");
	private Button btnEquals = new Button("Check Equal");
	private Button btnUpdateStat = new Button("Update Stat");
	private Button btnQuit = new Button("Quit");
	
	//Submission buttons
	//Takes inputs from TextFields
	private Button submitAddPlayer = new Button("Submit");
	private Button submitGetPlayerStat = new Button("Submit");
	private Button submitGetLeader = new Button("Submit");
	private Button submitRemovePlayer = new Button("Submit");
	private Button submitPrintAllPlayer = new Button("Submit");
	private Button submitSize = new Button("Submit");
	private Button submitSelectTeam = new Button("Submit");
	private Button submitClone = new Button("Submit");
	private Button submitEquals = new Button("Submit");
	private Button submitUpdateStat = new Button("Submit");
	
	//TextField for input
	private TextField tfName = new TextField();
	private TextField tfPosition = new TextField();
	private TextField tfHits = new TextField();
	private TextField tfErrors = new TextField();
	private TextField tfStat = new TextField();
	private TextField tfStatAmount = new TextField();
	private TextField tfTeamNumber = new TextField();
	private TextField tfFrom = new TextField();
	private TextField tfTo = new TextField();
	private TextField tfPosition2 = new TextField();
	
	//Label to label TextFields
	private Label lName = new Label("Name");
	private Label lPosition = new Label("Position");
	private Label lHits = new Label("Hits");
	private Label lErrors = new Label("Errors");
	private Label lStat = new Label("Stat");
	private Label lStatAmount = new Label("Stat Amount");
	private Label lTeamNumber = new Label("Team Number");
	private Label lFrom = new Label("Clone From");
	private Label lTo = new Label("Clone To");
	private Label lPosition2 = new Label("Position2");
	
	//Text for reporting action result or displaying info
	private Text txtReport = new Text();
	private Text txtCurrentTeam = new Text("Current Team: 1");
	
	//Custom EventHandler. See inner classes
	private MenuHandler menuHandler = new MenuHandler();
	private SubmitHandler submitHandler = new SubmitHandler();
	
	public static void main(String[]args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.setTitle("TeamManager");
		stage.setResizable(false);
		stage.setHeight(800);
		stage.setWidth(670);

		//add button to menu
		paneOption.getChildren().addAll(
				btnAddPlayer,
				btnGetPlayerStat,
				btnGetLeader,
				btnRemovePlayer,
				btnPrintAllPlayer,
				btnSize,
				btnSelectTeam,
				btnClone,
				btnEquals,
				btnUpdateStat,
				btnQuit,
				txtCurrentTeam
				);
		
		//add report_text to scrollable_pane
		scrollPane.setContent(txtReport);
		
		//add panes to container
		paneContainer.getChildren().addAll(
				paneOption,
				paneContent
				);
		
		addHandler(); //Interaction managers
		customize(); //Decoration the GUI
		scene = new Scene(paneContainer);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Set the properties of Nodes, decorates the GUI.
	 */
	private void customize(){
		//Menu Buttons
		for(Node node: paneOption.getChildren()){
			if(node instanceof Button){
				((Button)node).setPrefSize(150, 50);
				((Button)node).setFont(Font.font("MV Boli", 15));
				((Button)node).setTextFill(Color.WHITE);
				((Button)node).setStyle(
						"-fx-background-radius:60;-fx-background-color: DARKGRAY;"
						);
				((Button)node).setOnMouseEntered(new EventHandler<MouseEvent>(){
					public void handle(MouseEvent event) {
						((Button)node).setStyle(
								"-fx-background-radius:60;"
								+ "-fx-background-color: GRAY;"
								);
					}
				});
				((Button)node).setOnMouseExited(new EventHandler<MouseEvent>(){
					public void handle(MouseEvent event) {
						((Button)node).setStyle(
								"-fx-background-radius:60;"
								+ "-fx-background-color: DARKGRAY;"
								);
					}
				});
				((Button)node).setOnMousePressed(new EventHandler<MouseEvent>(){
					public void handle(MouseEvent event) {
						((Button)node).setStyle(
								"-fx-background-radius:60;"
								+ "-fx-background-color: DIMGRAY;"
								);
					}
				});
				((Button)node).setOnMouseReleased(new EventHandler<MouseEvent>(){
					public void handle(MouseEvent event) {
						((Button)node).setStyle(
								"-fx-background-radius:60;"
								+ "-fx-background-color: DARKGRAY;"
								);
					}
				});
			}
		}
		
		//Panes
		paneContent.setTranslateX(20);
		paneContent.setTranslateY(20);
		paneContainer.setStyle("-fx-background: DARKGRAY");
		scrollPane.setPrefSize(420, 550);
		scrollPane.setTranslateY(50);
		scrollPane.setStyle(
				"-fx-background: DARKGRAY;"
				+ "-fx-box-border: transparent;"
				+ "-fx-focus-color: DARKGRAY;"
				);
		
		//Texts
		txtCurrentTeam.setFont(Font.font("Mv Boli", 20));
		txtCurrentTeam.setTranslateY(20);
		txtCurrentTeam.setTranslateX(10);
		txtCurrentTeam.setEffect(new Reflection(0, 0.7, 0.5, 0));
		txtReport.setFont(Font.font("Mv Boli", 15));
		txtReport.setFill(Color.BLACK);
		txtReport.setEffect(new Reflection(0, 0.8, 0.6, 0));
		
		//TextFields
		tfName.setMaxWidth(200);
		tfPosition.setMaxWidth(200);
		tfHits.setMaxWidth(200);
		tfErrors.setMaxWidth(200);
		tfStat.setMaxWidth(200);
		tfStatAmount.setMaxWidth(200);
		tfTeamNumber.setMaxWidth(200);
		tfFrom.setMaxWidth(200);
		tfTo.setMaxWidth(200);
		tfPosition2.setMaxWidth(200);
		
		/*Unused textfield decoration
		InnerShadow innerShadow = new InnerShadow(30, Color.DIMGRAY);
		tfName.setEffect(innerShadow);
		tfPosition.setEffect(innerShadow);
		tfHits.setEffect(innerShadow);
		tfErrors.setEffect(innerShadow);
		tfStat.setEffect(innerShadow);
		tfStatAmount.setEffect(innerShadow);
		tfTeamNumber.setEffect(innerShadow);
		tfFrom.setEffect(innerShadow);
		tfTo.setEffect(innerShadow);
		tfPosition2.setEffect(innerShadow);
		*/
		
		//Animations and decoration
		//Animation #1
		RotateTransition rT1 = new RotateTransition();
		Rectangle rect1 = new Rectangle(100, 100, Color.BLACK);
		rect1.setTranslateX(-20);
		rect1.setTranslateY(120);
		rT1.setNode(rect1);
		rT1.setInterpolator(Interpolator.LINEAR);
		rT1.setCycleCount(Animation.INDEFINITE);
		rT1.setByAngle(360);
		rT1.setDuration(Duration.seconds(1));
		paneOption.getChildren().add(rect1);
		rT1.play();
		//Animation #2
		RotateTransition rT2 = new RotateTransition();
		Rectangle rect2 = new Rectangle(100, 100, Color.BLACK);
		rect2.setTranslateX(600);
		rect2.setTranslateY(-680);
		rT2.setNode(rect2);
		rT2.setInterpolator(Interpolator.LINEAR);
		rT2.setCycleCount(Animation.INDEFINITE);
		rT2.setByAngle(360);
		rT2.setDuration(Duration.seconds(3));
		paneOption.getChildren().add(rect2);
		rT2.play();
		
	}
	
	/**
	 * Add handlers to Nodes, enable Handlers to obtain information
	 */
	private void addHandler(){
		//Menu Button
		btnSize.setOnAction(menuHandler);
		btnClone.setOnAction(menuHandler);
		btnAddPlayer.setOnAction(menuHandler);
		btnGetPlayerStat.setOnAction(menuHandler);
		btnGetLeader.setOnAction(menuHandler);
		btnRemovePlayer.setOnAction(menuHandler);
		btnPrintAllPlayer.setOnAction(menuHandler);
		btnSize.setOnAction(menuHandler);
		btnSelectTeam.setOnAction(menuHandler);
		btnClone.setOnAction(menuHandler);
		btnEquals.setOnAction(menuHandler);
		btnUpdateStat.setOnAction(menuHandler);
		btnQuit.setOnAction(menuHandler);
		
		//Submit Button
		submitAddPlayer.setOnAction(submitHandler);
		submitRemovePlayer.setOnAction(submitHandler);
		submitUpdateStat.setOnAction(submitHandler);
		submitGetPlayerStat.setOnAction(submitHandler);
		submitGetLeader.setOnAction(submitHandler);
		submitSelectTeam.setOnAction(submitHandler);
		submitClone.setOnAction(submitHandler);
		submitEquals.setOnAction(submitHandler);
		submitSize.setOnAction(submitHandler);
		submitPrintAllPlayer.setOnAction(submitHandler);
	}
	
	/**
	 * Class SubmitHandler implements EventHandler.
	 * Based on source submit_button
	 * SubmitHandler decides which TextField to obtain information from,
	 * and delivers the information to TeamManager,
	 * then retrieve information from TeamManager 
	 * to display information onto the GUI
	 * 
	 * @author Yixiu Liu
	 *
	 */
	private class SubmitHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e){
			Object source = e.getSource();

			//Add Player
			if(submitAddPlayer.equals(source)){
				try {
					teamManager.addPlayer(
							new Player(
									tfName.getText(),
									Integer.parseInt(tfHits.getText()),
									Integer.parseInt(tfErrors.getText())
									), 
							Integer.parseInt(tfPosition.getText())
							);
					txtReport.setText("Player added");
					
				} catch (NumberFormatException e1) {
					txtReport.setText(e1.getMessage()+" is not an integer");
				} catch (IllegalArgumentException e1) {
					txtReport.setText(e1.getMessage());
				} catch (FullTeamException e1) {
					txtReport.setText(e1.getMessage());
				}
			}

			//Remove Player
			else if(submitRemovePlayer.equals(source)){
				try{
					int position = Integer.parseInt(tfPosition.getText());
					
					teamManager.removePlayer(position);
					txtReport.setText("Removed Player at "+position);
				} catch (NumberFormatException e1) {
					txtReport.setText(e1.getMessage()+" is not an integer");
				} catch (IllegalArgumentException e1) {
					txtReport.setText(e1.getMessage());
				} 
			}

			//Update Player
			else if(submitUpdateStat.equals(source)){
				try{
					String name = tfName.getText();
					String stat = tfStat.getText();
					int statAmount = Integer.parseInt(tfStatAmount.getText());
					
					teamManager.updateStat(name, stat, statAmount);
					txtReport.setText(
							name+" has been updated with "+statAmount+" "+stat
							);
					
				} catch (NumberFormatException e1) {
					txtReport.setText(e1.getMessage()+" is not an integer");
				} catch (IllegalArgumentException e1) {
					txtReport.setText(e1.getMessage());
				} 
			}
			
			//Get Stat
			else if(submitGetPlayerStat.equals(source)){
				try{
					txtReport.setText(
							teamManager.getPlayerStats(
									Integer.parseInt(tfPosition.getText())
									)
							);
					
				} catch (NumberFormatException e1) {
					txtReport.setText(e1.getMessage()+" is not an integer");
				} catch (IllegalArgumentException e1) {
					txtReport.setText(e1.getMessage());
				} 
			}
			
			//Get Leader
			else if(submitGetLeader.equals(source)){
				try{
					txtReport.setText(
							"Leader: "+teamManager.getLeader(tfStat.getText())
							);
					
				} catch (IllegalArgumentException e1) {
					txtReport.setText(e1.getMessage());
				} 
			}
			
			//Select Team
			else if(submitSelectTeam.equals(source)){
				try{
					int position = Integer.parseInt(tfTeamNumber.getText());
					
					teamManager.selectTeam(position);
					txtReport.setText("Team Selected: "+position);
					txtCurrentTeam.setText("Current Team: "+ position);
					
				} catch (NumberFormatException e1) {
					txtReport.setText(e1.getMessage()+" is not an integer");
				} catch (IllegalArgumentException e1) {
					txtReport.setText(e1.getMessage());
				} 
			}
			
			//Clone
			else if(submitClone.equals(source)){
				try{
					int from = Integer.parseInt(tfFrom.getText());
					int to = Integer.parseInt(tfTo.getText());
					
					teamManager.cloneTeam(from, to);
					txtReport.setText("Team "+ from +" cloned to Team "+to);
					
				} catch (NumberFormatException e1) {
					txtReport.setText(e1.getMessage()+" is not an integer");
				} catch (IllegalArgumentException e1) {
					txtReport.setText(e1.getMessage());
				} 
			}
			
			//Equals
			else if(submitEquals.equals(source)){
				try{
					int position = Integer.parseInt(tfPosition.getText());
					int position2 = Integer.parseInt(tfPosition2.getText());
					
					if(teamManager.teamsEqual(position, position2))
						txtReport.setText("Team "+position+" is equal to Team "+position2);
					else
						txtReport.setText("Team "+position+" is not equal to Team "+position2);
					
				} catch (NumberFormatException e1) {
					txtReport.setText(e1.getMessage()+" is not an integer");
				} catch (IllegalArgumentException e1) {
					txtReport.setText(e1.getMessage());
				} 
			}
	
			//Print All Players
			else if(submitPrintAllPlayer.equals(source)){
				try{
					txtReport.setText(
							teamManager.teamToString(
									Integer.parseInt(tfTeamNumber.getText())
									)
							);
					
				} catch (NumberFormatException e1) {
					txtReport.setText(e1.getMessage()+" is not an integer");
				} catch (IllegalArgumentException e1) {
					txtReport.setText(e1.getMessage());
				} 
			}
			
			//Size
			else if(submitSize.equals(source)){
				try{
					txtReport.setText(
							"Team "+tfTeamNumber.getText()+" Size: "+
							teamManager.sizeOfTeam(
									Integer.parseInt(tfTeamNumber.getText())
									)
							);
					
				} catch (NumberFormatException e1) {
					System.out.println(e1.getLocalizedMessage());
					txtReport.setText(e1.getMessage()+" is not an integer");
				} catch (IllegalArgumentException e1) {
					txtReport.setText(e1.getMessage());
				} 
			}
			
		}
	}

	/**
	 * Class MenuHandler implements EventHandler.
	 * MenuHandler clears all Nodes in content_pane
	 * and adds Nodes according to the source menu_button.
	 * Hence, Nodes are reused for different purposes.
	 * ie: position_textfield for remove_player is also
	 * used for add_player, as well as get_player_stats
	 * 
	 * @author Yixiu Liu
	 *
	 */
	private class MenuHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e){
			Object source = e.getSource();
			
			//Clears Textfields
			for(Node node: paneContent.getChildren())
				if(node instanceof TextField)
					((TextField) node).clear();
			
			//Clear report
			txtReport.setText(null);
			
			//Remove all nodes in content_pane
			paneContent.getChildren().clear();
			
			//Add according to the source Button
			if (btnAddPlayer.equals(source)) {
				paneContent.getChildren().addAll(
						lName,
						tfName,
						lHits,
						tfHits,
						lErrors,
						tfErrors,
						lPosition,
						tfPosition,
						submitAddPlayer
					);
			} 
			else if (btnGetPlayerStat.equals(source)) {
				paneContent.getChildren().addAll(
						lPosition,
						tfPosition,
						submitGetPlayerStat
						);
			} 
			else if (btnGetLeader.equals(source)) {
				paneContent.getChildren().addAll(
						lStat,
						tfStat,
						submitGetLeader
						);
			} 
			else if (btnRemovePlayer.equals(source)) {
				paneContent.getChildren().addAll(
						lPosition,
						tfPosition,
						submitRemovePlayer
						);
			} 
			else if (btnPrintAllPlayer.equals(source)) {
				paneContent.getChildren().addAll(
						lTeamNumber,
						tfTeamNumber,
						submitPrintAllPlayer
						);
			} 
			else if (btnSize.equals(source)) {
				paneContent.getChildren().addAll(
						lTeamNumber,
						tfTeamNumber,
						submitSize
						);
			} 
			else if (btnSelectTeam.equals(source)) {
				paneContent.getChildren().addAll(
						lTeamNumber,
						tfTeamNumber,
						submitSelectTeam
						);
			} 
			else if (btnClone.equals(source)) {
				paneContent.getChildren().addAll(
						lFrom,
						tfFrom,
						lTo,
						tfTo,
						submitClone
						);
			} 
			else if (btnEquals.equals(source)) {
				paneContent.getChildren().addAll(
						lPosition,
						tfPosition,
						lPosition2,
						tfPosition2,
						submitEquals
						);
			} 
			else if (btnUpdateStat.equals(source)) {
				paneContent.getChildren().addAll(
						lName,
						tfName,
						lStat,
						tfStat,
						lStatAmount,
						tfStatAmount,
						submitUpdateStat
						);
			} 
			else if (btnQuit.equals(source)) {
						System.exit(0);
			}
			
			//Always add the Report that reports result of the action
			//The Report Text is in the scrollPane
			paneContent.getChildren().add(scrollPane);
		}
	}
}
