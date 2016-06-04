package utopia.game.planet;

import java.util.Calendar;
import java.util.Date;

import utopia.game.planet.Climate.Season;

//Representa a passagem dos dias de um planeta em relação ao tempo real
public class Time {
	private final int durationOfDay; //Duração de um dia virtual (ms)
	private final Calendar calendar = Calendar.getInstance();
	private Season season = Season.EQUIAT;
	private boolean running = false; //Indica se a atualização ocorre normalmente
	private boolean fast = false; //Dobra velocidade de atualização
	private long time_lastUpdate = 0L;
	
	
	public Time(int duration){
		this.durationOfDay = duration;
	}
	

	public void start(){
		if (time_lastUpdate == 0L){
			time_lastUpdate = System.currentTimeMillis();
			running = true;
		}
		else resume();
	}
	
	public void pause(){
		running = false;
		fast = false;
	}
	
	public void resume(){
		running = true;
		fast = false;
	}
	
	public void speedUp(){
		running = true;
		fast = true;
	}
	
	private void checkSeason(){
		switch (calendar.get(Calendar.MONTH)) {
		case Calendar.JANUARY:
			season = Season.EQUIAT;
			break;
		case Calendar.APRIL:
			season = Season.SOLEK;
			break;
		case Calendar.JULY:
			season = Season.EQUIOT;
			break;
		case Calendar.OCTOBER:
			season = Season.WANEK;
			break;
		default:
			break;
		}
	}
	
	public boolean update(){
		if (!running) return false; //Pause ativo
		
		long delta_time = System.currentTimeMillis() - time_lastUpdate;
		int dur = (fast) ? (durationOfDay >> 1) : durationOfDay;
		if (delta_time >= dur){
			//virtualTime++;
			
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			if (calendar.get(Calendar.DAY_OF_MONTH) == 1) checkSeason();

			time_lastUpdate = System.currentTimeMillis();
			return true; //Atualização ocorreu
		}
		
		return false; //Não é o momento
	}

	public Date getCurrentDate(){
		return calendar.getTime();
	}

	public Season getCurrentSeason(){
		return season;
	}
}
