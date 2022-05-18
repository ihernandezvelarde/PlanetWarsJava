package PlanetWars;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Planet implements Variables {

	private int technologyDefense=0;
	private int technologyAtack=0;
	private int metal;
	private int deuterium;
	private int upgradeDefenseTechnologyDeuteriumCost=UPGRADE_BASE_DEFENSE_TECHNOLOGY_DEUTERIUM_COST;
	private int upgradeAttackTechnologyDeuteriumCost=UPGRADE_BASE_ATTACK_TECHNOLOGY_DEUTERIUM_COST;
    ArrayList<MilitaryUnit>[] army = new ArrayList[7];
    
    public void setTechnologyDefense(int technologyDefense) {
		this.technologyDefense = technologyDefense;
	}
	public void setTechnologyAtack(int technologyAtack) {
		this.technologyAtack = technologyAtack;
	}
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
		else {
    		JOptionPane.showMessageDialog(null, "ERROR: Insuficient resources");
    	}
    }
    void upgradeTechnologyAttack() {
    	
    	if (Comprobador(0, this.upgradeAttackTechnologyDeuteriumCost)==true) {
    		
    		this.technologyAtack++;
    		this.upgradeAttackTechnologyDeuteriumCost=this.upgradeAttackTechnologyDeuteriumCost+(this.upgradeAttackTechnologyDeuteriumCost*10)/100;
    	}
    	else {
    		JOptionPane.showMessageDialog(null, "ERROR: Insuficient resources");
    	}
    }
    
    void newLigthHunter(int n,Connection con) {
    	int conta=0;
    	CallableStatement cst;
    	InfoShips ship=new InfoShips();
    	cst= ship.getInfoShips(con, 1);
    	String insu="";
    	for (int i=0;i<n;i++) {
    		try {
    			cst.execute();
				if (Comprobador(cst.getInt(3), cst.getInt(5))==true) {
					conta++;
					LightHunter l= new LightHunter(technologyDefense, technologyAtack,con);
					this.army[0].add(l);
				}
				else {
					insu=("ERROR: Insuficient resources \n");
					break;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	JOptionPane.showMessageDialog(null, insu+"Added : "+conta+" Ligth hunters");
    }

    void newHeavyHunter(int n,Connection con) {
    	int conta=0;
    	CallableStatement cst;
    	InfoShips ship=new InfoShips();
    	cst= ship.getInfoShips(con, 2);
    	String insu="";
    	for (int i=0;i<n;i++) {
    		try {
    			cst.execute();
				if (Comprobador(cst.getInt(3), cst.getInt(5))==true) {
					conta++;
					HeavyHunter h= new HeavyHunter(technologyDefense, technologyAtack,con);
					this.army[1].add(h);
				}
				else {
					insu=("ERROR: Insuficient resources \n");
					break;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	JOptionPane.showMessageDialog(null, insu+"Added : "+conta+" Heavy hunters");
	    }
	void newBattleShip(int n,Connection con) {
		int conta=0;
		CallableStatement cst;
    	InfoShips ship=new InfoShips();
    	cst= ship.getInfoShips(con, 3);
    	String insu="";
		for (int i=0;i<n;i++) {
    		try {
    			cst.execute();
				if (Comprobador(cst.getInt(3), cst.getInt(5))==true) {
					conta++;
					BattleShip b= new BattleShip(technologyDefense, technologyAtack,con);
					this.army[2].add(b);
				}
				else {
					insu=("ERROR: Insuficient resources \n");
					break;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		JOptionPane.showMessageDialog(null, insu+"Added : "+conta+" Battleships");
	}
	void newArmoredShip(int n,Connection con) {
		int conta=0;
		CallableStatement cst;
    	InfoShips ship=new InfoShips();
    	cst= ship.getInfoShips(con, 4);
    	String insu="";
    	for (int i=0;i<n;i++) {
    		try {
    			cst.execute();
				if (Comprobador(cst.getInt(3), cst.getInt(5))==true) {
					conta++;
					ArmoredShip a= new ArmoredShip(technologyDefense, technologyAtack,con);
					this.army[3].add(a);
				}
				else {
					insu=("ERROR: Insuficient resources \n");
					break;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	JOptionPane.showMessageDialog(null, insu+"Added : "+conta+" Armored ships");
	}
	//DEFENSAS---------------------------------------------------------------------------------
	void newMissileLauncher(int n,Connection con) {
		int conta=0;
		CallableStatement cst;
    	InfoDefense def=new InfoDefense();
    	cst= def.getInfoDefense(con, 1);
    	String insu="";
    	for (int i=0;i<n;i++) {
    		try {
    			cst.execute();
				if (Comprobador(cst.getInt(3), cst.getInt(5))==true) {
					conta++;
					MissileLauncher m= new MissileLauncher(technologyDefense, technologyAtack,con);
					this.army[4].add(m);
				}
				else {
					insu=("ERROR: Insuficient resources \n");
					break;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	JOptionPane.showMessageDialog(null, insu+"Added : "+conta+" Missile Launchers");
	}
	void newIonCannon(int n,Connection con) {
		int conta=0;
		CallableStatement cst;
		InfoDefense def=new InfoDefense();
    	cst= def.getInfoDefense(con, 2);
    	String insu="";
    	for (int i=0;i<n;i++) {
    		
    		try {
    			cst.execute();
				if (Comprobador(cst.getInt(3), cst.getInt(5))==true) {
					conta++;
					IonCannon io= new IonCannon(technologyDefense, technologyAtack,con);
					this.army[5].add(io);
				}
				else {
					insu=("ERROR: Insuficient resources \n");
					break;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	JOptionPane.showMessageDialog(null, insu+"Added : "+conta+" Ion Cannons");
	}
	void newPlasmaCannon(int n,Connection con) {
		CallableStatement cst;
		InfoDefense def=new InfoDefense();
    	cst= def.getInfoDefense(con, 3);
		int conta=0;
		String insu="";
    	for (int i=0;i<n;i++) {
    		try {
    			cst.execute();
				if (Comprobador(cst.getInt(3), cst.getInt(5))==true) {
					conta++;
					PlasmaCannon p= new PlasmaCannon(technologyDefense, technologyAtack,con);
					this.army[6].add(p);
				}
				else {
					insu=("ERROR: Insuficient resources \n");
					break;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	JOptionPane.showMessageDialog(null, insu+"Added : "+conta+" Plasma Cannons");
    }
	public void setUpgradeDefenseTechnologyDeuteriumCost(int upgradeDefenseTechnologyDeuteriumCost) {
		this.upgradeDefenseTechnologyDeuteriumCost = upgradeDefenseTechnologyDeuteriumCost;
	}
	public void setUpgradeAttackTechnologyDeuteriumCost(int upgradeAttackTechnologyDeuteriumCost) {
		this.upgradeAttackTechnologyDeuteriumCost = upgradeAttackTechnologyDeuteriumCost;
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
