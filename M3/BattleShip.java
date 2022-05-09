package PlanetWars;

public class BattleShip extends Ship implements Variables,MilitaryUnit{
	public BattleShip(int armor, int baseDamage){
		int PLUS_ARMOR_LIGTHHUNTER_BY_TECHNOLOGY;
		if (armor == 0) {
			super.setInitialArmor(ARMOR_BATTLESHIP);
			super.setArmor(ARMOR_BATTLESHIP);
		}
	}
	public BattleShip() {
		super.setInitialArmor(ARMOR_BATTLESHIP);
		super.setArmor(ARMOR_BATTLESHIP);
		super.setBaseDamage(BASE_DAMAGE_BATTLESHIP);
	}
	@Override
	public void takeDamage(int receivedDamage) {
		this.setArmor(this.getArmor()-receivedDamage);		
	}
	@Override
	public int getMetalCost() {
		// TODO Auto-generated method stub
		return this.METAL_COST_BATTLESHIP;
	}
	@Override
	public int getDeuteriumCost() {
		// TODO Auto-generated method stub
		return this.DEUTERIUM_COST_BATTLESHIP;
	}
	@Override
	public int getChanceGeneratinWaste() {
		// TODO Auto-generated method stub
		return this.CHANCE_GENERATNG_WASTE_BATTLESHIP;
	}
	@Override
	public int getChanceAttackAgain() {
		// TODO Auto-generated method stub
		return this.CHANCE_ATTACK_AGAIN_BATTLESHIP;
	}
	public void cambiarArmadura(int tecnologia) {
		this.setInitialArmor(ARMOR_BATTLESHIP + (tecnologia*PLUS_ARMOR_BATTLESHIP_BY_TECHNOLOGY)*ARMOR_BATTLESHIP/100);     
		this.setBaseDamage(BASE_DAMAGE_BATTLESHIP+(tecnologia*PLUS_ATTACK_BATTLESHIP_BY_TECHNOLOGY)*BASE_DAMAGE_BATTLESHIP/100);
	}
}
