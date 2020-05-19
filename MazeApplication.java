import maze.Maze;
import maze.routing.RouteFinder;
import maze.InvalidMazeException;
import maze.MultipleEntranceException;
import maze.MultipleExitException;
import maze.NoEntranceException;
import maze.routing.NoRouteFoundException;
import maze.NoExitException;
import maze.RaggedMazeException;
import java.io.EOFException;
import java.io.Console;
import java.io.File;
import java.util.List;
import java.util.Stack;
import java.util.Set;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.CornerRadii;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.scene.control.CheckBox;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import java.util.Optional;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.Background;
import javafx.stage.Window;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; 
import java.util.Random; 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
*This is the class that holds all of the mechanics for the visualization
*and combines it with the logic for solving the maze
*
*@author Tsvetelina Kurteva
*@version 24th April 2020
*/
public class MazeApplication extends Application{

/**
*variable that holds the maze that is being solved
*/
public Maze mazeTest;

/**
*variable that holds the routefinder that is being used
*/
public RouteFinder routeFinderTest;

/**
*grid variable that holds the visuals of the maze itself
*/
public GridPane mazeGridPane;

/**
*method that updates the new looks of the maze visuals every time a change occurs
*/
public void MazeGridPaneUpdate(){
 	mazeGridPane = new GridPane(); 
	mazeGridPane.setVgap(5); 
    mazeGridPane.setHgap(5); 

	int row = 0;
	int col = 0;
	Rectangle r;
	String mazeString = routeFinderTest.toString();

	for (int i = 0; i < mazeString.length(); i++){

		if (mazeString.charAt(i) == '\n'){
			col = 0;
			row += 1;
			continue;
		}
		if (mazeString.charAt(i) == '#'){
			col += 1;
			r = new Rectangle(25, 25);
			r.setFill(Color.BLACK);
			mazeGridPane.add(r, col, row);
			continue;
		}
		if ((mazeString.charAt(i) == '*') || (mazeString.charAt(i) == 'e')){
			col += 1;
			r = new Rectangle(25, 25);
			r.setFill(Color.GREEN);
			mazeGridPane.add(r, col, row);
			continue;
		}		
		if (mazeString.charAt(i) == 'x'){
			col += 1;
			r = new Rectangle(25, 25);
			r.setFill(Color.RED);
			mazeGridPane.add(r, col, row);
			continue;
		}	
		if ((mazeString.charAt(i) == '.') || (mazeString.charAt(i) == '-')){
			col += 1;
			r = new Rectangle(25, 25);
			r.setFill(Color.WHITE);
			mazeGridPane.add(r, col, row);
			continue;
		}	
	}




}

/**
*mathod that is called if an error  has occured - it shows alert to the user
*@param message what the alert should say - more specifically - more information about the error
*/
public void showAlert(String message){
	Alert alert = new Alert(AlertType.ERROR);
	alert.setTitle("Error");
	alert.setHeaderText("Error has occured!");
	alert.setContentText(message);
	alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
	alert.showAndWait();
}

/**
*method that is used to show an image to the user if he solves the maze
*DISCLAMER: THE IMAGES ARE EITHER MADE BY ME OR MY FRIENDS OF THEIR PETS.
*THEY GAVE ME PERMISSION TO USE THE IMAGES
*@return the image in the approptiate format to be visualised by the player
*/
public ImageView winning(){
	
	Random number = new Random();
	int r = number.nextInt(5);
	Image image;
	ImageView imageView = new ImageView();
	try {
		image = new Image(new FileInputStream("./maze/visualisation/animal" + Integer.toString(r) + ".jpg"));
		imageView = new ImageView(image);
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(350);
		}	
	catch(FileNotFoundException e){
		showAlert(e.toString());
	}

	return imageView;
}

