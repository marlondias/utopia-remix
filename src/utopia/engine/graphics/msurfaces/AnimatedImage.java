package utopia.engine.graphics.msurfaces;

import java.awt.image.BufferedImage;

import utopia.engine.graphics.MAnimationSheet;
import utopia.engine.graphics.MSurface;

public class AnimatedImage extends MSurface {
	private MAnimationSheet animSheet;

	
	public AnimatedImage(int width, int height, MAnimationSheet anim) {
		super(width, height);
		if (anim == null) return;
		this.animSheet = anim;
		super.validate();

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
		super.drawBuffImg(img);
	}

}
