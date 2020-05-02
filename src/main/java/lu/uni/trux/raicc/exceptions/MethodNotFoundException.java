package lu.uni.trux.raicc.exceptions;

public class MethodNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public MethodNotFoundException(String m){
		super(m);
	}
}
