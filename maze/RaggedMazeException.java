package maze;
import java.io.IOException;
import java.lang.RuntimeException;
/**
*Exception that is thrown if the maze is ragged
*@author Tsvetelina Kurteva
*@version 24th April 2020
*/
public class RaggedMazeException extends InvalidMazeException{
	/**
	*constructor of the class
	*/	
	public RaggedMazeException(String message){
			super(message);
		}
	}
