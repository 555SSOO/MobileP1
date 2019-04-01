package rs.edu.raf.fragmentsbasic.model;

public class Expense {

    private int mId;
    private String mName;
    private Double mPrice;
    private String mImageUrl;
    private Category mCategory;

    public Expense(int id, String name, Double price, Category category) {
        this(id, name, price, "https://picsum.photos/200/300?image=" + id % 100, category);
    }

    public Expense(int id, String name, Double price, String imageUrl, Category category) {
        mId = id;
        mName = name;
        mPrice = price;
        mImageUrl = imageUrl;
        mCategory = category;
    }

    public int getId() {
        return mId;
    }

    public void setmId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public Double getmPrice() {
        return mPrice;
    }

    public void setmPrice(Double mPrice) {
        this.mPrice = mPrice;
    }

    public Category getmCategory() {
        return mCategory;
    }

    public void setmCategory(Category mCategory) {
        this.mCategory = mCategory;
    }
}
