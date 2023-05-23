package Seleccion_MembresiaCod;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import registro.Formulario;
import javax.swing.SwingConstants;
import inicioSesion.Inicio_Sesion;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
/**
 * En la página de Selección de Membresía podrás elegir el contenido audiovisual (Películas,Seria o Música) como tu suscripción mensual.
 * @param contentPane CONTIENE TODOS LOS ATRIBUTOS DE LA PÁGINA.
 * @param panel_contenedor CONTIENE TODOS LOS ATRIBUTOS PARA PODER INTERCAMBIAR PANELES SIN COMPLICACIÓN.
 * @param lbl_BotonDiamante BOTÓN DIAMANTE PREMIUM
 * @param lbl_BotonZafiro BOTÓN ZAFIRO STANDAR
 * @param lbl_BotonRubi BOTÓN RUBÍ STANDAR
 * @param lbl_BotonCristal BOTÓN CRISTAL BASIC
 * @param lbl_Logo_diamante LOGO QUE DEFINE LA TARIFA DIAMANTE PREMIUM(ESTÉTICO).
 * @param lbl_Logo_zafiro LOGO QUE DEFINE LA TARIFA ZAFIRO STANDAR(ESTÉTICO).
 * @param lbl_Logo_rubi LOGO QUE DEFINE LA TARIFA RUBI STANDAR(ESTÉTICO).
 * @param lbl_Logo_basic LOGO QUE DEFINE LA TARIFA CRISTAL BASIC(ESTÉTICO).
 * @param diamante_texto2 DESCRIPCIÓN DE LA TARIFA DIAMANTE PREMIUM(ESTÉTICO).
 * @param zafiro_texto2 DESCRIPCIÓN DE LA TARIFA ZAFIRO STANDAR(ESTÉTICO).
 * @param rubi_texto2 DESCRIPCIÓN DE LA TARIFA RUBÍ STANDAR(ESTÉTICO).
 * @param cristal_texto2 DESCRIPCIÓN DE LA TARIFA CRISTAL BASIC(ESTÉTICO).
 * @param lbl_diamante_precio PRECIO DE LA TARIFA DIAMANTE PREMIUM(ESTÉTICO).
 * @param lbl_zafiro_precio PRECIO DE LA TARIFA ZAFIRO STANDAR(ESTÉTICO).
 * @param lbl_rubi_precio PRECIO DE LA TARIFA RUBÍ STANDAR(ESTÉTICO).
 * @param lbl_Cristal_precio PRECIO DE LA TARIFA CRISTAL BASIC(ESTÉTICO).
 * @param btn_Salida ES EL BOTÓN SALIDA DE LA PÁGINA.
 * @param lbl_Logo ES NUESTRO LOGO TRILOGY(ESTÉTICO).
 * @param lblBordeSuperior ES LA BARRA VERDE SUPERIOR(ESTÉTICO).
 * @param lbl_DiamantePremium FONDO DE LA TARIFA DE DIAMANTE PREMIUM(ESTÉTICO).
 * @param lbl_ZafiroStandar FONDO DE LA TARIFA DE ZAFIRO STANDAR(ESTÉTICO).
 * @param lbl_RubiStandar FONDO DE LA TARIFA DE RUBÍ STANDAR(ESTÉTICO).
 * @param lbl_CristalBasic FONDO DE LA TARIFA DE CRISTAL BASIC(ESTÉTICO).
 * @param lblFondo_Verde TIENE LA IMAGEN DE FONDO DE LA PÁGINA.
 * @author TRILOGY. Miembros: Adrian Leal Vacas, Gonzalo Amo Cano y Raul Senso Montojo.
 */
