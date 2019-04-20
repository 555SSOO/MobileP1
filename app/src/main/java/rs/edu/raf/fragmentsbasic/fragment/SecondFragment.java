package rs.edu.raf.fragmentsbasic.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import rs.edu.raf.fragmentsbasic.R;
import rs.edu.raf.fragmentsbasic.activity.DetailActivity;
import rs.edu.raf.fragmentsbasic.activity.MainActivity;
import rs.edu.raf.fragmentsbasic.adapter.ExpenseAdapter;
import rs.edu.raf.fragmentsbasic.model.Category;
import rs.edu.raf.fragmentsbasic.model.Expense;
import rs.edu.raf.fragmentsbasic.viewmodel.MainViewModel;


public class SecondFragment extends Fragment {

    private MainViewModel viewModel;
    private ExpenseAdapter mExpenseAdapter;
    private Spinner mCategorySpinner;

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        EditText editText = view.findViewById(R.id.et_fragment_second_filter);
        Button button = view.findViewById(R.id.btn_fragment_second);
        mCategorySpinner = view.findViewById(R.id.sp_fragment_second_category);;

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String filter = editText.getText().toString();
                viewModel.setFilter(filter);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setCategoryFilter((Category) mCategorySpinner.getSelectedItem());
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.rv_fragment_second);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(manager);
        mExpenseAdapter = new ExpenseAdapter();

        mExpenseAdapter.setOnImageClickCallback(new ExpenseAdapter.OnImageClickCallback() {
            @Override
            public void onImageClick(int id) {

                viewModel.getExpensesLiveData().observe(getViewLifecycleOwner(),
                        new Observer<List<Expense>>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onChanged(List<Expense> expenses) {
                                DetailActivity.setExpense(expenses.stream().filter(expense -> id == expense.getId()).findFirst());
                            }
                        });

                //DetailActivity.setmViewModel(viewModel);

                Intent intent = new Intent(SecondFragment.this.getContext(), DetailActivity.class);
                intent.putExtra("id", String.valueOf(id));
                startActivityForResult(intent, 1);

                Toast.makeText(getContext(), "OPEN NEW ACTIVITY", Toast.LENGTH_SHORT).show();
            }
        });

        mExpenseAdapter.setOnItemRemoveCallback(new ExpenseAdapter.OnItemRemoveCallback() {
            @Override
            public void onItemRemove(int id) {

                viewModel.removeExpense(id);
                Toast.makeText(getContext(), "Removing expense..", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(mExpenseAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        viewModel.getExpensesLiveData().observe(getViewLifecycleOwner(),
                new Observer<List<Expense>>() {
                    @Override
                    public void onChanged(List<Expense> expenses) {
                        mExpenseAdapter.setData(expenses);
                    }
                });

        viewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(),
                categories -> {
                    // Init spinner array over all categories
                    ArrayAdapter<Category> adapter =
                            new ArrayAdapter<>(SecondFragment.this.getContext(), android.R.layout.simple_spinner_dropdown_item, categories);
                    adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                    mCategorySpinner.setAdapter(adapter);
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                viewModel.removeExpense(Integer.valueOf(result));
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Just closed
            }
        }
    }
}
