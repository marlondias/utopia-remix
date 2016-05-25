package utopia.engine.graphics;

//Contém dados sobre um efeito de transição entre (0.0 e 1.0)
public class TransitionController {
	private boolean goingIn = false; //0 -> 1
	private boolean goingOut = false; //1 -> 0
	private double transLevel = 0; //Nível do efeito
	private int duration; //Duração do efeito
	private long time_start; //Momento em que o efeito começou
	
	
	public TransitionController(){
		//Implementar tipos: Exponencial, Log e Linear.
	}

	
	private void updateTransLevel(){
		long deltaTime = System.currentTimeMillis() - time_start;
		
		if (deltaTime >= duration){
			//Finalize o efeito
			if (goingIn) transLevel = 1.0;
			if (goingOut) transLevel = 0;
			goingIn = false;
			goingOut = false;
			return;
		}
		
		//Atualiza o valor baseado no delta
		if (goingIn) transLevel = (double) deltaTime / duration;
		if (goingOut) transLevel = 1 - ((double) deltaTime / duration);
	}
	
	public void transitionIn(int durationMS){
		if (duration < 0) return; //Erro
		
		if (durationMS == 0) transLevel = 1.0; //Efeito é instantantâneo
		if (transLevel == 1.0) return; //Não precisa calcular

		duration = durationMS;
		goingIn = true;
		goingOut = false;
		time_start = System.currentTimeMillis();
	}

	public void transitionOut(int durationMS){
		if (duration < 0) return; //Erro
		
		if (durationMS == 0) transLevel = 0; //Efeito é instantantâneo
		if (transLevel == 0) return; //Não precisa calcular
		
		duration = durationMS;
		goingIn = false;
		goingOut = true;
		time_start = System.currentTimeMillis();
	}

	public double getTransitionLevel(){
		if (goingIn || goingOut) updateTransLevel();
		return transLevel;
	}
	
}
