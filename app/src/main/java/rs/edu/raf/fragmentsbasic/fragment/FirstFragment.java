package rs.edu.raf.fragmentsbasic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import rs.edu.raf.fragmentsbasic.R;
import rs.edu.raf.fragmentsbasic.model.Category;
import rs.edu.raf.fragmentsbasic.model.Expense;
import rs.edu.raf.fragmentsbasic.util.Util;
import rs.edu.raf.fragmentsbasic.viewmodel.MainViewModel;

public class FirstFragment extends Fragment {

    private MainViewModel mViewModel;
    private Spinner mCategorySpinner;


    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        EditText expenseNameET = view.findViewById(R.id.et_fragment_first_name);
        EditText priceET = view.findViewById(R.id.et_fragment_first_price);
        mCategorySpinner = view.findViewById(R.id.sp_fragment_first_category);
        Button button = view.findViewById(R.id.btn_fragmnet_first_add);
        button.setOnClickListener(v -> {

            String name = expenseNameET.getText().toString();
            Double price = Double.parseDouble(priceET.getText().toString());
            Category category = (Category) mCategorySpinner.getSelectedItem();
//            category.setmSum(category.getmSum() + price);
            Expense expense = new Expense(Util.generateId(), name, price, category);
            mViewModel.addExpense(expense);
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mViewModel.getExpensesLiveData().observe(getViewLifecycleOwner(),
                expenses -> Toast.makeText(FirstFragment.this.getContext(), expenses.size()+"", Toast.LENGTH_SHORT).show());

        mViewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(),
                categories -> {
                    // Init spinner array over all categories
                    ArrayAdapter<Category> adapter =
                            new ArrayAdapter<>(FirstFragment.this.getContext(), android.R.layout.simple_spinner_dropdown_item, categories);
                    adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                    mCategorySpinner.setAdapter(adapter);
                });

    }
}
