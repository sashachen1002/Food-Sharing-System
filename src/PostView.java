import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;


public class PostView {
	private JPanel panel;
	private JPanel actionPanel;
	private JLabel imageLabel;
	private JLabel username;
	private JPanel infoPanel;
	private JLabel locationLabel;
	private JLabel foodNameLabel;
	private JLabel pickupStartLabel;
	private JLabel totalAmountLabel;
	private JLabel postContentLabel;
	private JButton finish_pick;
	private JLabel reminderLabel;
	private JLabel remainingLabel;
	private JLabel lblNewLabel;
	private JLabel postponeReminderLabel;
	private JLabel peopleWaitingLabel;
    private JTextField num;
    private JLabel lblNewLabel_2;
    private JLabel timeNowLabel;
    private JButton updateTimeBtn;
    private JFrame frame;
    private Calendar calendar;
    private int month;
    private int date;
    private int hour;
    private int minute;
    private int second;

    private List<ProcessData> infoDataList;
    private SQLQuery sqlQuery = new SQLQuery();
    
    private String userID;
    
    private int postID; 
    private int remaining;
    private int peopleWaiting;
    private int totalAmount;
  
    private boolean finishPick = false;
    private JButton returnBtn;
    private JLabel minPriceLabel;
    
    public static void main(String[] args){
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PostView postView = new PostView(4,"108305093"); //default
			    	postView.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
    
    public PostView(int postID, String userID){
    	this.postID = postID;
    	this.userID = userID;
    	initialize();
	}
    
    public void initialize() {
    	frame = new JFrame();
    	frame.getContentPane().setBackground(Color.decode("#FFFF9F"));
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  	   	int screenWidth = screenSize.width;
  	   	int screenHeight = screenSize.height;
  	   	int frameWidth = frame.getWidth();
  	   	int frameHeight = frame.getHeight();
  	   	int x = (screenWidth - frameWidth) / 2;
  	   	int y = (screenHeight - frameHeight) / 2;
  	   	frame.setLocation(0, 0);
  	   	frame.setSize(screenSize);
        Font font = new Font("Arial", Font.BOLD, 32); 
        
        panel = new JPanel();
        panel.setBounds(20, 50, screenSize.width - 40, screenSize.height - 120);
        panel.setBackground(Color.decode("#FFFFE0")); 
        frame.getContentPane().add(panel);

        // Set preferred size of panel to fit within scroll pane
        panel.setPreferredSize(new Dimension(screenSize.width - 40, screenSize.height - 120));
	    panel.setLayout(null);
	    
	    imageLabel = new JLabel();
	    imageLabel.setBounds(117, 120, 456, 434);
	    imageLabel.setIcon(null);
	    panel.add(imageLabel);
	    
	    username = new JLabel("Hi, "+ this.userID);
	    username.setForeground(new Color(128, 128, 128));
	    username.setFont(new Font("Arial", Font.BOLD, 24));
	    username.setBounds(117, 79, 255, 31);
	    panel.add(username);
	    
	    infoPanel = new JPanel();
	    infoPanel.setBounds(627, 120, 800, 434);
	    infoPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	    panel.add(infoPanel);
	    infoPanel.setLayout(new GridLayout(6,1));
	    
	    foodNameLabel = new JLabel("post content");
	    foodNameLabel.setFont(new Font("微軟正黑體", Font.BOLD, 24));
	    foodNameLabel.setBounds(0, 0, 371, 20);
	    infoPanel.add(foodNameLabel);
	    
	    locationLabel = new JLabel();
	    locationLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 24));
	    locationLabel.setBounds(5, 399, 371, 34);
	    infoPanel.add(locationLabel);
	    
