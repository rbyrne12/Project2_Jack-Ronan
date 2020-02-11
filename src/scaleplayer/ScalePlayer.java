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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
    
    //Parameters for MidiPlayer setup
    private static int res = 4;
    private static int bpm = 60;
    
    //Parameters for notes
    private int note_incriments[]  = new int[]{2,2,1,2,2,2,1};
    private static int volume = 60;
    private static int duration = 2;
    private static int channel = 1;
    private static int track_no = 0;
    
    
   
   private void populate_notes(int start){
       int note_position = 1;
       for(int i = 0; i < 7; i++){
           player.addNote(start, volume, note_position, duration, channel, track_no);
           start += note_incriments[i];
           note_position += 2;
       }
        for(int j = 6; j > -1; j--){
           player.addNote(start, volume, note_position, duration, channel, track_no);
           start -= note_incriments[j];
           note_position += 2;
       }
        player.addNote(start, volume, note_position, duration, channel, track_no);
   }
   EventHandler<ActionEvent> play_event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
                player.clear();
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
                
            } 
        }; 
   
    EventHandler<ActionEvent> exit_event = new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent e) 
            { 
                System.exit(0);
            } 
        }; 
   

    
    @Override
    public void start(Stage primaryStage) {
        player = new MidiPlayer(res, bpm);
        
        Button main_buttons[] = new Button[3];
        main_buttons[0] = new Button("Play Scale");
        main_buttons[1] = new Button("Stop playing");
        
        main_buttons[0].setStyle("-fx-background-color: #98fb98; -fx-border-color: #008000; -fx-border-width: 1.5pt; ");
        main_buttons[1].setStyle("-fx-background-color: #ff6347; -fx-border-color: #800000; -fx-border-width: 1.5pt; ");
       
        
        Menu menu = new Menu("File");
        MenuBar menubar = new MenuBar();
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(exit_event);
        menu.getItems().add(exit);
        menubar.getMenus().add(menu);
        
        
        HBox buttons = new HBox();
        buttons.getChildren().add(main_buttons[0]);
        buttons.getChildren().add(main_buttons[1]);
        HBox menubox = new HBox(menubar);
        
        BorderPane main_screen = new BorderPane();
        main_screen.setTop(menubox);
        main_screen.setCenter(buttons);
      
  
    
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
