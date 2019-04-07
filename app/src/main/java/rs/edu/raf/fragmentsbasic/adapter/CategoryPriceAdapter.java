package rs.edu.raf.fragmentsbasic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import rs.edu.raf.fragmentsbasic.R;
import rs.edu.raf.fragmentsbasic.model.Category;
import rs.edu.raf.fragmentsbasic.util.CategoryDiffCallback;

public class CategoryPriceAdapter extends RecyclerView.Adapter<CategoryPriceAdapter.CategoryPriceHolder> {

    private List<Category> mDataSet;

    public CategoryPriceAdapter() {
        mDataSet = new ArrayList<>();
    }

    @NonNull
    @Override
    public CategoryPriceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_list_item, parent, false);
        return new CategoryPriceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryPriceHolder holder, int position) {
        Category category = mDataSet.get(position);
        holder.text.setText(category.getmName());
        holder.price.setText(category.getmSum().toString());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setData(List<Category> categories){
        CategoryDiffCallback callback = new CategoryDiffCallback(mDataSet, categories);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        mDataSet.clear();
        mDataSet.addAll(categories);
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
