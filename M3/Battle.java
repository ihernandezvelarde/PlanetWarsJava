package PlanetWars;

import java.util.ArrayList;

public class Battle implements Variables {
	private ArrayList<MilitaryUnit>[] planetArmy = new ArrayList[7];
	private ArrayList<MilitaryUnit>[] enemyArmy = new ArrayList[4];
	private ArrayList[][] armies = new ArrayList[2][7];
	private String battleDevelopment;
	int[][] initialCostFleet = new int[2][2];
	int initialNumberUnitsPlanet, initialNumberUnitsEnemy;
	int[] wasteMetalDeuterium = new int[2];
	int[][] resourcesLosses = new int[2][3];
	int[][] initialArmies = new int[2][2];
	int[] actualNumberUnitsPlanet = new int[7];
	int[] actualNumberUnitsEnemy = new int[7];
	
	int turn;
	
	public Battle(ArrayList<MilitaryUnit>[] planetArmy, ArrayList<MilitaryUnit>[] enemyArmy) {
		initVariables(planetArmy, enemyArmy);
	}
	
	// Funcio que retorna la quantitat total d'unitats d'un mateix tipus
	public int getTotalUnitsSquad(ArrayList<MilitaryUnit> squad) {
		int total = 0;
		for (int i = 0; i < squad.size(); i++) {
			total += squad.get(i).getQuantity();
		}
		return total;
	}
	
	// Funcio que retorna la quantitat total d'unitats d'un mateix exercit
	public int getTotalUnitsFleet(ArrayList<MilitaryUnit>[] fleet) {
		int total = 0;
		for (int i = 0; i < fleet.length; i++) {
			total += getTotalUnitsSquad(fleet[i]);
		}
		return total;
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
			initialArmies [0][i] = getTotalUnitsSquad(planetInitialArmy[i]);
			actualNumberUnitsPlanet[i] = getTotalUnitsSquad(planetInitialArmy[i]);
			for (int j = 0; j < planetInitialArmy[i].size(); j++) {
				total_cost_metal_planet += planetInitialArmy[i].get(j).getMetalCost();
				total_cost_deuterium_planet += planetInitialArmy[i].get(j).getDeuteriumCost();
			}
			// Com que l'enemic nomes te naus en el seu exercit, s'emplena la seva fila de la matriu fins a
			// la columna 7
			if (i <= 3) {
				initialArmies [1][i] = getTotalUnitsSquad(enemyInitialArmy[i]);
				actualNumberUnitsEnemy[i] = getTotalUnitsSquad(enemyInitialArmy[i]);
				
				for (int j = 0; j < enemyInitialArmy[i].size(); i++) {
					total_cost_metal_enemy += enemyInitialArmy[i].get(j).getMetalCost();
					total_cost_deuterium_enemy += enemyInitialArmy[i].get(j).getDeuteriumCost();
				}
			}
			
			initialCostFleet[0][0] = total_cost_metal_planet;
			initialCostFleet[0][1] = total_cost_deuterium_planet;
			initialCostFleet[1][0] = total_cost_metal_enemy;
			initialCostFleet[1][1] = total_cost_deuterium_enemy;
		}
		
