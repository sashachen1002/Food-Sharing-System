import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;


public class RegisterPage {

	private JFrame frame;
	private JTextField text_name;
	private JTextField text_account;
	private JPasswordField passwordField;
	private LoginPage loginPage;
	
	private SQLQuery sqlQuery = new SQLQuery();
	
	private boolean named; //unnamed or not

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterPage window = new RegisterPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RegisterPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Register");
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setBackground(Color.decode("#FFFF9F")); 
		/*frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		 // Center the frame on the screen
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    int screenWidth = screenSize.width;
	    int screenHeight = screenSize.height;
	    int frameWidth = frame.getWidth();
	    int frameHeight = frame.getHeight();
	    int x = (screenWidth - frameWidth) / 2;
	    int y = (screenHeight - frameHeight) / 2;
	    frame.setLocation(x, y);
		
		JLabel systemNameLabel = new JLabel("Sign up");
		systemNameLabel.setBackground(new Color(240, 240, 240));
		systemNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		systemNameLabel.setBounds(0, 0, 438, 34);
		frame.getContentPane().add(systemNameLabel);
		Font font = new Font("Arial", Font.BOLD, 20); 
		systemNameLabel.setFont(font);
		JPanel panel = new JPanel();
		panel.setBounds(87, 32, 268, 221);
        panel.setBackground(new Color(255, 255, 204)); // Set background color to yellowish
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JRadioButton unamedRbtn = new JRadioButton("Unamed");
		unamedRbtn.setBounds(36, 63, 109, 23);
		unamedRbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				named = false;
				text_name.setEditable(false);
			}	
		});
		unamedRbtn.setBackground(new Color(255, 255, 204)); 
		panel.add(unamedRbtn);
		
		JRadioButton namedRbtn = new JRadioButton("Named");
		namedRbtn.setBounds(36, 85, 109, 23);
		namedRbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				text_name.setEditable(true);
				named = true;
			}	
		});
		namedRbtn.setBackground(new Color(255, 255, 204)); 
		panel.add(namedRbtn);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(unamedRbtn);
		buttonGroup.add(namedRbtn);
		
		text_name = new JTextField();
		text_name.setBounds(100, 113, 96, 20);
		panel.add(text_name);
		text_name.setColumns(10);
		
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setBounds(56, 113, 48, 14);
		panel.add(nameLabel);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(36, 33, 80, 14);
		panel.add(passwordLabel);
		
		JLabel accountLabel = new JLabel("Account:");
		accountLabel.setBounds(36, 7, 80, 14);
		panel.add(accountLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(100, 33, 96, 20);
		panel.add(passwordField);
		
		text_account = new JTextField();
		text_account.setBounds(100, 7, 96, 20);
		panel.add(text_account);
		text_account.setColumns(10);
		
		JButton loginButton = new JButton("Log in");
		loginButton.setBackground(Color.decode("#FFD300")); 
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				loginPage = new LoginPage();//switch to loginPage
				loginPage.getFrame().setVisible(true);
			}
		});
		loginButton.setBounds(95, 188, 89, 23);
		panel.add(loginButton);
		
		JButton registerButton = new JButton("Sign up");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String account = text_account.getText();
		    	char[] password = passwordField.getPassword();
		        String passwordString = new String(password);
		        String userName = "";

		        if(named==true) {
		        	if(text_account.getText().isEmpty()||passwordString.isEmpty()||text_name.getText().isEmpty()) {
	                	JOptionPane.showMessageDialog(null, "Please complete the text.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
	                	text_account.setText("");
	                	passwordField.setText("");
	                	text_name.setText("");
		        	}else {
		        		userName = text_name.getText();
		        		ProcessData registration = new ProcessData(account,passwordString,userName);
						
						//check whether registered successfully
		                boolean success = sqlQuery.registration(registration);
		                if (success==false) {
		                	JOptionPane.showMessageDialog(null, "You have been registered! Please Login directly.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
		                	text_account.setText("");
		                	passwordField.setText("");
		                	text_name.setText("");
		                }else if(success!=false){
		                	//After uploading successfully, closing the JOptionPane and return to the home page
		    		        JOptionPane.showMessageDialog(null, "Congrats! You have registered successfully. Please login and start using NCCU HUNGER SAVER", "Registration Success", JOptionPane.INFORMATION_MESSAGE);
		                	frame.dispose();
		                	//User should login after completing registration
		                	LoginPage loginPage = new LoginPage();
		                	loginPage.getFrame().setVisible(true);
		                }
		        	}
		        }else {
		        	if(text_account.getText().isEmpty()||passwordString.isEmpty()) {
	                	JOptionPane.showMessageDialog(null, "Please complete the text.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
		        	}else {
			        	userName = "Anonymous User";
			        	ProcessData registration = new ProcessData(account,passwordString,userName);
						
						//check whether registered successfully
		                boolean success = sqlQuery.registration(registration);
		                if (success==false) {
		                	JOptionPane.showMessageDialog(null, "You have been registered! Please Login directly.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
		                }else if(success!=false){
		                	//After uploading successfully, closing the JOptionPane and return to the home page
		    		        JOptionPane.showMessageDialog(null, "Congrats! You have registered successfully. Please login and start using NCCU HUNGER SAVER", "Registration Success", JOptionPane.INFORMATION_MESSAGE);
		                	frame.dispose();
		                	//User should login after completing registration
		                	LoginPage loginPage = new LoginPage();
		                	loginPage.getFrame().setVisible(true);
		                }
		        	}
		        }
			}
		});
		registerButton.setBackground(new Color(255, 211, 0));
		registerButton.setBounds(95, 143, 89, 23);
		panel.add(registerButton);
		
		JLabel lblNewLabel = new JLabel("I already have an account:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		lblNewLabel.setBounds(36, 172, 193, 15);
		panel.add(lblNewLabel);
	}
	
	public JFrame getFrame() {
        return frame;
    }
}