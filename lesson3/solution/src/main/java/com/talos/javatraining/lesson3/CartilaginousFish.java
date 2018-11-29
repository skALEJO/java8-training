package com.talos.javatraining.lesson3;

import java.util.List;

import static java.util.Arrays.asList;


public interface CartilaginousFish extends Fish
{
	@Override
	default List<String> getCharacteristics()
	{
		List<String> characteristics = Fish.super.getCharacteristics();
		characteristics.addAll(asList("They have skeleton made of cartilage rather than bone"));
		return characteristics;
	}
}