		planetArmy = planetInitialArmy;
		enemyArmy = enemyInitialArmy;
		armies[0] = planetArmy;
		armies[1] = enemyArmy;
		
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
				// Es resta la probabilitat d'atacar de cada grup al nombre escollit aleatoriament
				attacking_squad -= CHANCE_ATTACK_PLANET_UNITS[i];
				// Si el nombre resultant a la resta es igual o menor a 0, s'escull una tropa aleatoria
				// entre les de l'ArrayList del grup
				if (attacking_squad <= 0) {
					// Es selecciona un nombre aleatori entre 0 i el nombre total d'unitats del mateix tipus
					int attacker = (int) (Math.random()*getTotalUnitsSquad(planetArmy[i]));
					for (int j = 0; j < planetArmy[i].size(); i++) {
						// Es resta la quantitat de tropes de cada nivell al nombre escollit aleatoriament
						attacker -= planetArmy[i].get(j).getQuantity();
						// Si el nombre resultant a la resta es igual o menor a 0, s'escull la tropa d'aquest nivell
						if (attacker <= 0) {
							return planetArmy[i].get(j);
						}
					}
					
				}
			}
		} else {
			for (int i = 0; i < enemyArmy.length; i++) {
				// Es resta la probabilitat d'atacar de cada grup al nombre escollit aleatoriament
				attacking_squad -= CHANCE_ATTACK_ENEMY_UNITS[i];
				// Si el nombre resultant a la resta es igual o menor a 0, s'escull una tropa aleatoria
				// entre les de l'ArrayList del grup
				if (attacking_squad <= 0) {
					int attacker = (int) (Math.random()*getTotalUnitsSquad(planetArmy[i]));
					for (int j = 0; j < planetArmy[i].size(); i++) {
						// Es resta la quantitat de tropes de cada nivell al nombre escollit aleatoriament
						attacker -= enemyArmy[i].get(j).getQuantity();
						// Si el nombre resultant a la resta es igual o menor a 0, s'escull la tropa d'aquest nivell
						if (attacker <= 0) {
							return enemyArmy[i].get(j);
						}
					}
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
			
			for (int i = 0; i < enemyArmy.length; i++) {
				// Es resta la quantitat de cada grup al nombre escollit aleatoriament
				defending_squad -= getTotalUnitsSquad(enemyArmy[i]);
				if (defending_squad <= 0) {
					// S'escull un nombre aleatori entre 0 i el nombre total d'unitats del mateix tipus
					int defender = (int) (Math.random()*getTotalUnitsSquad(enemyArmy[i]));
					for (int j = 0; j < enemyArmy[i].size(); i++) {
						// Es resta la quantitat de tropes de cada nivell al nombre escollit aleatoriament
						defender -= enemyArmy[i].get(j).getQuantity();
						// Si el nombre resultant es menor o igual a 0, s'escull la tropa d'aquest nivell
						if (defender <= 0) {
							return enemyArmy[i].get(j);
						}
					}
				}
			}
			
		} else {
			// Obte el nombre total d'unitats en l'exercit per a calcular les probabilitats
			int totalChances = getTotalUnitsFleet(planetArmy);
			
			// S'escull un nombre aleatori entre 0 i el nombre total d'unitats
			int defending_squad = (int) (Math.random()*totalChances);
			
			for (int i = 0; i < planetArmy.length; i++) {
				// Es resta la quantitat de cada grup al nombre escollit aleatoriament
				defending_squad -= getTotalUnitsSquad(planetArmy[i]);
				if (defending_squad <= 0) {
					// S'escull un nombre aleatori entre 0 i el nombre total d'unitats del mateix tipus
					int defender = (int) (Math.random()*getTotalUnitsSquad(planetArmy[i]));
					for (int j = 0; j < planetArmy[i].size(); i++) {
						// Es resta la quantitat de tropes de cada nivell al nombre escollit aleatoriament
						defender -= planetArmy[i].get(j).getQuantity();
						// Si el nombre resultant es menor o igual a 0, s'escull la tropa d'aquest nivell
						if (defender <= 0) {
							return planetArmy[i].get(j);
						}
					}
				}
			}
		}
		return null;
	}
	
	private String attack(MilitaryUnit attacker, MilitaryUnit defender) {
		String message = attacker.getClass().getSimpleName() + " attacks " + defender.getClass();
		int dmg = attacker.attack();
		message += "\n" + attacker.getClass().getSimpleName() + " deals " + dmg + " damage";
		defender.takeDamage(dmg);
		message += "\n" + defender.getClass().getSimpleName() + " has " + defender.getActualArmor() + " remaining armor";
		
		if (defender.getActualArmor() <= 0) {
			int chance_waste = (int) (Math.random()*100);
			if ((defender instanceof LightHunter && chance_waste <= CHANCE_GENERATNG_WASTE_LIGTHHUNTER) ||
					(defender instanceof HeavyHunter && chance_waste <= CHANCE_GENERATNG_WASTE_HEAVYHUNTER) ||
					(defender instanceof BattleShip && chance_waste <= CHANCE_GENERATNG_WASTE_BATTLESHIP) ||
					(defender instanceof ArmoredShip && chance_waste <= CHANCE_GENERATNG_WASTE_ARMOREDSHIP) ||
					(defender instanceof MissileLauncher && chance_waste <= CHANCE_GENERATNG_WASTE_MISSILELAUNCHER) ||
					(defender instanceof IonCannon && chance_waste <= CHANCE_GENERATNG_WASTE_IONCANNON) ||
					(defender instanceof PlasmaCannon && chance_waste <= CHANCE_GENERATNG_WASTE_PLASMACANNON)) {
				wasteMetalDeuterium[0] = defender.getMetalCost()*PERCENTATGE_WASTE;
				wasteMetalDeuterium[1] = defender.getDeuteriumCost()*PERCENTATGE_WASTE;
				message += "\n" + loseShip(defender);
			}	
			
		}
		
		
		return message;
	}
	
	
	public int getWinner() {
		if (getTotalUnitsFleet(planetArmy) < initialNumberUnitsPlanet*0.2 || getTotalUnitsFleet(enemyArmy) < initialNumberUnitsPlanet*0.2) {
			if (resourcesLosses[0][2] < resourcesLosses[1][2]) {
				return 1;
			}
			return 2;
		}
		return 0;
	}
	
	
	public String loseShip(MilitaryUnit ship) {
		resourcesLosses[turn][0] += ship.getMetalCost();
		resourcesLosses[turn][1] += ship.getDeuteriumCost();
		resourcesLosses[turn][2] += ship.getMetalCost() + 5*ship.getDeuteriumCost();
		int type = 0;
		switch (ship.getClass().getSimpleName()) {
			case "LightHunter:":
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
			case "PlasmaCannon":
				type = 6;
				break;
		}
		if (turn == 0) {
			if (ship.getQuantity() > 1) {
				ship.setQuantity(ship.getQuantity() - 1);
				ship.resetArmor();
			} else {
				planetArmy[type].remove(ship);
			}
				actualNumberUnitsPlanet[type] -= 1;
		} else {
			if (ship.getQuantity() > 1) {
				ship.setQuantity(ship.getQuantity() - 1);
				ship.resetArmor();
			} else {
				enemyArmy[type].remove(ship);
			}
			actualNumberUnitsEnemy[type] -= 1;
		}
		
		return ship.getClass().getSimpleName() + " has been destroyed";
	}
	
	public String startBattle() {
		String msg;
		if (turn == 0) {
			msg = "Planet attacks: " + "\n";
		} else {
			msg = "Enemy Fleet attacks: " + "\n";
		}
		MilitaryUnit attacker = getAttacker();
		MilitaryUnit defender = getDefender();
		
		msg += attack(attacker, defender);
		battleDevelopment = msg;
		
		return msg;
	}
	
	public String continueBattle() {
		String msg;
		if (turn == 0) {
			msg = "Planet attacks: " + "\n";
		} else {
			msg = "Enemy Fleet attacks: " + "\n";
		}
		MilitaryUnit attacker = getAttacker();
		MilitaryUnit defender = getDefender();
		
		msg += attack(attacker, defender);
		battleDevelopment = msg;
		return msg;
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

	public String getBattleDevelopment() {
		return battleDevelopment;
	}

	public void setBattleDevelopment(String battleDevelopment) {
		this.battleDevelopment = battleDevelopment;
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
