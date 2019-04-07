package rs.edu.raf.fragmentsbasic.viewmodel;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import rs.edu.raf.fragmentsbasic.model.Category;
import rs.edu.raf.fragmentsbasic.model.Expense;
import rs.edu.raf.fragmentsbasic.util.Util;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Expense>> mExpensesLiveData;
    private LiveData<Integer> mExpenseCountLiveData;
    private List<Expense> mExpenseList;

    private MutableLiveData<List<Category>> mCategoryLiveData;
    private List<Category> mCategoryList;


    public MainViewModel() {

        // Init expense arrays
        mExpensesLiveData = new MutableLiveData<>();
        mExpenseList = new ArrayList<>();
        // Init category arrays
        mCategoryLiveData = new MutableLiveData<>();
        mCategoryList = new ArrayList<>();

        // Add dummy categories
        for (int i = 0; i < 5; i++) {
            mCategoryList.add(new Category(Util.generateId(), "Category " + i));
        }
        mExpensesLiveData.setValue(mExpenseList);

        // Add dummy expenses
        for (int i = 0; i < 50; i++) {
            mExpenseList.add(new Expense(Util.generateId(), "Expense " + i, (double) i, mCategoryList.get(2)));
        }
        mExpensesLiveData.setValue(mExpenseList);

        // Populate expenses count live data
        mExpenseCountLiveData = Transformations.map(mExpensesLiveData, List::size);
    }

    public MutableLiveData<List<Expense>> getExpensesLiveData() {
        return mExpensesLiveData;
    }

    public MutableLiveData<List<Category>> getCategoriesLiveData() {
        return mCategoryLiveData;
    }

    public void addExpense(Expense expense) {
        mExpenseList.add(expense);
        mExpensesLiveData.setValue(mExpenseList);
    }

    public void addCategory(Category category) {
        mCategoryList.add(category);
        mCategoryLiveData.setValue(mCategoryList);
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

    public void setCategoryFilter(Category categoryFilter) {
        String filter = categoryFilter.getmName().toLowerCase();
        List<Expense> filteredList = new ArrayList<>();
        for (Expense expense: mExpenseList) {
            if(expense.getmCategory().getmName().toLowerCase().startsWith(filter)){
                filteredList.add(expense);
            }
        }
        mExpensesLiveData.setValue(filteredList);
    }

    public LiveData<Integer> getExpenseCountLiveData() {
        return mExpenseCountLiveData;
    }
}