	/**
	*the metod that combines all the logic of the solving mechanism with the vasuals that need to be presented
	*@param stage the stage that is used for the window
	*/
	@Override
	public void start(Stage stage){


		HBox top = new HBox(0);
		top.setAlignment(Pos.CENTER);

		HBox mid = new HBox(0);
		mid.setAlignment(Pos.CENTER);

		HBox bottom = new HBox();
		bottom.setAlignment(Pos.CENTER);

		VBox right = new VBox(0);
		right.setAlignment(Pos.CENTER);	

		Label pushMeLabel= new Label();
		

		Button loadMazeButton = new Button();
		Button loadMapButton = new Button();
		Button saveButton = new Button();
		Button stepButton = new Button();


		loadMapButton.setText("Load Map");
		loadMazeButton.setText("Load Maze");
		saveButton.setText("Save Maze");
		stepButton.setText("step");
		
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);


		top.getChildren().addAll(loadMapButton, loadMazeButton);
		top.setPadding(new Insets(15, 15, 15, 15));
		top.setBackground(new Background(new BackgroundFill(Color.rgb(231, 223, 242), CornerRadii.EMPTY, Insets.EMPTY)));
			
		top.setSpacing(15); 
      	VBox land = new VBox(0);
		land.getChildren().add(top);




		loadMapButton.setOnAction(e -> {
			File selectedFile = fileChooser.showOpenDialog(stage);
			
			try{
				mazeTest = Maze.fromTxt(selectedFile.getPath());
				routeFinderTest = new RouteFinder(mazeTest);
			}

			catch(InvalidMazeException ex){
				showAlert(ex.toString());
			}
			catch(IllegalArgumentException ex){
				showAlert(ex.toString());
			}

			MazeGridPaneUpdate();
			top.getChildren().clear();
			top.getChildren().addAll(loadMapButton, loadMazeButton, saveButton);
			mid.getChildren().clear();
			mid.getChildren().add(mazeGridPane);
			bottom.getChildren().clear();
			bottom.getChildren().add(stepButton);
			top.setSpacing(15); 
			top.setPadding(new Insets(15, 15, 15, 15));
			mid.setPadding(new Insets(5, 15, 20, 15));
			bottom.setPadding(new Insets(0, 15, 15, 15));
			top.setBackground(new Background(new BackgroundFill(Color.rgb(231, 223, 242), CornerRadii.EMPTY, Insets.EMPTY)));
			mid.setBackground(new Background(new BackgroundFill(Color.rgb(231, 223, 242), CornerRadii.EMPTY, Insets.EMPTY)));
			bottom.setBackground(new Background(new BackgroundFill(Color.rgb(231, 223, 242), CornerRadii.EMPTY, Insets.EMPTY)));

			land.getChildren().clear();
      		land.getChildren().addAll(top,mid, bottom);
      		stage.sizeToScene();

		});

		loadMazeButton.setOnAction(e -> {
			boolean isThereRoad = true;
			File selectedFile = fileChooser.showOpenDialog(stage);

			try{

				routeFinderTest = RouteFinder.load(selectedFile.getPath());	
				mazeTest = routeFinderTest.getMaze();			
			}

			catch(NoRouteFoundException ex){
				showAlert(ex.toString());
				isThereRoad = false;
			}
			catch(ClassNotFoundException ex){
				showAlert(ex.toString());
			}
			catch(EOFException ex){
				showAlert(ex.toString());
				
			}		
			catch(IOException ex){
				
				showAlert(ex.toString());
			}	
			MazeGridPaneUpdate();
			top.getChildren().clear();
			top.getChildren().addAll(loadMapButton, loadMazeButton, saveButton);
			mid.getChildren().clear();
			mid.getChildren().add(mazeGridPane);
			bottom.getChildren().clear();
			bottom.getChildren().add(stepButton);
			top.setSpacing(15); 
			top.setPadding(new Insets(15, 15, 15, 15));
			mid.setPadding(new Insets(5, 15, 20, 15));
			bottom.setPadding(new Insets(0, 15, 15, 15));
			top.setBackground(new Background(new BackgroundFill(Color.rgb(231, 223, 242), CornerRadii.EMPTY, Insets.EMPTY)));
			mid.setBackground(new Background(new BackgroundFill(Color.rgb(231, 223, 242), CornerRadii.EMPTY, Insets.EMPTY)));
			bottom.setBackground(new Background(new BackgroundFill(Color.rgb(231, 223, 242), CornerRadii.EMPTY, Insets.EMPTY)));
			if(isThereRoad == false)bottom.getChildren().clear();
			land.getChildren().clear();
      		land.getChildren().addAll(top,mid, bottom);
      		stage.sizeToScene();

			if(routeFinderTest.isFinished() == true){
				bottom.getChildren().clear();
				land.getChildren().clear();
      			land.getChildren().addAll(top,mid, bottom);
      			stage.sizeToScene();
			}


		});

