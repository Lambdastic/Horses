package edu.vt.alic.horses;

public enum Specialization {
	SWIFTNESS,
	STAMINA,
	JUMP,
	SPEED;
	
	@Override
	public String toString() {
		switch(this) {
		case SWIFTNESS: return "Swiftness";
		case STAMINA: return "Stamina";
		case JUMP: return "Jump";
		case SPEED: return "Speed";
		}
		return null;
	}
}
