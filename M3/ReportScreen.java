package PlanetWars;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;



public class ReportScreen extends JPanel {
	
	Connection con;
	
	JPanel panel1, panel2, panel3;
	JLabel label_bat = new JLabel("Battle ID: ");
	JTextField id_bat;
	JTextArea terminal;
	JButton boton_resumen, boton_completo;
	
	int total_starting_units_planet, total_final_units_planet, total_starting_units_enemy, total_final_units_enemy,
	planet_metal_costs, planet_deuterium_costs, enemy_metal_costs, enemy_deuterium_costs, planet_metal_losses, 
	planet_deuterium_losses, enemy_metal_losses, enemy_deuterium_losses, metal_wastings, deuterium_wastings, winner;
	
	String complete_rep;
	
	int[] starting_units_planet = new int[7];
	int[] final_units_planet = new int[7];
	
	int[] starting_units_enemy = new int[4];
	int[] final_units_enemy = new int[4];
	
	JScrollPane scroll;
	
	public ReportScreen(Connection con) {
		
		// guarda la connexio a la bbdd com a un atribut
		this.con = con;
		
		// panel principal que conte tots els elements i els altres panels
		panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		
		
		// panel que conte la JLabel de id de batalla, un textfield per a insertar la id de batalla i un panel amb dos botons per a mostrar els reports
		panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));

		// panel que conte els botons per a mostrar els reports
		panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
		
		label_bat.setForeground(Color.WHITE);
		
		// textfield per a insertar la id de batalla
		id_bat = new JTextField();
		id_bat.setPreferredSize(new Dimension(1, 1));
		
		// textarea per a mostrar els reports
		terminal = new JTextArea();
		terminal.setEditable(false);
		terminal.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		terminal.setBackground(Color.BLACK);
		terminal.setForeground(Color.GREEN);
		
		// textarea terminal amb una barra de scroll
		scroll = new JScrollPane(terminal);
		scroll.setPreferredSize(new Dimension(750, 500));
		
		this.setBackground(new Color(0, 0, 0, 0));
		panel1.setBackground(new Color(0, 0, 0, 0));
		panel2.setBackground(new Color(0, 0, 0, 0));
		panel3.setBackground(new Color(0, 0, 0, 0));
		this.setPreferredSize(new Dimension(800, 600));
		
		// botons per a mostrar els reports i els seus respectius listeners
		boton_resumen = new JButton("Show Battle Summary");
		boton_resumen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					// primer es comprova que la ID sigui correcta
					CallableStatement cst = con.prepareCall("{call check_battle (?, ?)}");
					cst.setInt(1, Integer.parseInt(id_bat.getText()));
					cst.registerOutParameter(2, java.sql.Types.INTEGER);
					cst.execute();
					System.out.println("comprobacion");
					int existe = cst.getInt(2);
					if (existe == 0) {
						JOptionPane.showMessageDialog(null, "ERROR: battle ID does not exist");;
					} else {
						System.out.println("a");
						// si existeix la batalla es genera el report resumit
						setVariables(Integer.parseInt(id_bat.getText()));
						terminal.setText(getSummary(Integer.parseInt(id_bat.getText())));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("ERROR");
					e.printStackTrace();
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "ERROR: battle ID must be a number");
				}
			}
			
		});
		
		boton_completo = new JButton("Show Battle Development");
		boton_completo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CallableStatement cst = con.prepareCall("{call check_battle (?, ?)");
					cst.setInt(1, Integer.parseInt(id_bat.getText()));
					cst.registerOutParameter(2, java.sql.Types.INTEGER);
					cst.execute();
					if (cst.getInt(2) == 0) {
						JOptionPane.showMessageDialog(null, "ERROR: battle ID does not exist");;
					} else {
						setVariables(Integer.parseInt(id_bat.getText()));
						setCompleteReport(Integer.parseInt(id_bat.getText()));
						terminal.setText(complete_rep);
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "ERROR: battle ID must be a number");
				}
			}
			
		});
		
		// s'aniden els panels i s'afageixen a la finestra
		panel1.add(panel2);
		panel1.add(scroll);
		panel2.add(label_bat);
		panel2.add(id_bat);
		panel2.add(panel3);
		panel3.add(boton_resumen);
		panel3.add(boton_completo);
		
		add(panel1);
		

	}
	
	private String getSummary(int id_battle) {
		String msg = ("\n" +
				"BATTLE ID: " + id_battle + "\n" + 
				"BATTLE STATISTICS" + "\n\n" + 
				"Planet Army"      + repetirChar(20-"Planet Army".length(), ' ')      + "Starting Units"            + repetirChar(15-"Starting Units".length(), ' ')                            + "Remaining Units"         + repetirChar(20-"Remaining Units".length(), ' ')                       + "Enemy Army"   + repetirChar(20-"Enemy Army".length(), ' ')   + "Starting Units"           + repetirChar(15-"Starting Units".length(), ' ')                        + "Remaining Units"    + "\n" +
				"Light Hunter"     + repetirChar(20-"Light Hunter".length(), ' ')     + starting_units_planet[0]    + repetirChar(15-String.valueOf(starting_units_planet[0]).length(), ' ')    + final_units_planet[0]    + repetirChar(20-String.valueOf(final_units_planet[0]).length(), ' ')    + "Light Hunter" + repetirChar(20-"Light Hunter".length(), ' ') + starting_units_enemy[0]    + repetirChar(15-String.valueOf(starting_units_enemy[0]).length(), ' ') + final_units_enemy[0] + "\n" +
				"Heavy Hunter"     + repetirChar(20-"Heavy Hunter".length(), ' ')     + starting_units_planet[1]    + repetirChar(15-String.valueOf(starting_units_planet[1]).length(), ' ')    + final_units_planet[1]    + repetirChar(20-String.valueOf(final_units_planet[1]).length(), ' ')    + "Heavy Hunter" + repetirChar(20-"Heavy Hunter".length(), ' ') + starting_units_enemy[1]    + repetirChar(15-String.valueOf(starting_units_enemy[1]).length(), ' ') + final_units_enemy[1] + "\n" +
				"Battle Ship"      + repetirChar(20-"Battle Ship".length(), ' ')      + starting_units_planet[2]    + repetirChar(15-String.valueOf(starting_units_planet[2]).length(), ' ')    + final_units_planet[2]    + repetirChar(20-String.valueOf(final_units_planet[2]).length(), ' ')    + "Battle Ship"  + repetirChar(20-"Battle Ship".length(), ' ')  + starting_units_enemy[2]    + repetirChar(15-String.valueOf(starting_units_enemy[2]).length(), ' ') + final_units_enemy[2] + "\n" +
				"Armored Ship"     + repetirChar(20-"Armored Ship".length(), ' ')     + starting_units_planet[3]    + repetirChar(15-String.valueOf(starting_units_planet[3]).length(), ' ')    + final_units_planet[3]    + repetirChar(20-String.valueOf(final_units_planet[3]).length(), ' ')    + "Armored Ship" + repetirChar(20-"Armored Ship".length(), ' ') + starting_units_enemy[3]    + repetirChar(15-String.valueOf(starting_units_enemy[3]).length(), ' ') + final_units_enemy[3] + "\n" +
				"Missile Launcher" + repetirChar(20-"Missile Launcher".length(), ' ') + starting_units_planet[4]    + repetirChar(15-String.valueOf(starting_units_planet[4]).length(), ' ')    + final_units_planet[4]    + "\n" +
				"Ion Cannon"       + repetirChar(20-"Ion Cannon".length(), ' ')       + starting_units_planet[5]    + repetirChar(15-String.valueOf(starting_units_planet[5]).length(), ' ')    + final_units_planet[5]    + "\n" +
				"Plasma Cannon"    + repetirChar(20-"Plasma Cannon".length(), ' ')    + starting_units_planet[6]    + repetirChar(15-String.valueOf(starting_units_planet[6]).length(), ' ')    + final_units_planet[6]    + "\n" +
				"TOTAL"            + repetirChar(20-"TOTAL".length(), ' ')            + total_starting_units_planet + repetirChar(15-String.valueOf(total_starting_units_planet).length(), ' ') + total_final_units_planet + repetirChar(20-String.valueOf(total_final_units_planet).length(), ' ') + "TOTAL"        + repetirChar(20-"TOTAL".length(), ' ')        + total_starting_units_enemy + repetirChar(15-String.valueOf(total_starting_units_enemy).length(), ' ') + total_final_units_enemy + "\n" +
				
				repetirChar(106, '*') + "\n" +
				"Planet Army's Costs"                  + repetirChar(55-"Planet Army's Costs".length(), ' ')                    + "Enemy Army's Costs" + "\n" +
				"Metal: "     + planet_metal_costs     + repetirChar(55-("Metal: " + planet_metal_costs).length(), ' ')         + "Metal: " + enemy_metal_costs + "\n" +
				"Deuterium: " + planet_deuterium_costs + repetirChar(55-("Deuterium: " + planet_deuterium_costs).length(), ' ') + "Deuterium: " + enemy_deuterium_costs + "\n" +
				
				repetirChar(106, '*') + "\n" +
				"Planet Army's Losses"                                            + repetirChar(55-"Planet Army's Losses".length(), ' ')                                          + "Enemy Army's Losses" + "\n" + 
				"Metal: "     + planet_metal_losses                               + repetirChar(55-("Metal: " + planet_metal_losses).length(), ' ')                               + "Metal: "             + enemy_metal_losses + "\n" +
				"Deuterium: " + planet_deuterium_losses                           + repetirChar(55-("Deuterium: " + planet_deuterium_losses).length(), ' ')                           + "Deuterium: "         + enemy_deuterium_losses + "\n" +
				"Weighted: "  + (planet_metal_losses + planet_deuterium_losses*5) + repetirChar(55-("Weighted: " + (planet_metal_losses + planet_deuterium_losses*5)).length(), ' ') + "Weighted: "          + (enemy_metal_losses + enemy_deuterium_losses*5) + "\n" + 
				
				repetirChar(106, '*') + "\n" +
				"Wastings Generated" + "\n" +
				"Metal: " + metal_wastings + "\n" +
				"Deuterium: " + deuterium_wastings + "\n\n"
				);
		if (winner == 0) {
			msg += "Battle won by Planet Army";
		} else {
			msg += "Battle won by Enemy Army";
		}
		return msg;
	}
	
	// retorna un string que es el caracter multiplicat per les repeticions (similar a ljust en python)
	private String repetirChar(int repeticiones, char caracter) {
		String msg = new String(new char[repeticiones]).replace('\0', caracter);
		return msg;
	}
	
	// extreu les naus i defenses que han participat en la batalla a buscar a partir de la base de dades
	private void setUnits(int id_battle, int index) {
		try {
			CallableStatement cst1 = con.prepareCall("{call get_ship_battle (?, ?, ?, ?, ?)}");
			CallableStatement cst2 = con.prepareCall("{call get_defense_battle (?, ?, ?, ?, ?)}");
			
			cst1.setInt(1, id_battle);
			
			cst1.registerOutParameter(4, java.sql.Types.INTEGER);
			cst1.registerOutParameter(5, java.sql.Types.INTEGER);
			
			cst1.setInt(2, index+1);
			cst1.setInt(3, 0);
			
			cst1.execute();
			
			starting_units_planet[index] = cst1.getInt(4);
			final_units_planet[index] = cst1.getInt(5);
			
			cst1.setInt(3, 1);
			
			cst1.execute();
			
			starting_units_enemy[index] = cst1.getInt(4);
			final_units_enemy[index] = cst1.getInt(5);
			
			if (index < 3) {
				cst2.setInt(1, id_battle);
				cst2.setInt(2, index+1);
				cst2.setInt(3, 0);
				cst2.registerOutParameter(4, java.sql.Types.INTEGER);
				cst2.registerOutParameter(5, java.sql.Types.INTEGER);
				cst2.execute();
				starting_units_planet[index+3] = cst2.getInt(4);
				final_units_planet[index+3] = cst2.getInt(5);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// extreu de la base de dades les variables necesaries per al report
	private void setVariables(int id_batalla) {
		try {
			CallableStatement cst = con.prepareCall("{call SHOW_REPORT (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			cst.setInt(1, id_batalla);
			
			for (int i = 1; i < 18; i++) {
				cst.registerOutParameter(i, java.sql.Types.INTEGER);
			}
			System.out.println("parametros variable");
			
			cst.execute();
			
			
			total_starting_units_planet = cst.getInt(3);
			total_final_units_planet = cst.getInt(4);
			total_starting_units_enemy = cst.getInt(5);
			total_final_units_enemy = cst.getInt(6);
			planet_metal_costs = cst.getInt(7);
			planet_deuterium_costs = cst.getInt(8);
			enemy_metal_costs = cst.getInt(9);
			enemy_deuterium_costs = cst.getInt(10);
			planet_metal_losses = cst.getInt(11);
			planet_deuterium_losses = cst.getInt(12);
			enemy_metal_losses = cst.getInt(13);
			enemy_deuterium_losses = cst.getInt(14);
			metal_wastings = cst.getInt(15);
			deuterium_wastings = cst.getInt(16);
			winner = cst.getInt(17);
			
			System.out.println("iniciando units");
			for (int i = 0; i < 4; i++) {
				setUnits(id_batalla, i);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// extreu de la base de dades el desenvolupament de la batalla a buscar
	private void setCompleteReport(int id_batalla) {
		try {
			CallableStatement cst = con.prepareCall("{call GET_STEP_AMOUNT (?, ?)}");
			
			cst.setInt(1, id_batalla);
			
			cst.registerOutParameter(2, java.sql.Types.INTEGER);
			
			cst.execute();
			System.out.println("a");
			String msg = "";
			int num_steps = cst.getInt(2);
			cst.close();
			for (int i = 1; i <= num_steps; i++) {
				
				cst = con.prepareCall("{call GETBATTLESTEP (?, ?, ?)}");
				cst.setInt(1, id_batalla);
				cst.setInt(2, i);
				
				cst.registerOutParameter(3, java.sql.Types.VARCHAR);
				cst.execute();
				
				msg += "\n" + cst.getString(3);
				cst.close();
			}
			
			complete_rep = msg;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}