		saveButton.setOnAction(e -> {
			FileChooser fileChooser1 = new FileChooser();
			fileChooser1.setTitle("Save Maze"); 
			File file = fileChooser1.showSaveDialog(stage);
			File file1;
			if (file != null) {				
				if(file.getName().endsWith(".txt") == false){
					file1 = new File(file.getPath() + ".txt");
				}else{
					file1 = file;
				}
				try{
					routeFinderTest.save(file1.getPath());
				}
				catch(IOException ex){
					showAlert(ex.toString());
				}	
			stage.sizeToScene();


			}
		});

		stepButton.setOnAction(e -> {
			boolean done = false;
			boolean isThereRoad = true;
			try{
				done = routeFinderTest.step();
			}

			catch(NoRouteFoundException ex){
				System.out.println(ex);
				bottom.getChildren().clear();
				land.getChildren().clear();
      			land.getChildren().addAll(top,mid, bottom);
      			showAlert(ex.toString());
      			isThereRoad = false;

			}	

			MazeGridPaneUpdate();
			top.getChildren().clear();
			top.getChildren().addAll(loadMapButton, loadMazeButton, saveButton);
			mid.getChildren().clear();
			mid.getChildren().add(mazeGridPane);
			bottom.getChildren().clear();
			bottom.getChildren().add(stepButton);
			top.setSpacing(15); 
			top.setPadding(new Insets(15, 15, 15, 15));
			mid.setPadding(new Insets(5, 15, 20, 15));
			bottom.setPadding(new Insets(0, 15, 15, 15));
			top.setBackground(new Background(new BackgroundFill(Color.rgb(231, 223, 242), CornerRadii.EMPTY, Insets.EMPTY)));
			mid.setBackground(new Background(new BackgroundFill(Color.rgb(231, 223, 242), CornerRadii.EMPTY, Insets.EMPTY)));
			bottom.setBackground(new Background(new BackgroundFill(Color.rgb(231, 223, 242), CornerRadii.EMPTY, Insets.EMPTY)));
			if(isThereRoad == false){bottom.getChildren().clear();}
			land.getChildren().clear();
      		land.getChildren().addAll(top,mid, bottom);

			if(done == true){
				ImageView animal = winning();

				bottom.getChildren().clear();
				right.getChildren().clear();
				right.getChildren().add(animal);
				right.setPadding(new Insets(0, 15, 15, 15));
				right.setBackground(new Background(new BackgroundFill(Color.rgb(231, 223, 242), CornerRadii.EMPTY, Insets.EMPTY)));
			
				land.getChildren().clear();
      			land.getChildren().addAll(top,mid, bottom, right);
			}
			stage.sizeToScene();

		});


		Scene scene = new Scene(land);
		stage.sizeToScene();
       	// Scene scene = new Scene(land).sizeToScene();
		// scene.setBackground(new Background(new BackgroundFill(Color.rgb(231, 223, 242), CornerRadii.EMPTY, Insets.EMPTY)));
		        
        stage.setScene(scene);
			
        stage.setTitle("Maze Solver Application");
        stage.show();

	}

	/**
	*main method that is only used to launch the visuals
	*/
	public static void main (String args[]) {
  		launch(args);


  	}	

}