public class PostInfo {
	private byte[] image;
	private int postID;
    private String foodName;
    private String foodLocation;
    private int foodAmount;
    private String pickupDDL;
    private int minPrice;

    public PostInfo(byte[] image, int postID, String foodName, String foodLocation, int foodAmount, String pickupDDL, int minPrice) {
        this.image = image;
        this.postID = postID;
    	this.foodName = foodName;
        this.foodLocation = foodLocation;
        this.foodAmount = foodAmount;
        this.pickupDDL = pickupDDL;
        this.minPrice = minPrice;
    }

    

	// getters
    public byte[] getImage() {
		return image;
	}
	
	public int getPostID() {
		return postID;
	}
	
	public String getFoodName() {
        return foodName;
    }

    public String getFoodLocation() {
        return foodLocation;
    }

    public int getFoodAmount() {
        return foodAmount;
    }

    public String getPickupDDL() {
        return pickupDDL;
    }

    public int getMinPrice() {
        return minPrice;
    }
}