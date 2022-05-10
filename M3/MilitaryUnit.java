package PlanetWars;

public interface MilitaryUnit {
	
    abstract int attack();
    abstract void takeDamage(int receivedDamage);
    abstract int getActualArmor();
    abstract int getMetalCost();
    abstract int getDeuteriumCost();
    abstract int getChanceGeneratinWaste();
    abstract int getChanceAttackAgain();
    abstract int getQuantity();
    abstract int getLevel();
    abstract void resetArmor();
//take damage
}
