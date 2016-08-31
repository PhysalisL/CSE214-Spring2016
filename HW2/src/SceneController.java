import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The SceneController Class 
 * is a manager class that handles imformation travel
 * between SlideList and SceneView.fmxl
 * 
 * @author Yixiu Liu 110602460 
 * yixiu.liu@stonbybrook.edu 
 * CSE 214-R03 Daniel Scanteianu 
 */
public class SceneController implements javafx.fxml.Initializable{
    SlideList list = new SlideList();

    @FXML
    private Button bNewSlide;
    @FXML
    private Button bForward;
    @FXML
    private Button bBackward;
    @FXML
    private Button bEditSlide;
    @FXML
    private Button bRemoveSlide;
    @FXML
    private Button bNewSlideSubmit;
    @FXML
    private Button bSummary;
    @FXML
    private Label lSummary;
    @FXML
    private VBox pSummary;
    @FXML
    private VBox pNewSlide;
    @FXML
    private VBox pEditSlide;
    @FXML
    private TextField tfNewTitle;
    @FXML
    private TextField tfNewDuration;
    @FXML
    private TextField tfNewBullet1;
    @FXML
    private TextField tfNewBullet2;
    @FXML
    private TextField tfNewBullet3;
    @FXML
    private TextField tfNewBullet4;
    @FXML
    private TextField tfNewBullet5;
    @FXML
    private RadioButton rbBefore;
    @FXML
    private RadioButton rbTail;
    @FXML
    private RadioButton rbEditTitle;
    @FXML
    private RadioButton rbEditDuration;
    @FXML
    private RadioButton rbEditBullet;
    @FXML
    private RadioMenuItem rmiBullet1;
    @FXML
    private RadioMenuItem rmiBullet2;
    @FXML
    private RadioMenuItem rmiBullet3;
    @FXML
    private RadioMenuItem rmiBullet4;
    @FXML
    private RadioMenuItem rmiBullet5;
    @FXML
    private TextField tfEditInput;
    @FXML
    private Button bEditSubmit;
    @FXML
    private Label lErrorMessage;
    @FXML
    private Label lCursorSlide;
    @FXML
    private Button bMoveToHead;
    
    /**
     * Gets SlideList object in a formatted table
     *
     * @param list
     * The SlideList to be printed out with format
     *
     * @return
     * SlideList object in a formatted table
     */
    private String summary(SlideList list){
        int separatorLength = 46; //The length of line
        String symbol = "="; //The symbol used for line
        String content = ""; //Content builder
        Slide mark = list.getCursorSlide(); //For cursor reset
        list.resetCursorToHead();

        String lineSeparator = new String(new char[separatorLength])
                .replace("\u0000", symbol);

        String heading = String.format(
                "%-2s%-9s%-14s%-11s%-10s",
                "", "Slide", "Title", "Duration", "Bullets");

        for(int i=1; i<=list.size(); i++){
            content += String.format(
                    "%-2s%-9d%-14s%-11.1f%-10d\n",
                    "",
                    i,
                    list.getCursorSlide().getTitle(),
                    list.getCursorSlide().getDuration(),
                    list.getCursorSlide().getNumBullets());

            //Because cursorForward will run one extra time
            //before termination, need to use a check to prevent it
            if(i<list.size()){
                try {
                    list.cursorForward();
                } catch (EndOfListException e) {
                    System.out.println("Size or iteration error");
                }
            }
        }

        String total = String.format(
                "Total: %d slide(s), %.1f minutes(s), %d bullet(s)",
                list.size(), list.duration(), list.numBullets());

        //restore cursor
        if(mark!=null){
            list.resetCursorToHead();
            while(!list.getCursorSlide().equals(mark)){
                try {
                    list.cursorForward();
                } catch (EndOfListException e) {}
            }
        }

        return  lineSeparator+
                "\n"+heading+
                "\n"+lineSeparator+
                "\n"+content+
                "\n"+lineSeparator+
                "\n"+total+
                "\n"+lineSeparator;
    }
    
