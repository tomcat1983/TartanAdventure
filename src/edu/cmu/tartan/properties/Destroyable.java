package edu.cmu.tartan.properties;

public interface Destroyable {
	void destroy();
	void setDestroyMessage(String s);
	void setDisappears(boolean b);
	boolean disappears();
}
