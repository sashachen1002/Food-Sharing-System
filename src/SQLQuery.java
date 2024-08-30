import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class SQLQuery {
	
	List<ProcessData> data = new ArrayList<ProcessData>();
	
	//WindowDemo模板
	public boolean uploadProduct(ProcessData uploadProduct){
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("INSERT INTO Post (UserID,Memo,Image,FoodName,FoodType,FoodLocation,FoodAmount,PickupTime,PickupDDL,MinPrice) VALUES (?,?, ?, ?, ?, ?,?,?,?,?)");
			pre.setString(1,uploadProduct.getAccount()); 
			pre.setString(2,uploadProduct.getPostContent());
			pre.setBytes(3,uploadProduct.getGraph());
			pre.setString(4,uploadProduct.getProductName());
			pre.setString(5,uploadProduct.getType());
			pre.setString(6,uploadProduct.getLocation());
			pre.setInt(7,uploadProduct.getAmount());
			pre.setString(8,uploadProduct.getStartTime());
			pre.setString(9,uploadProduct.getEndTime());
			pre.setInt(10,uploadProduct.getPrice());
			return pre.executeUpdate() > 0;
		} catch (SQLException e) {
			return false;
		}
	}
	
	//PayPageg //upload payment
	public boolean uploadPayment(String userID, int postID, byte[] payment){
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("UPDATE Placeholder SET Payment = ? WHERE UserID = ? AND PostID = ?");
			pre.setBytes(1, payment);
			pre.setString(2, userID);
			pre.setInt(3, postID);
			return pre.executeUpdate()>0;
		} catch (SQLException e) {
			return false;
		}
	}
	
	//RegisterPage
	public boolean registration(ProcessData registration){
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("INSERT INTO NCCUUser (UserID,Password,UserName) VALUES (?,?,?)");
			//資料庫還沒有建Account//已建立UserID
			//確認帳號沒有重複
			if(checkRepetitiveUser(registration.getAccount())==true){
				return false;
			}else {
				pre.setString(1,registration.getAccount());
				pre.setString(2,registration.getPassword());
				pre.setString(3,registration.getUserName());
				return pre.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace(); 
			return false;
		}
	}
	
	//RegisterPage //確認User有無重複
	public boolean checkRepetitiveUser(String userID){
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT * FROM NCCUUser WHERE UserID = ?");
			pre.setString(1, userID);
			ResultSet rs = pre.executeQuery();
			//確認帳號沒有重複
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		return false;
	}
	
	//LoginPage
	public String checkUserWithUserID(String userID, String password){//修改中
		String result = "";
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT * FROM NCCUUser WHERE UserID = ?"); 
			pre.setString(1, userID);
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {//有找到這個使用者
				String DBPassword = rs.getString("Password");
				if(DBPassword.equals(password)) {//使用者輸入的密碼與資料庫裡的相同
					result = "Login Successfully";
				}else {//使用者輸入的密碼與資料庫裡的不同
					result = "Wrong Password";
				}
			}
		} catch (SQLException e) {//沒有找到這個使用者
			result = "No such user";
			e.printStackTrace();
		}
		return result;
	}
	
	//PostView
	public List<ProcessData> findPost(int postID){
		data.clear();
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT * FROM Post WHERE PostID = ?");  //預設用PostID來找
			pre.setInt(1, postID);
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				ProcessData data = new ProcessData();
				data.setAccount(rs.getString("UserID"));
				data.setPostID(rs.getInt("PostID"));
				data.setGraph(rs.getBytes("Image"));
				data.setProductName(rs.getString("FoodName"));
				data.setType(rs.getString("FoodType"));
				data.setLocation(rs.getString("FoodLocation"));
				data.setPostContent(rs.getString("Memo"));
				data.setAmount(rs.getInt("FoodAmount"));
				data.setPeopleWaiting(rs.getInt("PeopleWaiting"));
				data.setStartTime(rs.getString("PickupTime"));
				data.setEndTime(rs.getString("PickupDDL"));
				data.setPrice(rs.getInt("MinPrice"));
				this.data.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.data;	
		
	}
	
	//PostView //擷取UserName
	public String getSalerName(int postID){
		String salerName = "";
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT n.UserName FROM Post AS p, NCCUUser AS n WHERE p.UserID = n.UserID AND p.PostID = ?");  //預設用PostID來找
			pre.setInt(1, postID);
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				salerName = rs.getString("UserName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return salerName;
	}
	
	//PostView //卡位 //確認同個使用者有無重複卡位
	public boolean checkRepetitivePlaceHolder(String userID, int postID){
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT UserID FROM Placeholder WHERE PostID = ?");
			pre.setInt(1,postID);
			ResultSet rs = pre.executeQuery();
			//確認帳號沒有重複
			while(rs.next()) {
				if(rs.getString("UserID").equals(userID)) {
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//PostView //卡位
	public boolean placeHolder(String userID, int postID, int purchaseAmount){
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("INSERT INTO Placeholder (UserID,PostID,Amount) VALUES (?,?,?)");
			pre.setString(1,userID);
			pre.setInt(2,postID);
			pre.setInt(3,purchaseAmount);
			return pre.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace(); 
			return false;
		}
	}
	
	//PostView //延後
	public void delayPickup(String userID, int postID){
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("UPDATE Placeholder SET Delay = ? WHERE UserID = ? AND PostID = ?"); 
			pre.setInt(1, 1);
			pre.setString(2, userID);
			pre.setInt(3, postID);
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//PostView //計算等待人數
	public int countPeopleWaiting(int postID){
		int count = 0;
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT COUNT(*) FROM Placeholder WHERE PostID = ?");
			pre.setInt(1, postID);
			ResultSet rs = pre.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1); // 獲取結果集中的第一列的值，即 SUM
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	//PostView //更新等待人數(畫面+資料庫的)
	public int upadatePeopleWaiting(int postID){
		int peopleWaiting = countPeopleWaiting(postID);
		try {
			PreparedStatement pre = ConnectDB.getCon()
						.prepareStatement("UPDATE Post SET PeopleWaiting = ? WHERE PostID = ?");
			pre.setInt(1, peopleWaiting);
			pre.setInt(2, postID);
			pre.executeUpdate();//更新資料庫
		} catch (SQLException e) {
				e.printStackTrace();
		}
		return peopleWaiting;
	}
	
	//PostView //計算商品剩餘數量
	public int countTotalFoodAmount(int postID){
		int sum = 0;
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT SUM(Amount) FROM Placeholder WHERE PostID = ?");
			pre.setInt(1, postID);
			ResultSet rs = pre.executeQuery();
			
			if (rs.next()) {
				sum = rs.getInt(1); // 獲取結果集中的第一列的值，即 SUM
		    }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sum;
	}
	
	//
	public int getFoodAmount(int postID){
		int foodAmount = 0;
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT FoodAmount FROM Post WHERE PostID = ?");  //預設用PostID來找
			pre.setInt(1, postID);
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				foodAmount = rs.getInt("FoodAmount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return foodAmount;
	}
	
	//PostView //更新商品剩餘數量
	public void updateTotalFoodAmount(int postID, int remaining){
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("UPDATE Post SET FoodAmount = ? WHERE PostID = ?");
			pre.setInt(1, remaining);
			pre.setInt(2, postID);
			pre.executeUpdate();//更新資料庫
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//HistoryPost
	public List<ProcessData> findHitoryPost(String userID){
		data.clear();
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT * FROM Post WHERE UserID = ?");  //預設用PostID來找
			pre.setString(1, userID);
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				ProcessData data = new ProcessData();
				data.setPostID(rs.getInt("PostID"));
				data.setProductName(rs.getString("FoodName"));
				data.setType(rs.getString("FoodType"));
				data.setLocation(rs.getString("FoodLocation"));
				data.setAmount(rs.getInt("FoodAmount"));
				data.setPeopleWaiting(rs.getInt("PeopleWaiting"));
				data.setStartTime(rs.getString("PickupTime"));
				data.setEndTime(rs.getString("PickupDDL"));
				data.setPrice(rs.getInt("MinPrice"));
				this.data.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.data;
	}
	
	//HistoryPost //Delete
	public boolean deleteHitoryPost(int postID){
		data.clear();
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("DELETE FROM Post WHERE PostID = ?");  //預設用PostID來找
			pre.setInt(1, postID);
			return pre.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//OngoingTransaction
	public List<ProcessData> findOngoingTransaction(String userID){
		data.clear();
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("SELECT po.PostID, po.FoodName, po.FoodLocation, po.PickupTime, po.PickupDDL, po.MinPrice, pl.Amount, pl.Payment, pl.Delay "
							+ "FROM Placeholder AS pl, Post AS po "
							+ "WHERE pl.PostID = po.PostID AND pl.UserID = ?");  //預設用PostID來找
			pre.setString(1, userID);
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				ProcessData data = new ProcessData();
				data.setPostID(rs.getInt("po.PostID"));
				data.setProductName(rs.getString("po.FoodName"));
				data.setLocation(rs.getString("po.FoodLocation"));
				data.setEndTime(rs.getString("po.PickupDDL"));
				data.setPrice(rs.getInt("po.MinPrice"));
				data.setPickupAmount(rs.getInt("pl.Amount"));
				data.setPayment(rs.getBytes("pl.Payment"));
				data.setDelay(rs.getInt("pl.Delay"));
				this.data.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.data;
	}
	
	//OngoingTransaction //Delete //update total amount
	public boolean deleteOngoingTransaction(int postID, String userID){
		data.clear();
		try {
			PreparedStatement pre = ConnectDB.getCon()
					.prepareStatement("DELETE FROM Placeholder WHERE PostID = ? AND UserID = ?");  //預設用PostID來找
			pre.setInt(1, postID);
			pre.setString(2, userID);
			return pre.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// HomePage // get posts info
	public List<PostInfo> allPostInfo(){
		List<PostInfo> postInfos = new ArrayList<>();
	    
		try {
	        Connection con = ConnectDB.getCon();
	        String query = "SELECT PostID, Image, FoodName, FoodLocation, FoodAmount, PickupDDL, MinPrice"
	                + " FROM Post";
	        Statement stat = con.createStatement();
	        ResultSet rs = stat.executeQuery(query);
	        
	        while (rs.next()) {
	            String foodName = rs.getString("FoodName");
	            int postID = rs.getInt("PostID");
	            String foodLocation = rs.getString("FoodLocation");
	            int foodAmount = rs.getInt("FoodAmount");
	            String pickupDDL = rs.getTime("PickupDDL").toString();
	            int minPrice = rs.getInt("MinPrice");
	            byte[] imageData = rs.getBytes("Image");

	            PostInfo postInfo = new PostInfo(imageData, postID, foodName, foodLocation, foodAmount, pickupDDL, minPrice);
	            postInfos.add(postInfo);
	        }

	        con.close(); 
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return postInfos;
	}

}