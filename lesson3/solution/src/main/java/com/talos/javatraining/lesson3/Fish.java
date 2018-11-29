package com.talos.javatraining.lesson3;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;


public interface Fish extends Animal
{
	@Override
	default List<String> getCharacteristics()
	{
		List<String> characteristics = new ArrayList<>(
				asList("They breathe using gills", "They have dominated the world's oceans, lakes and rivers"));
		return characteristics;
	}
}
