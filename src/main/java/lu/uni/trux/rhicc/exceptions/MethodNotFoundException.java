package lu.uni.trux.rhicc.exceptions;

public class MethodNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public MethodNotFoundException(String m){
		super(m);
	}
}
