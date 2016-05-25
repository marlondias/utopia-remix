package utopia.engine.graphics.surfaces;

import java.awt.image.BufferedImage;

import utopia.engine.graphics.MAnimationSheet;

public class AnimatedImage extends MSurface {
	private MAnimationSheet animSheet;
	private boolean isLoop = true;

	
	public AnimatedImage(int width, int height, MAnimationSheet anim) {
		super(width, height);
		if (anim == null) return;
		this.animSheet = anim;
		super.validate();
	}

	
	public void setLoop(boolean value){
		isLoop = value;
	}

	@Override
	public void show(int duration){
		if (duration < 0) duration = 0;
		super.show(duration);
		animSheet.play();
	}

	@Override
	public void hide(int duration){
		if (duration < 0) duration = 0;
		animSheet.pause();
		super.hide(duration);
	}
	
	@Override
	public void updateGraphics() {
		BufferedImage img = animSheet.getFrame();
		if (img != null) getDrawingSurf().drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
	}

}