public class Seleccion_Membresia extends JFrame {
	/**
	 * Es la versión número 1 de la página del contenido audiovisual del Selección Membresía.
	 */
	private static final long serialVersionUID = 1L;
	// ----------------------------------------------------------------------------------------
	// ATRIBUTOS
	// ----------------------------------------------------------------------------------------
	private JPanel contentPane;
	private JLabel lbl_BotonDiamante;
	private JLabel lbl_BotonZafiro;
	private JLabel lbl_BotonRubi;
	private JLabel lbl_BotonCristal;
	private JButton btn_Salida;
	private JLabel lbl_Logo_diamante;
	private JLabel lbl_Logo_zafiro;
	private JLabel lbl_Logo_basic;
	private JLabel lbl_Logo_rubi;
	private JLabel diamante_texto2;
	private JLabel zafiro_texto2;
	private JLabel rubi_texto2;
	private JLabel cristal_texto2;
	private JLabel lbl_diamante_precio;
	private JLabel lbl_zafiro_precio;
	private JLabel lbl_rubi_precio;
	private JLabel lbl_Cristal_precio;
	private JLabel lbl_Logo;
	private JLabel lblBordeSuperior;
	private JLabel lbl_DiamantePremium;
	private JLabel lbl_ZafiroStandar;
	private JLabel lbl_RubiStandar;
	private JLabel lbl_CristalBasic;
	private JLabel lblFondo_Verde;
	// ----------------------------------------------------------------------------------------
	// ATRIBUTOS U OBJETOS NECESARIOS PARA LA CONEXIÓN A LA BASE DE DATOS Y LA REALIZACIÓN DE UN CONSULTA BÁSICA
	// ----------------------------------------------------------------------------------------
	private static String bd="XE"; // NOMBRE DE LA BASE DE DATOS POR DEFECTO, SIEMPRE DEJAR EL "XE"
	private static String login="TRILOGY"; // USUARIO DE LA BBDD
	private static String password="TRILOGY"; // CONTRASEÑA DE LA BBDD
	// ----------------------------------------------------------------------------------------
	// RUTA DE SERVICIO
	// ----------------------------------------------------------------------------------------
	private static String url="jdbc:oracle:thin:@localhost:1521:"+bd;
	// ----------------------------------------------------------------------------------------
	// PONEMOS LOS OBJETOS A NULL Y SIN INTANCIAR
	// ----------------------------------------------------------------------------------------
	static Connection connection=null; // CONEXIÓN A LA BASE DE DATOS
	static Statement st; // PARA REALIZAR SQL ESTATICAS (HAY QUE ENLAZARLA SIEMPRE CON EL "Connection" SINO NO FUNCIONA)
	static ResultSet rs; // PARA REALIZAR LA CONSULTA IGUAL QUE EN SQL DEVELOPER
	// ----------------------------------------------------------------------------------------
	// MÉTODO MAIN
	// ----------------------------------------------------------------------------------------
	/**
	 * En el método main ejecutamos la ventana principal Seleccion_Membresia.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Seleccion_Membresia frame = new Seleccion_Membresia();
					frame.setVisible(true);
					conectar();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "ERROR. No se ha podido acceder a la página de Selección de Membresía.");
				}
			}
		});
	}
	// ------------------------------------------------------------------------------------------------------
	// MÉTODOS
	// ------------------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------------------
	// MÉTODO PARA CONECTAR A LA BASE DE DATOS
	// ------------------------------------------------------------------------------------------------------
	/**
	 * El método conectar() realiza una conexión a la base de datos.
	 */
	public static void conectar() throws Exception{
		// DRIVER PARA ORACLE
		Class.forName("oracle.jdbc.driver.OracleDriver"); // EL DRIVER DEL JDBC SIEMPRE ES EL MISMO QUE ESTÁ PUESTO
		connection=DriverManager.getConnection(url,login,password); // NOS CONECTAMOS A LA BASE DE DATOS CON LA URL, LOGIN Y EL PASSWORD QUE PREVIAMENTE PUSIMOS EN LOS ATRIBUTOS
	}
	// ------------------------------------------------------------------------------------------------------
	// MÉTODO PARA CERRAR LA BASE DE DATOS
	// ------------------------------------------------------------------------------------------------------
	/**
	 * El método cerrar() cierra la base de datos.
	 */
	public static void cerrar() throws SQLException{
		// -----------------------------------------------------------------------------------------
		// SIEMPRE EN EL MISMO ORDEN SINO DA FALLO
		// -----------------------------------------------------------------------------------------
		if (rs!=null) rs.close(); // CERRAMOS EL RS SI ES DIFERENTE AL NULL (FUNCIONANDO)
		if (st!=null) st.close(); // CERRAMOS EL ST SI ES DIFERENTE AL NULL (FUNCIONANDO)
		if (connection!=null) connection.close(); // CERRAMOS EL connection SI ES DIFERENTE AL NULL (FUNCIONANDO)
	}
	// ------------------------------------------------------------------------------------------------------
	// MÉTODO PARA CALCULAR EL ID DE LOS USUARIOS
	// ------------------------------------------------------------------------------------------------------
	/**
	 * El metodo calcular_ID_Ususarios() calcula el próximo ID del usuario.
	 * @return devuelve el ID del usuario
	 * @throws SQLException
	 */
	public static int calcular_ID_Ususarios() throws SQLException {
		int num_idusuario=0;
		// NOS CONECTAMOS A LA BASE DE DATOS
		st=connection.createStatement();
		// REALIZAMOS EL SELECT CON LOS DATOS QUE QUEREMOS ALMACENAR (? --> ES UN CAMPO QUE LO VA A ADQUIRIR DE LA VARIABLE correos) (SIRVE PARA CREAR LA SENTENCIA SQL)
		PreparedStatement statement = connection.prepareStatement("select max(ID_USUARIO) from USUARIOS");
		// EJECUTAMOS LA QUERY ANTERIOR
		rs = statement.executeQuery();
		// PARA RECORER LAS LÍNEAS QUE NOS SALGA EN LA SENTENCIA QUERY ANTERIOR
		// MIENTRAS QUE HAYA SIGUIENTE
		while (rs.next()) {
			// ALMACENA EL DATO DE LA COLUMNA NOMBRE EN LA VARIABLE nombre
			num_idusuario = rs.getInt(1);
		}
		return num_idusuario+=1;
	}
	// ----------------------------------------------------------------------------------------
	// CONSTRUCTOR
	// ----------------------------------------------------------------------------------------
	/**
	 * En el constructor Seleccion_Membresia() están todos los atributos y métodos necesarios para cuando creemos el objeto y tenga todas las funcionalidades.
	 */
	public Seleccion_Membresia() {
		setResizable(false);
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 977, 703);
		contentPane = new JPanel();
		//------------------------------------------------------------------------------------------------------------	
		//PANEL SELECCIÓN MEMBRESÍA
		//------------------------------------------------------------------------------------------------------------	
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//------------------------------------------------------------------------------------------------------------	
		// Panel Administrador
		//------------------------------------------------------------------------------------------------------------	
		JPanel Panel_Seleccion_Membresia = new JPanel();
		Panel_Seleccion_Membresia.setBounds(0, 0, 961, 664);
		contentPane.add(Panel_Seleccion_Membresia);
		Panel_Seleccion_Membresia.setLayout(null);
		//--------------------------------------------------------------------------------------------------------
		// btn_Salida ES EL BOTÓN SALIDA	
		//--------------------------------------------------------------------------------------------------------
		btn_Salida = new JButton("");
		btn_Salida.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_Salida.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_Salida.setContentAreaFilled(true);
				btn_Salida.setBackground(new Color(153,255,153));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btn_Salida.setContentAreaFilled(false);
				btn_Salida.setBackground(new Color(32,171,85));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					cerrar();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "ERROR. No se ha podido cerrar la base de datos. Perdone las molestias.");
				}
				System.exit(0);
			}
		});
		btn_Salida.setContentAreaFilled(false);
		btn_Salida.setBorderPainted(false);
		btn_Salida.setBounds(10, 603, 50, 50);
		//AJUSTAR LA IMAGEN AL BOTÓN
		ImageIcon salida = new ImageIcon(getClass().getResource("/Trilogy_imagenes/IconoLogout.png"));// DIRECCIÓN DE LA IMAGEN QUE QUIERO AJUSTAR
		ImageIcon imgsalida = new ImageIcon(salida.getImage().getScaledInstance(btn_Salida.getWidth(), btn_Salida.getHeight(), Image.SCALE_SMOOTH)); // CREAMOS OTRO OBJETO PARA QUE SE AJUSTE AUTOMÁTICAMENTE LA IMAGEN AL LABEL
		btn_Salida.setIcon(imgsalida);
		//---------------------------------------------------------------------------------------------------------
		//BOTÓN DE ELECCIÓN
		//---------------------------------------------------------------------------------------------------------		
		//---------------------------------------------------------------------------------------------------------
		//lbl_BotonDiamante BOTÓN DIAMANTE PREMIUM
		//---------------------------------------------------------------------------------------------------------		
		//FONDO TARIFA		
		lbl_DiamantePremium = new JLabel();
		lbl_DiamantePremium.setBounds(733, 229, 203, 353);
		//AJUSTAR IMAGEN AL LABEL
		ImageIcon diamante = new ImageIcon(getClass().getResource("/Trilogy_imagenes/Fondo_Diamante_Premium_Seleccion.jpg"));// DIRECCIÓN DE LA IMAGEN QUE QUIERO AJUSTAR
		ImageIcon imgfondodiamante = new ImageIcon(diamante.getImage().getScaledInstance(lbl_DiamantePremium.getWidth(), lbl_DiamantePremium.getHeight(), Image.SCALE_SMOOTH)); // CREAMOS OTRO OBJETO PARA QUE SE AJUSTE AUTOMÁTICAMENTE LA IMAGEN AL LABEL
		lbl_DiamantePremium.setIcon(imgfondodiamante);	
		//PRECIO
		lbl_diamante_precio = new JLabel("9,99€/MES");
		lbl_diamante_precio.setForeground(new Color(153, 255, 153));
		lbl_diamante_precio.setBounds(733, 550, 203, 20);
		lbl_diamante_precio.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_diamante_precio.setFont(new Font("Copperplate", Font.BOLD, 25));
		lbl_diamante_precio.setBorder(null);
		lbl_diamante_precio.setBackground(new Color (0,0,0,0));
		//DESCRIPCIÓN TARIFA
		diamante_texto2 = new JLabel("<html><p style=\"color: #000000;\">• Sin Anuncios<br>" 
				+ "• Toda la música<br>" 
				+ "• Películas<br>" 
				+ "• Series</p>" );
		diamante_texto2.setBounds(745, 241, 179, 102);
		diamante_texto2.setFont(new Font("Copperplate", Font.BOLD, 16));
		diamante_texto2.setBorder(BorderFactory.createLineBorder(Color.black,1));
		//BOTÓN ENCIMA DE TODA LA TARIFA
		lbl_BotonDiamante = new JLabel();
		lbl_BotonDiamante.setBorder(BorderFactory.createLineBorder(Color.black,1));
		lbl_BotonDiamante.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lbl_BotonDiamante.setBorder(BorderFactory.createLineBorder(Color.ORANGE,4));	
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lbl_BotonDiamante.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					conectar();
				} catch (Exception e3) {
					JOptionPane.showMessageDialog(null, "ERROR. No se ha podido conectar a la base de datos. Perdone por las molestias."); // MENSAJE QUE SE LE MUESTRA AL USUARIO
				}
				int membresias=4;
				// ID DE LOS USUARIOS QUE IRÁ AUMENTANDO
				int num_idusuario=0;
				// TRATANDO LA EXCEPCIÓN QUE LANZA EL MÉTODO PARA CALCULAR EL ID DEL USUARIOS
				try {
					num_idusuario = calcular_ID_Ususarios();
					System.out.println(num_idusuario);
				} catch (SQLException e2) {
					JOptionPane.showMessageDialog(null, "ERROR. No he podido comprobar el ID del usuarios. Perdone por las molestias.");
				}
				// INSERTAMOS EL USUARIOS EN LA TABLA USUARIOS DE LA BASE DE DATOS
				try {
					Connection conn = DriverManager.getConnection(url, login, password);
					String sql = "INSERT INTO USUARIOS (ID_USUARIO,CONTRASENA,CORREO,NOMBRE,APELLIDOS,DIRECCION,N_TELEFONO) VALUES (?,?,?,?,?,?,?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, num_idusuario);
					pstmt.setString(2, Formulario.passwd);
					pstmt.setString(3, Formulario.correo);
					pstmt.setString(4, Formulario.nombre);
					pstmt.setString(5, Formulario.apellidos);
					pstmt.setString(6, Formulario.calle);
					pstmt.setString(7, Formulario.num_telefono);
					pstmt.executeUpdate();
					pstmt.close();
					conn.close();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "ERROR. No he podido dar de alta. Perdone por las molestias.");
				}
				// INSERTAMOS EL USUARIOS EN LA TABLA CON_MEMBRESIAS DE LA BASE DE DATOS
				try {
					Connection conn = DriverManager.getConnection(url, login, password);
					String sql = "INSERT INTO CON_MEMBRESIAS VALUES (?,?,?,?,?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, num_idusuario);
					pstmt.setString(2, Formulario.num_tarjeta);
					pstmt.setString(3, Formulario.fecha_caducidad); // este es date
					pstmt.setString(4, Formulario.tipo_tarjeta);
					pstmt.setInt(5, membresias);
					pstmt.executeUpdate();
					pstmt.close();
					conn.close();
					JOptionPane.showMessageDialog(null, "Usuario insertado correctamente");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "ERROR. No he podido dar de alta. Perdone por las molestias.");
				}
				try {
					cerrar();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "ERROR. No se ha podido cerrar la base de datos. Perdone por las molestias.");
				}
				Inicio_Sesion inicioSesion = new Inicio_Sesion();
				inicioSesion.setVisible(true);
				dispose();
			}
		});
		lbl_BotonDiamante.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lbl_BotonDiamante.setBounds(733, 229, 203, 353);
		//IMAGEN QUE DEFINE LA TARIFA	
		lbl_Logo_diamante = new JLabel();
		lbl_Logo_diamante.setBounds(780, 390, 124, 97);
		ImageIcon logo_d = new ImageIcon(getClass().getResource("/Trilogy_imagenes/Diamante_Premium.JPG"));// DIRECCIÓN DE LA IMAGEN QUE QUIERO AJUSTAR
		ImageIcon imgfondologodiamante = new ImageIcon(logo_d.getImage().getScaledInstance(lbl_Logo_diamante.getWidth(), lbl_Logo_diamante.getHeight(), Image.SCALE_SMOOTH)); // CREAMOS OTRO OBJETO PARA QUE SE AJUSTE AUTOMÁTICAMENTE LA IMAGEN AL LABEL
		lbl_Logo_diamante.setIcon(imgfondologodiamante);
		//---------------------------------------------------------------------------------------------------------
		//lbl_BotonZafiro BOTÓN ZAFIRO STANDAR
		//---------------------------------------------------------------------------------------------------------
		//FONDO TARIFA
		lbl_ZafiroStandar = new JLabel();
		lbl_ZafiroStandar.setBounds(499, 229, 203, 353);
		ImageIcon zafiro = new ImageIcon(getClass().getResource("/Trilogy_imagenes/Fondo_Zafiro_Standar_Seleccion.jpg"));// DIRECCIÓN DE LA IMAGEN QUE QUIERO AJUSTAR
		ImageIcon imgfondozafiro = new ImageIcon(zafiro.getImage().getScaledInstance(lbl_ZafiroStandar.getWidth(),lbl_ZafiroStandar.getHeight(), Image.SCALE_SMOOTH)); // CREAMOS OTRO OBJETO PARA QUE SE AJUSTE AUTOMÁTICAMENTE LA IMAGEN AL LABEL
		lbl_ZafiroStandar.setIcon(imgfondozafiro);
		//PRECIO
		lbl_zafiro_precio = new JLabel("5.99€/MES");
		lbl_zafiro_precio.setForeground(new Color(153, 255, 153));
		lbl_zafiro_precio.setBounds(499, 550, 203, 20);
		lbl_zafiro_precio.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_zafiro_precio.setFont(new Font("Copperplate", Font.BOLD, 25));
		lbl_zafiro_precio.setBorder(null);
		lbl_zafiro_precio.setBackground(new Color (0,0,0,0));	
		//DESCRIPCIÓN TARIFA
		zafiro_texto2 = new JLabel("<html><p style=\"color: #000000;\">• Sin Anuncios<br>" 
				+ "• Toda la música<br>" 
				+ "• Series</p>" );
		zafiro_texto2.setBounds(510, 241, 179, 102);
		zafiro_texto2.setFont(new Font("Copperplate", Font.BOLD, 16));
		zafiro_texto2.setBorder(BorderFactory.createLineBorder(Color.black,1));				
		//BOTÓN ENCIMA DE TODA LA TARIFA
		lbl_BotonZafiro = new JLabel();
		lbl_BotonZafiro.setBorder(BorderFactory.createLineBorder(Color.black,1));
		lbl_BotonZafiro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lbl_BotonZafiro.setBorder(BorderFactory.createLineBorder(Color.ORANGE,4));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lbl_BotonZafiro.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));	
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					conectar();
				} catch (Exception e3) {
					JOptionPane.showMessageDialog(null, "ERROR. No se ha podido conectar a la base de datos. Perdone por las molestias."); // MENSAJE QUE SE LE MUESTRA AL USUARIO
				}
				int membresias=2;
				// ID DE LOS USUARIOS QUE IRÁ AUMENTANDO
				int num_idusuario=0;
				// TRATANDO LA EXCEPCIÓN QUE LANZA EL MÉTODO PARA CALCULAR EL ID DEL USUARIOS
				try {
					num_idusuario = calcular_ID_Ususarios();
				} catch (SQLException e2) {
					JOptionPane.showMessageDialog(null, "ERROR. No he podido comprobar el ID del usuarios. Perdone por las molestias.");
				}
				// INSERTAMOS EL USUARIOS EN LA TABLA USUARIOS DE LA BASE DE DATOS
				try {
					Connection conn = DriverManager.getConnection(url, login, password);
					String sql = "INSERT INTO USUARIOS (ID_USUARIO,CONTRASENA,CORREO,NOMBRE,APELLIDOS,DIRECCION,N_TELEFONO) VALUES (?,?,?,?,?,?,?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, num_idusuario);
					pstmt.setString(2, Formulario.passwd);
					pstmt.setString(3, Formulario.correo);
					pstmt.setString(4, Formulario.nombre);
					pstmt.setString(5, Formulario.apellidos);
					pstmt.setString(6, Formulario.calle);
					pstmt.setString(7, Formulario.num_telefono);
					pstmt.executeUpdate();
					pstmt.close();
					conn.close();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "ERROR. No he podido dar de alta. Perdone por las molestias.");
				}
				// INSERTAMOS EL USUARIOS EN LA TABLA CON_MEMBRESIAS DE LA BASE DE DATOS
				try {
					Connection conn = DriverManager.getConnection(url, login, password);
					String sql = "INSERT INTO CON_MEMBRESIAS VALUES (?,?,?,?,?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, num_idusuario);
					pstmt.setString(2, Formulario.num_tarjeta);
					pstmt.setString(3, Formulario.fecha_caducidad); // este es date
					pstmt.setString(4, Formulario.tipo_tarjeta);
					pstmt.setInt(5, membresias);
					pstmt.executeUpdate();
					pstmt.close();
					conn.close();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "ERROR. No he podido dar de alta. Perdone por las molestias.");
				}
				try {
					cerrar();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "ERROR. No se ha podido cerrar la base de datos. Perdone por las molestias.");
				}
				JOptionPane.showMessageDialog(null, "Usuario insertado correctamente");
				Inicio_Sesion inicioSesion = new Inicio_Sesion();
				inicioSesion.setVisible(true);
				dispose();
			}
		});
		lbl_BotonZafiro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lbl_BotonZafiro.setBounds(499, 229, 203, 353);
		//IMAGEN QUE DEFINE LA TARIFA	
		lbl_Logo_zafiro = new JLabel();
		lbl_Logo_zafiro.setBounds(539, 390, 124, 97);
		ImageIcon logo_z = new ImageIcon(getClass().getResource("/Trilogy_imagenes/Zafiro_Standar.JPG"));// DIRECCIÓN DE LA IMAGEN QUE QUIERO AJUSTAR
		ImageIcon imgfondologozafiro = new ImageIcon(logo_z.getImage().getScaledInstance(lbl_Logo_zafiro.getWidth(),lbl_Logo_zafiro.getHeight(), Image.SCALE_SMOOTH)); // CREAMOS OTRO OBJETO PARA QUE SE AJUSTE AUTOMÁTICAMENTE LA IMAGEN AL LABEL
		lbl_Logo_zafiro.setIcon(imgfondologozafiro);	
		//---------------------------------------------------------------------------------------------------------
		//lbl_BotonRubi BOTÓN RUBÍ STANDAR
		//---------------------------------------------------------------------------------------------------------	
		//FONDO TARIFA
		lbl_RubiStandar = new JLabel();
		lbl_RubiStandar.setBounds(261, 229, 203, 353);
		ImageIcon rubi = new ImageIcon(getClass().getResource("/Trilogy_imagenes/Fondo_Rubi_Standar_Seleccion.jpg"));// DIRECCIÓN DE LA IMAGEN QUE QUIERO AJUSTAR
		ImageIcon imgfondorubi = new ImageIcon(rubi.getImage().getScaledInstance(lbl_RubiStandar.getWidth(),lbl_RubiStandar.getHeight(), Image.SCALE_SMOOTH)); // CREAMOS OTRO OBJETO PARA QUE SE AJUSTE AUTOMÁTICAMENTE LA IMAGEN AL LABEL
		lbl_RubiStandar.setIcon(imgfondorubi);			
		//PRECIO
		lbl_rubi_precio = new JLabel("5.99€/MES");
		lbl_rubi_precio.setForeground(new Color(153, 255, 153));
		lbl_rubi_precio.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_rubi_precio.setFont(new Font("Copperplate", Font.BOLD, 25));
		lbl_rubi_precio.setBorder(null);
		lbl_rubi_precio.setBackground(new Color (0,0,0,0));
		lbl_rubi_precio.setBounds(261, 550, 203, 20);				
		//DESCRIPCIÓN TARIFA
		rubi_texto2 = new JLabel("<html><p style=\"color: #000000;\">• Sin Anuncios<br>" 
				+ "• Toda la música<br>" 
				+ "• Películas</p>" );
		rubi_texto2.setBounds(275, 241, 173, 102);
		rubi_texto2.setFont(new Font("Copperplate", Font.BOLD, 16));
		rubi_texto2.setBorder(BorderFactory.createLineBorder(Color.black,1));				
		//BOTÓN ENCIMA DE TODA LA TARIFA
		lbl_BotonRubi = new JLabel();
		lbl_BotonRubi.setBorder(BorderFactory.createLineBorder(Color.black,1));
		lbl_BotonRubi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lbl_BotonRubi.setBorder(BorderFactory.createLineBorder(Color.ORANGE,4));						
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lbl_BotonRubi.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					conectar();
				} catch (Exception e3) {
					JOptionPane.showMessageDialog(null, "ERROR. No se ha podido conectar a la base de datos. Perdone por las molestias."); // MENSAJE QUE SE LE MUESTRA AL USUARIO
				}
				int membresias=3;
				// ID DE LOS USUARIOS QUE IRÁ AUMENTANDO
				int num_idusuario=0;
				// TRATANDO LA EXCEPCIÓN QUE LANZA EL MÉTODO PARA CALCULAR EL ID DEL USUARIOS
				try {
					num_idusuario = calcular_ID_Ususarios();
				} catch (SQLException e2) {
					JOptionPane.showMessageDialog(null, "ERROR. No he podido comprobar el ID del usuarios.Perdone por las molestias.");
				}
				// INSERTAMOS EL USUARIOS EN LA TABLA USUARIOS DE LA BASE DE DATOS
				try {
					Connection conn = DriverManager.getConnection(url, login, password);
					String sql = "INSERT INTO USUARIOS (ID_USUARIO,CONTRASENA,CORREO,NOMBRE,APELLIDOS,DIRECCION,N_TELEFONO) VALUES (?,?,?,?,?,?,?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, num_idusuario);
					pstmt.setString(2, Formulario.passwd);
					pstmt.setString(3, Formulario.correo);
					pstmt.setString(4, Formulario.nombre);
					pstmt.setString(5, Formulario.apellidos);
					pstmt.setString(6, Formulario.calle);
					pstmt.setString(7, Formulario.num_telefono);
					pstmt.executeUpdate();
					pstmt.close();
					conn.close();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "ERROR. No he podido dar de alta. Perdone por las molestias.");
				}
				// INSERTAMOS EL USUARIOS EN LA TABLA CON_MEMBRESIAS DE LA BASE DE DATOS
				try {
					Connection conn = DriverManager.getConnection(url, login, password);
					String sql = "INSERT INTO CON_MEMBRESIAS VALUES (?,?,?,?,?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, num_idusuario);
					pstmt.setString(2, Formulario.num_tarjeta);
					pstmt.setString(3, Formulario.fecha_caducidad); // este es date
					pstmt.setString(4, Formulario.tipo_tarjeta);
					pstmt.setInt(5, membresias);
					pstmt.executeUpdate();
					pstmt.close();
					conn.close();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "ERROR. No he podido dar de alta. Perdone por las molestias.");
				}
				try {
					cerrar();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "ERROR. No se ha podido cerrar la base de datos. Perdone por las molestias.");
				}
				JOptionPane.showMessageDialog(null, "Usuario insertado correctamente");
				Inicio_Sesion inicioSesion = new Inicio_Sesion();
				inicioSesion.setVisible(true);
				dispose();
			}
		});
		lbl_BotonRubi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lbl_BotonRubi.setDisplayedMnemonic(KeyEvent.VK_ENTER);
		lbl_BotonRubi.setBounds(261, 229, 203, 353);				
		//IMAGEN QUE DEFINE LA TARIFA	
		lbl_Logo_rubi = new JLabel();
		lbl_Logo_rubi.setBounds(301, 390, 124, 97);
		ImageIcon logo_r = new ImageIcon(getClass().getResource("/Trilogy_imagenes/Rubi_Standar.JPG"));// DIRECCIÓN DE LA IMAGEN QUE QUIERO AJUSTAR
		ImageIcon imgfondologorubi = new ImageIcon(logo_r.getImage().getScaledInstance(lbl_Logo_rubi.getWidth(), lbl_Logo_rubi.getHeight(), Image.SCALE_SMOOTH)); // CREAMOS OTRO OBJETO PARA QUE SE AJUSTE AUTOMÁTICAMENTE LA IMAGEN AL LABEL
		lbl_Logo_rubi.setIcon(imgfondologorubi);		
		//---------------------------------------------------------------------------------------------------------
		//lbl_BotonCristal BOTÓN CRISTAL BASIC
		//---------------------------------------------------------------------------------------------------------	
		//FONDO TARIFA
		lbl_CristalBasic = new JLabel();
		lbl_CristalBasic.setBounds(25, 229, 203, 353);
		ImageIcon cristal = new ImageIcon(getClass().getResource("/Trilogy_imagenes/Fondo_Cristal_Basic_Seleccion.jpg"));// DIRECCIÓN DE LA IMAGEN QUE QUIERO AJUSTAR
		ImageIcon imgfondocristal = new ImageIcon(cristal.getImage().getScaledInstance(lbl_CristalBasic.getWidth(),lbl_CristalBasic.getHeight(), Image.SCALE_SMOOTH)); // CREAMOS OTRO OBJETO PARA QUE SE AJUSTE AUTOMÁTICAMENTE LA IMAGEN AL LABEL
		lbl_CristalBasic.setIcon(imgfondocristal);				
		//PRECIO
		lbl_Cristal_precio = new JLabel("GRATUITO");
		lbl_Cristal_precio.setForeground(new Color(153, 255, 153));
		lbl_Cristal_precio.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Cristal_precio.setFont(new Font("Copperplate", Font.BOLD, 25));
		lbl_Cristal_precio.setBorder(null);
		lbl_Cristal_precio.setBackground(new Color(0, 0, 0, 0));
		lbl_Cristal_precio.setBounds(25, 550, 203, 20);				
		//DESCRIPCIÓN TARIFA
		cristal_texto2 = new JLabel("<html><p style=\"color: #000000;\">• Anuncios<br>" 
				+ "• Toda la música</p>");
		cristal_texto2.setBounds(40, 241, 173, 102);
		cristal_texto2.setFont(new Font("Copperplate", Font.BOLD, 16));
		cristal_texto2.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));				
		//BOTÓN ENCIMA DE TODA LA TARIFA
		lbl_BotonCristal = new JLabel();
		lbl_BotonCristal.setBorder(BorderFactory.createLineBorder(new Color(0,0,0))); 
		lbl_BotonCristal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lbl_BotonCristal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lbl_BotonCristal.setBorder(BorderFactory.createLineBorder(Color.ORANGE,4));						
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lbl_BotonCristal.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					conectar();
				} catch (Exception e3) {
					JOptionPane.showMessageDialog(null, "ERROR. No se ha podido conectar a la base de datos. Perdone por las molestias."); // MENSAJE QUE SE LE MUESTRA AL USUARIO
				}
				// ID DE LA MEMBRESIA
				int membresias=1;
				// ID DE LOS USUARIOS QUE IRÁ AUMENTANDO
				int num_idusuario=0;
				// TRATANDO LA EXCEPCION QUE LANZA EL MÉTODO PARA CALCULAR EL ID DEL USUARIOS
				try {
					num_idusuario = calcular_ID_Ususarios();
				} catch (SQLException e2) {
					JOptionPane.showMessageDialog(null, "ERROR. No he podido comprobar el ID del usuarios. Perdone por las molestias.");
				}
				// INSERTAMOS EL USUARIOS EN LA TABLA USUARIOS DE LA BASE DE DATOS
				try {
					Connection conn = DriverManager.getConnection(url, login, password);
					String sql = "INSERT INTO USUARIOS (ID_USUARIO,CONTRASENA,CORREO,NOMBRE,APELLIDOS,DIRECCION,N_TELEFONO) VALUES (?,?,?,?,?,?,?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, num_idusuario);
					pstmt.setString(2, Formulario.passwd);
					pstmt.setString(3, Formulario.correo);
					pstmt.setString(4, Formulario.nombre);
					pstmt.setString(5, Formulario.apellidos);
					pstmt.setString(6, Formulario.calle);
					pstmt.setString(7, Formulario.num_telefono);
					pstmt.executeUpdate();
					pstmt.close();
					conn.close();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "ERROR. No he podido dar de alta. Perdone por las molestias.");
				}
				// INSERTAMOS EL USUARIOS EN LA TABLA CON_MEMBRESIAS DE LA BASE DE DATOS
				try {
					Connection conn = DriverManager.getConnection(url, login, password);
					String sql = "INSERT INTO CON_MEMBRESIAS VALUES (?,?,?,?,?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, num_idusuario);
					pstmt.setString(2, Formulario.num_tarjeta);
					pstmt.setString(3, Formulario.fecha_caducidad); // este es date
					pstmt.setString(4, Formulario.tipo_tarjeta);
					pstmt.setInt(5, membresias);
					pstmt.executeUpdate();
					pstmt.close();
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "ERROR. No he podido dar de alta. Perdone por las molestias.");
				}
				try {
					cerrar();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "ERROR. No se ha podido cerrar la base de datos. Perdone por las molestias.");
				}
				JOptionPane.showMessageDialog(null, "Usuario insertado correctamente");
				Inicio_Sesion inicioSesion = new Inicio_Sesion();
				inicioSesion.setVisible(true);
				dispose();
			}
		});
		lbl_BotonCristal.setBounds(25, 229, 203, 353);		
		//IMAGEN QUE DEFINE LA TARIFA	
		lbl_Logo_basic = new JLabel();
		lbl_Logo_basic.setBounds(65, 390, 124, 97);
		ImageIcon logo_c = new ImageIcon(getClass().getResource("/Trilogy_imagenes/cristal_basic.JPG"));// DIRECCIÓN DE LA IMAGEN QUE QUIERO AJUSTAR
		ImageIcon imgfondologocristal = new ImageIcon(logo_c.getImage().getScaledInstance(lbl_Logo_basic.getWidth(), lbl_Logo_basic.getHeight(), Image.SCALE_SMOOTH)); // CREAMOS OTRO OBJETO PARA QUE SE AJUSTE AUTOMÁTICAMENTE LA IMAGEN AL LABEL
		lbl_Logo_basic.setIcon(imgfondologocristal);

		//---------------------------------------------------------------------------------------------------------
		//lblFondo_Verde ES EL FONDO VERDE
		//---------------------------------------------------------------------------------------------------------	
		lblFondo_Verde = new JLabel();
		lblFondo_Verde.setBounds(0, 0, 961, 669);
		ImageIcon fondoverde = new ImageIcon(getClass().getResource("/Trilogy_imagenes/Fondo_Formulario.png"));// DIRECCIÓN DE LA IMAGEN QUE QUIERO AJUSTAR
		ImageIcon imgfondo = new ImageIcon(fondoverde.getImage().getScaledInstance(lblFondo_Verde.getWidth(), lblFondo_Verde.getHeight(), Image.SCALE_SMOOTH)); // CREAMOS OTRO OBJETO PARA QUE SE AJUSTE AUTOMÁTICAMENTE LA IMAGEN AL LABEL
		lblFondo_Verde.setIcon(imgfondo);
		//--------------------------------------------------------------------------------------------------------
		//lbl_Logo ES NUESTRO LOGO TRILOGY		
		//--------------------------------------------------------------------------------------------------------		
		lbl_Logo = new JLabel();
		lbl_Logo.setBounds(518, 67, 408, 108);
		//AJUSTAR LA IMAGEN AL BOTÓN
		ImageIcon logo = new ImageIcon(getClass().getResource("/Trilogy_imagenes/LogoTrilogy.jpg"));// DIRECCIÓN DE LA IMAGEN QUE QUIERO AJUSTAR
		ImageIcon imgfondologo = new ImageIcon(logo.getImage().getScaledInstance(lbl_Logo.getWidth(), lbl_Logo.getHeight(), Image.SCALE_SMOOTH)); // CREAMOS OTRO OBJETO PARA QUE SE AJUSTE AUTOMÁTICAMENTE LA IMAGEN AL LABEL
		lbl_Logo.setIcon(imgfondologo);		 
		//---------------------------------------------------------------------------------------------------------
		//lblBordeSuperior ES LA BARRA VERDE SUPERIOR
		//---------------------------------------------------------------------------------------------------------	
		lblBordeSuperior = new JLabel();
		lblBordeSuperior.setOpaque(true);
		lblBordeSuperior.setBackground(new Color(153, 255, 153));
		lblBordeSuperior.setBounds(0, 33, 961, 132);
		// ----------------------------------------------------------------------------------------
		// JERARQUÍA DEL panel_contenedor
		// ----------------------------------------------------------------------------------------
		Panel_Seleccion_Membresia.add(lbl_BotonDiamante);
		Panel_Seleccion_Membresia.add(lbl_BotonZafiro);
		Panel_Seleccion_Membresia.add(lbl_BotonRubi);
		Panel_Seleccion_Membresia.add(lbl_BotonCristal);
		Panel_Seleccion_Membresia.add(lbl_Logo_diamante);			
		Panel_Seleccion_Membresia.add(lbl_Logo_zafiro);
		Panel_Seleccion_Membresia.add(lbl_Logo_rubi);
		Panel_Seleccion_Membresia.add(lbl_Logo_basic);
		Panel_Seleccion_Membresia.add(diamante_texto2);
		Panel_Seleccion_Membresia.add(zafiro_texto2);
		Panel_Seleccion_Membresia.add(rubi_texto2);
		Panel_Seleccion_Membresia.add(cristal_texto2);
		Panel_Seleccion_Membresia.add(lbl_diamante_precio);
		Panel_Seleccion_Membresia.add(lbl_zafiro_precio);
		Panel_Seleccion_Membresia.add(lbl_rubi_precio);
		Panel_Seleccion_Membresia.add(lbl_Cristal_precio);
		Panel_Seleccion_Membresia.add(btn_Salida);
		Panel_Seleccion_Membresia.add(lbl_Logo);
		Panel_Seleccion_Membresia.add(lblBordeSuperior);
		Panel_Seleccion_Membresia.add(lbl_DiamantePremium);
		Panel_Seleccion_Membresia.add(lbl_ZafiroStandar);
		Panel_Seleccion_Membresia.add(lbl_RubiStandar);
		Panel_Seleccion_Membresia.add(lbl_CristalBasic);
		Panel_Seleccion_Membresia.add(lblFondo_Verde);
	}
}