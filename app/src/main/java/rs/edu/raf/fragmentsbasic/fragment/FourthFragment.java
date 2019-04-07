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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import rs.edu.raf.fragmentsbasic.R;
import rs.edu.raf.fragmentsbasic.adapter.CategoryAdapter;
import rs.edu.raf.fragmentsbasic.adapter.ExpenseAdapter;
import rs.edu.raf.fragmentsbasic.model.Category;
import rs.edu.raf.fragmentsbasic.model.Expense;
import rs.edu.raf.fragmentsbasic.util.Util;
import rs.edu.raf.fragmentsbasic.viewmodel.MainViewModel;

public class FourthFragment extends Fragment {

    private MainViewModel mViewModel;
    private CategoryAdapter mAdapter;

    public static FourthFragment newInstance() {
        return new FourthFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);

        EditText categoryNameET = view.findViewById(R.id.et_category_name);
        Button button = view.findViewById(R.id.btn_fragmnet_first_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = categoryNameET.getText().toString();

                Category category = new Category(Util.generateId(), name);
                mViewModel.addCategory(category);
            }
        });

        RecyclerView recycler = view.findViewById(R.id.rv_category_list);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        recycler.setLayoutManager(manager);
        mAdapter = new CategoryAdapter();
        recycler.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mViewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(),
                new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        mAdapter.setData(categories);
                        Toast.makeText(FourthFragment.this.getContext(), categories.size()+"", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
