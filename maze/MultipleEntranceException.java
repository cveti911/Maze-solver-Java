package maze;
import java.io.IOException;
import java.lang.RuntimeException;
/**
*Exception that is thrown if there is more than one entrance to the maze
*@author Tsvetelina Kurteva
*@version 24th April 2020
*/
public class MultipleEntranceException extends InvalidMazeException{
	/**
	*constructor of the class
	*/
	public MultipleEntranceException(String messege){
			super(messege);

		}
}		