package PlanetWars;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class VentanaInicial extends JFrame {

    private JTabbedPane pestanyas;
    private JTabbedPane build;
    private JPanel panel2, panel3,panel4;
    private JPanel panelB1,panelB2,panelBp1,panelBp2,panelBp10;
    private JTextField[] textoP1= new JTextField[5];
    private Connection con;
    private JLabel[] etiquetaP1=new JLabel[5];
    private JLabel[] etiquetaBuild=new JLabel[5];
    private JPanel inp1;
    private JButton botonP11, botonP12,salir;
    private JButton[] btnBuild=new JButton[5];
    private JLabel deuterium,metal;
    //private Image background;

    public void conectar() {
        String url = "jdbc:mySQL://localhost/farmville?serverTimezone=UTC";
        String password = "root";
        String user = "root";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver cargado correctamente.");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Conexion creada!!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Jo tio");
        }
    }

    public void inicializarTextos() {
        //Falta config
        textoP1[0]=new JTextField("");
        textoP1[1]=new JTextField("");
        textoP1[2]=new JTextField("0");
        textoP1[3]=new JTextField("0");
        textoP1[4]=new JTextField("1");
        etiquetaP1[0]=new JLabel(" ");
        etiquetaP1[1]=new JLabel("Descripcion: ");
        etiquetaP1[2]=new JLabel("Dinero: ");
        etiquetaP1[3]=new JLabel("Puntos: ");
        etiquetaP1[4]=new JLabel("Nivel: ");
        etiquetaBuild[0]=new JLabel(" ");
        etiquetaBuild[1]=new JLabel(" ");
        etiquetaBuild[2]=new JLabel(" ");
        etiquetaBuild[3]=new JLabel(" ");
        etiquetaBuild[4]=new JLabel(" ");
        //botonP11=new JButton("REINICIAR");
        botonP12=new JButton("CONFIRMAR");
        deuterium=new JLabel("0");
        metal=new JLabel("0");
        for (int i=1;i<etiquetaP1.length;i++) {
        	etiquetaP1[i].setForeground(Color.GREEN);
        }
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
    }
    
    public void aumentadorRecursos(int metalito,int deuterito) {
    	metal.setText(String.valueOf(metalito));
    	deuterium.setText(String.valueOf(deuterito));
    }

    public VentanaInicial(Connection con) {
        inicializarTextos();
        this.setSize(728, 455);
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
        
        // Tocar estos grid layouts para agregar cosas
        panelB1.setLayout(new GridLayout(4,2));
        panelB2.setLayout(new GridLayout(2,2));

        // arriba tocar
        etiquetaP1[0].setPreferredSize(new Dimension(700,50));
        //etiqueta gigante para redimensionar el layout y que el jtabbedpane salga bien
        
        

        //NO TOCAR ABAJO
        panelBp10.add(panelBp1);

        panelBp10.add(etiquetaP1[0]);
        pestanyas=new JTabbedPane();
        build=new JTabbedPane();
        panelBp1.add(panelB1);
        panelBp2.add(panelB2);

        inp1=new JPanelConFondo(imagen);
        
        inp1.setLayout(new FlowLayout());
        
        inp1.add(metal);
        inp1.add(deuterium);
        
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
					int opc=JOptionPane.showOptionDialog(rootPane,"¿ESTAS SEGURO QUE QUIERES SALIR?","Exit",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[] {"SI","No"}, "No");
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
      //pestanya 1 Planet Stats-------------------------------------------------------------------------------------------
        
        
        // pestanya 2 BUILD----------------------------------------------------------------------------------------------------
        //troops
        panelB1.add(etiquetaP1[2]);
        btnBuild[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Funciona");
				
			}
		});
        panelB1.add(btnBuild[0]);
        //defenses
        panelB2.add(etiquetaP1[1]);
        panelB2.add(botonP12);
        panelB2.add(etiquetaP1[3]);
        panelB2.add(etiquetaP1[4]);
        
        
      //pestanya 3 technology-------------------------------------------------------------------------------------------------------
        
      //pestanya 4 battle reports------------------------------------------------------------------------------------------------------

        
                
        this.setVisible(true);
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
