package utopia.game.planet;

import utopia.game.buildings.BuildingType;

//Informações individuais de cada tile do planeta
public class Block {
	private BlockType type; //Contém propriedades físicas e aparência
	private boolean hasOre = false;
	private boolean hasFuel = false;
	private boolean hasMoss = false;
	private boolean hasStructure = false;
	
	
	public Block(BlockType bt){
		this.type = (bt == null) ? BlockType.NULL : bt;
		if (type.equals(BlockType.MOSS)) hasMoss = true;
	}
	
	public boolean allowBuilding(BuildingType bt){
		//Verifica se a estrutura do tipo BT pode ser colocada neste bloco (mas não inicia construção)
		
		if (hasStructure) return false; //Ja tem algo aqui
		
		if (hasMoss){
			//Verifica se a estrutura é um MossConverter
			if (bt.equals(BuildingType.MOSS_CONVERTER)) return true;
		}
		
		if (type.allowBuilding() == false) return false; //Tipo de terreno não permite
		
		return true; //É possível construir (mas não constroi nada)
	}
	
	public void setFuel(){
		hasFuel = true;
	}
	public boolean hasFuel(){
		return this.hasFuel;
	}

	public void setOre(){
		hasOre = true;
	}
	public boolean hasOre(){
		return this.hasOre;
	}

	public int getTileID(){
		return type.getTileID();
	}
	
}
