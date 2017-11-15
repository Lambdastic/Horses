package edu.vt.alic.horses;

public enum Gender {
	STALLION,
	MARE,
	GELD;
	
	@Override
	public String toString() {
		switch (this) {
		case STALLION: return "Stallion";
		case MARE: return "Mare";
		case GELD: return "Geld";
		}
		return null;
	}
}