    /**
     * Updates certain nodes with new information
     * This method should be called any event is fired
     */
    private void update(){
        lErrorMessage.setText(". . . . . .");
        String bulletString = list.getCursorSlide()==null?
        		"No Slides":list.getCursorSlide().toString();
        lCursorSlide.setText(bulletString);
        lSummary.setText(summary(list));
    }

    @FXML
    /**
     * Initialize the javafx nodes and events
     */
    public void initialize(
    		URL fxmlFileLocation, ResourceBundle resources) {
    	
        bMoveToHead.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                list.resetCursorToHead();
                update();
            }
        });
        
        bEditSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (rbEditTitle.isSelected()) {
                        list.getCursorSlide().setTitle(
                        		tfEditInput.getText());
                    } else if (rbEditDuration.isSelected()) {
                        list.getCursorSlide().setDuration(
                        		Double.parseDouble(
                        				tfEditInput.getText()));
                    } else if (rbEditBullet.isSelected()) {
                        list.getCursorSlide().setBullet(
                                tfEditInput.getText().trim().isEmpty()?
                                		null:tfEditInput.getText(),
                                rmiBullet1.isSelected()?1
                                :rmiBullet2.isSelected()?2
                                :rmiBullet3.isSelected()?3
                                :rmiBullet4.isSelected()?4
                                :5);
                    }
                    update();
                }catch(Exception e){
                    lErrorMessage.setText(e.getMessage());
                }
            }
        });
        
        bSummary.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                update();
                pSummary.toFront();
            }
        });
        
        bNewSlideSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Slide slide = new Slide();
                try {
                    slide.setTitle(tfNewTitle.getText().trim());
                    slide.setDuration(
                            Double.parseDouble(
                                    tfNewDuration.getText().trim()));

                    if(!tfNewBullet1.getText().trim().isEmpty())
                        slide.setBullet(tfNewBullet1.getText().trim(), 1);
                    if(!tfNewBullet2.getText().trim().isEmpty())
                        slide.setBullet(tfNewBullet2.getText().trim(), 2);
                    if(!tfNewBullet3.getText().trim().isEmpty())
                        slide.setBullet(tfNewBullet3.getText().trim(), 3);
                    if(!tfNewBullet4.getText().trim().isEmpty())
                        slide.setBullet(tfNewBullet4.getText().trim(), 4);
                    if(!tfNewBullet5.getText().trim().isEmpty())
                        slide.setBullet(tfNewBullet5.getText().trim(), 5);

                    if(rbBefore.isSelected())
                        list.insertBeforeCursor(slide);
                    else
                        list.appendToTail(slide);

                    update();
                }catch(Exception e) {
                    lErrorMessage.setText(e.getMessage());
                }
            }
        });
        
        bRemoveSlide.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               try{
                   list.removeCursor();
                   update();
               }catch(EndOfListException e){
                   lErrorMessage.setText(e.getMessage());
               }
            }
        });
        
        bForward.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try{
                    list.cursorForward();
                    update();
                }catch(EndOfListException e){
                    lErrorMessage.setText(e.getMessage());
                }catch(NullPointerException e){
                    lCursorSlide.setText("No Slides");
                }catch(Exception e){
                    lErrorMessage.setText(e.getMessage());
                }
            }
        });
        
        bBackward.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try{
                    list.cursorBackward();
                    update();
                }catch(EndOfListException e){
                    lErrorMessage.setText(e.getMessage());
                }catch(NullPointerException e){
                    lCursorSlide.setText("No Slides");
                }catch(Exception e){
                    lErrorMessage.setText(e.getMessage());
                }
            }
        });
        
        bNewSlide.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                pNewSlide.toFront();
            }
        });

        bEditSlide.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                pEditSlide.toFront();
            }
        });
    }


}
