package maze.routing;
import maze.Tile;
import maze.Maze;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.HashSet;
import java.util.Stack;
import java.util.Set;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.EOFException;
import java.io.Console;

/**
*This is the class that holds all of the mechanics for solving the mazes
*You can receive information about the steps of the solution for the maze
*
*@author Tsvetelina Kurteva
*@version 24th April 2020
*/
public class RouteFinder implements Serializable{

	/**
	*variable that holds the maze that the routefinder is going through
	*/
	private Maze maze;

	/**
	*stack that holds the route that the routefinder is working on to solve the maze
	*/	
	private Stack<Tile> route;

	/**
	*variable that signalizes if the maze is solved
	*/
	private boolean finished = false;

	/**
	*set that holds all the tiles the routefinder has visited
	*/
	private Set<Tile> visited;

	/**
	*Constructior of the maze
	*@param mazeIn parameter of the maze to initialize routefinder
	*/	
	public RouteFinder(Maze mazeIn){
		maze = mazeIn;
		route = new Stack<Tile>();
		visited = new HashSet<Tile>();
	}

	/**
	*returns the maze the RouteFinder is working on
	*@return the maze the RouteFinder is working on
	*/
	public Maze getMaze(){
		return maze;
	}

	/**
	*returns the route the RouteFinder is working on
	*@return the list of the route RouteFinder is working on
	*/	
	public List<Tile> getRoute(){
		return route;
	}

	/**
	*returns wheter the maze in solved or not
	*@return true or false wheter the maze in solved or not
	*/
	public boolean isFinished(){
		return finished;
	}

	/**
	*loads a RouteFinder from a file or filepath
	*@param fromFileIn the file or file path to the file to be loaded
	*@throws ClassNotFoundException if the RouteFinder is not found
	*@throws FileNotFoundException if the file is not found
	*@throws IOException if an error has occured
	*@throws EOFException if an error has occured
	*/	
	public static RouteFinder load(String fromFileIn) throws ClassNotFoundException, FileNotFoundException, IOException, EOFException{
		RouteFinder tempRouteFinder;
		try(FileInputStream mazeFile = new FileInputStream(fromFileIn);
			ObjectInputStream mazeStream = new ObjectInputStream(mazeFile);
		){

			tempRouteFinder = (RouteFinder) mazeStream.readObject();
			mazeStream.close();

			return tempRouteFinder;

		}
		catch(FileNotFoundException e){
			
			throw new FileNotFoundException("File is not found.");

		}
		catch(ClassNotFoundException e){
			throw new ClassNotFoundException("Class is not found");

		}
		catch(EOFException e){
			throw new EOFException("There is something unexpected with the file");
		}

		catch(IOException e){
			throw new IOException("There is a problem loading the file.");
		}
	}

	/**
	*saves the current RouteFinder
	*@param saveMazeRouteFinder saves the RouteFinder in the location and with the name specified
	*@throws IOException if there is a problem saving the file
	*/	
	public void save(String saveMazeRouteFinder) throws IOException{
		try (FileOutputStream mazeFile = new FileOutputStream(saveMazeRouteFinder);
			ObjectOutputStream mazeStream = new ObjectOutputStream(mazeFile);
			)
		{
			mazeStream.writeObject(this);
			mazeStream.close();
			mazeFile.close();
		}
		catch(IOException e){
			throw new IOException("There was a problem with saving the file.");

		}

	}

