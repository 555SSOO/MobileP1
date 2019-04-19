package rs.edu.raf.fragmentsbasic.viewmodel;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import rs.edu.raf.fragmentsbasic.model.Category;
import rs.edu.raf.fragmentsbasic.model.Expense;
import rs.edu.raf.fragmentsbasic.util.Util;

import static java.util.stream.Collectors.summingDouble;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Expense>> mExpensesLiveData;
    private LiveData<Integer> mExpenseCountLiveData;
    private LiveData<Map<Category, Double>> mCategorySumLiveData;
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
        mCategoryLiveData.setValue(mCategoryList);

        // Add dummy expenses
        for (int i = 0; i < 10; i++) {
//            mCategoryList.get(2).setmSum(mCategoryList.get(2).getmSum() + (double) i);
            mExpenseList.add(new Expense(Util.generateId(), "Expense " + i, (double) i, mCategoryList.get(2)));
        }
        mExpensesLiveData.setValue(mExpenseList);

        // Populate expenses count live data
        mExpenseCountLiveData = Transformations.map(mExpensesLiveData, List::size);

        // Populate sum of categories live data
        mCategorySumLiveData = Transformations.map(mExpensesLiveData,
                new Function<List<Expense>, Map<Category, Double>>() {
                    @Override
                    @TargetApi(Build.VERSION_CODES.N)
                    public Map<Category, Double> apply(List<Expense> input) {
                        return input.stream().collect(
                                Collectors.groupingBy(Expense::getmCategory, summingDouble(Expense::getmPrice))
                        );
                    }
                }
        );
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

    public Expense getExpense(int id) {
        for(Expense expense : mExpenseList){
            if(expense.getId() == id) return expense;
        }
        return null;
    }

    public void removeExpense(int id) {
        Expense expense = null;
        for(int i = 0; i < mExpenseList.size(); i++){
            expense = mExpenseList.get(i);
            if (expense.getId() == id) {

                break;
            }
        }
        mExpenseList.remove(expense);
        mExpensesLiveData.setValue(mExpenseList);
    }

    public void addCategory(Category category) {
        mCategoryList.add(category);
        mCategoryLiveData.setValue(mCategoryList);
    }

    public void removeCategory(Category category) {
        mCategoryList.remove(category);
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

    public LiveData<Map<Category, Double>> getmCategorySumLiveData() {
        return mCategorySumLiveData;
    }
}
