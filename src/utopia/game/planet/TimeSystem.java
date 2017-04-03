package utopia.game.planet;

/***
 * Representa a contagem de tempo dentro do jogo.
 * O ano tem 13 meses de 28 dias, com 1 dia extra para comemorações de ano novo. 
 * Não existe ano bissexto.
 * 
 * As horas são apresentadas no formato 24/60, mas isso poderá ser customizado.
 * Internamente, o tempo é contado em um float variando entre 0 e 1, onde 0.0 equivale à 00:00, 
 * 0.5 equivale às 12:00 e 1.0 reinicia a contagem em 00:00.
 * É possível reverter a contagem de tempo.
 * 
 * O momento inicial é 00:00 do dia 01/01/0000.
 * 
 * @author marlon
 *
 */
public class TimeSystem {
	private final Planet planet; // Planeta a ser manipulado
	
	private final int hoursInDay = 24; // Quantas horas formam 1 dia in-game
	private final int minutesInHour = 60; // Quantos minutos formam 1 hora in-game
	private final float absoluteHour;
	private final float absoluteMinute;
	private final float absoluteIncrement;
	private float currentAbsTime;
	private int currentHours;
	private int currentMinutes;
	private boolean reverse = false; // Muda o sentido da contagem
	
	private final int yearDuration; // Quantidade de dias em 1 ano
	private final int monthDuration;
	private final int weekDuration;
	private final int totalMonths; // Quantidade de meses em 1 ano
	private int currentYear;
	private int currentMonth;
	private int currentDay; // Dia em relação ao ano
	private int currentDayWeek; // Dia em relação à semana
	private int currentDayMonth; // Dia em relação ao mês
	

	public TimeSystem(Planet p, int ticksInDay){
		if (p == null) throw new IllegalArgumentException("Planet cannot be null.");
		else planet = p;
		
		// Iniciaiza variáveis de tempo
		absoluteHour = 1F / hoursInDay;
		absoluteMinute = absoluteHour / minutesInHour;
		currentAbsTime = 0F;
		currentHours = 0;
		currentMinutes = 0;
		
		// Armazena o quando cada update deve influenciar no tempo absoluto
		if (ticksInDay < 1) throw new IllegalArgumentException("The duration of a day must be at least 1 tick.");
		absoluteIncrement = 1F / ticksInDay;
		
		// Inicializa variáveis de data
		yearDuration = 365;
		monthDuration = 28;
		weekDuration = 7;
		int months = yearDuration / monthDuration;
		if (yearDuration % monthDuration != 0) months++;
		totalMonths = months;
		currentDay = 1;
		currentYear = 0;
		updateCalendarValues();
	}

	public int getHours(){
		return currentHours;
	}
	
	public int getMinutes(){
		return currentMinutes;
	}

	public int getYearDuration(){
		return yearDuration;
	}

	public int getMonthDuration(){
		return monthDuration;
	}

	public int getWeekDuration(){
		return weekDuration;
	}

	public int getCurrentYear(){
		return currentYear;
	}
	
	public int getCurrentMonth(){
		return currentMonth;
	}
	
	public int getCurrentDayInYear(){
		return currentDay;
	}

	public int getCurrentDayInMonth(){
		return currentDayMonth;
	}

	public int getCurrentDayInWeek(){
		return currentDayWeek;
	}

	public void setTime(int hours, int minutes){
		if (hours < 0 || hours >= hoursInDay) throw new IllegalArgumentException("Invalid value for hours.");
		if (minutes < 0 || minutes >= minutesInHour) throw new IllegalArgumentException("Invalid value for minutes.");
		currentAbsTime = hours * absoluteHour + minutes * absoluteMinute;
		updateClockValues();
	}

	public void setDate(int day, int month, int year){
		if (year < 0) throw new IllegalArgumentException("Invalid date. Negative years are not accepted.");
		if (month <= 0 || month > totalMonths) throw new IllegalArgumentException("Invalid date. The month does not exist in this calendar.");
		if (day <= 0 || day > monthDuration) throw new IllegalArgumentException("Invalid date. The day does not exist in this calendar.");
		
		int newDay = (month - 1) * monthDuration + day;
		if (newDay > yearDuration) throw new IllegalArgumentException("Invalid date. The date exceeds this calendar.");
		
		currentYear = year;
		currentDay = newDay;
		updateCalendarValues();
	}

	private void updateClockValues(){
		currentHours = (int)(currentAbsTime / absoluteHour);
		currentMinutes = (int)((currentAbsTime % absoluteHour) / absoluteMinute);
	}

	private void updateCalendarValues(){
		// Atualiza o ano (ou não)
		if (currentDay < 1){
			currentDay = yearDuration;
			currentYear--;
		}
		else if (currentDay > yearDuration){
			currentDay = 1;
			currentYear++;
		}
		
		// Atualiza o mês
		int newMonth = currentDay / monthDuration;
		if (currentDay % monthDuration != 0) newMonth++;
		currentMonth = newMonth;
		
		// Atualiza o dia (mês)
		int newDayMonth = currentDay % monthDuration;
		currentDayMonth = (newDayMonth == 0) ? monthDuration : newDayMonth;
		
		// Atualiza o dia (semana)
		currentDayWeek = currentDay % weekDuration;
	}

	public void update(){
		if (reverse){
			currentAbsTime -= absoluteIncrement;
			if (currentAbsTime < 0){
				currentAbsTime += 1F;
				currentDay--;
				updateCalendarValues();
				changePreviousDay();
			}
		}
		else{
			currentAbsTime += absoluteIncrement;
			if (currentAbsTime >= 1F){
				currentAbsTime -= 1F;
				currentDay++;
				updateCalendarValues();
				changeNextDay();
			}
		}
		updateClockValues();
	}
	
	private void changePreviousDay(){
		// Aqui acontece a magia
		planet.getClimate().prevDay();
	}

	private void changeNextDay(){
		// Aqui acontece a magia
		planet.getClimate().nextDay();
	}

}
