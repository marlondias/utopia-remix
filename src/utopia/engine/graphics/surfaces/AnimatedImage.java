package utopia.engine.graphics.surfaces;

import java.awt.image.BufferedImage;

import utopia.engine.graphics.MAnimationSheet;

public class AnimatedImage extends MSurface {
	private MAnimationSheet animSheet;

	
	public AnimatedImage(int width, int height, MAnimationSheet anim) {
		super(width, height);
		if (anim == null) return;
		this.animSheet = anim;
		super.validate();

		this.animSheet.play(); //mudar?
	}
	
	
	public void show(){
		super.transitionIn(200);
		animSheet.play();
	}

	public void hide(){
		animSheet.pause();
		super.transitionOut(200);
	}
	
	@Override
	public void updateGraphics() {
		BufferedImage img = animSheet.getFrame();
		super.drawBuffImg(img);
	}

}
