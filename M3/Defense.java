package PlanetWars;

public abstract class Defense implements Variables,MilitaryUnit{
    private int armor;
    private int initialArmor;
    private int baseDamage;

    public int getArmor() {return this.armor;}
    public void setArmor(int armor) {this.armor = armor;}

    public int getInitialArmor() {return initialArmor;}
    public void setInitialArmor(int initialArmor) {this.initialArmor = initialArmor;}

    public int getBaseDamage() {return this.baseDamage;}
    public void setBaseDamage(int baseDamage) {this.baseDamage = baseDamage;}

    @Override
    public void resetArmor() {this.setArmor(this.getInitialArmor());}
} 
