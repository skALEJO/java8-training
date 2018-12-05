package com.talos.javatraining.lesson9.commands.impl;

import com.talos.javatraining.lesson9.commands.AppCommand;
import com.talos.javatraining.lesson9.events.EventBus;
import com.talos.javatraining.lesson9.events.EventType;


public class CommandTemplate implements AppCommand
{

	private String[] args;
	private EventBus eventBus;
	private EventType eventType;

	public CommandTemplate(EventBus eventBus, EventType eventType, String... args)
	{
		this.args = args;
		this.eventBus = eventBus;
		this.eventType = eventType;
	}

	@Override
	public void execute()
	{
		eventBus.notify(getEvent(), args);
	}

	public EventType getEvent()
	{
		return eventType;
	}
}
