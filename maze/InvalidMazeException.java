package maze;
import java.lang.RuntimeException;

/**
*This is a parent Exception of a few other Exceptions that the maze might throw
*@author Tsvetelina Kurteva
*@version 24th April 2020
*/
public class InvalidMazeException extends RuntimeException{
	/**
	*constructor of the class
	*/
	public InvalidMazeException(String message){
		super(message);
	}
}