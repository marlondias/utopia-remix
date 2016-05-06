package utopia.basic;

//Imports para renderizar uma janela simples (sim, tudo isso)
import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.logging.Level;
import java.util.logging.Logger;

//Imports para a biblioteca gráfica customizada
import utopia.engine.graphics.MRenderer;
import utopia.engine.graphics.MRenderer2;
import utopia.engine.graphics.MTileset;
import utopia.engine.graphics.canvas.MCanvasTile;


public class GameLoop implements Runnable {
    public static final int WIDTH = 800; //largura da tela
    public static final int HEIGHT = 600;//(WIDTH / 16) * 9; //altura da tela (proporcão 16:9)
    public static final int SCALE = 1; //multiplica o tamanho da tela

    private JFrame frame; //janela para exibir o jogo
    private Canvas canvas; //tela para renderização

    private boolean running = false; //condição para o gameloop
    private int tickCount;
    
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB); //cria uma imagem para mostrar na tela
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData(); //MACUMBA
    
    private InputHandler keyboard;
    private GameGraphics gfx;
    private MRenderer renderer;
    private MRenderer2 renderer2;
    private MCanvasTile tileCanvas;
    
    
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
        frame.setLocationRelativeTo(null); //não interfere na posição
        frame.setLayout(new BorderLayout()); //evita erro com a borda        
        frame.add(canvas, BorderLayout.CENTER); //coloca o Canvas dentro desta janela
        frame.pack(); //mantem as dimensões perto do PreferredSize
        frame.setVisible(true);
        
        gfx = new GameGraphics(canvas);
    }
    
    
    public void init(){
        keyboard = new InputHandler(canvas); //instancia e coloca o jogo como alvo

        tileCanvas = new MCanvasTile(288, 288, new MTileset("res/tileset48-utopia2.png", 48, 48));
        tileCanvas.show();
        
        renderer = new MRenderer(WIDTH, HEIGHT);
        renderer2 = new MRenderer2(WIDTH, HEIGHT);
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
        
        init(); //chama a tileScreen
        this.canvas.requestFocus();
        
        while (this.running){
            long time_now = System.nanoTime(); //coleta o tempo na entrada do WHILE
            delta += (time_now - time_lastLoop) / NS_PER_UPDATE; //acumula a diferença de tempo (divisão??)
            time_lastLoop = time_now; //o tempo atual será usado na proxima iteração
            
            boolean renderNow = true; //indica se pode atualizar a tela (colocar true se tiver o SLEEP)
            
            while(delta >= 1){
                //limitador de updates
                ups++;
                update();
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
                //render();
                gfx.render();
            }
            
            if (System.nanoTime() - time_lastSecond >= 1000000000){
                //passou 1 segundo
                time_lastSecond += 1000000000; //soma mais 1 segundo na variavel
                System.out.println("UPS: " + ups + " FPS: " + fps);
                this.frame.setTitle("UPS: " + ups + " FPS: " + fps);
                ups = 0; //zera tudo pra usar no proximo segundo
                fps = 0;
            }    
        }
    }
    
    public void update(){
        //Lógica e matemática
        
        if (keyboard.up.isPressed()) tileCanvas.moveU();
        if (keyboard.down.isPressed()) tileCanvas.moveD();
        if (keyboard.left.isPressed()) tileCanvas.moveL();
        if (keyboard.right.isPressed()) tileCanvas.moveR();        
        //renderer.addCanvas(10,3, tileCanvas);
    }
    
    public void render(){
        //Gráficos

    	BufferStrategy buffStr = canvas.getBufferStrategy(); //cria um buffer que faz "coisas"
    	if (buffStr == null){
            //se for nulo, crie um novo buffer
            canvas.createBufferStrategy(3); //tripleBuffering (reduz defeitos de renderização)
            return;
        }

    	if (pixels[0] % 5 == 0){
        	for (int i=0; i<pixels.length; i++){
                pixels[i] = 0xff000000 | (int)(System.nanoTime() >> 8);
        	}    	
    	}
    	else{
        	for (int i=0; i<pixels.length; i++){
                pixels[i] += 13;
        	}
    	}

        //renderer.render(pixels);
        
        Graphics gfx = buffStr.getDrawGraphics();
        //gfx.drawRect(0, 0, getWidth(), getHeight()); //cria um retangulo do tamanho exato de FRAME
        gfx.drawImage(this.image, 0, 0, canvas.getWidth(), canvas.getHeight(), null); //desenha o que estiver em IMAGE
        gfx.dispose(); //libera manualmente os recursos usados
        buffStr.show(); //exibe o que está no buffer
    }  

}
