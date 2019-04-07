package rs.edu.raf.fragmentsbasic.model;

import java.util.Date;

public class Expense {

    private int mId;
    private String mName;
    private Double mPrice;
    private Category mCategory;
    private Date mDate;

    public Expense(int id, String name, Double price, Category category) {
        mId = id;
        mName = name;
        mPrice = price;
        mCategory = category;
        mDate = new Date();
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

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }
}
