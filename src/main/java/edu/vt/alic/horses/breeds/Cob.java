package edu.vt.alic.horses.breeds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;

import edu.vt.alic.horses.Specialization;

public class Cob extends Breed {

	@Override
	public String getName() {
		return "Cob";
	}

	@Override
	public double getHeight() {
		return 14.2;
	}

	@Override
	public Specialization getSpecialization() {
		return Specialization.STAMINA;
	}

	@Override
	public List<Color> getSupportedColors() {
		return new ArrayList<Color>(Arrays.asList(Color.values()));
	}

	@Override
	public List<Style> getSupportedStyles() {
		return new ArrayList<Style>(Arrays.asList(Style.NONE, Style.WHITE, Style.WHITEFIELD));
	}
}
