package PlanetWars;

public class ResourceException extends Exception{
	public ResourceException() {
		// TODO Auto-generated constructor stub
		super("íRecursos insuficientes!");
	}
	public ResourceException(String error) {
		super(error);
	}
	
}
