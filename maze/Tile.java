package maze;
import java.io.IOException;
import java.io.Serializable;
/**
*This is the smallest piece of the maze - the tile
*This makes up all of the tiles when a maze is loaded
*@author Tsvetelina Kurteva
*@version 24th April 2020
*/

public class Tile implements Serializable{



	/**
	*This is used to enumerate all of the possible types the tiles could be
	*/
	public enum Type {
		CORRIDOR,ENTRANCE, EXIT, WALL;
		
	}

	/**
	*The type of the tile
	*/
	private Type type;


	/**
	*The counstructor of the class
	*@param typeIn to inisialize type
	*/
	private Tile(Type typeIn){
		type = typeIn;
	}

	/**
	*From char to tile
	*@param charIn is used to be changed to tile
	*@return new tile that has the type of tile that the char indicated it to be
	*/
	protected static Tile fromChar(char charIn){
		if (charIn == '#'){
			return new Tile(Type.WALL);
		}
		if (charIn == '.'){
			return new Tile(Type.CORRIDOR);
		}
		if (charIn == 'e'){
			return new Tile(Type.ENTRANCE);
		}			
	
		if (charIn == 'x'){
			return new Tile(Type.EXIT);
		}

		return null;
	}
	/**
	*From char to tile
	*@return the type of the Tile
	*/
	public Type getType(){

		return type;
	}

	/**
	*Not all tiles are possible to be navigated through
	*@return true if the tile type lets you navigate through it, otherwise - false
	*/

	public boolean isNavigable(){
		if (type == Type.WALL){
			return false;
		}
		if (type == Type.CORRIDOR){
			return true;
		}
		if (type == Type.ENTRANCE){
			return true;
		}
		if (type == Type.EXIT){
			return true;
		}
		return false;
	}	

	/**
	*Returns the Tile type in the form of a string
	*@return the String form of the type of tile
	*/
	public String toString(){
		if (type == Type.WALL){
			return "#";
		}
		if (type == Type.CORRIDOR){
			return ".";
		}
		if (type == Type.ENTRANCE){
			return "e";
		}
		if (type == Type.EXIT){
			return "x";
		}									
		return null;
	}
}	

