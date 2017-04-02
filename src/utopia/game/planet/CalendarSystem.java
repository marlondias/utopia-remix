package utopia.game.planet;

/***
 * Representa o sistema de tempo in-game.O ano tem 13 meses de 28 dias, com 1 dia extra para o 
 * ano novo (caos geral). Não existe ano bissexto.
 * 
 * @author marlon
 *
 */
public class CalendarSystem {
	private final int yearDuration;
	private final int monthDuration;
	private final int weekDuration;
	private final int totalMonths;
	private int currentYear;
	private int currentMonth;
	private int currentDay;
	private int currentDayWeek;
	private int currentDayMonth;
	
	
	public CalendarSystem(){
		yearDuration = 365;
		monthDuration = 28;
		weekDuration = 7;
		
		int months = yearDuration / monthDuration;
		if (yearDuration % monthDuration != 0) months++;
		totalMonths = months;
		
		currentDay = 1;
		currentYear = 0;
		updateValues();
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

	public void setDate(int day, int month, int year){
		if (year < 0) throw new IllegalArgumentException("Invalid date. Negative years are not accepted.");
		if (month <= 0 || month > totalMonths) throw new IllegalArgumentException("Invalid date. The month does not exist in this calendar.");
		if (day <= 0 || day > monthDuration) throw new IllegalArgumentException("Invalid date. The day does not exist in this calendar.");
		
		int newDay = (month - 1) * monthDuration + day;
		if (newDay > yearDuration) throw new IllegalArgumentException("Invalid date. The date exceeds this calendar.");
		
		currentYear = year;
		currentDay = newDay;
		updateValues();
	}
	
	private void updateValues(){
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
	
	public void previousDay(){
		currentDay--;
		updateValues();
	}

	public void nextDay(){
		currentDay++;
		updateValues();
	}
	
}
