/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utopia.buildings;

/**
 *
 * @author Marlon Dias
 */
public class Scaffold {
    //não é Building
    //Usado no processo de construção
    //Após o tempo determinado, ele se torna uma das outras classes
    
    //ideia: Guardar um ID para a estrutura e usar para criar o objeto no mesmo lugar (apos X dias)
    private int builders; //construtores envolvidos
    private int buildingID; //id para uso depois do fim da obra
    private int remainingDays; //dias para o término da obra
    private boolean finished; //indica se foi completada
    
    //implementar aqui o sistema de construção que está na Building
    
}
