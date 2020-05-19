package maze;
import java.io.IOException;
import java.lang.RuntimeException;
/**
*Exception that is thrown if there is more than no entrance to the maze
*@author Tsvetelina Kurteva
*@version 24th April 2020
*/
public class NoEntranceException extends InvalidMazeException{
	/**
	*constructor of the class
	*/
	public NoEntranceException(String message){
		super(message);

	}
}	