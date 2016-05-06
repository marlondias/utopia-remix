/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utopia.engine.graphics;

import utopia.engine.graphics.canvas.MCanvas;
import java.util.ArrayList;

/**
 * Uma MScene é onde os MCanvas são criados e posicionados
 * Existe também uma matriz de interação, responsável por reconhecer clicks.
 * 
 * As dimensões são independentes do tamanho da tela, os MCanvas estão em uma
 * pilha,  e apenas o MCanvas do topo oferece interação.
 * 
 * @author Marlon Dias
 */
public class MScene {
    
    public ArrayList<MCanvas> canvases = new ArrayList<>();
        
    public void newGame(){
        canvases.clear();
    }
    
}
