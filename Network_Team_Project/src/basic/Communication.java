package basic;

import java.io.Serializable;

public class Communication implements Serializable{
	public String sentence;
	
	public Communication(String str) {
		this.sentence = str; 
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return sentence;
	}
}
