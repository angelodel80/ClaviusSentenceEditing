package it.cnr.ilc.clavius.domain;

import java.util.ArrayList;

public class PosTaggedToken {
	private String token;
	private String lemma;
	private ArrayList<String> posTag = new ArrayList<String>();
	private ArrayList<String> person = new ArrayList<String>();
	private ArrayList<String> number = new ArrayList<String>();
	private ArrayList<String> tense = new ArrayList<String>();
	private ArrayList<String> mood = new ArrayList<String>();
	private ArrayList<String> voice = new ArrayList<String>();
	private ArrayList<String> gender = new ArrayList<String>();
	private ArrayList<String> cases = new ArrayList<String>();
	private ArrayList<String> degree = new ArrayList<String>();

	private double prob;
	
	public PosTaggedToken() {
		
	}
	
	public PosTaggedToken(String t, String l, String pos) {
		setToken(t);
		setLemma(l);
		setPosTag(pos);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLemma() {
		return lemma;
	}

	public void setLemma(String lemma) {
		this.lemma = lemma;
	}

	public ArrayList<String> getPosTag() {
		return posTag;
	}

	public void setPosTag(String posTag) {
		this.posTag.add(posTag);
	}

	public double getProb() {
		return prob;
	}

	public void setProb(double prob) {
		this.prob = prob;
	}

	public ArrayList<String> getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person.add(person);
	}

	public ArrayList<String> getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number.add(number);
	}

	public ArrayList<String> getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree.add(degree);
	}

	public ArrayList<String> getTense() {
		return tense;
	}

	public void setTense(String tense) {
		this.tense.add(tense);
	}

	public ArrayList<String> getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood.add(mood);
	}

	public ArrayList<String> getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice.add(voice);
	}

	public ArrayList<String> getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender.add(gender);
	}

	public ArrayList<String> getCases() {
		return cases;
	}

	public void setCases(String cases) {
		this.cases.add(cases);
	}

}
