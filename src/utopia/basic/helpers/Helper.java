package utopia.basic.helpers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//Processos repetitivos que são necessários em várias classes
public class Helper {
	
	/**
	 * Carrega um arquivo de imagem do local informado, pode retornar NULL
	 * 
	 * @param file o caminho completo do arquivo
	 * @return BufferedImage com o conteúdo visual da imagem
	 */
	public static BufferedImage openImageFile(String file){
		File f = new File(file);
		BufferedImage img = null;
        try {
			img = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return img;
	}
	

}
