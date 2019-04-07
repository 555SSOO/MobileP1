package rs.edu.raf.fragmentsbasic.util;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import rs.edu.raf.fragmentsbasic.model.Category;

public class CategoryDiffCallback extends DiffUtil.Callback  {

    private List<Category> mOldList;
    private List<Category> mNewList;

    public CategoryDiffCallback(List<Category> oldList, List<Category> newList){
        mOldList = oldList;
        mNewList = newList;
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Category oldExpense = mOldList.get(oldItemPosition);
        Category newExpense = mNewList.get(newItemPosition);
        return oldExpense.getmId() == newExpense.getmId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Category oldExpense = mOldList.get(oldItemPosition);
        Category newExpense = mNewList.get(newItemPosition);
        return oldExpense.getmName().equals(newExpense.getmName());
    }

}
