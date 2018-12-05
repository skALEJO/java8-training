package com.talos.javatraining.lesson8;



import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;


public class MainImpl implements Main
{

	@Override
	public Instant getInstant(String dateTime)
	{
		LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
		Instant instantDateTime = localDateTime.toInstant(ZoneOffset.ofHours(-5));
		Instant result = instantDateTime.plus(1, ChronoUnit.SECONDS);
		result = result.minus(10, ChronoUnit.MINUTES);
		return result;
	}

	@Override
	public Duration getDuration(Instant a, Instant b)
	{
		Duration duration = Duration.between(a, b);
		Duration result = duration.plus(1, ChronoUnit.DAYS);
		result = result.minus(4, ChronoUnit.HOURS);
		return result;
	}

	@Override
	public String getHumanReadableDate(LocalDateTime localDateTime)
	{
		LocalDateTime result = localDateTime.plus(3, ChronoUnit.HOURS);
		result = result.withMonth(7);
		int oldYear = result.getYear();
		if (oldYear % 2 == 0)
		{
			result = result.withYear(oldYear + 1);
		}
		return DateTimeFormatter.ISO_LOCAL_DATE.format(result);
	}

	@Override
	public LocalDateTime getLocalDateTime(String dateTime)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ssmmHHddMMyyyy");
		LocalDateTime result = LocalDateTime.parse(dateTime, formatter);

		int oldMonth = result.getMonthValue();
		if (oldMonth % 2 != 0)
		{
			result = result.withMonth(oldMonth + 1);
		}

		int oldSeconds = result.getSecond();
		oldSeconds *= 2;
		if (oldSeconds > 59)
		{
			int remainingSeconds = oldSeconds - 60;
			result = result.plus(1, ChronoUnit.MINUTES);
			result = result.withSecond(remainingSeconds);
		}
		else
		{
			result = result.withSecond(oldSeconds);
		}
		return result;
	}

	@Override
	public Period calculateNewPeriod(Period period)
	{
		Period result = period.plusMonths(5);
		result = result.plusDays(6);
		result = result.minus(Period.ofWeeks(2));
		return result;
	}

	@Override
	public LocalDate toLocalDate(Year year, MonthDay monthDay)
	{
		LocalDate result = year.atMonthDay(monthDay);
		result = result.plusYears(3);
		int oldDays = result.getDayOfMonth();
		int newDays = (int) Math.floor(oldDays / 5) * 5;
		if (newDays == 0)
		{
			newDays = 1;
		}
		result = result.withDayOfMonth(newDays);
		return result;
	}

	@Override
	public LocalDateTime toLocalDateTime(YearMonth yearMonth, int dayOfMonth, LocalTime time)
	{
		LocalDate localDate = yearMonth.atDay(dayOfMonth);
		LocalDateTime result = LocalDateTime.of(localDate, time);
		result = result.withSecond(0);
		result = result.minusMinutes(37);
		result = result.plusDays(3);
		return result;
	}

	@Override
	public TemporalAdjuster createTemporalAdjusterNextMonday()
	{
		return TemporalAdjusters.next(DayOfWeek.MONDAY);
	}

	@Override
	public TemporalAdjuster createTemporalAdjusterNextFebruaryFirst()
	{
		TemporalAdjuster NEXT_FEBRUARY_FIRST = localDate -> {
			LocalDate result = (LocalDate) localDate;
			if (result.getMonthValue() >= Month.FEBRUARY.getValue())
			{
				result = result.plusYears(1);
			}
			result = result.withMonth(Month.FEBRUARY.getValue());
			result = result.withDayOfMonth(1);
			return result;
		};
		return NEXT_FEBRUARY_FIRST;
	}

	@Override
	public String adjustDateTime(String localDateTime, TemporalAdjuster adjuster)
	{
		LocalDateTime result = LocalDateTime.parse(localDateTime);
		result = result.with(adjuster);
		return result.toString();
	}

	@Override
	public String processZonedDateTime(String zonedDateTime)
	{
		ZonedDateTime result = ZonedDateTime.parse(zonedDateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		result = result.plusHours(1);
		result = result.withZoneSameInstant(ZoneOffset.UTC);
		int oldMinutes = result.getMinute();
		int newMinutes = (int) Math.floor(oldMinutes / 15) * 15;
		result = result.withMinute(newMinutes);
		return result.format(DateTimeFormatter.RFC_1123_DATE_TIME);
	}
}
