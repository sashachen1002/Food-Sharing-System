import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class LoginPage {

	private JFrame frame;
	private JTextField text_account;
	private JPasswordField passwordField;
	private RegisterPage registerPage;
	
	private SQLQuery sqlQuery = new SQLQuery();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
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
	public LoginPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Login");
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setBackground(Color.decode("#FFFF9F")); 
		/*frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		 // Center the frame on the screen
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    int screenWidth = screenSize.width;
	    int screenHeight = screenSize.height;
	    int frameWidth = frame.getWidth();
	    int frameHeight = frame.getHeight();
	    int x = (screenWidth - frameWidth) / 2;
	    int y = (screenHeight - frameHeight) / 2;
	    frame.setLocation(x, y);
		frame.getContentPane().setLayout(null);
		
		JLabel nccuHungerSaverLabel = new JLabel("Log in");
		nccuHungerSaverLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nccuHungerSaverLabel.setBounds(0, 0, 438, 34);
		frame.getContentPane().add(nccuHungerSaverLabel);
		Font font = new Font("Arial", Font.BOLD, 20); 
		nccuHungerSaverLabel.setFont(font);

		
		JPanel panel = new JPanel();
		panel.setBounds(87, 32, 268, 196);
        panel.setBackground(new Color(255, 255, 204)); // Set background color to yellowish
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel accountLabel = new JLabel("Account:");
		accountLabel.setBounds(61, 21, 60, 14);
		panel.add(accountLabel);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(61, 58, 60, 14);
		panel.add(passwordLabel);
		
		text_account = new JTextField();
		text_account.setBounds(125, 18, 96, 20);
		panel.add(text_account);
		text_account.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(125, 55, 96, 20);
		panel.add(passwordField);
		
		JButton registerButton = new JButton("Sign up");//there should change the frame
        registerButton.setBackground(Color.decode("#FFD300")); 
		registerButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	frame.dispose();
				registerPage = new RegisterPage();
				registerPage.getFrame().setVisible(true);
		    }
		});
		registerButton.setBounds(92, 160, 89, 23);
        registerButton.setBackground(Color.decode("#FFD300")); 
		panel.add(registerButton);
		
		JLabel registerLabel = new JLabel("I don't have an account yet:");
		registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registerLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		registerLabel.setBounds(37, 135, 195, 15);
		panel.add(registerLabel);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userID = text_account.getText();
				char[] passwordChars = passwordField.getPassword();
				String password = new String(passwordChars);
				
				if(text_account.getText().isEmpty()||password.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Please fill ALL empty field", "Login Failed", JOptionPane.ERROR_MESSAGE);
	                text_account.setText("");
	                passwordField.setText("");
				}else {
					String checkLogin = sqlQuery.checkUserWithUserID(userID, password);

					if (checkLogin.equals("Wrong Password")) {
		                JOptionPane.showMessageDialog(null, "Please enter the correct user ID and password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
						text_account.setText("");
						passwordField.setText("");
						return;
					}else if(checkLogin.equals("Login Successfully")){
					    JOptionPane.showMessageDialog(null, "Welcome to NCCU HUNGER SAVER", "Login Success", JOptionPane.INFORMATION_MESSAGE);
					    frame.dispose();
					    SignoutPage signoutPage = new SignoutPage(userID);
						signoutPage.getFrame().setVisible(true);
					}else {
					    JOptionPane.showMessageDialog(null, "This user does not exist", "Login Failed", JOptionPane.INFORMATION_MESSAGE);
						text_account.setText("");
						passwordField.setText("");
					}
				}	
				
			}
		});
		loginButton.setBackground(new Color(255, 211, 0));
		loginButton.setBounds(92, 102, 89, 23);
		panel.add(loginButton);

	}
	
	public JFrame getFrame() {
        return frame;
    }
}