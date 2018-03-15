package edu.cmu.tartan.properties;

public interface Destroyable {
	public final static String OPERATION = "destroy";
	void destroy();
	void setDestroyMessage(String s);
	void setDisappears(boolean b);
	boolean disappears();
}
