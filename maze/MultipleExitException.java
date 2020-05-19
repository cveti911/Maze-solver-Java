package maze;
import java.io.IOException;
import java.lang.RuntimeException;
/**
*Exception that is thrown if there is more than one exit to the maze
*@author Tsvetelina Kurteva
*@version 24th April 2020
*/
public class MultipleExitException extends InvalidMazeException{
	/**
	*constructor of the class
	*/
	public MultipleExitException(String messege){
			super(messege);
		}

}		