/*
 * CS 300-A, 2017S
 */
package scaleplayer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import java.util.*;
import static javafx.application.Application.launch;


/**
 * This JavaFX app lets the user play scales.
 * @author Janet Davis 
 * @author Put your name here!
 * @author Put your partner's name here!
 * @since January 26, 2017
 */
public class ScalePlayer extends Application {
   
    MidiPlayer player;
   
   private void populate_notes(int start){
       int note_incriments[]  = new int[]{2,2,1,2,2,2,1};
       int note_position = 1;
       for(int i = 0; i < 7; i++){
           player.addNote(start, 60, note_position, 2, 1, 0);
           start += note_incriments[i];
           note_position += 2;
       }
        for(int j = 6; j > -1; j--){
           player.addNote(start, 60, note_position, 2, 1, 0);
           start -= note_incriments[j];
           note_position += 2;
       }
   }
   EventHandler<ActionEvent> play_event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
                TextInputDialog td = new TextInputDialog(""); 
                td.setHeaderText("Choose a note");
                td.showAndWait(); 
                int note_value = Integer.parseInt(td.getEditor().getText());
                System.out.println(note_value);
                
                populate_notes(note_value);
                System.out.println("Playing");
                player.play();
            } 
        }; 
   
   EventHandler<ActionEvent> stop_event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
                player.stop();
                player.clear();
            } 
        }; 
    
    @Override
    public void start(Stage primaryStage) {
        player = new MidiPlayer(4, 60);
        
        Button main_buttons[] = new Button[3];
        main_buttons[0] = new Button("Play Scale");
        main_buttons[1] = new Button("Stop playing");
        main_buttons[2] = new Button("Exit");
        
      
        
        
        HBox buttons_top = new HBox();
        HBox buttons_bottom = new HBox();
        buttons_top.getChildren().add(main_buttons[0]);
        buttons_top.getChildren().add(main_buttons[1]);
        buttons_bottom.getChildren().add(main_buttons[2]);
        
        BorderPane main_screen = new BorderPane();
        main_screen.setCenter(buttons_top);
        main_screen.setBottom(buttons_bottom);
        main_screen.setLayoutX(100);
        main_screen.setLayoutY(30);
    
        Scene scene = new Scene(main_screen, 400, 100);
        
        
        

        
        
        main_buttons[0].setOnAction(play_event);
        main_buttons[1].setOnAction(stop_event);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
