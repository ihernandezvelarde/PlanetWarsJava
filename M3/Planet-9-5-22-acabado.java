package PlanetWars;

import java.util.ArrayList;

public class Planet implements Variables {

	private int technologyDefense=1;
	private int technologyAtack=1;
	private int metal;
	private int deuterium;
	private int upgradeDefenseTechnologyDeuteriumCost=UPGRADE_BASE_DEFENSE_TECHNOLOGY_DEUTERIUM_COST;
	private int upgradeAttackTechnologyDeuteriumCost=UPGRADE_BASE_ATTACK_TECHNOLOGY_DEUTERIUM_COST;
    ArrayList<MilitaryUnit>[] army = new ArrayList[7];
    
    /*
    Army[0]  arrayList de Ligth Hunter
	Army[1]  arrayList de Heavy Hunter
	Army[2]  arrayList de Battle Ship 
	Army[3]  arrayList de Armored Ship
	Army[4]  arrayList de Missile Launcher
	Army[5]  arrayList de Ion Cannon
	Army[6]  arrayList de Plasma Cannon 
     */
    public Planet () {
    	for (int i=0;i<7;i++) {
    		army[i]= new ArrayList<MilitaryUnit>();
    	}
    }
    boolean Comprobador(int Metal,int Deuterium) {
    	try {
			if (this.metal-Metal<0 || this.deuterium-Deuterium<0) {
				throw new ResourceException();
			}
			this.metal=this.metal-Metal;
			this.deuterium=this.deuterium-Deuterium;
			return true;
		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			//throw new ResourceException();
			System.out.println(e.getMessage());
			return false;
		}
    }
    void upgradeTechnologyDefense() {
    	
		if (Comprobador(0,this.upgradeDefenseTechnologyDeuteriumCost)==true) {
			this.technologyDefense++;
			this.upgradeDefenseTechnologyDeuteriumCost=this.upgradeDefenseTechnologyDeuteriumCost+(this.upgradeDefenseTechnologyDeuteriumCost*10)/100;
			//this.upgradeDefenseTechnologyDeuteriumCost=this.upgradeDefenseTechnologyDeuteriumCost+UPGRADE_PLUS_DEFENSE_TECHNOLOGY_DEUTERIUM_COST;
			//this.upgradeDefenseTechnologyDeuteriumCost=this.upgradeDefenseTechnologyDeuteriumCost+(UPGRADE_PLUS_DEFENSE_TECHNOLOGY_DEUTERIUM_COST*60)/100;
		}
    }
    void upgradeTechnologyAttack() {
    	
    	if (Comprobador(0, this.upgradeAttackTechnologyDeuteriumCost)==true) {
    		
    		this.technologyAtack++;
    		this.upgradeAttackTechnologyDeuteriumCost=this.upgradeAttackTechnologyDeuteriumCost+(this.upgradeAttackTechnologyDeuteriumCost*10)/100;
    	}
    }
    
    void newLigthHunter(int n) {
    	int conta=0;
    	for (int i=0;i<n;i++) {
    		if (Comprobador(METAL_COST_UNITS[0], DEUTERIUM_COST_UNITS[0])==true) {
    			conta++;
    			LightHunter l= new LightHunter();
    			l.changeArmorAndDamage(technologyDefense, technologyAtack);
    			this.army[0].add(l);
    		}
    		else {
    			System.out.println("Recursos insuficientes");
    			break;
    		}
    	}
    	System.out.println("Se han anyadido : "+conta+" Ligth hunters");
    }

