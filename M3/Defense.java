package PlanetWars;

public abstract class Defense implements Variables,MilitaryUnit{
    private int armor;
    private int initialArmor;
    private int baseDamage;
    private int quantity;
    private int level;

    public int getArmor() {return this.armor;}
    public void setArmor(int armor) {this.armor = armor;}

    public int getInitialArmor() {return initialArmor;}
    public void setInitialArmor(int initialArmor) {this.initialArmor = initialArmor;}

    public int getBaseDamage() {return this.baseDamage;}
    public void setBaseDamage(int baseDamage) {this.baseDamage = baseDamage;}

    
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
	public void resetArmor() {this.setArmor(this.getInitialArmor());}
} 
