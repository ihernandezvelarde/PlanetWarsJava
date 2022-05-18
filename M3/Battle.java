package PlanetWars;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Battle implements Variables {
	private ArrayList<MilitaryUnit>[] planetArmy = new ArrayList[7];
	private ArrayList<MilitaryUnit>[] enemyArmy = new ArrayList[4];
	private ArrayList[][] armies = new ArrayList[2][7];
	private ArrayList<String> battleDevelopment = new ArrayList<String>();
	int[][] initialCostFleet = new int[2][2];
	int initialNumberUnitsPlanet, initialNumberUnitsEnemy;
	int[] wasteMetalDeuterium = new int[2];
	int[][] resourcesLosses = new int[2][3];
	int[][] initialArmies = new int[2][7];
	int[] actualNumberUnitsPlanet = new int[7];
	int[] actualNumberUnitsEnemy = new int[7];
	
	int turn;
	
	public Battle(ArrayList<MilitaryUnit>[] planetArmy, ArrayList<MilitaryUnit>[] enemyArmy) {
		initVariables(planetArmy, enemyArmy);
	}
	
	// Funcio que retorna la quantitat total d'unitats d'un mateix exercit
	public int getTotalUnitsFleet(ArrayList<MilitaryUnit>[] fleet) {
		int total = 0;
		for (int i = 0; i < fleet.length; i++) {
			total += fleet[i].size();
		}
		return total;
	}
	
	// Funcio que crea una ArrayList a partir dife
	private ArrayList<MilitaryUnit> initSquad(ArrayList<MilitaryUnit> squad) {
		ArrayList<MilitaryUnit> returnable = new ArrayList<MilitaryUnit>();
		for (int i = 0; i < squad.size(); i++) {
			returnable.add(squad.get(i));
		}
		return returnable;
	}
	
	
	private void initVariables(ArrayList<MilitaryUnit>[] planetInitialArmy, ArrayList<MilitaryUnit>[] enemyInitialArmy) {
		initialNumberUnitsPlanet = getTotalUnitsFleet(planetInitialArmy);
		initialNumberUnitsEnemy = getTotalUnitsFleet(enemyInitialArmy);
		int total_cost_metal_planet = 0;
		int total_cost_deuterium_planet = 0;
		int total_cost_metal_enemy = 0;
		int total_cost_deuterium_enemy = 0;
		
		// Recorre les arrays d'exercits per a comptar el nombre total de tropes (objectes) que hi ha a cada exercit
		for (int i = 0; i < planetInitialArmy.length; i ++) {
			initialArmies [0][i] = planetInitialArmy[i].size();
			actualNumberUnitsPlanet[i] = planetInitialArmy[i].size();
			planetArmy[i] = initSquad(planetInitialArmy[i]);
			armies[0][i] = planetArmy[i];
			for (int j = 0; j < planetInitialArmy[i].size(); j++) {
				total_cost_metal_planet += METAL_COST_UNITS[i];
				total_cost_deuterium_planet += DEUTERIUM_COST_UNITS[i];
			}
		}
		
		for (int i = 0; i < enemyInitialArmy.length; i++) {
			initialArmies[1][i] = enemyInitialArmy[i].size();
			actualNumberUnitsEnemy[i] = enemyInitialArmy[i].size();
			enemyArmy[i] = initSquad(enemyInitialArmy[i]);
			armies[1][i] = enemyArmy[i];
			for (int j = 0; j < enemyInitialArmy[i].size(); j++) {
				total_cost_metal_enemy += METAL_COST_UNITS[i];
				total_cost_deuterium_enemy += DEUTERIUM_COST_UNITS[i];
			}
		}
			initialCostFleet[0][0] = total_cost_metal_planet;
			initialCostFleet[0][1] = total_cost_deuterium_planet;
			initialCostFleet[1][0] = total_cost_metal_enemy;
			initialCostFleet[1][1] = total_cost_deuterium_enemy;
		
		
		getStartingTurn();
	}
	
	private void getStartingTurn() {
		// Retorna un numero enter aleatori entre 0 i 1
		// Si retorna 0 es el torn del jugador, si retorna 1 es el torn de l'enemic
		turn = (int) (Math.random());
	}
	
	private MilitaryUnit getAttacker() {
		// S'escull un nombre aleatori entre 0 i 100 perque 
		// els valors totals de les Arrays de probabilitat, tant les de jugador com les d'enemic,
		// equivalen a 100
		int attacking_squad = (int) (Math.random()*100);
		if (turn == 0) {
			for (int i = 0; i < planetArmy.length; i++) {
				System.out.println("inicio: " + i);
				// Es resta la probabilitat d'atacar de cada grup al nombre escollit aleatoriament
				attacking_squad -= CHANCE_ATTACK_PLANET_UNITS[i];
				// Si el nombre resultant a la resta es igual o menor a 0 i n'hi al menys una tropa en el grup, s'escull una tropa aleatoria
				// entre les de l'ArrayList del grup
				if (attacking_squad <= 0 && planetArmy[i].size() > 0) {
					return planetArmy[i].get((int) (Math.random()*planetArmy[i].size()));
					
				} else if (attacking_squad <= 0 && i == planetArmy.length-1) {
					i = -1;
				}
				System.out.println("final: " + i);
			}
		} else {
			for (int i = 0; i < enemyArmy.length; i++) {
				// Es resta la probabilitat d'atacar de cada grup al nombre escollit aleatoriament
				attacking_squad -= CHANCE_ATTACK_ENEMY_UNITS[i];
				// Si el nombre resultant a la resta es igual o menor a 0, s'escull una tropa aleatoria
				// entre les de l'ArrayList del grup
				if (attacking_squad <= 0 && enemyArmy[i].size() > 0) {
					return enemyArmy[i].get((int) (Math.random()*enemyArmy[i].size()));
				} else if (attacking_squad <= 0 && i == enemyArmy.length-1) {
					i = -1;
				}
			}
		}
		// retorna un valor nul per defecte
		return null;
	}
	
	private MilitaryUnit getDefender() {
		if (turn == 0) {
			// Obte el nombre total d'unitats en l'exercit per a calcular les probabilitats
			int totalChances = getTotalUnitsFleet(enemyArmy);
			
			// S'escull un nombre aleatori entre 0 i el nombre total d'unitats
			int defending_squad = (int) (Math.random()*totalChances);
			System.out.println(enemyArmy.length);
			for (int i = 0; i < enemyArmy.length; i++) {
				// Es resta la quantitat de cada grup al nombre escollit aleatoriament
				defending_squad -= enemyArmy[i].size();
				if (defending_squad <= 0 && enemyArmy[i].size() > 0) {
					return enemyArmy[i].get((int) (Math.random()*enemyArmy[i].size()));
				} else if (defending_squad <= 0 && i == enemyArmy.length-1) {
					i = -1;
				}
			}
			
		} else {
			// Obte el nombre total d'unitats en l'exercit per a calcular les probabilitats
			int totalChances = getTotalUnitsFleet(planetArmy);
			
			// S'escull un nombre aleatori entre 0 i el nombre total d'unitats
			int defending_squad = (int) (Math.random()*totalChances);
			
			for (int i = 0; i < planetArmy.length; i++) {
				// Es resta la quantitat de cada grup al nombre escollit aleatoriament
				defending_squad -= planetArmy[i].size();
				if (defending_squad <= 0 && planetArmy[i].size() > 0) {
					return planetArmy[i].get((int) (Math.random()*planetArmy[i].size()));
				} else if (defending_squad <= 0 && i == planetArmy.length-1) {
					i = -1;
				}
			}
		}
		return null;
	}
	
	// Retorna el missatge d'accio amb l'atac i els dels metodes GenerateWastings i LoseShip
	private String attack(MilitaryUnit attacker, MilitaryUnit defender) {
		String message = attacker.getClass().getSimpleName() + " attacks " + defender.getClass().getSimpleName();
		int dmg = attacker.attack();
		message += "\n" + attacker.getClass().getSimpleName() + " deals " + dmg + " damage";
		defender.takeDamage(dmg);
		message += "\n" + defender.getClass().getSimpleName() + " has " + defender.getActualArmor() + " remaining armor";
		
		if (defender.getActualArmor() <= 0) {
			int chance_waste = (int) (Math.random()*100);
			int type;
			switch (defender.getClass().getSimpleName()) {
				case "LightHunter":
					type = 0;
					break;
				case "HeavyHunter":
					type = 1;
					break;
				case "BattleShip":
					type = 2;
					break;
				case "ArmoredShip":
					type = 3;
					break;
				case "MissileLauncher":
					type = 4;
					break;
				case "IonCannon":
					type = 5;
					break;
				default:
					type = 6;
					break;
			}
			
			message += "\n" + loseShip(defender, type);
			if (chance_waste <= CHANCE_GENERATNG_WASTE_LIGTHHUNTER && type == 0) {
				message += "\n" + generateWastings(type); 
			} else if (chance_waste <= CHANCE_GENERATNG_WASTE_HEAVYHUNTER && type == 1) {
				message += "\n" + generateWastings(type); 
			} else if (chance_waste <= CHANCE_GENERATNG_WASTE_BATTLESHIP && type == 2) {
				message += "\n" + generateWastings(type); 
			} else if (chance_waste <= CHANCE_GENERATNG_WASTE_ARMOREDSHIP && type == 3) {
				message += "\n" + generateWastings(type); 
			} else if (chance_waste <= CHANCE_GENERATNG_WASTE_MISSILELAUNCHER && type == 4) {
				message += "\n" + generateWastings(type); 
			} else if (chance_waste <= CHANCE_GENERATNG_WASTE_IONCANNON && type == 5) {
				message += "\n" + generateWastings(type); 
			} else if (chance_waste <= CHANCE_GENERATNG_WASTE_PLASMACANNON && type == 6) {
				message += "\n" + generateWastings(type); 
			}
			
		}
		return message;
	}
	
	// retorna 1 si ha guanyat planeta, 2 si ha guanyat l'enemic i 0 si no ha guanyat cap encara (per a continuar)
	public int getWinner() {
		if (getTotalUnitsFleet(planetArmy) < initialNumberUnitsPlanet*0.2 || getTotalUnitsFleet(enemyArmy) < initialNumberUnitsPlanet*0.2) {
			if (resourcesLosses[0][2] < resourcesLosses[1][2]) {
				return 1;
			}
			return 2;
		}
		return 0;
	}
	
	
	// genera residus i retorna el missatge corresponent
	public String generateWastings(int type) {
		wasteMetalDeuterium[0] = METAL_COST_UNITS[type]*PERCENTATGE_WASTE/100;
		wasteMetalDeuterium[1] = DEUTERIUM_COST_UNITS[type]*PERCENTATGE_WASTE/100;
		return METAL_COST_UNITS[type]*PERCENTATGE_WASTE/100 + " Metal and " + DEUTERIUM_COST_UNITS[type]*PERCENTATGE_WASTE/100 + " generated as Wastings";
	}
	
	// elimina una nau i retorna un missatge corresponent
	public String loseShip(MilitaryUnit ship, int type) {
		resourcesLosses[turn][0] += METAL_COST_UNITS[type];
		resourcesLosses[turn][1] += DEUTERIUM_COST_UNITS[type];
		resourcesLosses[turn][2] += METAL_COST_UNITS[type] + 5*DEUTERIUM_COST_UNITS[type];
		
		if (turn == 0) {
				enemyArmy[type].remove(ship);
				System.out.println(actualNumberUnitsEnemy);
				actualNumberUnitsEnemy[type] -= 1;
				System.out.println(actualNumberUnitsEnemy);
		} else {
				planetArmy[type].remove(ship);
				actualNumberUnitsPlanet[type] -= 1;
		}
		
		return ship.getClass().getSimpleName() + " has been destroyed";
	}
	
	
	public String startBattle() {
		String msg = "\n\n" + "****************************** START BATTLE ******************************" + "\n";
		if (turn == 0) {
			msg += "Planet attacks: " + "\n";
		} else {
			msg += "Enemy Fleet attacks: " + "\n";
		}
		MilitaryUnit attacker = getAttacker();
		MilitaryUnit defender = getDefender();
		
		msg += attack(attacker, defender);
		battleDevelopment.add(msg);
		
		if (turn == 0) {
			turn = 1;
		} else {
			turn = 0;
		}
		
		return msg;
	}
	
	public String continueBattle() {
		String msg = "\n\n" + "***************************** CHANGE ATTACKER ****************************" + "\n";
		if (turn == 0) {
			msg += "Planet attacks: " + "\n";
		} else {
			msg += "Enemy Fleet attacks: " + "\n";
		}
		MilitaryUnit attacker = getAttacker();
		MilitaryUnit defender = getDefender();
		
		msg += attack(attacker, defender);
		battleDevelopment.add(msg);
		
		if (turn == 0) {
			turn = 1;
		} else {
			turn = 0;
		}
		
		return msg;
	}
	
	// guarda totes les dades a les taules Battle, Battle_step, Battle_has_ships i Battle_has_defenses de la bbdd
	public void saveBattleData(Connection con, int idUser) {
		try {
			System.out.println(idUser);
			CallableStatement get_id = con.prepareCall("{call get_battleid (?)}");
			get_id.registerOutParameter(1, java.sql.Types.VARCHAR);
			get_id.execute();
			int id = get_id.getInt(1);
			
			CallableStatement add_battle = con.prepareCall("{call add_battle (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			add_battle.setInt(1,  id);
			add_battle.setInt(2,  idUser);
			add_battle.setInt(3,  initialNumberUnitsPlanet);
			add_battle.setInt(4,  getTotalUnitsFleet(planetArmy));
			add_battle.setInt(5,  initialNumberUnitsEnemy);
			add_battle.setInt(6,  getTotalUnitsFleet(enemyArmy));
			add_battle.setInt(7,  initialCostFleet[0][0]);
			add_battle.setInt(8,  initialCostFleet[0][1]);
			add_battle.setInt(9,  initialCostFleet[1][0]);
			add_battle.setInt(10, initialCostFleet[1][1]);
			add_battle.setInt(11, resourcesLosses[0][0]);
			add_battle.setInt(12, resourcesLosses[0][1]);
			add_battle.setInt(13, resourcesLosses[1][0]);
			add_battle.setInt(14, resourcesLosses[1][1]);
			add_battle.setInt(15, wasteMetalDeuterium[0]);
			add_battle.setInt(16, wasteMetalDeuterium[1]);
			add_battle.setInt(17, getWinner());
			add_battle.execute();
			
			CallableStatement add_ship = con.prepareCall("{call add_ship_battle (?, ?, ?, ?, ?)}");
			CallableStatement add_defenses = con.prepareCall("{call add_defense_battle (?, ?, ?, ?, ?)}");
			add_ship.setInt(1, id);
			add_defenses.setInt(1, id);
			
			for (int i = 0; i < enemyArmy.length; i++) {
				
				add_ship.setInt(2, i+1);
				add_ship.setInt(3, initialArmies[0][i]);
				add_ship.setInt(4, actualNumberUnitsPlanet[i]);
				add_ship.setInt(5, 0);
				
				add_ship.execute();
				
				add_ship.setInt(3, initialArmies[1][i]);
				add_ship.setInt(4, actualNumberUnitsEnemy[i]);
				add_ship.setInt(5, 1);
				
				add_ship.execute();
			}
			
			for (int i = 4; i < planetArmy.length; i++) {
				
				add_defenses.setInt(2, i-3);
				add_defenses.setInt(3, initialArmies[0][i]);
				add_defenses.setInt(4, actualNumberUnitsPlanet[i]);
				add_defenses.setInt(5, 0);
				add_defenses.execute();
				
			}
			
			CallableStatement add_step = con.prepareCall("{call add_battle_step (?, ?, ?)}");
			for (int i = 0; i < battleDevelopment.size(); i++) {
				add_step.setInt(1, id);
				add_step.setInt(2, i+1);
				add_step.setString(3, battleDevelopment.get(i));
				add_step.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public ArrayList<MilitaryUnit>[] getPlanetArmy() {
		return planetArmy;
	}

	public void setPlanetArmy(ArrayList<MilitaryUnit>[] planetArmy) {
		this.planetArmy = planetArmy;
	}

	public ArrayList<MilitaryUnit>[] getEnemyArmy() {
		return enemyArmy;
	}

	public void setEnemyArmy(ArrayList<MilitaryUnit>[] enemyArmy) {
		this.enemyArmy = enemyArmy;
	}

	public ArrayList[][] getArmies() {
		return armies;
	}

	public void setArmies(ArrayList[][] armies) {
		this.armies = armies;
	}

	public int[][] getInitialCostFleet() {
		return initialCostFleet;
	}

	public void setInitialCostFleet(int[][] initialCostFleet) {
		this.initialCostFleet = initialCostFleet;
	}

	public int getInitialNumberUnitsPlanet() {
		return initialNumberUnitsPlanet;
	}

	public void setInitialNumberUnitsPlanet(int initialNumberUnitsPlanet) {
		this.initialNumberUnitsPlanet = initialNumberUnitsPlanet;
	}

	public int getInitialNumberUnitsEnemy() {
		return initialNumberUnitsEnemy;
	}

	public void setInitialNumberUnitsEnemy(int initialNumberUnitsEnemy) {
		this.initialNumberUnitsEnemy = initialNumberUnitsEnemy;
	}

	public int[] getWasteMetalDeuterium() {
		return wasteMetalDeuterium;
	}

	public void setWasteMetalDeuterium(int[] wasteMetalDeuterium) {
		this.wasteMetalDeuterium = wasteMetalDeuterium;
	}

	public int[][] getResourcesLosses() {
		return resourcesLosses;
	}

	public void setResourcesLosses(int[][] resourcesLosses) {
		this.resourcesLosses = resourcesLosses;
	}

	public int[][] getInitialArmies() {
		return initialArmies;
	}

	public void setInitialArmies(int[][] initialArmies) {
		this.initialArmies = initialArmies;
	}

	public int[] getActualNumberUnitsPlanet() {
		return actualNumberUnitsPlanet;
	}

	public void setActualNumberUnitsPlanet(int[] actualNumberUnitsPlanet) {
		this.actualNumberUnitsPlanet = actualNumberUnitsPlanet;
	}

	public int[] getActualNumberUnitsEnemy() {
		return actualNumberUnitsEnemy;
	}

	public void setActualNumberUnitsEnemy(int[] actualNumberUnitsEnemy) {
		this.actualNumberUnitsEnemy = actualNumberUnitsEnemy;
	}
	
	
	
	
}