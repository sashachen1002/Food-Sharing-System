import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import javax.swing.ImageIcon;

public class PayPage {

    private JFrame frame;
    private File file_path;
	private byte[] imageBytes;
    private JButton uploadButton; // Declare uploadButton as a class field
    private JProgressBar progressBar;
    private String userID;
    private int postID;
    
    private SQLQuery sqlQuery = new SQLQuery();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PayPage window = new PayPage("108305093",1);//test
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
    public PayPage(String userID, int postID) {
    	this.userID = userID;
    	this.postID = postID;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.decode("#FFFF9F"));
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);

        // Set frame size
        int frameWidth = 800; // Specify the desired width
        int frameHeight = 600; // Specify the desired height
        frame.setSize(frameWidth, frameHeight);

        // Center the frame on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int x = (screenWidth - frame.getWidth()) / 2;
        int y = (screenHeight - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        JLabel lblNewLabel = new JLabel("NCCU HUNGER SAVER");
        lblNewLabel.setBackground(new Color(240, 240, 240));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(0, 0, frameWidth, 34);
        frame.getContentPane().add(lblNewLabel);
        Font font = new Font("Arial", Font.BOLD, 32);
        lblNewLabel.setFont(font);

        JLabel lblNewLabel_1 = new JLabel("Please transfer to this account (post office):");
        lblNewLabel_1.setBounds(208, 74, 315, 14);
        frame.getContentPane().add(lblNewLabel_1);
        JLabel lblNewLabel_11 = new JLabel("Please transfer to this account (LINE Pay QR Code):");
        lblNewLabel_11.setBounds(208, 121, 315, 14);
        frame.getContentPane().add(lblNewLabel_11);

        JLabel lblNewLabel_2 = new JLabel("Transaction record:");
        lblNewLabel_2.setBounds(208, 379, 150, 14);
        frame.getContentPane().add(lblNewLabel_2);

        uploadButton = new JButton("Upload");
        uploadButton.setBounds(351, 436, 89, 23);
        uploadButton.setBackground(Color.decode("#FFD300"));
        frame.getContentPane().add(uploadButton);
        uploadButton.setEnabled(false); // Disable the button initially

        JLabel lblNewLabel_3 = new JLabel("(700)00011230123123");
        lblNewLabel_3.setBounds(468, 74, 240, 14);
        lblNewLabel_3.setBackground(Color.decode("#FFD300"));
        frame.getContentPane().add(lblNewLabel_3);

        ImageIcon originalIcon = new ImageIcon(PayPage.class.getResource("/image/qrcode.png")); 
        Image originalImage = originalIcon.getImage();
        int labelWidth = 200; 
        int labelHeight = 200; 
        Image scaledImage = originalImage.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);

        // deal with the scaled image
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel qrcode = new JLabel(scaledIcon);
        qrcode.setBounds(233, 141, 294, 228);
        frame.getContentPane().add(qrcode);

        JButton browseButton = new JButton("Browse");
        browseButton.setBounds(392, 379, 89, 23);
        browseButton.setBackground(Color.decode("#FFD300"));
        frame.getContentPane().add(browseButton);

        JLabel fileName = new JLabel("");
        fileName.setBounds(392, 405, 209, 15);
        frame.getContentPane().add(fileName);

        progressBar = new JProgressBar();
        progressBar.setBounds(323, 470, 146, 7);
        frame.getContentPane().add(progressBar);

        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String file = selectedFile.getName();
                    fileName.setText("File name: " + file);
                    
                    //upload the payment to the database
					file_path=new File(fileChooser.getSelectedFile().getAbsolutePath());
					try {
						imageBytes = Files.readAllBytes(file_path.toPath());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
                    // Enable the uploadButton
                    uploadButton.setEnabled(true);
                }
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Disable the uploadButton
                uploadButton.setEnabled(false);
                
                // Simulating upload progress for demonstration
                new Thread(new Runnable() {
                    public void run() {
                        for (int i = 0; i <= 100; i++) {
                            progressBar.setValue(i);
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                        
                      //upload the payment to the database
                        boolean success = sqlQuery.uploadPayment(userID,postID,imageBytes);
                        if (success==false) {
                        	JOptionPane.showMessageDialog(null, "Please upload again.", "Upload Failed", JOptionPane.ERROR_MESSAGE);
                        }else {
                        	JOptionPane.showMessageDialog(null, "Uploaded Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        	frame.dispose();
                        	transcation_success successFrame = new transcation_success(userID);
                            successFrame.showFrame();
                        }
                        
                    }
                }).start();
            }
        });
    }
    
    public JFrame getFrame() {
        return frame;
    }
}