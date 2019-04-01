package rs.edu.raf.fragmentsbasic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import rs.edu.raf.fragmentsbasic.R;
import rs.edu.raf.fragmentsbasic.model.Category;
import rs.edu.raf.fragmentsbasic.model.Expense;
import rs.edu.raf.fragmentsbasic.util.Util;
import rs.edu.raf.fragmentsbasic.viewmodel.MainViewModel;

public class FirstFragment extends Fragment {

    private MainViewModel mViewModel;
    private TextView mContentTv;


    public static FirstFragment newInstance(String arg) {
        return new FirstFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        mContentTv = view.findViewById(R.id.tv_fragment_first_content);
        EditText expenseNameET = view.findViewById(R.id.et_fragment_first_name);
        EditText priceET = view.findViewById(R.id.et_fragment_first_price);
        Spinner categorySP = view.findViewById(R.id.sp_fragment_first_category);
        Button button = view.findViewById(R.id.btn_fragmnet_first_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String name = expenseNameET.getText().toString();
                Double price = Double.parseDouble(priceET.getText().toString());
                Category category = (Category) categorySP.getSelectedItem();

                Expense expense = new Expense(Util.generateId(), name, price, category);
                mViewModel.addExpense(expense);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mViewModel.getExpensesLiveData().observe(getViewLifecycleOwner(),
                new Observer<List<Expense>>() {
                    @Override
                    public void onChanged(List<Expense> expenses) {
                        Toast.makeText(FirstFragment.this.getContext(), expenses.size()+"", Toast.LENGTH_SHORT).show();
                    }
                });

        mViewModel.getExpenseCountLiveData().observe(getViewLifecycleOwner(),
                new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {

                        // Parametrized string
                        String parametrizedString = getResources().getString(R.string.expense_count, integer);

                        // Plurals
                        String pluralString = getResources().getQuantityString(R.plurals.expense_count, integer, integer);

                        mContentTv.setText(pluralString);
                    }
                });
    }
}
