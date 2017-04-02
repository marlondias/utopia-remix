package utopia.basic;

public class Launcher {
	public static GameLoop GAME_LOOP;

	public static void main(String[] args) {
		//Abrir janela comum com opções de idioma, resolução e fullscreen
		//LauncherWindow.openWindow();

		//Salvar as configurações escolhidas e iniciar carregamento
		//Exibir splash screen com a barra de progresso
		
		//Abrir a tela principal e exibir intro
		
		//Mostrar tela de título com New, Load e Exit.
		
		//System.out.println("Iniciando loop de jogo..");
		GAME_LOOP = new GameLoop();
        GAME_LOOP.start();
	}

}