    void newHeavyHunter(int n) {
    	int conta=0;
    	for (int i=0;i<n;i++) {
    		if (Comprobador(METAL_COST_UNITS[1], DEUTERIUM_COST_UNITS[1])==true) {
    			conta++;
    			HeavyHunter h= new HeavyHunter();
    			h.changeArmorAndDamage(technologyDefense, technologyAtack);
    			this.army[1].add(h);
    		}
    		else {
    			System.out.println("Recursos insuficientes");
    			break;
    		}
    	}
    	System.out.println("Se han anyadido : "+conta+" Heavy hunters");
	    }
	void newBattleShip(int n) {
		int conta=0;
		for (int i=0;i<n;i++) {
    		if (Comprobador(METAL_COST_UNITS[2], DEUTERIUM_COST_UNITS[2])==true) {
    			conta++;
    			BattleShip b= new BattleShip();
    			b.changeArmorAndDamage(technologyDefense, technologyAtack);
    			this.army[2].add(b);
    		}
    		else {
    			System.out.println("Recursos insuficientes");
    			break;
    		}
    	}
    	System.out.println("Se han anyadido : "+conta+" Battleships");
	}
	void newArmoredShip(int n) {
		int conta=0;
    	for (int i=0;i<n;i++) {
    		if (Comprobador(METAL_COST_UNITS[3], DEUTERIUM_COST_UNITS[3])==true) {
    			conta++;
    			ArmoredShip a= new ArmoredShip();
    			a.changeArmorAndDamage(technologyDefense, technologyAtack);
    			this.army[3].add(a);
    		}
    		else {
    			System.out.println("Recursos insuficientes");
    			break;
    		}
    	}
    	System.out.println("Se han anyadido : "+conta+" Armored ships");
	}
	void newMissileLauncher(int n) {
		int conta=0;
    	for (int i=0;i<n;i++) {
    		if (Comprobador(METAL_COST_UNITS[4], DEUTERIUM_COST_UNITS[4])==true) {
    			conta++;
    			MissileLauncher m= new MissileLauncher();
    			m.changeStatsByTech(technologyDefense, technologyAtack);
    			this.army[4].add(m);
    		}
    		else {
    			System.out.println("Recursos insuficientes");
    			break;
    		}
    	}
    	System.out.println("Se han anyadido : "+conta+" Missile Launchers");
	}
	void newIonCannon(int n) {
		int conta=0;
    	for (int i=0;i<n;i++) {
    		if (Comprobador(METAL_COST_UNITS[5], DEUTERIUM_COST_UNITS[5])==true) {
    			conta++;
    			IonCannon io= new IonCannon();
    			io.changeStatsByTech(technologyDefense, technologyAtack);
    			this.army[5].add(io);
    		}
    		else {
    			System.out.println("Recursos insuficientes");
    			break;
    		}
    	}
    	System.out.println("Se han anyadido : "+conta+" Ion Cannons");
	}
	void newPlasmaCannon(int n) {
		int conta=0;
    	for (int i=0;i<n;i++) {
    		if (Comprobador(METAL_COST_UNITS[6], DEUTERIUM_COST_UNITS[6])==true) {
    			conta++;
    			PlasmaCannon p= new PlasmaCannon();
    			p.changeStatsByTech(technologyDefense, technologyAtack);
    			this.army[6].add(p);
    		}
    		else {
    			System.out.println("Recursos insuficientes");
    			break;
    		}
    	}
    	System.out.println("Se han anyadido : "+conta+" Plasma Cannons");
    }
	public void printStats() {
		System.out.println("Planet Stats:\n\nTECHNOLOGY\n");
		System.out.println("Atack Technology: "+this.technologyAtack);
		System.out.println("Defense Technology: "+this.technologyDefense);
		System.out.println("\nDEFENSES\n");
		System.out.println("Missile Launcher: "+this.army[4].size());
		System.out.println("Ion Cannon: "+this.army[5].size());
		System.out.println("Plasma Cannon: "+this.army[6].size());
		System.out.println("\nFLEET\n");
		System.out.println("Ligth hunter: "+this.army[0].size());
		System.out.println("Heavy hunter: "+this.army[1].size());
		System.out.println("Battle ship: "+this.army[2].size());
		System.out.println("Armored Ship: "+this.army[3].size());
		System.out.println("\nRESOURCES\n");
		System.out.println("Metal: "+this.metal+"\nDeuterium: "+this.deuterium);
		
	}
  //setters y getters
	public int getMetal() {
		return metal;
	}
	public void setMetal(int metal) {
		this.metal = metal;
	}
	public int getDeuterium() {
		return deuterium;
	}
	public void setDeuterium(int deuterium) {
		this.deuterium = deuterium;
	}
	public ArrayList<MilitaryUnit>[] getArmy() {
		return army;
	}
	public void setArmy(ArrayList<MilitaryUnit>[] army) {
		this.army = army;
	}
	public int getTechnologyDefense() {
		return technologyDefense;
	}
	public int getTechnologyAtack() {
		return technologyAtack;
	}
	public int getUpgradeDefenseTechnologyDeuteriumCost() {
		return upgradeDefenseTechnologyDeuteriumCost;
	}
	public int getUpgradeAttackTechnologyDeuteriumCost() {
		return upgradeAttackTechnologyDeuteriumCost;
	}
}
