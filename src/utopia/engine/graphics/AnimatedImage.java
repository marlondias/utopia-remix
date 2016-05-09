package utopia.engine.graphics;

import java.awt.image.BufferedImage;

public class AnimatedImage extends MSurface {
	private MAnimationSheet animSheet;

	
	public AnimatedImage(int width, int height, MAnimationSheet anim) {
		super(width, height);
		this.animSheet = anim;
		this.animSheet.play(); //mudar?
	}
	
	
	@Override
	public void show(){
		super.show();
		animSheet.play();
	}

	@Override
	public void hide(){
		animSheet.pause();
		super.hide();
	}
	
	@Override
	public void updateGraphics() {
		BufferedImage img = animSheet.getFrame();
		super.drawingSurface.drawImage(img, 0, 0, super.getWidth(), super.getHeight(), 0, 0, img.getWidth(), img.getHeight(), null);
	}

}
