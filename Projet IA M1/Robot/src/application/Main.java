package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;



public class Main extends Application {
	double x,y;
	
	@Override
	public void start(Stage primaryStage) 
	{
		try {
			
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("Scene.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			  
			root.setOnMousePressed(event -> {
		            x = event.getSceneX();
		            y = event.getSceneY();
		        });
		        root.setOnMouseDragged(event -> {

		            primaryStage.setX(event.getScreenX() - x);
		            primaryStage.setY(event.getScreenY() - y);

		        });
		        
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
