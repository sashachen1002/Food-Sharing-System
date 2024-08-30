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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

public class WindowDemo extends JFrame{
	
	private JButton browseButton;
	private JFrame frame;
    private JLabel imgLabel;
    private JTextArea postTextArea;
    private JComboBox typeComboBox;
    private JTextField text_name;
    private JTextField text_location;
    private JTextField text_startTime;
    private JTextField text_price;
    private JTextField text_endTime;
    private JTextField text_amount;
    private File file_path;
	private byte[] imageBytes;
	
	private String userID;
    
	private SQLQuery sqlQuery = new SQLQuery();
	
	public static void main(String[] args){
		WindowDemo windowDemo = new WindowDemo("108305091");//default
		windowDemo.getFrame().setVisible(true);
    }
    
    public WindowDemo(String userID){
    	this.userID = userID;
    	initialize();
    }

   private void initialize() {
	   frame = new JFrame();
	   frame.setTitle("Post");
	   frame.getContentPane().setBackground(new Color(255, 255, 224));
	   /*frame.setBounds(100, 100, 1920, 1080);*/
	   frame.getContentPane().setLayout(null);
	   frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	   
	   /*frame.setExtendedState(JFrame.MAXIMIZED_BOTH);*/
	   
	   /*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	   int screenWidth = screenSize.width;
	   int screenHeight = screenSize.height;
	   int frameWidth = frame.getWidth();
	   int frameHeight = frame.getHeight();
	   int x = (screenWidth - frameWidth) / 2;
	   int y = (screenHeight - frameHeight) / 2;
	   frame.setLocation(x, y);
	   frame.setSize(screenSize);
	   frame.setVisible(true);
       Font font = new Font("Arial", Font.BOLD, 32);
       
       JPanel panel = new JPanel();
       panel.setBorder(null);
       panel.setBounds(20, 50, screenSize.width - 40, screenSize.height - 120);
       panel.setBackground(Color.decode("#FFFFE0")); 
       frame.getContentPane().add(panel);*/
       
       JPanel infoPanel = new JPanel();
       infoPanel.setBackground(new Color(255, 255, 224));
       infoPanel.setBounds(660, 111, 790, 443);
       /*panel.add(infoPanel);*/
       frame.getContentPane().add(infoPanel);
       infoPanel.setLayout(new GridLayout(8,2));
       
       JLabel NameLabel = new JLabel("Food Name");
       infoPanel.add(NameLabel);
       NameLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
       
       text_name = new JTextField();
       infoPanel.add(text_name);
       text_name.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 24));
       text_name.setColumns(10);
       
       JLabel uploadImgLabel = new JLabel("Upload Image");
       uploadImgLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
       infoPanel.add(uploadImgLabel);
       
       browseButton = new JButton("Browse");
       infoPanel.add(browseButton);
       browseButton.setFont(new Font("·L³n¥¿¶ÂÅé", Font.PLAIN, 24));
       
       browseButton.addActionListener(new ActionListener() {
       	public void actionPerformed(ActionEvent e) {
				if(e.getSource()==browseButton) {
					JFileChooser file_upload = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
				    file_upload.setFileFilter(filter);

					int res=file_upload.showSaveDialog(null);

					if(res==JFileChooser.APPROVE_OPTION) {
						file_path=new File(file_upload.getSelectedFile().getAbsolutePath());
					    try {
					    	imageBytes = Files.readAllBytes(file_path.toPath());
							ImageIcon icon = new ImageIcon(imageBytes);
							Image scaledImage = icon.getImage().getScaledInstance(500, 600, Image.SCALE_SMOOTH);
							imgLabel = new JLabel(new ImageIcon(scaledImage));
							imgLabel.setBounds(85, 111, 500, 600);
							frame.getContentPane().add(imgLabel);
							frame.getContentPane().revalidate();
							frame.getContentPane().repaint();
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				}
			}	
       });
       
       JLabel locationLabel = new JLabel("Give out Location");
       locationLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
       infoPanel.add(locationLabel);
       
       text_location = new JTextField();
       text_location.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 24));
       infoPanel.add(text_location);
       text_location.setColumns(10);
       
       JLabel typeLabel = new JLabel("Food Type");
       typeLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
       infoPanel.add(typeLabel);
       
       typeComboBox = new JComboBox();
       typeComboBox.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
       infoPanel.add(typeComboBox);
       typeComboBox.setModel(new DefaultComboBoxModel(new String[] {"lunch box", "drinks", "fresh green", "others"}));
       
       JLabel startTimeLabel = new JLabel("Give-out Start Time (ex. 13:00)");
       startTimeLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
       infoPanel.add(startTimeLabel);
       
       text_startTime = new JTextField();
       text_startTime.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 24));
       infoPanel.add(text_startTime);
       text_startTime.setColumns(10);
       
       JLabel endTimeLabel = new JLabel("Give-out End Time (ex. 16:00)");
       endTimeLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
       infoPanel.add(endTimeLabel);
       
       text_endTime = new JTextField();
       text_endTime.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 24));
       infoPanel.add(text_endTime);
       text_endTime.setColumns(10);
       
       JLabel priceLabel = new JLabel("Lowest Price per Unit");
       priceLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
       infoPanel.add(priceLabel);
       
       text_price = new JTextField();
       text_price.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 24));
       infoPanel.add(text_price);
       text_price.setColumns(10);
       
       JLabel amountLabel = new JLabel("Food Amount");
       amountLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
       infoPanel.add(amountLabel);
       
       text_amount = new JTextField();
       text_amount.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 24));
       infoPanel.add(text_amount);
       text_amount.setColumns(10);

       // Set preferred size of panel to fit within scroll pane
       	/*panel.setPreferredSize(new Dimension(screenSize.width - 40, screenSize.height - 120));
	    panel.setLayout(null);*/
	    
	    postTextArea = new JTextArea();
	    postTextArea.setForeground(new Color(192, 192, 192));
	    postTextArea.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 24));
	    postTextArea.setText("post content...");
	    postTextArea.setBounds(660, 581, 790, 118);
	    frame.getContentPane().add(postTextArea);
	    /*panel.add(postTextArea);*/
	    
	    JLabel username = new JLabel("Hi, "+userID);
	    username.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
	    username.setBounds(85, 50, 545, 34);
	    frame.getContentPane().add(username);
	    /*panel.add(username);*/
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLocationRelativeTo(null);

	    JButton postButton = new JButton("POST");
	    postButton.setBackground(Color.decode("#FFD300")); 
	    postButton.setFont(new Font("Dialog", Font.BOLD, 24));
	    postButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (text_name.getText().isEmpty()
		                || postTextArea.getText().isEmpty()
		                || text_location.getText().isEmpty()
		                || text_amount.getText().isEmpty()
		                || text_startTime.getText().isEmpty()
		                || text_endTime.getText().isEmpty()
		                || text_price.getText().isEmpty()) {
					text_name.setText("");
					postTextArea.setText("");
					text_location.setText("");
					text_amount.setText("");
					text_startTime.setText("");
					text_endTime.setText("");
					text_price.setText("");
		            JOptionPane.showMessageDialog(null, "Please complete the text.", "Upload Failed", JOptionPane.ERROR_MESSAGE);
		        }else {
		        	String productName = text_name.getText();
					String postContent = postTextArea.getText();
					byte[] image = imageBytes;
					String location = text_location.getText();
					String type = (String)typeComboBox.getSelectedItem();
					int amount = Integer.parseInt(text_amount.getText());
					String startTime = text_startTime.getText();
					String endTime = text_endTime.getText();
					int price = Integer.parseInt(text_price.getText());

					ProcessData uploadProduct = new ProcessData(userID,productName,postContent,image,location,type,amount,startTime,endTime,price);
	                boolean success = sqlQuery.uploadProduct(uploadProduct);
	                if (!success) {
	                	JOptionPane.showMessageDialog(null, "Please complete the text.", "Upload Failed", JOptionPane.ERROR_MESSAGE);
	                }else {
	                	
	                	JOptionPane.showMessageDialog(null, "Uploaded Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
	                	frame.dispose();
	                	SignoutPage signoutPage = new SignoutPage(userID);
	                	signoutPage.getFrame().setVisible(true);
	                }
		        }
			}
		});
	    postButton.setBounds(660, 732, 790, 40);
	    /*panel.add(postButton);*/
	    frame.getContentPane().add(postButton);
	    
	    
	   JLabel lblNewLabel = new JLabel("NCCU HUNGER SAVER");
	   lblNewLabel.setFont(new Font("Arial", Font.BOLD, 32));
	   frame.getContentPane().add(lblNewLabel);
	   /*panel.add(lblNewLabel);*/
	   lblNewLabel.setBackground(new Color(240, 240, 240));
	   
	   lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	   lblNewLabel.setBounds(0, 6, 1540, 34);
	   /*lblNewLabel.setFont(font);*/
	   
	   JButton returnBtn = new JButton("Return to the Homepage");
	   returnBtn.addActionListener(new ActionListener() {
	   	public void actionPerformed(ActionEvent e) {
	   		frame.dispose();
        	SignoutPage signoutPage = new SignoutPage(userID);
        	signoutPage.getFrame().setVisible(true);
	   	}
	   });
	   returnBtn.setFont(new Font("Dialog", Font.PLAIN, 20));
	   returnBtn.setBackground(new Color(255, 211, 0));
	   returnBtn.setBounds(1251, 10, 264, 40);
	   frame.getContentPane().add(returnBtn);
	   /*panel.add(returnBtn);*/
	    
	    }
    
	     // Method to resize imageIcon with the same size of a Jlabel
	    public ImageIcon ResizeImage(String ImagePath)
	    {
	        ImageIcon MyImage = new ImageIcon(ImagePath);
	        Image img = MyImage.getImage();
	        Image newImg = img.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_SMOOTH);
	        ImageIcon image = new ImageIcon(newImg);
	        return image;
	    }
	    
	    public JFrame getFrame() {
	    	return this.frame;
	    }
   }