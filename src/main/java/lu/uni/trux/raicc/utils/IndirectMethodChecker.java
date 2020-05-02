package lu.uni.trux.raicc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lu.uni.trux.raicc.exceptions.MethodNotFoundException;
import soot.SootMethod;

public class IndirectMethodChecker {

	private static IndirectMethodChecker instance;
	protected Logger logger = LoggerFactory.getLogger(Utils.class);
	private Map<String, Pair<Boolean, String>> methods;	

	private IndirectMethodChecker() {
		this.methods = new HashMap<String, Pair<Boolean,String>>();
		this.loadMethods();
	}

	public static IndirectMethodChecker v() {
		if(instance == null) {
			instance = new IndirectMethodChecker();
		}
		return instance;
	}

	private void loadMethods() {
		InputStream fis = null;
		BufferedReader br = null;
		String line = null;
		try {
			fis = Utils.class.getResourceAsStream(Constants.INDIRECT_ICC_METHODS);
			br = new BufferedReader(new InputStreamReader(fis));
			String method = null;
			boolean forResult = false;
			String type = null;
			Pair<Boolean, String> v = null;
			while ((line = br.readLine()) != null)   {
				String[] split = line.split("\\|");
				method = split[0];
				forResult = split[1].equals("1") ? true:false;
				type = split[2];
				v = new Pair<Boolean, String>(forResult, type);
				this.methods.put(method, v);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		try {
			br.close();
			fis.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public boolean isIndirectMethod(String m) {
		return this.methods.containsKey(m);
	}

	public boolean isIndirectMethod(SootMethod m) {
		return this.isIndirectMethod(m.getSignature());
	}

	public boolean isForResultMethod(String m) throws MethodNotFoundException {
		this.checkMethodExistence(m);
		Pair<Boolean, String> v = this.methods.get(m);
		return v.getValue0();
	}
	
	public boolean isForResultMethod(SootMethod m) throws MethodNotFoundException {
		return this.isForResultMethod(m.getSignature());
	}
	
	public boolean isWrapperBase(String m) throws MethodNotFoundException {
		this.checkMethodExistence(m);
		Pair<Boolean, String> v = this.methods.get(m);
		return v.getValue1().split(":")[0].equals("b");
	}
	
	public boolean isWrapperBase(SootMethod m) throws MethodNotFoundException {
		return this.isWrapperBase(m.getSignature());
	}
	
	public boolean isWrapperParameter(String m) throws MethodNotFoundException {
		this.checkMethodExistence(m);
		Pair<Boolean, String> v = this.methods.get(m);
		return v.getValue1().split(":")[0].equals("p");
	}
	
	public boolean isWrapperParameter(SootMethod m) throws MethodNotFoundException {
		return this.isWrapperParameter(m.getSignature());
	}
	
	private void checkMethodExistence(String m) throws MethodNotFoundException {
		Pair<Boolean, String> v = this.methods.get(m);
		if(v == null) {
			throw new MethodNotFoundException(String.format("Method not found in list of indirect methods: %s", m));
		}
	}
}
