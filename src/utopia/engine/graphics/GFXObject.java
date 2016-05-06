package utopia.engine.graphics;

import java.awt.Dimension;
import java.awt.Point;

public abstract class GFXObject {
	private Dimension dimension;
	private Point position;
	private int[] pixels;
	
	
	public abstract void update(); //Atualiza posições e objetos antes de exibir

	public int[] render(){
		return this.pixels;
	}

}
