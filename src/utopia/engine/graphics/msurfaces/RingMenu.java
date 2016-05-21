package utopia.engine.graphics.msurfaces;

import java.awt.Color;
import java.awt.Graphics2D;

import utopia.engine.graphics.MSurface;

//Um menu really cool que gira :)
public class RingMenu extends MSurface {
	//Enum com tipo de menu RING1(48, true, true, false, true, false, false) relativo às opções diponíveis no menu
	
	private int radius;
	private double separationAngle;
	private int itemCount;
	private int itemSize = 48;
	private final double angleFix = Math.toRadians(90); //Corrige posição do primeiro item
	private double offset; //Correção de X e Y para cada item
	
	
	public RingMenu(int items, int radius) {
		super(1,1);
		
		this.radius = radius;
		this.separationAngle = Math.toRadians(360.00 / items);
		this.itemCount = items;
		
		int center = radius + (itemSize >> 1) + 1;
		this.offset = center - (itemSize >> 1);
		
		//Calcular tamanho necessário da superfície
		int size = center * 2;
		super.changeSize(size, size);
		super.validate();
	}
	
	
	@Override
	public void updateGraphics() {
		double angleIncrement = separationAngle; //Multiplicar nesse também da um efeito legal
		double angle = -angleFix * super.getTransitionLevel();
		double r = radius * super.getTransitionLevel();
		
		for (int i=0; i<itemCount; i++){
			int x = (int) (r * Math.cos(angle) + offset); //Calcula as coordenadas do item
			int y = (int) (r * Math.sin(angle) + offset);
			
			super.getDrawingSurf().fillOval(x, y, itemSize, itemSize); //Desenha o círculo
			angle += angleIncrement; //Atualiza para o próximo item
		}
	}

}
