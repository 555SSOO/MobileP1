package rs.edu.raf.fragmentsbasic.util;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import rs.edu.raf.fragmentsbasic.model.Expense;

public class ExpenseDiffCallback extends DiffUtil.Callback {

    private List<Expense> mOldList;
    private List<Expense> mNewList;

    public ExpenseDiffCallback(List<Expense> oldList, List<Expense> newList){
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
        Expense oldExpense = mOldList.get(oldItemPosition);
        Expense newExpense = mNewList.get(newItemPosition);
        return oldExpense.getId() == newExpense.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Expense oldExpense = mOldList.get(oldItemPosition);
        Expense newExpense = mNewList.get(newItemPosition);
        return oldExpense.getName().equals(newExpense.getName());
    }
}
