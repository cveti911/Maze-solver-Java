package maze;
import java.io.IOException;
import java.lang.RuntimeException;
/**
*Exception that is thrown if there is more than no exit to the maze
*@author Tsvetelina Kurteva
*@version 24th April 2020
*/
public class NoExitException extends InvalidMazeException{
	/**
	*constructor of the class
	*/
	public NoExitException(String message){
		super(message);

	}
}		