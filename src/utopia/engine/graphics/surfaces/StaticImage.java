package utopia.engine.graphics.surfaces;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import utopia.basic.Assets;

//Uma imagem simples (implementar efeitos e tals)
public class StaticImage extends MSurface {
	private BufferedImage currentImage;
	
	
	public StaticImage(String path) {
		//Carrega imagem do arquivo em PATH
		super(1, 1);
		
		if (path == null) return;
		currentImage = Assets.openImageFile(path);
        if (currentImage == null) return; //Não carregou
        
        changeSize(currentImage.getWidth(), currentImage.getHeight());
        getDrawingSurf().drawImage(currentImage, 0, 0, currentImage.getWidth(), currentImage.getHeight(), null);
		
		super.validate();
	}
	
	public StaticImage(BufferedImage img) {
		//Copia a imagem informada
		super(1, 1);
		
		currentImage = img;
        if (currentImage == null) return; //IMG não existe
        
        changeSize(currentImage.getWidth(), currentImage.getHeight());
        getDrawingSurf().drawImage(currentImage, 0, 0, currentImage.getWidth(), currentImage.getHeight(), null);
		
		super.validate();
	}

	
	@Override
	public void updateGraphics() {
		//Nesse caso não há o que atualizar
	}

}
