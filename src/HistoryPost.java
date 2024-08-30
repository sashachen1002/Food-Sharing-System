import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HistoryPost {

	private JFrame frame;
	private JScrollPane scrollPane;
	private DefaultTableModel model;
	private JTable table;
	private List<ProcessData> historyPosts;
	
	private SQLQuery sqlQuery = new SQLQuery();
	
	private String userID;
	private JLabel username;
	private JButton returnBtn;
	private JButton btnDelete;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HistoryPost window = new HistoryPost("aaa123");
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
	public HistoryPost(String userID) {
		this.userID = userID;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("History Post");
		frame.getContentPane().setBackground(new Color(255, 255, 224));
		/*frame.setBounds(100, 100, 1920, 1080);*/
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		createDesign();
		createJTable();
	}
	
	public void createJTable() {
		model = new DefaultTableModel() {
			public Class<?> getColumnClass(int columnIndex) {
				return columnIndex == 0 ? Boolean.class : String.class; // 第一列的資料類型為Boolean，其餘列為String
		    }
		    public boolean isCellEditable(int row, int column) {
		    	return column == 0; // 僅允許編輯第一列的勾選欄
		    }
		};
		String[]columnNames = {"Check","Post ID","Name","Type","Location","Remaining","Waiting","Start Time","End Time","Min Price"};//移除圖片
        model.setColumnIdentifiers(columnNames);
		
        historyPosts = sqlQuery.findHitoryPost(userID);
        for(ProcessData data : historyPosts) {
            model.addRow(new Object[]{
            		false,
            		data.getPostID(),
            		data.getProductName(),
            		data.getType(),
            		data.getLocation(),
            		data.getAmount(),
            		data.getPeopleWaiting(),
            		data.getStartTime(),
            		data.getEndTime(),
            		data.getPrice()
            });
        }
        
        
        table = new JTable(model);
        table.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
        table.setForeground(new Color(0, 0, 0));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("微軟正黑體", Font.BOLD, 18));
        table.setRowHeight(70);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 1; i < model.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        scrollPane = new JScrollPane();
        scrollPane.setBounds(110, 206, 1300, 536);
		scrollPane.setViewportView(table);
		frame.getContentPane().add(scrollPane);
		
		JButton btnSwitchToThe = new JButton("Switch to Waiting Food List");
		btnSwitchToThe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				OngoingTransaction ongoingTransaction = new OngoingTransaction(userID);
				ongoingTransaction.getFrame().setVisible(true);
			}
		});
		btnSwitchToThe.setFont(new Font("Dialog", Font.PLAIN, 20));
		btnSwitchToThe.setBackground(new Color(255, 211, 0));
		btnSwitchToThe.setBounds(110, 14, 370, 40);
		frame.getContentPane().add(btnSwitchToThe);
	}
	
	public void createDesign() {
		JLabel lblNewLabel = new JLabel("NCCU HUNGER SAVER");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 32));
		lblNewLabel.setBackground(SystemColor.menu);
		lblNewLabel.setBounds(0, 12, 1540, 34);
		frame.getContentPane().add(lblNewLabel);
		
		username = new JLabel("Hi, " + userID +". You can remove the food you posted here!");
		username.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 24));
		username.setBounds(110, 159, 779, 34);
		frame.getContentPane().add(username);
		
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
		returnBtn.setBounds(1146, 14, 264, 40);
		frame.getContentPane().add(returnBtn);
		
		btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeSelectedData();
			}
		});
		btnDelete.setFont(new Font("Dialog", Font.BOLD, 20));
		btnDelete.setBackground(new Color(255, 211, 0));
		btnDelete.setBounds(1287, 162, 123, 34);
		frame.getContentPane().add(btnDelete);
	}
	
	public void removeSelectedData() {
		int[] selectedRows = table.getSelectedRows();

        if (selectedRows.length > 0) {
            int option = JOptionPane.showConfirmDialog(frame, "Are you sure to delete this post?", "Delete Post", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();

                //delete begin from the last column
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int selectedRow = selectedRows[i];
                    boolean success = false;
                    int postID = (int) model.getValueAt(selectedRow, 1);
                    
                    success = sqlQuery.deleteHitoryPost(postID);
                    //Remove the data from the table
                    model.removeRow(selectedRow); 
                    
                    if (success==false) {
                    	JOptionPane.showMessageDialog(null, "You cannot delete this post.","Delete Failed", JOptionPane.INFORMATION_MESSAGE);
                    }else {
                    	JOptionPane.showMessageDialog(null, "This post has been deleted.","Delete Successfully", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select the post you want to delete first.", "Delete Failed", JOptionPane.WARNING_MESSAGE);
        }
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
}
