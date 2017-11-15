package edu.vt.alic.horses.breeds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;

import edu.vt.alic.horses.Specialization;

public class Mustang extends Breed {

	@Override
	public String getName() {
		return "Mustang";
	}

	@Override
	public double getHeight() {
		return 15.0;
	}

	@Override
	public Specialization getSpecialization() {
		return Specialization.SWIFTNESS;
	}
	
	@Override
	public List<Color> getSupportedColors() {
		return new ArrayList<Color>(Arrays.asList(Color.values()));
	}

	@Override
	public List<Style> getSupportedStyles() {
		return new ArrayList<Style>(Arrays.asList(Style.BLACK_DOTS, Style.NONE, Style.WHITE));
	}
}
