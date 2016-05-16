package utopia.basic;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GameLoop implements Runnable {
    public static final int WIDTH = 800; //largura da tela
    public static final int HEIGHT = 600;//(WIDTH / 16) * 9; //altura da tela (proporcão 16:9)
    public static final int SCALE = 1; //multiplica o tamanho da tela

    private JFrame frame; //janela para exibir o jogo
    private Canvas canvas; //tela para renderização
    private boolean running = false; //condição para o gameloop
        
    private GameGraphics gfx;
    private GameLogic logic;

    
    
    public GameLoop(){
    	canvas = new Canvas();
    	canvas.setIgnoreRepaint(true); //evita renderização passiva
    	canvas.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE)); //mantem o tamanho da tela fixo
        canvas.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        canvas.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        
        frame = new JFrame("2D Game Mgine");
        frame.setIgnoreRepaint(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //quando fechar, mata a jframe
        frame.setResizable(false);
        frame.setLayout(new BorderLayout()); //evita erro com a borda        
        frame.add(canvas, BorderLayout.CENTER); //coloca o Canvas dentro desta janela
        frame.pack(); //mantem as dimensões perto do PreferredSize
        frame.setLocationRelativeTo(null); //centraliza
        frame.setVisible(true);
        
        gfx = new GameGraphics(canvas);
        logic = new GameLogic(canvas, gfx);
    }
    
    
    public void init(){
    	//Não sei o que por aqui
    }
    
    public synchronized void start(){
        this.running = true; //permite o inicio do gameloop
        new Thread(this).start(); //chama o run() do objeto da thread, nesse caso o run() abaixo
    }
    
    public synchronized void stop(){
        this.running = false; //termina o gameloop
    }
    
    public void run() {
        final int TARGET_FPS = 60; //fps desejado
        final double NS_PER_UPDATE = 1000000000D / (double)TARGET_FPS; //1 segundo dividido em 60 partes (1000000000ns = 1s)
        
        int ups = 0; //updates per second
        int fps = 0; //frames per second
        double delta = 0D; //usado para medir se ja passou 1 segundo
        
        long time_lastLoop = System.nanoTime(); //marca quando ocorreu o ultimo loop do WHILE abaixo
        long time_lastSecond = System.nanoTime(); //usado para exibir as informações a cada 1 segundo
        
        init(); //chama a titleScreen
        this.canvas.requestFocus();
        
        while (this.running){
            long time_now = System.nanoTime(); //coleta o tempo na entrada do WHILE
            delta += (time_now - time_lastLoop) / NS_PER_UPDATE; //acumula a diferença de tempo (divisão??)
            time_lastLoop = time_now; //o tempo atual será usado na proxima iteração
            
            boolean renderNow = true; //indica se pode atualizar a tela (colocar true se tiver o SLEEP)
            
            while(delta >= 1){
                //limitador de updates
                ups++;
                //update();
                logic.update();
                delta -= 1;
                renderNow = true;
            }
            
            //diminui a performance de proposito (retirar depois dos testes)
            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (renderNow){
                //limitador de frames
                fps++;
                gfx.render();
            }
            
            if (System.nanoTime() - time_lastSecond >= 1000000000){
                //passou 1 segundo
                time_lastSecond += 1000000000; //soma mais 1 segundo na variavel
                //System.out.println("UPS: " + ups + " FPS: " + fps);
                this.frame.setTitle("UPS: " + ups + " FPS: " + fps);
                ups = 0; //zera tudo pra usar no proximo segundo
                fps = 0;
            }    
        }
    }

}
