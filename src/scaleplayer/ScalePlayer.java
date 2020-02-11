
/*
 * CS 300-A, 2017S
 */
package scaleplayer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.cert.PKIXRevocationChecker.Option;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;

/**
 * This JavaFX app lets the user play scales.
 * @author Janet Davis 
 * @author Tristan Chung
 * @author Ronan Byrne
 * @since January 26, 2017
 */
public class ScalePlayer extends Application {
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Button playbtn;
        Button cancelbtn;
        MenuBar menuBar=new MenuBar();
        MidiPlayer midiPlayer= new MidiPlayer(10,100);
        
        playbtn = playButton(midiPlayer);
        cancelbtn = cancelButton(midiPlayer);
        menuBar = menu_bar();

        StackPane root = new StackPane();
        
        root.getChildren().add(playbtn);
        root.getChildren().add(cancelbtn);
        root.getChildren().add(menuBar);
        root.setAlignment(menuBar,Pos.TOP_LEFT);
        
        Scene scene = new Scene(root, 300, 250);
    
        primaryStage.setTitle("Scale Player");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * function creates a cancel button that stops the scale from playing
     * @param midiPlayer allows play and stop scale
     * @return the cancel button that stops the scale
     */
    public Button cancelButton(MidiPlayer midiPlayer){ 
        
        Button stopBtn = new Button();
        stopBtn.setText("Stop Playing");
        stopBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                midiPlayer.stop();
                midiPlayer.clear();
            }
        });
        //Stop button add to stack pane and edit size and color
        stopBtn.setTranslateX(70);
        stopBtn.setMaxWidth(100);
        stopBtn.setMaxHeight(40);
        stopBtn.setStyle("-fx-base: pink;");
        return stopBtn;
    }
    
    /**
     * creates play button that opens input window
     * @param midiPlayer allows sound to be played
     * @return the button that opens the new window
     */
    public Button playButton(MidiPlayer midiPlayer){  
        Button playScaleBtn = new Button();
        playScaleBtn.setText("Play Scale");
        
        playScaleBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextInputDialog inputBox= new TextInputDialog();
                createInputWindow(inputBox, midiPlayer);
            }
        });
        
        //play scale button add to stack pane and edit size and color
        playScaleBtn.setTranslateX(-70);
        playScaleBtn.setMaxWidth(100);
        playScaleBtn.setMaxHeight(40);
        playScaleBtn.setStyle("-fx-base: green;");
        return playScaleBtn;
        
    }

    /**
     * adjusts the TextInputDialog to proper specifications
     * @param inputWindow the text inputdialog window
     * @param midiPlayer allows sound to be played
     */
    public void createInputWindow(TextInputDialog inputWindow, MidiPlayer midiPlayer){       
        
        inputWindow.setTitle("Starting Note:");
        inputWindow.setHeaderText("Give me a starting note (0-115):");
        
        inputWindow.setX(1150);
        inputWindow.setY(340);
        
        Optional <String> userNoteInput=inputWindow.showAndWait();
        String startNote= userNoteInput.get();
        
        try{
            if(userNoteInput.isPresent()){
                midiPlayer.stop();
                midiPlayer.clear();
                playScale(Integer.parseInt(startNote),midiPlayer);
            }
        }catch(NumberFormatException ex){
            Alert alertMe=new Alert(AlertType.WARNING);
            alertMe.setContentText("Enter Number between 0-115");
            alertMe.show();
        }

    }
    
    /**
     * plays the scale up and down
     * @param note the index of the note
     * @param notePlayer allows notes to be played
     */
    public void playScale(Integer note, MidiPlayer notePlayer){
        int currentNote = integerToInt(note);
        
        for(int i=0;i<16;i++){
            if(i==10 || i==11 || i==12 || i==14 || i==15){
                currentNote-=2;
            }else if(i==1 || i==2 || i==4 || i==5 || i==6){
                currentNote+=2;
            }else if(i==3 || i==7){
                currentNote+=1;
            }else if(i==9 || i==13){
                currentNote-=1;
            }
            notePlayer.addNote(currentNote,127,(i+1)*10,20,0,0);
        }
        notePlayer.play();
        
    }
    
    /**
     * converts Integer to int
     * @param integer1
     * @return 
     */
    public int integerToInt(Integer integer1){
        return integer1.intValue();
    }
    
    /**
     * creates a menu bar for the initial window
     * @return 
     */
    public MenuBar menu_bar(){
        MenuBar menuBar=new MenuBar();
        Menu menu1= new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
        menu1.getItems().add(exitItem);
        menuBar.getMenus().add(menu1);
        
        return menuBar;
    }
    /**
     * @param args the  command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }   
}