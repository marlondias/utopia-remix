package utopia.game.planet;

/***
 * Representa o sistema de horas e minutos in-game. Usa o formato 24/60, mas poderá ser customizado.
 * Internamente, o tempo é contado em um float variando entre 0 e 1, onde 0.0 equivale à 00:00, 
 * 0.5 equivale às 12:00 e 1.0 reinicia a contagem em 00:00.
 * É possível reverter a contagem de tempo.
 * 
 * @author marlon
 *
 */
public class ClockSystem {
	private final int hoursInDay = 24; // Quantas horas formam 1 dia in-game
	private final int minutesInHour = 60; // Quantos minutos formam 1 hora in-game
	private final float absoluteHour;
	private final float absoluteMinute;
	private final float absoluteIncrement;
	private float currentAbsTime;
	private int currentHours;
	private int currentMinutes;
	private boolean reverse = false;
	private CalendarSystem calendar;
	
	
	public ClockSystem(int ticksInDay){
		absoluteHour = 1F / hoursInDay;
		absoluteMinute = absoluteHour / minutesInHour;
		currentAbsTime = 0F;
		currentHours = 0;
		currentMinutes = 0;
		
		// Armazena o quando cada update deve influenciar no tempo absoluto
		if (ticksInDay < 1) throw new IllegalArgumentException("Invalid value for ticks in a day.");
		absoluteIncrement = 1F / ticksInDay;
	}
	

	public int getHours(){
		return currentHours;
	}
	
	public int getMinutes(){
		return currentMinutes;
	}

	public void setTime(int hours, int minutes){
		if (hours < 0 || hours >= hoursInDay) throw new IllegalArgumentException("Invalid value for hours.");
		if (minutes < 0 || minutes >= minutesInHour) throw new IllegalArgumentException("Invalid value for minutes.");
		currentAbsTime = hours * absoluteHour + minutes * absoluteMinute;
		updateValues();
	}
	
	public void addCalendar(CalendarSystem cal){
		calendar = cal;
	}

	private void updateValues(){
		currentHours = (int)(currentAbsTime / absoluteHour);
		currentMinutes = (int)((currentAbsTime % absoluteHour) / absoluteMinute);
	}
	
	private void changeNextDay(){
		if (calendar != null) calendar.nextDay();
	}

	private void changePreviousDay(){
		if (calendar != null) calendar.previousDay();
	}

	public void update(){
		if (reverse){
			currentAbsTime -= absoluteIncrement;
			if (currentAbsTime < 0){
				changePreviousDay();
				currentAbsTime += 1F;
			}
		}
		else{
			currentAbsTime += absoluteIncrement;
			if (currentAbsTime >= 1F){
				changeNextDay();
				currentAbsTime -= 1F;
			}
		}
		updateValues();
	}

}
