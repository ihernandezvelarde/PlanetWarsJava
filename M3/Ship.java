package PlanetWars;

public abstract class Ship implements MilitaryUnit,Variables{
	int armor;
	int initialArmor;
	int baseDamage;
	
	
	public int getArmor() {
		return armor;
	}
	public int getInitialArmor() {
		return initialArmor;
	}
	public int getBaseDamage() {
		return baseDamage;
	}
	public void setArmor(int armor) {
		this.armor = armor;
	}
	public int attack() {
		return baseDamage;
	}
	public abstract void takeDamage(int receivedDamage);
	public int getActualArmor() {
		return armor;
	}
	public abstract int getMetalCost();
	public abstract int getDeuteriumCost();
	public abstract int getChanceGeneratinWaste();
	public abstract int getChanceAttackAgain();
	public void resetArmor() {
		armor = initialArmor;
	}
	
	public void setInitialArmor(int initialArmor) {
		this.initialArmor = initialArmor;
	}
	
	public void setBaseDamage(int baseDamage) {
		this.baseDamage = baseDamage;
	}
}
