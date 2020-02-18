/*
 * CS 300-A, 2017S
 */
package scaleplayer;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * This JavaFX app lets the user play scales.
 * @author Janet Davis 
 * @author Ronan Byrne
 * @author Jack Taylor
 * @since January 26, 2017
 */
public class ScalePlayer extends Application {
    
    private MidiPlayer midiPlayer = new MidiPlayer(10,100);
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ScalePlayer.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Scale Player");
        stage.setScene(scene);
        stage.show();
    }

    public void exitProgram(ActionEvent exit_event){
        Platform.exit();
        System.exit(0);
    }

    public void cancelButton(ActionEvent cancelButtonEvent){
        midiPlayer.stop();
        midiPlayer.clear();
    }
    
    public void playButton(ActionEvent playButtonEvent){
        createInputWindow();
    }
    
    public void createInputWindow(){
        TextInputDialog inputWindow = new TextInputDialog();
        inputWindow.setTitle("Starting Note:");
        inputWindow.setHeaderText("Give me a starting note (0-115):");
        
        inputWindow.setX(1250);
        inputWindow.setY(340);
        
        Optional <String> userNoteInput=inputWindow.showAndWait();
        String startNote= userNoteInput.get();
        
        try{
            if(userNoteInput.isPresent()){
                midiPlayer.stop();
                midiPlayer.clear();
                playScale(integerToInt(Integer.parseInt(startNote)));
            }
        }catch(NumberFormatException ex){
            Alert alertMe=new Alert(AlertType.WARNING);
            alertMe.setContentText("Enter Number between 0-115");
            alertMe.show();
        }
    }
        
    public void playScale(int currentNote){
        int[] majorScale={0,2,4,5,7,9,11,12,12,11,9,7,5,4,2,0};
        for(int i=0;i<16;i++){
            midiPlayer.addNote(currentNote+majorScale[i],127,(i+1)*10,10,0,0);
        }
        midiPlayer.play();
    }
    
    public int integerToInt(Integer integer1){
        return integer1.intValue();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
