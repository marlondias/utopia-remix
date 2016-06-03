package utopia.engine.graphics.surfaces;

import java.awt.image.BufferedImage;

public class InteractiveButton extends MSurface {
	public enum ButtonState{ NORMAL, HOVER, ACTIVE }

	private BufferedImage icon;
	private String text;
	
	
	protected InteractiveButton(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void updateGraphics() {
		// TODO Auto-generated method stub
	}
	
}
