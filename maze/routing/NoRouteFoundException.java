package maze.routing;
import java.lang.RuntimeException;
/**
*This exception occurs if there is no exit to the maze
*@author Tsvetelina Kurteva
*@version 24th April 2020
*/
public class NoRouteFoundException extends RuntimeException{
	/**
	*constructor of the class
	*/	
	public NoRouteFoundException(String message){
		super(message);
	}
}		