	/**
	*holds the whole logic of the solving mechanism using depth first search for solving the maze
	*@throws NoRouteFoundException if the maze doesn't have a solution
	*@return true or false wheter the maze in solved or not
	*/	
	public boolean step() throws NoRouteFoundException{	

		if(isFinished() == true) return true;

		if(visited.isEmpty() == true){
			visited.add(maze.getEntrance());
			route.add(maze.getEntrance());

		}

		if((maze.getAdjacentTile(route.peek(), Maze.Direction.EAST) != null) && (visited.contains(maze.getAdjacentTile(route.peek(), Maze.Direction.EAST)) == false) && (maze.getAdjacentTile(route.peek(), Maze.Direction.EAST).isNavigable() == true)){
			visited.add(maze.getAdjacentTile(route.peek(), Maze.Direction.EAST));
			route.push(maze.getAdjacentTile(route.peek(), Maze.Direction.EAST));
			if (route.peek().getType() == Tile.Type.EXIT){
				finished = true;
				return true;
			}else{
				return false;
			}
		}	
		if((maze.getAdjacentTile(route.peek(), Maze.Direction.SOUTH) != null) && (visited.contains(maze.getAdjacentTile(route.peek(), Maze.Direction.SOUTH)) != true) && (maze.getAdjacentTile(route.peek(), Maze.Direction.SOUTH).isNavigable() == true)){
			visited.add(maze.getAdjacentTile(route.peek(), Maze.Direction.SOUTH));
			route.push(maze.getAdjacentTile(route.peek(), Maze.Direction.SOUTH));
			if (route.peek().getType() == Tile.Type.EXIT){
				finished = true;
				return true;
			}else{
				return false;
			}
		}
		if((maze.getAdjacentTile(route.peek(), Maze.Direction.WEST) != null) && (visited.contains(maze.getAdjacentTile(route.peek(), Maze.Direction.WEST)) != true) && (maze.getAdjacentTile(route.peek(), Maze.Direction.WEST).isNavigable() == true)){
			visited.add(maze.getAdjacentTile(route.peek(), Maze.Direction.WEST));
			route.push(maze.getAdjacentTile(route.peek(), Maze.Direction.WEST));
			if (route.peek().getType() == Tile.Type.EXIT){
				finished = true;
				return true;
			}else{
				return false;
			}
		}		
		if((maze.getAdjacentTile(route.peek(), Maze.Direction.NORTH) != null) && (visited.contains(maze.getAdjacentTile(route.peek(), Maze.Direction.NORTH)) != true) && (maze.getAdjacentTile(route.peek(), Maze.Direction.NORTH).isNavigable() == true)){
			visited.add(maze.getAdjacentTile(route.peek(), Maze.Direction.NORTH));
			route.push(maze.getAdjacentTile(route.peek(), Maze.Direction.NORTH));
			if (route.peek().getType() == Tile.Type.EXIT){
				finished = true;
				return true;
			}else{
				return false;
			}
		}
		route.pop();
		if (route.empty() == true) throw new NoRouteFoundException("No route found to solve the maze");
		return false;
		
	}
	/**
	*Returns the current state of the maze the RouteFinder is solving in the form of a string
	*@return the String form of the Maze
	*/
	public String toString(){
		String currentMazeString = "";
		int sizeX = 0;
		int sizeY = 0;

		sizeY = maze.getTiles().size();
		sizeX = maze.getTiles().get(0).size();

		for (int i = 0; i < sizeY; i++){			
			for (int j = 0; j < sizeX; j++){

				if((route.contains(maze.getTiles().get(i).get(j)) == true) && (visited.contains(maze.getTiles().get(i).get(j)) == true)){
					currentMazeString = currentMazeString + "*";
					continue;
				}
				if((route.contains(maze.getTiles().get(i).get(j)) == false) && (visited.contains(maze.getTiles().get(i).get(j)) == true)){
					currentMazeString = currentMazeString + "-";
					continue;
				}							
				if(maze.getTiles().get(i).get(j).getType() == Tile.Type.CORRIDOR){
					currentMazeString = currentMazeString + ".";
					continue;					
				}
				if(maze.getTiles().get(i).get(j).getType() == Tile.Type.EXIT){
					currentMazeString = currentMazeString + "x";
					continue;					
				}
				if(maze.getTiles().get(i).get(j).getType() == Tile.Type.ENTRANCE){
					currentMazeString = currentMazeString + "e";
					continue;					
				}
				if(maze.getTiles().get(i).get(j).getType() == Tile.Type.WALL){
					currentMazeString = currentMazeString + "#";
					continue;					
				}
			}
			currentMazeString = currentMazeString + "\n";
		}
		return currentMazeString;
	}

}