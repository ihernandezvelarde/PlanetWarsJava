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
    private JPanel panelFinalBattleReports,panelFinalUpgrade; //paneles finales donde estara el contenido

    private JPanel panelFinalP1,panelFinalP2,panelFinalP3,panelFinalP4; // Paneles Inp1

    private JTextField[] textoP1= new JTextField[5];
    private JLabel[] etiquetaP1=new JLabel[8];
    // build
    private JButton[] btnBuild=new JButton[5];
    private JLabel[] etiquetaBuild=new JLabel[8];
    private JLabel[] etiquetaBuild2=new JLabel[8];
    private JLabel[] etiquetaInfoBuild=new JLabel[5];
    private ArrayList<JLabel>[] labelsBuild=new ArrayList[5];
    private JTextField[] buildText=new JTextField[4];
    private JTextField[] buildText2=new JTextField[3];
    private JLabel[] images=new JLabel[7];
    private JPanel[] textoformatado=new JPanel[4];

    private JButton botonP11, botonP12,salir;

    private JLabel deuterium,metal;

    private CallableStatement cst;
    InfoShips ship= new InfoShips();
    InfoUsers usu= new InfoUsers();
    InfoPlanet plane= new InfoPlanet();

    private int deuterito,metalito;

    private int deftech;
    private int atktech;

    private JLabel attackTech,defenseTech;
    private JLabel planetGIF;


    public void aumentadorRecursos(int metalito,int deuterito) {
        metal.setText(String.valueOf(metalito));
        deuterium.setText(String.valueOf(deuterito));
    }

    public VentanaInicial(Connection con,String usuario) {

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
        pestanyas.addTab("SALIR",salir);

        pestanyas.setForeground(Color.BLUE);
        pestanyas.setBackground(Color.BLACK);

        build.addTab("TROOPS", panelBp10);
        build.addTab("DEFENSES", panelBp2);

        build.setForeground(Color.BLUE);
        build.setBackground(Color.BLACK);

        panel2.add(build);
        this.add(pestanyas);

        //boton salir del jpane, cuando el index es el de la pestanya salir, salimos del programa

        pestanyas.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                // TODO Auto-generated method stub
                if (pestanyas.getSelectedIndex()==4) {
                    pestanyas.setSelectedIndex(0);
                    int opc=JOptionPane.showOptionDialog(rootPane,"ESTAS SEGURO QUE QUIERES SALIR?","Exit",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[] {"SI","No"}, "No");
                    switch (opc) {
                        case 0:
                            System.exit(EXIT_ON_CLOSE);
                            break;}}}
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
            planetGIF = new JLabel(new ImageIcon("planet_rotate.gif"));
            etiquetaP1[7].setForeground(Color.white);etiquetaP1[7].setFont(new Font("Arial", Font.BOLD, 30));

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
            deuterium = new JLabel("0");
            metal = new JLabel("0");
            metal.setForeground(Color.black);
            deuterium.setForeground(Color.black);

            // Anyadir elementos
            resources.add(buildMtl);resources.add(metal);
            resources.add(buildDtm);resources.add(deuterium);

            JPanel technology = new JPanel(new GridLayout(2,3));technology.setBackground(new Color(247,235,232));
            technology.setPreferredSize(new Dimension(275,100));
            technology.setBorder(BorderFactory.createBevelBorder(1,new Color(229,75,75),new Color(30,30,36)));

            attackTech = new JLabel(String.valueOf(atktech));
            defenseTech = new JLabel(String.valueOf(deftech));
            JLabel buildTechAtk = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("techAtk.png").getScaledInstance(35,35,Image.SCALE_DEFAULT)));
            JLabel buildTechDef = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("techDef.png").getScaledInstance(35,35,Image.SCALE_DEFAULT)));
            etiquetaP1[5].setForeground(Color.black);attackTech.setForeground(new Color(255,169,135));
            etiquetaP1[6].setForeground(Color.black);defenseTech.setForeground(new Color(255,169,135));

            technology.add(buildTechAtk);technology.add(etiquetaP1[5]);technology.add(attackTech);
            technology.add(buildTechDef);technology.add(etiquetaP1[6]);technology.add(defenseTech);

            panelFinalP2.add(resources);
            panelFinalP2.add(technology);

        panelFinalP3 = new JPanel();panelFinalP2.setOpaque(false);

            JLabel fleet = new JLabel();
            panelFinalP3.setBorder(BorderFactory.createBevelBorder(1,new Color(229,75,75),new Color(30,30,36)));
            panelFinalP3.setBackground(new Color(247,235,232));

        panelFinalP4 = new JPanel();
            panelFinalP4.setBorder(BorderFactory.createBevelBorder(1,new Color(229,75,75),new Color(30,30,36)));
            panelFinalP4.setBackground(new Color(247,235,232));



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

        //------------------------------------------------------------------------------------------------

        for (int i=0;i<etiquetaBuild.length;i++) {panelB1.add(etiquetaBuild[i]);}

        // Declaracion de jlabels build
        InfoShips ship=new InfoShips();
        cst= ship.getInfoShips(con, 1);
        try {cst.execute();}
        catch (SQLException e1) {e1.printStackTrace();}
        //------------------------------------------------------------ prueba
        for (int l=0;l<6;l++) {images[l]=new JLabel();}

        images[0]=buildLight;images[1]=buildHeavy;images[2]=buildArmored;
        images[3]=buildBattle;images[4]=buildIon;images[5]=buildPlasma;
        images[6]=buildMissile;

        for (int q=0;q<4;q++) {
            panelB1.add(images[q]);
            for (int k=0;k<5;k++) {
                panelB1.add(labelsBuild[q].get(k));
                labelsBuild[q].get(k).setForeground(Color.WHITE);
            }panelB1.add(textoformatado[q]);panelB1.add(btnBuild[q]);}

        // Botones tropa build
        for(int i = 0;i<4;i++){
            btnBuild[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Funciona")
                    ;}
            });
        }

        // Defenses
        panelB2.add(etiquetaP1[1]);
        panelB2.add(botonP12);
        panelB2.add(etiquetaP1[3]);
        panelB2.add(etiquetaP1[4]);


        //pestanya 3 technology-------------------------------------------------------------------------------------------------------===============

        //pestanya 4 battle reports------------------------------------------------------------------------------------------------------=================



        this.setVisible(true);
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

        // iamgen,nombre,armor,dmg, cose metal, coste deuterio, texfield, boton
        etiquetaBuild[0]=new JLabel(" "); // cabecera imagen
        etiquetaBuild[1]=new JLabel("Name");
        etiquetaBuild[2]=new JLabel("Damage");
        etiquetaBuild[3]=new JLabel("Armor");
        etiquetaBuild[4]=new JLabel("Metal cost");
        etiquetaBuild[5]=new JLabel("Deuterium cost");
        etiquetaBuild[6]=new JLabel("  Quantity");
        etiquetaBuild[7]=new JLabel("  "); //cabecera boton build

        // pruebas
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

                //---------------------------------------------------------------------------------MODIFICAR
                cst=usu.getInfoPlanetId(con, usuari);
                cst.execute();
                int planetita=cst.getInt(2);
                cst=plane.getInfoPlanet(con, planetita);
                cst.execute();
                int atktech=cst.getInt(3);
                int deftech=cst.getInt(4);

                labelsBuild[i].add(new JLabel(Integer.toString(basedef+ (deftech*PLUS_ARMOR_MISSILELAUNCHER_BY_TECHNOLOGY)))); // armor
                labelsBuild[i].add(new JLabel(Integer.toString(basedmg+ (atktech*PLUS_ATTACK_MISSILELAUNCHER_BY_TECHNOLOGY)))); // dmg
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }}
        //------------------------------------------------------------

        // defensas:

        // for de inicializar textfield-------------------------------------------------prueba
        Image fondito = Toolkit.getDefaultToolkit().getImage("space.jpg");
        for (int i=0;i<buildText.length;i++) {
            buildText[i]=new JTextField(3);
            textoformatado[i]=new JPanelConFondo(fondito);
            textoformatado[i].setLayout(new GridLayout(3,1));
            textoformatado[i].add(new JLabel(" "));
            textoformatado[i].add(buildText[i]);
            textoformatado[i].add(new JLabel(" "));
        }
        for (int i=0;i<buildText2.length;i++) {
            buildText2[i]=new JTextField();
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
