package PlanetWars;

public abstract class Ship implements MilitaryUnit,Variables{
    private int armor;
    private int initialArmor;
    private int baseDamage;
    private int quantity;
    private int level;

    // Metodos MilitaryUnit
    public int attack() {return baseDamage;}
    public void takeDamage(int receivedDamage) {
        this.armor -= receivedDamage;
    }
    public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getActualArmor() {return armor;}
    public abstract int getMetalCost();
    public abstract int getDeuteriumCost();
    public abstract int getChanceGeneratinWaste();
    public abstract int getChanceAttackAgain();
    public void resetArmor() {armor = initialArmor;}

    // Setters y Getters
    public int getArmor() {return armor;}
    public int getInitialArmor() {return initialArmor;}
    public int getBaseDamage() {return baseDamage;}
    public void setArmor(int armor) {this.armor = armor;}
    public void setInitialArmor(int initialArmor) {this.initialArmor = initialArmor;}
    public void setBaseDamage(int baseDamage) {this.baseDamage = baseDamage;}
}