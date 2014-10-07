package it.cnr.ilc.clavius.utils;

public class Sentence {

	private String text = null;
	private String id = null;
	
	public Sentence() {
		// TODO Auto-generated constructor stub
		this(null,null);
	}
	
	public Sentence(String text, String id){
		this.text = text;
		this.id = id;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	

}
