package rs.edu.raf.fragmentsbasic.viewmodel;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import rs.edu.raf.fragmentsbasic.model.Expense;
import rs.edu.raf.fragmentsbasic.util.Util;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Expense>> mExpensesLiveData;
    private LiveData<Integer> mExpenseCountLiveData;

    private List<Expense> mExpenseList;


    public MainViewModel() {
        mExpensesLiveData = new MutableLiveData<>();
        mExpenseList = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            mExpenseList.add(new Expense(Util.generateId(), "Expense " + i));
        }
        mExpensesLiveData.setValue(mExpenseList);

        mExpenseCountLiveData = Transformations.map(mExpensesLiveData,
                new Function<List<Expense>, Integer>() {
                    @Override
                    public Integer apply(List<Expense> input) {
                        return input.size();
                    }
                }
        );
    }

    public MutableLiveData<List<Expense>> getExpensesLiveData() {
        return mExpensesLiveData;
    }

    public void addExpense(Expense expense) {
        mExpenseList.add(expense);
        mExpensesLiveData.setValue(mExpenseList);
    }

    public void setFilter(String filter) {
        filter = filter.toLowerCase();
        List<Expense> filteredList = new ArrayList<>();
        for (Expense expense: mExpenseList) {
            if(expense.getName().toLowerCase().startsWith(filter)){
                filteredList.add(expense);
            }
        }
        mExpensesLiveData.setValue(filteredList);
    }

    public LiveData<Integer> getExpenseCountLiveData() {
        return mExpenseCountLiveData;
    }
}
