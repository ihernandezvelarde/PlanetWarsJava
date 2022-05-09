package PlanetWars;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class VentanaInicial extends JFrame {

    private JTabbedPane pestanyas;
    private JTabbedPane build;
    private JPanel panel2, panel3,panel4;
    private JPanel panelB1,panelB2,panelBp1,panelBp2,panelBp10;
    private JTextField[] textoP1= new JTextField[5];
    private Connection con;
    private JLabel[] etiquetaP1=new JLabel[5];
    private JPanel inp1;
    private JButton botonP11, botonP12;
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
        botonP11=new JButton("REINICIAR");
        botonP12=new JButton("CONFIRMAR");
        deuterium=new JLabel("0");
        metal=new JLabel("0");
        for (int i=1;i<etiquetaP1.length;i++) {
        	etiquetaP1[i].setForeground(Color.GREEN);
        }
        
    }
    public void reiniciarP1() {
        //Falta config
        textoP1[0].setText("");
        textoP1[1].setText("");
        textoP1[2].setText("0");
        textoP1[3].setText("0");
        textoP1[4].setText("1");
        etiquetaP1[0].setText(" ");
        etiquetaP1[1].setText(" ");
        etiquetaP1[2].setText("Dinero: ");
        etiquetaP1[3].setText("Puntos: ");
        etiquetaP1[4].setText("Nivel: ");
    }
    public void aumentadorRecursos(int metalito,int deuterito) {
    	metal.setText(String.valueOf(metalito));
    	deuterium.setText(String.valueOf(deuterito));
    }

    public VentanaInicial() {
        inicializarTextos();
        this.setSize(728, 455);
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
        panelB1.add(botonP11);
        
        

        // Tocar estos grid layouts para agregar cosas
        panelB1.setLayout(new GridLayout(4,2));
        panelB2.setLayout(new GridLayout(2,2));

        // arriba tocar
        etiquetaP1[0].setPreferredSize(new Dimension(700,50));
        
        panelB2.add(etiquetaP1[1]);
        panelB2.add(botonP12);
        panelB2.add(etiquetaP1[3]);
        panelB2.add(etiquetaP1[4]);
        panelB1.add(etiquetaP1[2]);

        //NO TOCAR ABAJO
        panelBp10.add(panelBp1);

        panelBp10.add(etiquetaP1[0]);
        pestanyas=new JTabbedPane();
        build=new JTabbedPane();
        panelBp1.add(panelB1);
        panelBp2.add(panelB2);

        inp1=new JPanelConFondo(imagen);
        
        inp1.setLayout(new FlowLayout());
        metal.setForeground(Color.green);
        deuterium.setForeground(Color.green);
        inp1.add(metal);
        inp1.add(deuterium);
        
        
        
        
        pestanyas.addTab("PLANET STATS",inp1);
        pestanyas.addTab("BUILD",panel2);
        pestanyas.addTab("UPGRADE TECHNOLOGY",panel3);
        pestanyas.addTab("VIEW BATTLE REPORTS",panel4);
        
        pestanyas.setForeground(Color.BLUE);
        pestanyas.setBackground(Color.BLACK);
        
        build.addTab("TROOPS", panelBp10);
        build.addTab("DEFENSES", panelBp2);
        
        build.setForeground(Color.BLUE);
        build.setBackground(Color.BLACK);

        panel2.add(build);
        this.add(pestanyas);

        //Panel View Battle Reports
        
        

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

        //PANEL1 pestanya 1:
		/*
		botonP11.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reiniciarP1();
			}
		});  */
        
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
class VentanaLogin extends JFrame{
    private JLabel titulo,nom,pws;
    private JTextField nombre,password;

    VentanaLogin(){
        iniciar();

        JLabel titulo = new JLabel("Planet Wars");

        JLabel nom = new JLabel("Nombre: ");
        JLabel pwd = new JLabel("Contraseña: ");
    }

    public void iniciar(){
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,400);
        setLocationRelativeTo(null);
    }
}


