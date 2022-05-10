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
	
	private int getTotalUnitsSquad(ArrayList<MilitaryUnit> squad) {
		int total = 0;
		for (int i = 0; i < squad.size(); i++) {
			total += squad.get(i).getQuantity();
		}
		return total;
	}
	
	private int getTotalUnitsFleet(ArrayList<MilitaryUnit>[] fleet) {
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
			actualNumberUnitsPlanet[i] = planetInitialArmy[i].size();
			for (int j = 0; j < planetInitialArmy[i].size(); j++) {
				total_cost_metal_planet += planetInitialArmy[i].get(j).getMetalCost();
				total_cost_deuterium_planet += planetInitialArmy[i].get(j).getDeuteriumCost();
			}
			// Com que l'enemic nomes te naus en el seu exercit, s'emplena la seva fila de la matriu fins a
			// la columna 7
			if (i <= 3) {
				initialArmies [1][i] = getTotalUnitsSquad(enemyInitialArmy[i]);
				actualNumberUnitsEnemy[i] = enemyInitialArmy[i].size();
				
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
	}
	
	private int getStartingTurn() {
		// Retorna un numero enter aleatori entre 0 i 1
		// Si retorna 0 es el torn del jugador, si retorna 1 es el torn de l'enemic
		return (int) (Math.random());
	}
	
	private MilitaryUnit getAttacker(int turn) {
		// S'escull un nombre aleatori entre 0 i 100 perque 
		// els valors totals de les Arrays de probabilitat, tant les de jugador com les d'enemic,
		// equivalen a 100
		int attacker = (int) (Math.random()*100);
		if (turn == 0) {
			for (int i = 0; i < planetArmy.length; i++) {
				// Es resta la probabilitat d'atacar de cada grup al nombre escollit aleatoriament
				attacker -= CHANCE_ATTACK_PLANET_UNITS[i];
				// Si el nombre resultant a la resta es igual o menor a 0, s'escull una tropa aleatoria
				// entre les de l'ArrayList del grup
				if (attacker <= 0) {
					return planetArmy[i].get((int) (Math.random()*planetArmy[i].size()));
				}
			}
		} else {
			for (int i = 0; i < enemyArmy.length; i++) {
				// Es resta la probabilitat d'atacar de cada grup al nombre escollit aleatoriament
				attacker -= CHANCE_ATTACK_ENEMY_UNITS[i];
				// Si el nombre resultant a la resta es igual o menor a 0, s'escull una tropa aleatoria
				// entre les de l'ArrayList del grup
				if (attacker <= 0) {
					return enemyArmy[i].get((int) (Math.random()*enemyArmy[i].size()));
				}
			}
		}
		// retorna un valor nul per defecte
		return null;
	}
	
	private MilitaryUnit getDefender(int turn) {
		if (turn == 0) {
			int totalChances = getTotalUnitsFleet(enemyArmy);
			
			int defender = (int) (Math.random()*totalChances);
			
			for (int i = 0; i < enemyArmy.length; i++) {
				defender -= enemyArmy[i].size();
				if (defender <= 0) {
					return enemyArmy[i].get((int) (Math.random()*enemyArmy[i].size()));
				}
			}
			
		} else {
			int totalChances = getTotalUnitsFleet(planetArmy);
			
			int defender = (int) (Math.random()*totalChances);
			
			for (int i = 0; i < planetArmy.length; i++) {
				defender -= planetArmy[i].size();
				if (defender <= 0) {
					return planetArmy[i].get((int) (Math.random()*enemyArmy[i].size()));
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
			}	
			
		}
		
		
		return message;
	}
	
	private int continueBattle() {
		int total_units_planet = 0;
		int total_units_enemy = 0;
		for (int i = 0; i < actualNumberUnitsPlanet.length; i++) {
			total_units_planet += actualNumberUnitsPlanet[i];
			if (i <= 3) {
				total_units_enemy += actualNumberUnitsEnemy[i];
			}
		}
		if (total_units_planet < initialNumberUnitsPlanet*0.2 || total_units_planet < initialNumberUnitsPlanet*0.2) {
			return 1;
		}
		return 0;
	}
	
	private int getWinner() {
		if (resourcesLosses[0][2] < resourcesLosses[1][2]) {
			return 0;
		}
		return 1;
	}
	
	private void loseShip(MilitaryUnit ship, int side) {
		resourcesLosses[side][0] += ship.getMetalCost();
		resourcesLosses[side][1] += ship.getDeuteriumCost();
		resourcesLosses[side][2] += ship.getMetalCost() + 5*ship.getDeuteriumCost();
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
		if (side == 0) {
			planetArmy[type].remove(ship);
			actualNumberUnitsPlanet[type] -= 1;
		} else {
			enemyArmy[type].remove(ship);
			actualNumberUnitsEnemy[type] -= 1;
		}
	}
	
	
}
