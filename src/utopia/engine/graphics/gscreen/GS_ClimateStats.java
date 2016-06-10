package utopia.engine.graphics.gscreen;

import java.awt.Point;

import utopia.basic.controls.MouseInput.MouseActionType;
import utopia.basic.helpers.Helper;
import utopia.basic.helpers.PerlinNoise;
import utopia.engine.graphics.surfaces.Button;
import utopia.engine.graphics.surfaces.DotGraph;
import utopia.engine.graphics.surfaces.HeightMap;
import utopia.engine.graphics.surfaces.StaticImage;

public class GS_ClimateStats extends MGameScreen {
	private StaticImage bg = new StaticImage("res/ui_elements/clima.png");
	private DotGraph graph = new DotGraph(768, 165, 6, 30);
	private Button btn = new Button(100, 48);
	private Point pointer = new Point();
	private HeightMap map = new HeightMap(600, 600);
	
	
	public GS_ClimateStats() {
		bg.show(0);
		
		graph.setPosition(16, 414);
		graph.show(0);
		
		btn.show(0);
		btn.setPosition(10, 10);
		
		map.setPosition(150, 0);
		map.setMap(PerlinNoise.generatePerlinNoise(123, 600, 600, 6));
		map.show(0);
		
		//super.surfaces.add(bg);
		//super.surfaces.add(graph);
		//super.surfaces.add(btn);
		super.surfaces.add(map);
	}

	
	public void addTemperature(float temp){
		graph.addValue(temp);
	}
	
	@Override
	protected void handleMouse(MouseActionType actionType) {
		
		switch (actionType) {
		case MOVE:
			btn.setMousePosition(mouse.getMousePosition().x, mouse.getMousePosition().y);
			break;
		case CLICK:
			btn.setMouseClick();
			break;
		default:
			break;
		}
		
	}
	
	@Override
	protected void handleKeyboard() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void updateLogic() {
		
		if (btn.isActive()){
			System.out.println("botão!");
		}
	}
	
}
