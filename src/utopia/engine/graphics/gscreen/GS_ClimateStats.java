package utopia.engine.graphics.gscreen;

import utopia.basic.controls.MouseInput.MouseActionType;
import utopia.engine.graphics.surfaces.DotGraph;
import utopia.engine.graphics.surfaces.StaticImage;

public class GS_ClimateStats extends MGameScreen {
	private StaticImage bg = new StaticImage("res/ui_elements/clima.png");
	private DotGraph graph = new DotGraph(768, 165, 6, 30);
	
	
	public GS_ClimateStats() {
		bg.show(0);
		
		graph.setPosition(16, 414);
		graph.show(0);
		
		super.surfaces.add(bg);
		super.surfaces.add(graph);
	}

	
	public void addTemperature(float temp){
		graph.addValue(temp);
	}
	
	@Override
	protected void handleMouse(MouseActionType actionType) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void handleKeyboard() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void updateLogic() {
		
	}
	
}
