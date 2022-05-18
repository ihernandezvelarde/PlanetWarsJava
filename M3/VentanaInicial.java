package PlanetWars;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class VentanaInicial extends JFrame implements Variables{

    private JTabbedPane pestanyas;
    private JTabbedPane build;
    private JPanel inp1,panel2, panel3,panel4; //paneles principales
    private JPanel panelB1,panelB2,panelBp1,panelBp2,panelBp10; //subpaneles

    private JPanel panelFinalP1,panelFinalP2,panelFinalP3,panelFinalP4; // Paneles Inp1

    private JTextField[] textoP1= new JTextField[5];
    private JLabel[] etiquetaP1=new JLabel[10];
    // build
    private JButton[] btnBuild=new JButton[5];
    private JButton[] btnBuild2=new JButton[3];
    private JLabel[] etiquetaBuild=new JLabel[8];
    private JLabel[] etiquetaBuild2=new JLabel[8];
    private ArrayList<JLabel>[] labelsBuild=new ArrayList[5];
    private ArrayList<JLabel>[] labelsBuild2=new ArrayList[3];
    private JTextField[] buildText=new JTextField[4];
    private JTextField[] buildText2=new JTextField[3];
    private JLabel[] images=new JLabel[7];
    private JPanel[] textoformatado=new JPanel[4];
    private JPanel[] textoformatado2=new JPanel[4];
    private JLabel[] etiquetaP3=new JLabel[6];
    
    private JButton botonP11, botonP12,salir;

    private JLabel deuterium,metal,lgtCant,hvyCant,btlCant,missileCant,ionCant,plasmaCant,arshipCant;
    
    private CallableStatement cst;
    InfoShips ship= new InfoShips();
    InfoUsers usu= new InfoUsers();
    InfoPlanet plane= new InfoPlanet();
    InfoDefense defe=new InfoDefense();

    private JLabel[] buildTroopsdef=new JLabel[4];
    private JLabel[] buildTroopsatk=new JLabel[4];

    private JLabel attackTech,defenseTech;
    private JLabel planetGIF;

    private Planet planeta;
    private int IDplanetita;
    private int compro=0;
    
    ArrayList<MilitaryUnit>[] armyInicial = new ArrayList[7];
    
    public void aumentadorRecursos(int metalito,int deuterito) {
        metal.setText(String.valueOf(metalito));
        deuterium.setText(String.valueOf(deuterito));
    }

    public VentanaInicial(Connection con,String usuario) {
    	for (int i=0;i<7;i++) {
    		armyInicial[i]= new ArrayList<MilitaryUnit>();
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
    	
    	//INICIAR PLANETA
    	planeta=new Planet();
        
        	try {
				
	            cst=usu.getInfoPlanetId(con, usuario);
				cst.execute();
				IDplanetita=cst.getInt(2);
				cst=plane.getInfoPlanet(con, IDplanetita);
				cst.execute();
				
		        planeta.setTechnologyAtack(cst.getInt(3)); //tecnoatk
		    	planeta.setTechnologyDefense(cst.getInt(4)); //tecno def
		    	planeta.setDeuterium(cst.getInt(7));
		    	planeta.setMetal(cst.getInt(6));
		    	
		    	//for nave del 0 a 4 for tecno1 for tecno 2 si no es 0 for anyadir naves
		    	for (int i=0;i<4;i++) {
		    		
		    		for (int j=0;j<planeta.getTechnologyAtack()+1;j++) {
		    			
		    			for (int k=0;k<planeta.getTechnologyDefense()+1;k++) {
		    				int canti_defense=plane.getInfoQuantityDefenses(con, IDplanetita, i+1, j, k);
		    				int canti = plane.getInfoQuantityShips(con, IDplanetita , i+1, j, k);
		    				if(canti>0) {
		    					for (int l=0; l < canti ;l++) {
		    						if (i==0) {
		    							armyInicial[0].add(new LightHunter(planeta.getTechnologyDefense(), planeta.getTechnologyAtack(), con));
		    						}
		    						else if(i==1) {
		    							armyInicial[1].add(new HeavyHunter(planeta.getTechnologyDefense(), planeta.getTechnologyAtack(), con));
	    						}
		    						else if(i==2) {
		    							armyInicial[2].add(new BattleShip(planeta.getTechnologyDefense(), planeta.getTechnologyAtack(), con));
		    						}
		    						else if(i==3) {
		    							armyInicial[3].add(new ArmoredShip(planeta.getTechnologyDefense(), planeta.getTechnologyAtack(), con));
		    						}
		    					}}
		    				if(canti_defense>0) {
		    					for (int h=0;h<canti_defense;h++) {
		    						if (i==0) {
		    							armyInicial[4].add(new MissileLauncher(planeta.getTechnologyDefense(), planeta.getTechnologyAtack(), con));
		    						}
		    						else if(i==1) {
		    							armyInicial[5].add(new IonCannon(planeta.getTechnologyDefense(), planeta.getTechnologyAtack(), con));
		    							
	    						}
		    						else if(i==2) {
		    							armyInicial[6].add(new PlasmaCannon(planeta.getTechnologyDefense(), planeta.getTechnologyAtack(), con));
		    							
		    					}
		    					}
		    				}
		    			}
		    		}
		    	}
		    	planeta.setArmy(armyInicial);
		    	
		    	for (int k=0;k<planeta.getTechnologyAtack();k++) {
		    		planeta.setUpgradeAttackTechnologyDeuteriumCost(planeta.getUpgradeAttackTechnologyDeuteriumCost()+planeta.getUpgradeAttackTechnologyDeuteriumCost()*10/100);
		    	}
		    	for (int g=0;g<planeta.getTechnologyDefense();g++) {
		    		planeta.setUpgradeDefenseTechnologyDeuteriumCost(planeta.getUpgradeDefenseTechnologyDeuteriumCost() +planeta.getUpgradeDefenseTechnologyDeuteriumCost()*10/100);
		    	}
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
        	compro=0;
        	Timer countdown = new Timer (180000,new ActionListener() {
    			
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				// TODO Auto-generated method stub
    				guardado(con,  usuario);
    				System.out.println("SAVEPOINT");
    				compro=1;
    				dispose();
    				new BattleScreen(planeta, new Enemy(con).getEnemyArmy(), con, usu.getIdUser(con, usuario),usuario);
    				
    			}
    		});
        	Timer batalla =new Timer (65000,new ActionListener() {
    			
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				// TODO Auto-generated method stub
    				double rnd=Math.floor(Math.random()*10);
    				System.out.println(rnd);
    				if (rnd==5.0) {
    				countdown.start(); JOptionPane.showMessageDialog(null, "ENEMY SHIPS INCOMING!\nYOU HAVE 3 MINUTES TO PREPARE THE DEFENSES");  }
    			}
    		});
        	//TIMERS
        	//timer de recursos
        	Timer timer = new Timer (1000, new ActionListener ()
    		{
    		    public void actionPerformed(ActionEvent e)
    		    {
    		        // Aqu el cdigo que queramos ejecutar.
    		    	planeta.setDeuterium(planeta.getDeuterium()+100);
    		    	planeta.setMetal(planeta.getMetal()+1000);
    		    	aumentadorRecursos(planeta.getMetal(), planeta.getDeuterium());
    		    	  		    	
    		     }
    		});
    		timer.start();
    		Timer savepoint=new Timer (60000,new ActionListener() {
    			
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				// TODO Auto-generated method stub
    				guardado(con,  usuario);
    				System.out.println("SAVEPOINT");
    			}
    		});
    		savepoint.start();
    		Timer Stopper=new Timer (500,new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (countdown.isRunning()==true) {
						batalla.stop();
						timer.stop();
						savepoint.stop();
						if (compro==1) {
							countdown.stop();
						}
					}
				}
			});
    		Stopper.start();
    		batalla.start(); 
        inicializarTextos(con,usuario);
        this.setSize(800, 600);
        setUndecorated(true);
        //undecorated para eliminar la barra de maximizar y cerrar
        Image imagen = Toolkit.getDefaultToolkit().getImage("eve.jpg");
        Image imagen2 = Toolkit.getDefaultToolkit().getImage("space.jpg");
        panel2=new JPanelConFondo(imagen);
        panel3=new JPanelConFondo(imagen);
        panel4=new JPanelConFondo(imagen);
        panelB1=new JPanelConFondo(imagen2);

        panelB2=new JPanelConFondo(imagen2);
        panelBp1=new JPanelConFondo(imagen2);
        panelBp2=new JPanelConFondo(imagen2);
        panelBp10=new JPanelConFondo(imagen2);
        panelBp10.setLayout(new GridLayout(2,1));

        // Tocar estos grid layouts para agregar cosas finales---------------------------------------
        panelB1.setLayout(new GridLayout(5,8)); // iamgen,nombre,armor,dmg, cose metal, coste deuterio, texfield, boton
        panelB2.setLayout(new GridLayout(4,8));

        // arriba tocar
        etiquetaP1[0].setPreferredSize(new Dimension(700,50));
        //etiqueta gigante para redimensionar el layout y que el jtabbedpane salga bien


        //NO TOCAR ABAJO
        panelBp10.add(panelBp1);

        panelBp10.add(etiquetaP1[0]); // etiqueta de formatado
        pestanyas=new JTabbedPane();
        build=new JTabbedPane();
        panelBp1.add(panelB1);
        panelBp2.add(panelB2);

        inp1=new JPanelConFondo(imagen);
        GridLayout grid = new GridLayout(2,2);
        grid.setHgap(15);
        grid.setVgap(15);
        inp1.setLayout(grid);
        inp1.setBorder(new EmptyBorder(10, 10, 10, 10));

        pestanyas.addTab("PLANET STATS",inp1);
        pestanyas.addTab("BUILD",panel2);
        pestanyas.addTab("UPGRADE TECHNOLOGY",panel3);
        pestanyas.addTab("VIEW BATTLE REPORTS",panel4);
        pestanyas.addTab("EXIT",salir);

        pestanyas.setForeground(Color.BLUE);
        pestanyas.setBackground(Color.BLACK);

        build.addTab("TROOPS", panelBp10);
        build.addTab("DEFENSES", panelBp2);

        build.setForeground(Color.BLUE);
        build.setBackground(Color.BLACK);

        panel2.add(build);
        this.add(pestanyas);

        //boton salir del jpane, cuando el index es el de la pestanya salir, salimos del programa con idex 4

        pestanyas.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                // TODO Auto-generated method stub
                if (pestanyas.getSelectedIndex()==4) {
                    pestanyas.setSelectedIndex(0);
                    int opc=JOptionPane.showOptionDialog(rootPane,"ARE YOU SURE TO EXIT THE GAME?","Exit",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[] {"Yes","No"}, "No");
                    switch (opc) {
                        case 0:
                        	guardado(con,  usuario);
            				System.out.println("SAVEPOINT EXIT");
                            System.exit(EXIT_ON_CLOSE);
                            break;}}
                else if (pestanyas.getSelectedIndex()==1) {
                	pestanyas.setSelectedIndex(1);
                	buildReset(con, usuario);
                }
                else if (pestanyas.getSelectedIndex()==0) {
                	pestanyas.setSelectedIndex(0);
                	armyInicial=planeta.getArmy();
                	lgtCant.setText(String.valueOf(armyInicial[0].size()));
                	hvyCant.setText(String.valueOf(armyInicial[1].size()));
                	btlCant.setText(String.valueOf(armyInicial[2].size()));
                	arshipCant.setText(String.valueOf(armyInicial[3].size()));
                	missileCant.setText(String.valueOf(armyInicial[4].size()));
                	ionCant.setText(String.valueOf(armyInicial[5].size()));
                	plasmaCant.setText(String.valueOf(armyInicial[6].size()));
                	attackTech.setText(String.valueOf(planeta.getTechnologyAtack()));
                	defenseTech.setText(String.valueOf(planeta.getTechnologyDefense()));
                	
                }
            }
            
        });

        this.setTitle("PLANET WARS");
        this.setResizable(false);
        //arriba para evitar redimensionar ventana , permitido por defecto

        Toolkit pantalla=Toolkit.getDefaultToolkit();
        Image img=pantalla.getImage("ico.png");
        this.setIconImage(img);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // arriba para terminar programa al cerrar ventana

        Dimension d=pantalla.getScreenSize();
        int ancho=d.width;
        int alto=d.height;
        this.setLocation(ancho/2-this.getWidth()/2, alto/2-this.getHeight()/2);
        //arriba para centrar , independientemente de la pantalla

        //ABAJO SE PUEDE TOCAR

        // Pestanya 1 Planet Stats -------------------------------------------------------------------------------------------=========================

     // Panel global de Planet Stats
        panelFinalP1=new JPanel();panelFinalP1.setOpaque(false);
            // GIF del planeta giratorio
            planetGIF = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("planet_rotate.gif").getScaledInstance(250,150,Image.SCALE_DEFAULT)));
            etiquetaP1[7].setForeground(Color.white);etiquetaP1[7].setFont(new Font("Aliens.ttf",Font.BOLD,40));
            panelFinalP1.add(etiquetaP1[7]);
            panelFinalP1.add(planetGIF);


        panelFinalP2 = new JPanel();panelFinalP2.setOpaque(false);
            // Image Icons de deuterio y metal
            JLabel buildMtl = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("metal.png").getScaledInstance(25, 25, Image.SCALE_DEFAULT)));
            JLabel buildDtm = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("deuterium.png").getScaledInstance(25, 25, Image.SCALE_DEFAULT)));
            // JPanel para los recursos del planeta
            JPanel resources = new JPanel(new GridLayout(1,5));resources.setBackground(new Color(247,235,232));
            resources.setPreferredSize(new Dimension(175,50));
            resources.setBorder(BorderFactory.createBevelBorder(1,new Color(229,75,75),new Color(30,30,36)));

            // JLabels de los recursos
            deuterium = new JLabel(String.valueOf(planeta.getDeuterium()));
            metal = new JLabel(String.valueOf(planeta.getMetal()));
            metal.setForeground(Color.black);
            deuterium.setForeground(Color.black);

            // Anyadir elementos
            resources.add(buildMtl);resources.add(metal);
            resources.add(buildDtm);resources.add(deuterium);

            JPanel technology = new JPanel(new GridLayout(2,3));technology.setBackground(new Color(247,235,232));
            technology.setPreferredSize(new Dimension(325,100));
            technology.setBorder(BorderFactory.createBevelBorder(1,new Color(229,75,75),new Color(30,30,36)));

            attackTech = new JLabel(String.valueOf(planeta.getTechnologyAtack()));
            defenseTech = new JLabel(String.valueOf(planeta.getTechnologyDefense()));
            JLabel buildTechAtk = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("techAtk.png").getScaledInstance(35,35,Image.SCALE_DEFAULT)));
            JLabel buildTechDef = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("techDef.png").getScaledInstance(35,35,Image.SCALE_DEFAULT)));
            etiquetaP1[5].setForeground(Color.black);attackTech.setForeground(new Color(255,169,135));
            etiquetaP1[6].setForeground(Color.black);defenseTech.setForeground(new Color(255,169,135));

            technology.add(buildTechAtk);technology.add(etiquetaP1[5]);technology.add(attackTech);
            technology.add(buildTechDef);technology.add(etiquetaP1[6]);technology.add(defenseTech);

            panelFinalP2.add(resources);
            panelFinalP2.add(technology);

        panelFinalP3 = new JPanel();panelFinalP3.setLayout(new BoxLayout(panelFinalP3, BoxLayout.X_AXIS));
            panelFinalP3.setBorder(BorderFactory.createBevelBorder(1,new Color(229,75,75),new Color(30,30,36)));
            panelFinalP3.setBackground(new Color(247,235,232));

            JPanel fleetP = new JPanel(new GridLayout(5,3));
            fleetP.setOpaque(false);fleetP.setBorder(new EmptyBorder(10,10,10,10));
            JLabel fleetIMG = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("fleet.png").getScaledInstance(35,35,Image.SCALE_DEFAULT)));
            JLabel lightL = new JLabel("Light Hunter");lgtCant = new JLabel(String.valueOf(armyInicial[0].size()));lgtCant.setForeground(new Color(255,169,135));
            JLabel heavyL = new JLabel("Heavy Hunter");hvyCant = new JLabel(String.valueOf(armyInicial[1].size()));hvyCant.setForeground(new Color(255,169,135));
            JLabel battleL = new JLabel("Battle Ship");btlCant = new JLabel(String.valueOf(armyInicial[2].size()));btlCant.setForeground(new Color(255,169,135));
            JLabel ArShipL = new JLabel("Armored Ship");arshipCant = new JLabel(String.valueOf(armyInicial[3].size()));arshipCant.setForeground(new Color(255,169,135));

            etiquetaP1[8].setForeground(Color.black);etiquetaP1[8].setFont(new Font("Arial",Font.BOLD,20));

            fleetP.add(fleetIMG);fleetP.add(etiquetaP1[8]);

            fleetP.add(lightL);fleetP.add(lgtCant);
            fleetP.add(heavyL);fleetP.add(hvyCant);
            fleetP.add(ArShipL);fleetP.add(arshipCant);
            fleetP.add(battleL);fleetP.add(btlCant);

            panelFinalP3.add(fleetP);


        panelFinalP4 = new JPanel();panelFinalP4.setLayout(new BoxLayout(panelFinalP4, BoxLayout.X_AXIS));
            panelFinalP4.setBorder(BorderFactory.createBevelBorder(1,new Color(229,75,75),new Color(30,30,36)));
            panelFinalP4.setBackground(new Color(247,235,232));

            JPanel defenseP = new JPanel(new GridLayout(4,2));defenseP.setOpaque(false);defenseP.setBorder(new EmptyBorder(10,10,10,10));
            JLabel defenseIMG = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("cannon.png").getScaledInstance(35,35,Image.SCALE_DEFAULT)));

            JLabel missileL = new JLabel("Missile Launcher");missileCant = new JLabel(String.valueOf(armyInicial[4].size()));missileCant.setForeground(new Color(255,169,135));
            JLabel ionL = new JLabel("ION Cannon");ionCant = new JLabel(String.valueOf(armyInicial[5].size()));ionCant.setForeground(new Color(255,169,135));
            JLabel plasmaL = new JLabel("Plasma Cannon");plasmaCant = new JLabel(String.valueOf(armyInicial[6].size()));plasmaCant.setForeground(new Color(255,169,135));
            etiquetaP1[9].setForeground(Color.black);etiquetaP1[9].setFont(new Font("Arial",Font.BOLD,20));

            defenseP.add(defenseIMG);defenseP.add(etiquetaP1[9]);

            defenseP.add(missileL);defenseP.add(missileCant);
            defenseP.add(ionL);defenseP.add(ionCant);
            defenseP.add(plasmaL);defenseP.add(plasmaCant);

            panelFinalP4.add(defenseP);


        inp1.add(panelFinalP1);inp1.add(panelFinalP3);
        inp1.add(panelFinalP2);inp1.add(panelFinalP4);


        // Pestanya 2 BUILD ----------------------------------------------------------------------------------------------------=================
        // Troops
        // Imagenes de build
        Image ligth = Toolkit.getDefaultToolkit().getImage("light.png");
        ImageIcon lgth= new ImageIcon(ligth.getScaledInstance(65, 65, Image.SCALE_DEFAULT));
        JLabel buildLight= new JLabel(lgth);
        Image heavy = Toolkit.getDefaultToolkit().getImage("heavy.png");
        ImageIcon hvy= new ImageIcon(heavy.getScaledInstance(65, 65, Image.SCALE_DEFAULT));
        JLabel buildHeavy = new JLabel(hvy);
        Image armored = Toolkit.getDefaultToolkit().getImage("armored.jpg");
        ImageIcon armo= new ImageIcon(armored.getScaledInstance(65, 65, Image.SCALE_DEFAULT));
        JLabel buildArmored= new JLabel(armo);
        Image battleshp = Toolkit.getDefaultToolkit().getImage("battle.jpg");
        ImageIcon bttl= new ImageIcon(battleshp.getScaledInstance(65, 65, Image.SCALE_DEFAULT));
        JLabel buildBattle=new JLabel(bttl);
        Image ion = Toolkit.getDefaultToolkit().getImage("ion.jpg");
        ImageIcon ioncan= new ImageIcon(ion.getScaledInstance(65, 65, Image.SCALE_DEFAULT));
        JLabel buildIon=new JLabel(ioncan);
        Image plasma = Toolkit.getDefaultToolkit().getImage("plasma.jpg");
        ImageIcon plas= new ImageIcon(plasma.getScaledInstance(65, 65, Image.SCALE_DEFAULT));
        JLabel buildPlasma=new JLabel(plas);
        Image missile = Toolkit.getDefaultToolkit().getImage("missile.jpg");
        ImageIcon missl= new ImageIcon(missile.getScaledInstance(65, 65, Image.SCALE_DEFAULT));
        JLabel buildMissile=new JLabel(missl);

        //---------------------------------cabecera troops---------------------------------------------------------------

        for (int i=0;i<etiquetaBuild.length;i++) {panelB1.add(etiquetaBuild[i]);}

        // Declaracion de jlabels build
        InfoShips ship=new InfoShips();
        cst= ship.getInfoShips(con, 1);
        try {cst.execute();}
        catch (SQLException e1) {e1.printStackTrace();}
        //------------------------------------------------------------ 
        for (int l=0;l<6;l++) {images[l]=new JLabel();}

        images[0]=buildLight;images[1]=buildHeavy;images[2]=buildArmored;
        images[3]=buildBattle;images[5]=buildIon;images[6]=buildPlasma;
        images[4]=buildMissile;

        for (int q=0;q<4;q++) {
            panelB1.add(images[q]);
            for (int k=0;k<5;k++) {
                panelB1.add(labelsBuild[q].get(k));
                labelsBuild[q].get(k).setForeground(Color.WHITE);
            }panelB1.add(textoformatado[q]);panelB1.add(btnBuild[q]);}

        // Botones tropa build
        
            btnBuild[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	try {
                    planeta.newLigthHunter(Integer.parseInt(buildText[0].getText()), con);}
                	
                catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "ERROR: must be a number");
                }}
            });
            btnBuild[1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	try {
                        planeta.newHeavyHunter (Integer.parseInt(buildText[1].getText()), con);}
                    
                    catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(null, "ERROR: must be a number");
                    }}
                });
            btnBuild[2].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	try {
                        planeta.newBattleShip(Integer.parseInt(buildText[2].getText()), con);}
                    
                    catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(null, "ERROR: must be a number");
                    }}
                });
            btnBuild[3].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	try {
                        planeta.newArmoredShip(Integer.parseInt(buildText[3].getText()), con);}
                    
                    catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(null, "ERROR: must be a number");
                    }}
                });
        

        // Defenses
        for (int i=0;i<etiquetaBuild2.length;i++) {panelB2.add(etiquetaBuild2[i]);}
        for (int q=0;q<3;q++) {
            panelB2.add(images[q+4]);
            for (int k=0;k<5;k++) {
                panelB2.add(labelsBuild2[q].get(k));
                labelsBuild2[q].get(k).setForeground(Color.WHITE);
            }
            panelB2.add(textoformatado2[q]);
            panelB2.add(btnBuild2[q]);}
        // botones def
        btnBuild2[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
                    planeta.newMissileLauncher(Integer.parseInt(buildText2[0].getText()), con);}
                
                catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "ERROR: must be a number");
                }}
            });
        btnBuild2[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
                    planeta.newIonCannon(Integer.parseInt(buildText2[1].getText()), con);}
                
                catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "ERROR: must be a number");
                }}
            });
        btnBuild2[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
                    planeta.newPlasmaCannon(Integer.parseInt(buildText2[2].getText()), con);}
                
                catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "ERROR: must be a number");
                }}
            });

        //pestanya 3 technology-------------------------------------------------------------------------------------------------------===============
        JPanel marcador = new JPanel();marcador.setOpaque(false);
        marcador.setBorder(new EmptyBorder(30,30,30,30));

        JPanel unitgrid_tecnoAtack = new JPanel();unitgrid_tecnoAtack.setOpaque(false);

        JPanel unitgrid_tecnoDefense = new JPanel();unitgrid_tecnoDefense.setOpaque(false);


        JPanel buttons = new JPanel(new FlowLayout());buttons.setOpaque(false);

        JButton boton_attack = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage("atkPlus.png").getScaledInstance(65,65,Image.SCALE_DEFAULT)));
        boton_attack.setBorderPainted(false);
        boton_attack.setContentAreaFilled(false);
        boton_attack.setFocusPainted(false);
        boton_attack.setOpaque(false);

        JButton boton_defense = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage("defPlus.png").getScaledInstance(65,65,Image.SCALE_DEFAULT)));
        boton_defense.setBorderPainted(false);
        boton_defense.setContentAreaFilled(false);
        boton_defense.setFocusPainted(false);
        boton_defense.setOpaque(false);

        for (int i=0;i<etiquetaP3.length;i++){
            etiquetaP3[i].setForeground(Color.black);
        }
        unitgrid_tecnoAtack.add(etiquetaP3[2]); //Titulo Coste Ataque
        unitgrid_tecnoAtack.add(etiquetaP3[3]);//Titulo Nivel Ataque

        JLabel DeuteriumCostAttack = new JLabel(Integer.toString(planeta.getUpgradeAttackTechnologyDeuteriumCost()));DeuteriumCostAttack.setForeground(new Color(255,169,135));
        JLabel lvlAttack = new JLabel(Integer.toString(planeta.getTechnologyAtack()));lvlAttack.setForeground(new Color(255,169,135));

        unitgrid_tecnoAtack.add(DeuteriumCostAttack); //coste
        unitgrid_tecnoAtack.add(lvlAttack); // nivel ataque


        unitgrid_tecnoDefense.add(etiquetaP3[4]);//Titulo Coste Defense
        unitgrid_tecnoDefense.add(etiquetaP3[5]);// Titulo Nivel Defense
        
        JLabel DeuteriumCostDefense = new JLabel(Integer.toString(planeta.getUpgradeDefenseTechnologyDeuteriumCost()));DeuteriumCostDefense.setForeground(new Color(255,169,135));
        JLabel lvlDefense = new JLabel(Integer.toString(planeta.getTechnologyDefense()));lvlDefense.setForeground(new Color(255,169,135));

        unitgrid_tecnoDefense.add(DeuteriumCostDefense);//coste defense
        unitgrid_tecnoDefense.add(lvlDefense);//nivel defense

        marcador.setLayout(new GridLayout(2, 3));
        unitgrid_tecnoAtack.setLayout(new GridLayout(2, 2));
        unitgrid_tecnoDefense.setLayout(new GridLayout(2, 2));

        marcador.add(etiquetaP3[0]);
        marcador.add(etiquetaP3[1]);
        marcador.add(unitgrid_tecnoAtack);
        marcador.add(unitgrid_tecnoDefense);

        boton_attack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                planeta.upgradeTechnologyAttack();
                lvlAttack.setText(String.valueOf(planeta.getTechnologyAtack()));
                DeuteriumCostAttack.setText(String.valueOf(planeta.getUpgradeAttackTechnologyDeuteriumCost()));
            }
        });

        boton_defense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                planeta.upgradeTechnologyDefense();
                lvlDefense.setText(String.valueOf(planeta.getTechnologyDefense()));
                DeuteriumCostDefense.setText(String.valueOf(planeta.getUpgradeDefenseTechnologyDeuteriumCost()));
            }
        });

        buttons.add(boton_attack);
        buttons.add(boton_defense);

        JPanel panelGeneral3 = new JPanel();panelGeneral3.setLayout(new GridLayout(2, 1));panelGeneral3.setBackground(new Color(247,235,232));
        panelGeneral3.setBorder(BorderFactory.createBevelBorder(1,new Color(229,75,75),new Color(30,30,36)));
        panelGeneral3.setPreferredSize(new Dimension(600,250));
        panelGeneral3.add(marcador);
        panelGeneral3.add(buttons);

        panel3.add(panelGeneral3);
        //pestanya 4 battle reports------------------------------------------------------------------------------------------------------=================
        
        panel4.add(new ReportScreen(con));


        this.setVisible(true);
    }

    //metodo actualizador de dmg y defense en build
    public void buildReset(Connection con ,String usuari) {
    	for (int i=0;i<4;i++) {
            cst= ship.getInfoShips(con, i+1);
            try {
            	cst.execute();
            	int basedmg=cst.getInt(8);
                int basedef=cst.getInt(7);
                
				
		        	buildTroopsdef[i].setText(Integer.toString(basedef+ (planeta.getTechnologyDefense()*PLUS_ARMOR_MISSILELAUNCHER_BY_TECHNOLOGY)));
		        	buildTroopsatk[i].setText(Integer.toString(basedmg+ (planeta.getTechnologyAtack()*PLUS_ATTACK_MISSILELAUNCHER_BY_TECHNOLOGY)));
		        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
        
    	
        
    	//buildTroopsatk[1].setText(getName());
    }
    public void inicializarTextos(Connection con,String usuari) {
        //Falta config
        textoP1[0]=new JTextField(""); // etiqueta reservada para el formatado
        textoP1[1]=new JTextField("");
        textoP1[2]=new JTextField("0");
        textoP1[3]=new JTextField("0");
        textoP1[4]=new JTextField("1");
        etiquetaP1[0]=new JLabel(" ");
        etiquetaP1[1]=new JLabel("Descripcion: ");
        etiquetaP1[3]=new JLabel("Puntos: ");
        etiquetaP1[4]=new JLabel("Nivel: ");
        etiquetaP1[5]=new JLabel("Attack Tech: ");
        etiquetaP1[6]=new JLabel("Defense Tech: ");

        try {
            cst = usu.getInfoPlanetId(con,usuari);
            cst.execute();

            cst = plane.getInfoPlanet(con,cst.getInt(2));
            cst.execute();
            etiquetaP1[7]=new JLabel(cst.getString(2));}
        catch (SQLException e) {e.printStackTrace();}
        etiquetaP1[8] = new JLabel("Fleet");
        etiquetaP1[9] = new JLabel("Defenses");
        // iamgen,nombre,armor,dmg, cose metal, coste deuterio, texfield, boton
        etiquetaBuild[0]=new JLabel(" "); // cabecera imagen
        etiquetaBuild[1]=new JLabel("Name");
        etiquetaBuild[4]=new JLabel("   Damage");
        etiquetaBuild[5]=new JLabel("Armor");
        etiquetaBuild[2]=new JLabel("Metal cost");
        etiquetaBuild[3]=new JLabel("Deuterium cost");
        etiquetaBuild[6]=new JLabel("  Quantity");
        etiquetaBuild[7]=new JLabel("  "); //cabecera boton build

        // BUILD SHIPS
        for (int i=0;i<5;i++) {
            labelsBuild[i]= new ArrayList<JLabel>();
        }
        for (int i=0;i<4;i++) {
            cst= ship.getInfoShips(con, i+1);
            try {
                cst.execute();
                labelsBuild[i].add(new JLabel(cst.getString(2)));// nombre
                labelsBuild[i].add(new JLabel(Integer.toString(cst.getInt(3))));//metal cost
                labelsBuild[i].add(new JLabel(Integer.toString(cst.getInt(5))));//deuterium cost
                int basedmg=cst.getInt(8);
                int basedef=cst.getInt(7);
           
                labelsBuild[i].add(buildTroopsatk[i]=new JLabel(Integer.toString(basedmg+ (planeta.getTechnologyAtack()*PLUS_ATTACK_MISSILELAUNCHER_BY_TECHNOLOGY)))); // dmg
                labelsBuild[i].add(buildTroopsdef[i]= new JLabel(Integer.toString(basedef+ (planeta.getTechnologyDefense()*PLUS_ARMOR_MISSILELAUNCHER_BY_TECHNOLOGY)))); // armor
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }}
        //------------------------------------------------------------

        // defensas:
        for (int i=0;i<8;i++) {
        	etiquetaBuild2[i]=new JLabel(etiquetaBuild[i].getText());
        }
        for (int i=0;i<3;i++) {
            labelsBuild2[i]= new ArrayList<JLabel>();
        }
        
        for (int i=0;i<3;i++) {
            cst= defe.getInfoDefense(con, i+1);
            try {
                cst.execute();
                labelsBuild2[i].add(new JLabel(cst.getString(2)));// nombre
                labelsBuild2[i].add(new JLabel("  "+Integer.toString(cst.getInt(3))));//metal cost
                labelsBuild2[i].add(new JLabel(Integer.toString(cst.getInt(5))));//deuterium cost
                int basedmg=cst.getInt(8);
                int basedef=cst.getInt(7);
           
                labelsBuild2[i].add(buildTroopsatk[i]=new JLabel(Integer.toString(basedmg+ (planeta.getTechnologyAtack()*PLUS_ATTACK_MISSILELAUNCHER_BY_TECHNOLOGY)))); // dmg
                labelsBuild2[i].add(buildTroopsdef[i]= new JLabel(Integer.toString(basedef+ (planeta.getTechnologyDefense()*PLUS_ARMOR_MISSILELAUNCHER_BY_TECHNOLOGY)))); // armor
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }}
        // for de inicializar textfield-------------------------------------------------prueba
        Image fondito = Toolkit.getDefaultToolkit().getImage("space.jpg");
        for (int i=0;i<buildText.length;i++) {
            buildText[i]=new JTextField("0",3);
            textoformatado[i]=new JPanelConFondo(fondito);
            textoformatado[i].setLayout(new GridLayout(3,1));
            textoformatado[i].add(new JLabel(" "));
            textoformatado[i].add(buildText[i]);
            textoformatado[i].add(new JLabel(" "));
        }
        
        for (int i=0;i<buildText2.length;i++) {
            buildText2[i]=new JTextField("0",3);
            textoformatado2[i]=new JPanelConFondo(fondito);
            textoformatado2[i].setLayout(new GridLayout(3,1));
            textoformatado2[i].add(new JLabel(" "));
            textoformatado2[i].add(buildText2[i]);
            textoformatado2[i].add(new JLabel(" "));
        }
        //botonP11=new JButton("REINICIAR");
        botonP12=new JButton("CONFIRMAR");
        deuterium=new JLabel("0");
        metal=new JLabel("0");

        metal.setForeground(Color.green);
        deuterium.setForeground(Color.green);
        //Abajo anyado botones con aspecto personalizado, para poder cambiar el tamanyo de la imagen he tenido que pasarlo a imagen y luego otra vez a icon
        ImageIcon btn = new ImageIcon("btn.png");
        Image trans = btn.getImage();
        Image newimg = trans.getScaledInstance(65, 65,  java.awt.Image.SCALE_SMOOTH);
        btn = new ImageIcon(newimg);
        for (int i=0;i<4;i++) {
            btnBuild[i] = new JButton("",btn);
            btnBuild[i].setPreferredSize( new Dimension(65, 65));
            btnBuild[i].setBorderPainted(false);
            btnBuild[i].setContentAreaFilled(false);
            btnBuild[i].setFocusPainted(false);
            btnBuild[i].setOpaque(false);
        }
        for (int i=0;i<3;i++) {
            btnBuild2[i] = new JButton("",btn);
            btnBuild2[i].setPreferredSize( new Dimension(65, 65));
            btnBuild2[i].setBorderPainted(false);
            btnBuild2[i].setContentAreaFilled(false);
            btnBuild2[i].setFocusPainted(false);
            btnBuild2[i].setOpaque(false);
        }
        //Label de upgrade

        etiquetaP3[0]=new JLabel("TECHNOLOGY ATTACK");

        etiquetaP3[1]=new JLabel("TECHNOLOGY DEFENSE");

        etiquetaP3[2]=new JLabel("COST");
        etiquetaP3[3]=new JLabel("LEVEL");

        etiquetaP3[4]= new JLabel("COST");
        etiquetaP3[5]=new JLabel("LEVEL");
        //abajo etiquetas que importan valores de la base de datos
        try {
            etiquetaP1[2]=new JLabel(Integer.toString(cst.getInt(1)));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //cambiar toda label de la array a color verde
        for (int i=1;i<etiquetaP1.length;i++) {
            etiquetaP1[i].setForeground(Color.GREEN);
        }
        for (int i=0;i<etiquetaBuild.length;i++) {
            etiquetaBuild[i].setForeground(Color.GREEN);
            etiquetaBuild2[i].setForeground(Color.GREEN);
        }
    }
    
    public void guardado(Connection con,String usuario) {
    	
    	UpdateGame upda=new UpdateGame(con,usu.getIdUser(con,usuario ) , IDplanetita, planeta.getTechnologyAtack(), planeta.getTechnologyDefense(), planeta.getMetal(), planeta.getDeuterium());
    	for (int w=0;w<4;w++) { //id nave
    		
    		for (int i=0;i<planeta.getTechnologyAtack()+1;i++) {
        		for (int j=0;j<planeta.getTechnologyDefense()+1;j++) {
        			int quantity=0;
        			for (int o =0;o<planeta.getArmy()[w].size();o++) {
        				MilitaryUnit shipAux=new LightHunter(j, i, con);
        				if (w==0) {
        					shipAux=new LightHunter(j, i, con);
        				}
        				else if (w==1) {
        					shipAux=new HeavyHunter(j, i, con);
        				}
        				else if (w==2) {
        					shipAux=new BattleShip(j, i, con);
        				}
        				else if(w==3) {
        					shipAux=new ArmoredShip(j, i, con);
        				}
        				if (planeta.getArmy()[w].get(o).attack()==shipAux.attack() && planeta.getArmy()[w].get(o).getActualArmor()==shipAux.getActualArmor()) {
        					quantity=quantity+1;
        				}
        				
        			}
        			upda.UpdateShips(con, IDplanetita, w+1, i, j, quantity);
        		}
        	}
    	}
    	for (int w=0;w<3;w++) { //id def
    		for (int i=0;i<planeta.getTechnologyAtack()+1;i++) {
        		for (int j=0;j<planeta.getTechnologyDefense()+1;j++) {
        			int quantity=0;
        			for (int o =0;o<planeta.getArmy()[w+4].size();o++) {
        				MilitaryUnit defAux=new MissileLauncher(j, i, con);
        				if (w==0) {
        					defAux=new MissileLauncher(j, i, con);
        				}
        				else if (w==1) {
        					defAux=new IonCannon(j, i, con);
        				}
        				else if (w==2) {
        					defAux=new PlasmaCannon(j, i, con);
        				}
        				if (planeta.getArmy()[w+4].get(o).attack()==defAux.attack() && planeta.getArmy()[w+4].get(o).getActualArmor()==defAux.getActualArmor()) {
        					quantity=quantity+1;
        				}
        				
        			}
        			upda.UpdateDefenses(con, IDplanetita, w+1, i, j, quantity);
        		}
        	}
    	}
    }


}
//Fondo
class JPanelConFondo extends JPanel {

    private Image imagen;

    //...

    @Override
    public void paint(Graphics g) {

        g.drawImage(imagen, 0, 0, getWidth(), getHeight(),
                this);

        setOpaque(false);
        super.paint(g);
    }
    public JPanelConFondo(Image imagenInicial) {
        if (imagenInicial != null) {
            imagen = imagenInicial;
        }

        //...
    }
}
