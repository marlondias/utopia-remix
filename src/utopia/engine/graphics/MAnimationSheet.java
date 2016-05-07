package utopia.engine.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class MAnimationSheet {
	private int width; //Dimensões da animação
	private int height;
	private int frameCount; //Quantos frames compõe a animação
	private int duration; //Duração de um ciclo dessa animação (em ms)
    private BufferedImage img; //Imagem que contém os frames
    private boolean valid; //trava de segurança (que não impede nada)

    private int frameDuration; //Duração de cada frame (em ms)
    private int currentFrame; //Marca o frame a ser exibido
    private boolean running = false; //Estado da animação
    private long lastUpdate;
    private BufferedImage lastFrame;

    
    public MAnimationSheet(int width, int height, int frames, int duration, String path){
    	if (width <= 0 || height <= 0) return; //Erro nas dimensões informadas
    	this.width = width;
    	this.height = height;

    	if (frames <= 0) return; //Erro na quantidade de frames
    	this.frameCount = frames;

    	if (duration < frames) return; //Duração insuficiente para animar
    	this.duration = duration;
        this.frameDuration = this.duration / this.frameCount;
        
    	//Tenta o carregamento usando PATH como endereço da imagem
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException ex) {
            Logger.getLogger(MTileset.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (img == null) return;

        //Verifica se o tamanho da imagem é compatível com a animação
        if (img.getWidth() % width != 0) return;
        if (img.getHeight() % height != 0) return;
        if ((img.getHeight() / height) < frames) return; //Não tem frames suficientes

        
        //Limitador1 (imagem deve conter apenas uma coluna com os frames de) -- remover
        if (img.getWidth() > width) return;
        
        
        this.lastFrame = img.getSubimage(0, 0, this.width, this.height);
        this.lastUpdate = 0L;
        
        this.valid = true; //A animação pode ser acessada com segurança
    }
    
    
    public boolean isValid(){
    	return this.valid;
    }
    
    public void play(){
    	this.currentFrame = 0;
    	this.running = true;
    }

    public void pause(){
    	this.running = false;
    }

    public void stop(){
    	this.currentFrame = 0;
    	this.running = false;
    }

    public void changeDuration(int newDuration){
    	if (newDuration < frameCount) return; //Duração insuficiente para animar
    	this.duration = newDuration;
    	this.frameDuration = newDuration / frameCount;
    }

    public BufferedImage getFrame(){
    	//Retorna a imagem do frame atual e atualiza o estado da animação
    	
    	if (running){
			if (System.nanoTime() - lastUpdate >= frameDuration){
                //Hora de avançar o frame
				currentFrame++;
				if (currentFrame >= frameCount) currentFrame = 0;

		    	int posX = 0; //Lembrete: use posX para trocar entre "poses"
		    	int posY = currentFrame * this.height;

				lastUpdate = System.nanoTime();
		    	return img.getSubimage(posX, posY, this.width, this.height);
			}
    	}

    	//Nada a fazer, mantenha o frame atual
		return this.lastFrame;
    }

}
