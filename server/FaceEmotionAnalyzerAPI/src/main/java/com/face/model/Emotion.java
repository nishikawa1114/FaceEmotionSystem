package com.face.model;

public class Emotion {
	private double neutral;
	private double anger;
	private double contermpt;
	private double disgust;
	private double fear;
	private double happiness;
	private double sadness;
	private double surprise;
	
	public Emotion(double neutral, double anger, double contermpt, double disgust, double fear, double happiness,
			double sadness, double surprise) {
		super();
		this.neutral = neutral;
		this.anger = anger;
		this.contermpt = contermpt;
		this.disgust = disgust;
		this.fear = fear;
		this.happiness = happiness;
		this.sadness = sadness;
		this.surprise = surprise;
	}

	public double getNeutral() {
		return neutral;
	}

	public void setNeutral(double neutral) {
		this.neutral = neutral;
	}

	public double getAnger() {
		return anger;
	}

	public void setAnger(double anger) {
		this.anger = anger;
	}

	public double getContermpt() {
		return contermpt;
	}

	public void setContermpt(double contermpt) {
		this.contermpt = contermpt;
	}

	public double getDisgust() {
		return disgust;
	}

	public void setDisgust(double disgust) {
		this.disgust = disgust;
	}

	public double getFear() {
		return fear;
	}

	public void setFear(double fear) {
		this.fear = fear;
	}

	public double getHappiness() {
		return happiness;
	}

	public void setHappiness(double happiness) {
		this.happiness = happiness;
	}

	public double getSadness() {
		return sadness;
	}

	public void setSadness(double sadness) {
		this.sadness = sadness;
	}

	public double getSurprise() {
		return surprise;
	}

	public void setSurprise(double surprise) {
		this.surprise = surprise;
	}
	
	
}
