package edu.vt.alic.horses.breeds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;

import edu.vt.alic.horses.Specialization;

public class Lippizzaner extends Breed {
	
	@Override
	public String getName() {
		return "Lippizzaner";
	}

	@Override
	public double getHeight() {
		return 16.1;
	}

	@Override
	public Specialization getSpecialization() {
		return Specialization.JUMP;
	}

	@Override
	public List<Color> getSupportedColors() {
		return new ArrayList<Color>(Arrays.asList(Color.BLACK, Color.BROWN, Color.DARK_BROWN, Color.GRAY, Color.CREAMY, Color.WHITE));
	}

	@Override
	public List<Style> getSupportedStyles() {
		return new ArrayList<Style>(Arrays.asList(Style.NONE, Style.WHITE, Style.BLACK_DOTS));
	}

}
