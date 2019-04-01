package rs.edu.raf.fragmentsbasic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import rs.edu.raf.fragmentsbasic.R;
import rs.edu.raf.fragmentsbasic.adapter.ExpenseAdapter;
import rs.edu.raf.fragmentsbasic.model.Expense;
import rs.edu.raf.fragmentsbasic.viewmodel.MainViewModel;

public class ThirdFragment extends Fragment {

    private MainViewModel viewModel;
    private ExpenseAdapter mExpenseAdapter;

    public static ThirdFragment newInstance() {
        return new ThirdFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        EditText editText = view.findViewById(R.id.et_fragment_second_filter);
        Button button = view.findViewById(R.id.btn_fragment_second);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filter = editText.getText().toString();
                viewModel.setFilter(filter);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.rv_fragment_second);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        mExpenseAdapter = new ExpenseAdapter();

        mExpenseAdapter.setOnImageClickCallback(new ExpenseAdapter.OnImageClickCallback() {
            @Override
            public void onImageClick() {
                Toast.makeText(getContext(), "OPEN NEW ACTIVITY", Toast.LENGTH_SHORT).show();
            }
        });

        mExpenseAdapter.setOnItemRemoveCallback(new ExpenseAdapter.OnItemRemoveCallback() {
            @Override
            public void onItemRemove(int position) {
                Toast.makeText(getContext(), "REMOVE EXPENSE ON POSITION " + position, Toast.LENGTH_SHORT).show();
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
    }
}
