package rs.edu.raf.fragmentsbasic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import rs.edu.raf.fragmentsbasic.R;
import rs.edu.raf.fragmentsbasic.model.Expense;
import rs.edu.raf.fragmentsbasic.util.ExpenseDiffCallback;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder> {

    private List<Expense> mDataSet;
    private OnImageClickCallback mOnImageClickCallback;
    private OnItemRemoveCallback mOnItemRemoveCallback;

    public ExpenseAdapter() {
        mDataSet = new ArrayList<>();
    }

    @NonNull
    @Override
    public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ExpenseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseHolder holder, int position) {
        Expense expense = mDataSet.get(position);
        holder.text.setText(String.format("%s\n%s\n%s", expense.getName(), expense.getmCategory().getmName(), expense.getmPrice().toString()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expense.getmDate());
        holder.date.setText(String.format("%s/%s/%s", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setData(List<Expense> expenses){
        ExpenseDiffCallback callback = new ExpenseDiffCallback(mDataSet, expenses);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        mDataSet.clear();
        mDataSet.addAll(expenses);
        result.dispatchUpdatesTo(this);
    }

    public class ExpenseHolder extends RecyclerView.ViewHolder {

        Button detailsButton;
        Button removeButton;
        TextView text;
        TextView date;

        public ExpenseHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.tv_list_item);
            date = itemView.findViewById(R.id.tv_list_item_date);

            detailsButton = itemView.findViewById(R.id.btn_list_item_details);
            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        if (mOnImageClickCallback != null) {
                            mOnImageClickCallback.onImageClick(position);
                        }
                    }
                }
            });

            removeButton = itemView.findViewById(R.id.btn_list_item_remove);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        if (mOnItemRemoveCallback != null) {
                            mOnItemRemoveCallback.onItemRemove(position);
                        }
                    }
                }
            });
        }
    }

    public void setOnImageClickCallback(OnImageClickCallback onImageClickCallback){
        mOnImageClickCallback = onImageClickCallback;
    }

    public void setOnItemRemoveCallback (OnItemRemoveCallback onItemRemoveCallback) {
        mOnItemRemoveCallback = onItemRemoveCallback;
    }

    // Callback we use when user clicks on remove
    public interface OnItemRemoveCallback {
        void onItemRemove(int position);
    }

    //Callback we use when user click on avatar avatarImage
    public interface OnImageClickCallback {
        void onImageClick(int position);
    }
}
