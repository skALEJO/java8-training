package com.talos.javatraining.lesson3;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;


public interface Invertebrate extends Animal
{
	@Override
	default List<String> getCharacteristics()
	{
		List<String> characteristics = new ArrayList<>(asList("Lack of backbones and internal skeletons", "Simple anatomy"));
		return characteristics;
	}
}
