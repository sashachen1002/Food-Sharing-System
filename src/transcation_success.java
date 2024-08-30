import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class transcation_success{
	private JFrame frame;
	private JButton button;
	private String userID;
    
    public transcation_success(String userID){
	    this.userID = userID;
    	initialize();
	}
    
    private void initialize() {
    	frame = new JFrame();
		frame.setTitle("Transation Success!");
		// Set frame size
        frame.setSize(800, 600);
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
	    
	    JPanel panel = new JPanel();
		panel.setBounds(87, 32, 268, 221);
        panel.setBackground(new Color(255, 255, 204)); // Set background color to yellowish
		frame.getContentPane().add(panel);
		panel.setLayout(null);
        

        JLabel successText = new JLabel("Transaction Completed!");
	    successText.setFont(new Font("�s�ө���", Font.PLAIN, 14));
	    successText.setHorizontalAlignment(SwingConstants.CENTER);
	    successText.setBounds(31, 29, 200, 61);
	    panel.add(successText);
	    
	    button = new JButton("Back");
	    button.setBounds(82,100,100,40);
	    panel.add(button);
	    
	    button.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	frame.dispose();
	        	SignoutPage page = new SignoutPage(userID);
	        	page.getFrame();
	        	
	        }
	    });
	    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
        
    }
    
    public void showFrame() {
        frame.setVisible(true);
    }
    public static void main(String[] args){
	        new transcation_success("108305093");//test
	}
}