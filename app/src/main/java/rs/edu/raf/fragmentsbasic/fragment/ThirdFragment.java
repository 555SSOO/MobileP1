package rs.edu.raf.fragmentsbasic.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import rs.edu.raf.fragmentsbasic.R;
import rs.edu.raf.fragmentsbasic.adapter.CategoryPriceAdapter;
import rs.edu.raf.fragmentsbasic.model.Category;
import rs.edu.raf.fragmentsbasic.viewmodel.MainViewModel;

public class ThirdFragment extends Fragment {

    private MainViewModel mViewModel;
    private CategoryPriceAdapter mAdapter;
    private TextView textView;
    private PieChart pieChart;

    public static ThirdFragment newInstance() {
        return new ThirdFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);


        textView = view.findViewById(R.id.tv_fragment_third_content);

        pieChart = view.findViewById(R.id.piechart_1);;
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(true);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);


        RecyclerView recycler = view.findViewById(R.id.rv_category_list_third);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        recycler.setLayoutManager(manager);
        mAdapter = new CategoryPriceAdapter();
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


                        ArrayList<PieEntry> yValues = new ArrayList<>();
                        for(Category category : categories){
                            yValues.add(new PieEntry(category.getmSum().floatValue(),category.getmName()));
                        }

                        PieDataSet dataSet = new PieDataSet(yValues, "Categories");
                        dataSet.setSliceSpace(3f);
                        dataSet.setSelectionShift(5f);
                        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                        PieData pieData = new PieData((dataSet));
                        pieData.setValueTextSize(10f);
                        pieData.setValueTextColor(Color.YELLOW);
                        pieChart.setData(pieData);

                        Double sum = 0.0;
                        for(Category category:categories){
                            sum += category.getmSum();
                        }
                        textView.setText("Ukupno " + sum);
                        Toast.makeText(ThirdFragment.this.getContext(), "UPDATED 3", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
