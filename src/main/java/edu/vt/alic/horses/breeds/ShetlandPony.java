package edu.vt.alic.horses.breeds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;

import edu.vt.alic.horses.Specialization;

public class ShetlandPony extends Breed {

	@Override
	public String getName() {
		return "Shetland Pony";
	}

	@Override
	public double getHeight() {
		return 10.2;
	}

	@Override
	public Specialization getSpecialization() {
		return Specialization.SPEED;
	}

	@Override
	public List<Color> getSupportedColors() {
		return new ArrayList<Color>(Arrays.asList(Color.values()));
	}

	@Override
	public List<Style> getSupportedStyles() {
		return new ArrayList<Style>(Arrays.asList(Style.WHITEFIELD, Style.BLACK_DOTS, Style.WHITE, Style.NONE));
	}
}
