package com.talos.javatraining.lesson9.strategies;

import com.talos.javatraining.lesson9.events.EventBus;
import com.talos.javatraining.lesson9.events.EventType;
import com.talos.javatraining.lesson9.strategies.impl.CalculatorTemplate;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.function.BiFunction;
import java.util.function.Function;


public class Calculator
{
	private static final String BASIC_MODE = "basic";
	private static final String SCIENTIFIC_MODE = "scientific";

	private EventBus eventBus;
	private CalculatorStrategy calculatorStrategy;
	private Function<BigDecimal, String> basicStrategy, scientificStrategy;
	private String mode = BASIC_MODE;// default mode

	@Inject
	public Calculator(EventBus eventBus)
	{
		this.eventBus = eventBus;

		eventBus.register(EventType.ADD, args -> calculate(getStrategy()::add, args));
		eventBus.register(EventType.SUBTRACT, args -> calculate(getStrategy()::subtract, args));
		eventBus.register(EventType.MULTIPLY, args -> calculate(getStrategy()::multiply, args));
		eventBus.register(EventType.DIVIDE, args -> calculate(getStrategy()::divide, args));
		eventBus.register(EventType.CHANGE_MODE, this::changeMode);

		basicStrategy = value -> value.setScale(5, RoundingMode.HALF_UP).toString();
		scientificStrategy = value -> {
			DecimalFormat formatter = new DecimalFormat("0.00E00");
			formatter.setRoundingMode(RoundingMode.HALF_UP);
			formatter.setMinimumFractionDigits(10);
			return formatter.format(value);
		};
	}

	private CalculatorStrategy getStrategy()
	{
		if (mode.equals(BASIC_MODE))
		{
			calculatorStrategy = new CalculatorTemplate()
			{
				@Override
				protected String convertToString(BigDecimal value)
				{
					return basicStrategy.apply(value);
				}
			};
		}
		else if (mode.equals(SCIENTIFIC_MODE))
		{
			calculatorStrategy = new CalculatorTemplate()
			{
				@Override
				protected String convertToString(BigDecimal value)
				{
					return scientificStrategy.apply(value);
				}
			};
		}

		return calculatorStrategy;
	}

	private void calculate(BiFunction<String, String, String> operation, String[] args)
	{
		String result = operation.apply(args[0], args[1]);
		eventBus.notify(EventType.RESULT, "> " + result);
	}

	private void changeMode(String[] args)
	{
		String tempMode = args[0];
		if (!tempMode.equals(BASIC_MODE) && !tempMode.equals(SCIENTIFIC_MODE))
		{
			eventBus.notify(EventType.RESULT, "not supported mode: " + tempMode);
			return;
		}
		mode = tempMode;
		eventBus.notify(EventType.RESULT, "mode: " + mode);
	}
}
