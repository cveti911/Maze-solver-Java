package maze;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;


/**
*This is the maze and it is constructed of tiles
*You can receive information about the maze using the methods
*
*@author Tsvetelina Kurteva
*@version 24th April 2020
*/

public class Maze implements Serializable{

	/**
	*this is used to know which tile is the entrance tile for the maze
	*/
	private Tile entrance;

	/**
	*this is used to know which tile is the exit tile for the maze
	*/
	private Tile exit;

	/**
	*this holds all the tiles that the maze is consisted of and makes up exactly the construction of the maze
	*/
	private List<List<Tile>> tiles;

	/**
	*This is used to enumerate all of the possible directions, so you can later check 
	*on a tile tepending on the direction you are asking for
	*/
	public enum Direction {
		NORTH,SOUTH, EAST, WEST;
	}

	/**
	*This is an inner class of the Maze class
	*It is used to keep up with the cordinates of each of the tiles
	*that construct the maze
	*/
	public class Coordinate{
		/**
		*makes up the x coordinate of the tile
		*/
		private int x;

		/**
		*makes up the y coordinate of the tile
		*/
		private int y;

		/**
		*Constructor of the coordinate class
		*@param xIn to inisialise the x coordinate 
		*@param yIn to inisialise the y coordinate
		*/
		public Coordinate(int xIn, int yIn){
			x = xIn;
			y = yIn;
		}

		/**
		*used to return the x coordinate of the tile in the maze
		*@return the x coordinate of the tile in the maze
		*/
		public int getX(){
			return x;
		}

		/**
		*used to return the y coordinate of the tile in the maze
		*@return the y coordinate of the tile in the maze
		*/
		public int getY(){
			return y;
		}
		/**
		*Gives back the cordinate in the form of a String
		*@return the cordinate in the form of a String
		*/
		public String toString(){
			String strCoordinates = "";
			strCoordinates = "(" + getX() + ", " + getY() + ")";
			return strCoordinates;

		}
	}
	/**
	*Constructior of the maze
	*/
	private Maze(){}

	/**
	*Reads the maze from a text file
	*@param fileName gives the file path or name 
	*@throws InvalidMazeException if the maze is inavlid and does not fit any of the rest of the categories for invalid maze
	*@throws MultipleEntranceException if there is more than 1 entrance to the maze
	*@throws MultipleExitException if there is more than 1 exit to the maze
	*@throws NoEntranceException if there is no entrance to the maze
	*@throws NoExitException if there is no exit to the maze
	*@throws RaggedMazeException if not all lines of the maze have the same number of tiles or all rows
	*@return a new maze made up from the file data
	*/
	public static Maze fromTxt(String fileName) throws InvalidMazeException, MultipleEntranceException,MultipleExitException, NoEntranceException, NoExitException, RaggedMazeException{
		Maze currMaze = new Maze();
		currMaze.tiles = new ArrayList<List<Tile>>();

		try(
			FileReader mazeFile = new FileReader(fileName);
			BufferedReader mazeStream = new BufferedReader(mazeFile);
		)
		{
			int exitCounter = 0;
			int entranceCounter = 0;
			String temp;
			temp = mazeStream.readLine();
			int lineCounter = 0;
			while(temp != null){
				temp = temp.trim();
				currMaze.tiles.add(new ArrayList<Tile>());
				for (int i = 0; i < temp.length(); i++){
					char c = temp.charAt(i);
					if (c == 'e'){
						entranceCounter += 1;
						if (entranceCounter>1) throw new MultipleEntranceException("There is more than one entrance to the maze.");
						 currMaze.tiles.get(lineCounter).add(Tile.fromChar(c));
						 currMaze.setEntrance(currMaze.tiles.get(lineCounter).get(i));
						 continue;
					}
					if (c == 'x'){
						exitCounter += 1;
						if (exitCounter>1) throw new MultipleExitException("There is more than one exit to the maze."); 
						currMaze.tiles.get(lineCounter).add(Tile.fromChar(c));
						currMaze.setExit(currMaze.tiles.get(lineCounter).get(i));

						continue;
					}
					if(c != 'x' && c!= 'e' && c!='.' && c!='#') throw new InvalidMazeException("Invalid char!");


					currMaze.tiles.get(lineCounter).add(Tile.fromChar(c));
				}
				temp = mazeStream.readLine();
				lineCounter += 1;
			}
			if (entranceCounter == 0) throw new NoEntranceException("There is no entrance to the maze.");
			if (exitCounter == 0) throw new NoExitException("There is no exit to the maze.");

			if (lineCounter > 0){

				int numberOfTiles = currMaze.tiles.get(0).size();
				for (int i = 1; i<lineCounter-1; i++){
					if (numberOfTiles != currMaze.tiles.get(i).size()){
						throw new RaggedMazeException("The maze is ragged.");
					}
				}	
			}
			mazeStream.close();
			mazeFile.close();	

		}	
		catch(FileNotFoundException e){
			System.out.println("\nNo file was read");

		}
		catch(IOException e){
			System.out.println("\nThere was a problem reading the file");
		}	

		return currMaze;
	}

