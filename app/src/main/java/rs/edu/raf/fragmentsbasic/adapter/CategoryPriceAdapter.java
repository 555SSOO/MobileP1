package rs.edu.raf.fragmentsbasic.adapter;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import rs.edu.raf.fragmentsbasic.R;
import rs.edu.raf.fragmentsbasic.model.Category;
import rs.edu.raf.fragmentsbasic.util.CategoryDiffCallback;

public class CategoryPriceAdapter extends RecyclerView.Adapter<CategoryPriceAdapter.CategoryPriceHolder> {

    private List<Category> mCategoryDataSet;
    private Map<Category, Double> mCategorySumsDataSet;

    public CategoryPriceAdapter() {
        mCategoryDataSet = new ArrayList<>();
        mCategorySumsDataSet = new HashMap<>();
    }

    @NonNull
    @Override
    public CategoryPriceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_list_item, parent, false);
        return new CategoryPriceHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull CategoryPriceHolder holder, int position) {
        Category category = mCategoryDataSet.get(position);
        holder.text.setText(category.getmName());
        if(Objects.nonNull(mCategorySumsDataSet.get(category))){
            holder.price.setText(Objects.requireNonNull(mCategorySumsDataSet.get(category)).toString());
        }
        else{
            holder.price.setText(String.valueOf(0));
        }

    }

    @Override
    public int getItemCount() {
        return mCategoryDataSet.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setData(List<Category> categories, Map<Category, Double> categorySums){
        CategoryDiffCallback callback = new CategoryDiffCallback(mCategoryDataSet, categories);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        mCategoryDataSet.clear();
        mCategoryDataSet.addAll(categories);
        mCategorySumsDataSet.clear();
        categorySums.forEach(mCategorySumsDataSet::putIfAbsent);
        result.dispatchUpdatesTo(this);
    }

    public class CategoryPriceHolder extends RecyclerView.ViewHolder {

        TextView text;
        TextView price;

        public CategoryPriceHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tv_category_list_item);
            price = itemView.findViewById(R.id.tv_category_price_list_item);
        }
    }
}
