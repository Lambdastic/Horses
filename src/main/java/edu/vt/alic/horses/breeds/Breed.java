package edu.vt.alic.horses.breeds;

import java.util.List;

import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;

import edu.vt.alic.horses.Specialization;

public abstract class Breed {
	public abstract String getName();
	public abstract double getHeight();
	public abstract Specialization getSpecialization();
	public abstract List<Color> getSupportedColors();
	public abstract List<Style> getSupportedStyles();
}
