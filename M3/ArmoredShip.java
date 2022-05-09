package PlanetWars;

public class ArmoredShip extends Ship implements Variables,MilitaryUnit{
	public ArmoredShip(int armor, int baseDamage){
		int PLUS_ARMOR_LIGTHHUNTER_BY_TECHNOLOGY;
		if (armor == 0) {
			super.setInitialArmor(ARMOR_ARMOREDSHIP);
			super.setArmor(ARMOR_ARMOREDSHIP);
		}
	}
	public ArmoredShip() {
		super.setInitialArmor(ARMOR_ARMOREDSHIP);
		super.setArmor(ARMOR_ARMOREDSHIP);
		super.setBaseDamage(BASE_DAMAGE_ARMOREDSHIP);
	}
	@Override
	public void takeDamage(int receivedDamage) {
		this.setArmor(this.getArmor()-receivedDamage);		
	}
	@Override
	public int getMetalCost() {
		// TODO Auto-generated method stub
		return this.METAL_COST_ARMOREDSHIP;
	}
	@Override
	public int getDeuteriumCost() {
		// TODO Auto-generated method stub
		return this.DEUTERIUM_COST_ARMOREDSHIP;
	}
	@Override
	public int getChanceGeneratinWaste() {
		// TODO Auto-generated method stub
		return this.CHANCE_GENERATNG_WASTE_ARMOREDSHIP;
	}
	@Override
	public int getChanceAttackAgain() {
		// TODO Auto-generated method stub
		return this.CHANCE_ATTACK_AGAIN_ARMOREDSHIP;
	}
	public void cambiarArmadura(int tecnologia) {
		this.setInitialArmor(ARMOR_ARMOREDSHIP + (tecnologia*PLUS_ARMOR_ARMOREDSHIP_BY_TECHNOLOGY)*ARMOR_ARMOREDSHIP/100);     
		this.setBaseDamage(BASE_DAMAGE_ARMOREDSHIP+(tecnologia*PLUS_ATTACK_ARMOREDSHIP_BY_TECHNOLOGY)*BASE_DAMAGE_ARMOREDSHIP/100);
	}
}