	/**
	*gets the tile that is the direction indicated towards the tile that is indicated
	*@param tileIn the tile, whose adjecent tile we are looking for
	*@param dirIn the direction in chich the adjecent tile is searched
	*@return the adjecent tile or null if there is no tile in the direction
	*/

	public Tile getAdjacentTile(Tile tileIn, Direction dirIn){
		int currX;
		int currY;

		currX = getTileLocation(tileIn).getX();
		currY = getTileLocation(tileIn).getY();
		if(dirIn == Direction.NORTH){
			currY = currY+ 1;
			if (currY > tiles.size()-1){
				return null;
			}
			Coordinate needed = new Coordinate(currX, currY);
			return getTileAtLocation(needed);
		}
		if(dirIn == Direction.SOUTH){
			currY = currY - 1;
			if (currY < 0){
				return null;
			}
			Coordinate needed = new Coordinate(currX, currY);
			return getTileAtLocation(needed);
		}
		if(dirIn == Direction.WEST){
			currX = currX - 1;
			if (currX < 0){
				return null;
			}
			Coordinate needed = new Coordinate(currX, currY);
			return getTileAtLocation(needed);
		}
		if(dirIn == Direction.EAST){
			currX = currX + 1;
			if (currX > tiles.get(0).size()-1){
				return null;
			}
			Coordinate needed = new Coordinate(currX, currY);
			return getTileAtLocation(needed);
		}
		return null;			
	
	}

	/**
	*used to return the entrance of the maze
	*@return the entrance of the maze
	*/	
	public Tile getEntrance(){
		return entrance;
	}
	/**
	*used to return the exit of the maze
	*@return the exit of the maze
	*/
	public Tile getExit(){
		return exit;
	}

	/**
	*gets the tile that is cordinates indicated
	*@param cIn the coordinate of the tile we are looking for
	*@return tile we are looking for at the location
	*/
	public Tile getTileAtLocation(Coordinate cIn){
		int xOfTile;
		int yOfTile;
		xOfTile = cIn.getX();
		yOfTile = cIn.getY();
		return tiles.get((tiles.size() - yOfTile )- 1).get(xOfTile);
	}

	/**
	*gets the coordinate of the tile that is indicated
	*@param tileIn the tile whoese coordinate we are looking for
	*@return coordinate we are looking for
	*/
	public Coordinate getTileLocation(Tile tileIn){
		for (int i = 0; i<tiles.size(); i++){
			for (int j = 0; j<tiles.get(i).size(); j++){
				if(tiles.get(i).get(j) == tileIn){
					return new Coordinate(j,(tiles.size() - i - 1));
				}
			}
		}
		return null;
	}
	/**
	*used to return the tiles that make up the maze
	*@return tiles that make up the maze
	*/
	public List<List<Tile>> getTiles(){

		return tiles;
	}

	/**
	*sets the tile as the entrance tile
	*@param tileIn the entrance tile
	*@throws MultipleEntranceException if there is more than one tile for entrance
	*@throws IllegalArgumentException if the parameter is not of type Tile
	*/

	private void setEntrance(Tile tileIn) throws MultipleEntranceException, IllegalArgumentException{

		if (entrance != null) throw new MultipleEntranceException("There is more than one entrance to the maze.");
	

		boolean isOkay = false;
		for (int i = 0; i<tiles.size(); i++){
			for (int j = 0; j<tiles.get(i).size(); j++){
				if(tiles.get(i).get(j) == tileIn){
					isOkay = true;
				}				

			}
		}

		if (isOkay != true) throw new IllegalArgumentException("You gave incorrect argument for a tile");

		entrance = tileIn;

	}

	/**
	*sets the tile as the exit tile
	*@param tileIn the exit tile
	*@throws MultipleEntranceException if there is more than one tile for exit
	*@throws IllegalArgumentException if the parameter is not of type Tile
	*/
	private void setExit(Tile tileIn) throws MultipleExitException, IllegalArgumentException{

		if (exit != null) throw new MultipleExitException("There is more than one exit to the maze.");
		

		boolean isOkay = false;
		for (int i = 0; i<tiles.size(); i++){
			for (int j = 0; j<tiles.get(i).size(); j++){
				if(tiles.get(i).get(j) == tileIn){
					isOkay = true;
				}				

			}
		}

		if (isOkay != true) throw new IllegalArgumentException("You gave incorrect argument for a tile");
		exit = tileIn;

	}
	/**
	*Returns the Maze type in the form of a string
	*@return the String form of the Maze
	*/
	public String toString(){
		String stringVersion = "";
		for (int i = 0; i < tiles.size(); i++){
			if((tiles.size() - 1 - i)<100 && (tiles.size() - 1 - i)>9) stringVersion = stringVersion + (tiles.size() - 1 - i) + "  "; 
			if((tiles.size() - 1 - i)<10) stringVersion = stringVersion + (tiles.size() - 1 - i) + "   "; 
			for (int j = 0; j<tiles.get(i).size(); j++){
				stringVersion = stringVersion + tiles.get(i).get(j).toString() + " ";
			}
			stringVersion = stringVersion + "\n";
		}
		stringVersion = stringVersion + "    ";
		for (int i=0; i<tiles.get(0).size();i++){
			stringVersion = stringVersion + i + " ";
		}		
		return stringVersion;
	}

}