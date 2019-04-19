package rs.edu.raf.fragmentsbasic.model;

public class Category {

    private int mId;
    private String mName;

    public Category(int id, String name) {
        mId = id;
        mName = name;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    @Override
    public String toString(){
        return this.mName;
    }

}