	    pickupStartLabel = new JLabel();
	    pickupStartLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 24));
	    pickupStartLabel.setBounds(5, 367, 371, 34);
	    infoPanel.add(pickupStartLabel);
	    
	    totalAmountLabel = new JLabel();
	    totalAmountLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 24));
	    totalAmountLabel.setBounds(5, 331, 371, 34);
	    infoPanel.add(totalAmountLabel);
	    
	    minPriceLabel = new JLabel("");
	    minPriceLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 24));
	    infoPanel.add(minPriceLabel);
	    
	    postContentLabel = new JLabel();
	    postContentLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 24));
	    postContentLabel.setBounds(0, 34, 371, 20);
	    infoPanel.add(postContentLabel);
	    
	    finish_pick = new JButton("Finish Pickup");
	    finish_pick.setFont(new Font("Arial", Font.BOLD, 20));
	    finish_pick.setBounds(1131, 610, 209, 128);
	    panel.add(finish_pick);
	    finish_pick.setEnabled(false);

	    reminderLabel = new JLabel("Please enter pickup amount:");
	    reminderLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    reminderLabel.setFont(new Font("Arial", Font.PLAIN, 18));
	    reminderLabel.setBounds(510, 579, 278, 21);
	    panel.add(reminderLabel);
	    
	    remainingLabel = new JLabel(); //要用SQL count
	    remainingLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    remainingLabel.setFont(new Font("Arial", Font.PLAIN, 20));
	    remainingLabel.setForeground(new Color(128, 0, 0));

	    timeNowLabel = new JLabel();
	    timeNowLabel.setFont(new Font("Arial", Font.BOLD, 20));
	    timeNowLabel.setForeground(new Color(128, 0, 0));
	    calendar = Calendar.getInstance();
	    month = calendar.get(Calendar.MONTH)+1;
	    date = calendar.get(Calendar.DATE);
	    hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
        timeNowLabel.setText("Current Time: 6/16 17:14:43");
	    timeNowLabel.setBounds(1131, 84, 282, 21);
	    panel.add(timeNowLabel);
	    peopleWaiting = sqlQuery.upadatePeopleWaiting(this.postID);
	    
	    infoDataList = sqlQuery.findPost(this.postID);
	    for(ProcessData data : infoDataList) {
	    	//resize imageIcon with the same size of a Jlabel
	    	ImageIcon MyImage = new ImageIcon(data.getGraph());
		    Image img = MyImage.getImage();
		    Image newImg = img.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
		    ImageIcon image = new ImageIcon(newImg);
		    
		    String minPrice;
		    if(data.getPrice()==0) {
		    	minPrice = "Free";
		    }else {
		    	minPrice = data.getPrice()+"";
		    }
	    	
	    	imageLabel.setIcon(image);
	    	foodNameLabel.setText(data.getProductName());
	    	minPriceLabel.setText("Minimun Price: "+ minPrice);
	    	postContentLabel.setText(data.getPostContent());
	    	locationLabel.setText("Location: "+data.getLocation());
	    	pickupStartLabel.setText("Pick up Time: " + data.getStartTime()+" ~ "+data.getEndTime());
	    	totalAmountLabel.setText("Amount: " + data.getAmount());//directly show the amount stored in the database
	    	remainingLabel.setText("Left Amount: " + data.getAmount());//revised0617
        }

	    
	    updateTimeBtn = new JButton("Update Time");
	    updateTimeBtn.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		calendar = Calendar.getInstance();
	    		month = calendar.get(Calendar.MONTH)+1;
	    		date = calendar.get(Calendar.DATE);
		    	hour = calendar.get(Calendar.HOUR_OF_DAY);
		        minute = calendar.get(Calendar.MINUTE);
		        second = calendar.get(Calendar.SECOND);
		        timeNowLabel.setText("Current Time: "+ month + "/" +date+ " " + hour + ":" + minute+ ":" + second);
		        timeNowLabel.repaint();
	    	}
	    });
	    updateTimeBtn.setFont(new Font("Arial", Font.PLAIN, 20));
	    updateTimeBtn.setBounds(969, 79, 154, 31);
	    panel.add(updateTimeBtn);
	    
	    lblNewLabel = new JLabel("NCCU HUNGER SAVER");
		panel.add(lblNewLabel);
		lblNewLabel.setBackground(new Color(240, 240, 240));
		
		postponeReminderLabel = new JLabel("Not Postponed");
	    postponeReminderLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    postponeReminderLabel.setFont(new Font("Arial", Font.PLAIN, 20));
	    postponeReminderLabel.setForeground(new Color(128, 0, 0));
	    	    
	    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    lblNewLabel.setBounds(0, 5, 1536, 34);
	    lblNewLabel.setFont(font);
	    
	    actionPanel = new JPanel();
	    actionPanel.setBackground(new Color(255, 255, 224));
	    actionPanel.setBounds(183, 610, 938, 128);
	    panel.add(actionPanel);
	    actionPanel.setLayout(new GridLayout(2,3));
	    
	    	   
	    peopleWaitingLabel = new JLabel("current waiting people"); //SQL count
	    peopleWaitingLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    actionPanel.add(peopleWaitingLabel);
	    peopleWaitingLabel.setFont(new Font("Arial", Font.PLAIN, 20));
	    peopleWaitingLabel.setForeground(new Color(128, 0, 0));
	    peopleWaitingLabel.setText("Current Waiting People:" + peopleWaiting); 
	    
	    JTextField num_1 = new JTextField();
	    actionPanel.add(num_1);
	    num_1.setForeground(new Color(192, 192, 192));
	    num_1.setHorizontalAlignment(SwingConstants.CENTER);
	    num_1.setFont(new Font("Arial", Font.PLAIN, 20));
	    num_1.setText("amount...");
	    
	    JButton postpone_yn = new JButton("Postpone");
	    actionPanel.add(postpone_yn);
	    postpone_yn.setFont(new Font("Arial", Font.BOLD, 20));
	    postpone_yn.setEnabled(false);
	    
	    
	    postpone_yn.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		sqlQuery.delayPickup(userID, postID);
	    		postponeReminderLabel.setText("postponed");
	    		postpone_yn.setEnabled(false);
	    		JOptionPane.showMessageDialog(null, "Your product has been reserved for 10 minutes, please come and pick it up, otherwise you will be disqualified", "Postponed Successfully", JOptionPane.INFORMATION_MESSAGE);
	    	}
	    });
	    
	    actionPanel.add(remainingLabel);
	    
	    JButton waitBtn = new JButton("Wait");
	    actionPanel.add(waitBtn);
	    waitBtn.setFont(new Font("Arial", Font.BOLD, 20));
	    
	    waitBtn.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	if(sqlQuery.checkRepetitivePlaceHolder(userID,postID)==true) {//user cannot take the same product again
	                JOptionPane.showMessageDialog(null, "The product can only be purchased once by the same user.", "Waiting Failed", JOptionPane.ERROR_MESSAGE);
	                waitBtn.setEnabled(false);
	                num_1.setText("");
	                num_1.setEditable(false);
	        	}else {
	        		if (num_1.getText().isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Please enter the quantity you want", "Waiting Failed", JOptionPane.ERROR_MESSAGE);
		            } else {
		                try {
		                    int quantity = Integer.parseInt(num_1.getText());
		                    remaining = Integer.parseInt(totalAmountLabel.getText().substring(8));
		                    System.out.println("remaining:"+remaining);
		                    //make sure there has enough product
		                    if(remaining<quantity) {
		                    	JOptionPane.showMessageDialog(null, "Please change the quantity you want", "Insufficient Quantity", JOptionPane.ERROR_MESSAGE);
		                    	num_1.setText("");//clear field
		                    }else {
		                    	sqlQuery.placeHolder(userID, postID, quantity); 
					            JOptionPane.showMessageDialog(null, "Please be sure to pick up the food!", "Waiting Successfully", JOptionPane.INFORMATION_MESSAGE);
					            num_1.setText("");//clear field
					            num_1.setEditable(false);//user cannot edit again
					            peopleWaiting = sqlQuery.upadatePeopleWaiting(postID);
					            remaining -= quantity;
					            System.out.println("peopleWaiting:"+peopleWaiting);
					            System.out.println("remaining2:"+remaining);
					            sqlQuery.updateTotalFoodAmount(postID,remaining);//update the FoodAmount in the database
					            remainingLabel.setText("Remaining Amount: " + remaining);
					            peopleWaitingLabel.setText("Current Waiting People: " + peopleWaiting);
					            postpone_yn.setEnabled(true);//user can postpone from now on 
					            waitBtn.setEnabled(false); //user cannot press the wait btn from now on 
					            finish_pick.setEnabled(true);//user can finish the transaction from now on 
		                    }
		                } catch (NumberFormatException ex) {
		                    JOptionPane.showMessageDialog(null, "Please enter a valid quantity", "Waiting Failed", JOptionPane.ERROR_MESSAGE);
		                }
		            }
	        	} 
	        }
	    });
	    actionPanel.add(postponeReminderLabel);
	    
	    String salerName = sqlQuery.getSalerName(this.postID);
	    JLabel salerNameLabel = new JLabel("Provider: "+salerName);
	    salerNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
	    salerNameLabel.setBounds(627, 79, 235, 31);
	    panel.add(salerNameLabel);
	    
	    returnBtn = new JButton("Return to the Homepage");
	    returnBtn.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		frame.dispose();
	        	SignoutPage signoutPage = new SignoutPage(userID);
	        	signoutPage.getFrame().setVisible(true);
	    	}
	    });
	    returnBtn.setFont(new Font("Dialog", Font.PLAIN, 20));
	    returnBtn.setBackground(new Color(255, 211, 0));
	    returnBtn.setBounds(1163, 10, 264, 40);
	    panel.add(returnBtn);
	    
	    finish_pick.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		finish_pick.setEnabled(false);
	    		num_1.setEditable(false);
	    		waitBtn.setEnabled(false);
	    		postpone_yn.setEnabled(false);
	    		finishPick = true;
	    		finish_pick.setText("finish pickup!");
	    		
	    		if(minPriceLabel.getText().substring(15).equals("Free")) {
	            	JOptionPane.showMessageDialog(null, "This transction has finished", "Transaction Finished", JOptionPane.INFORMATION_MESSAGE);
	            	frame.dispose();
	            	SignoutPage signoutPage = new SignoutPage(userID);
		        	signoutPage.getFrame().setVisible(true);
	    		}else {
	    			JOptionPane.showMessageDialog(null, "This transction hasn't finished, please upload the payment detail", "Upload Payment", JOptionPane.INFORMATION_MESSAGE);
	            	frame.dispose();
	            	PayPage payPage = new PayPage(userID, postID);
	            	payPage.getFrame().setVisible(true);
	    		}
	    	}
	    });
	    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
    }
    
    /*public void openPostView() {
    	PostView frame = new PostView(postID,userID);
    }*/

	public int getTotalAmount() {
		return totalAmount;
	}
}