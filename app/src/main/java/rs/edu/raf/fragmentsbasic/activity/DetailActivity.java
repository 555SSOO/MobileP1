package rs.edu.raf.fragmentsbasic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import rs.edu.raf.fragmentsbasic.R;
import rs.edu.raf.fragmentsbasic.model.Expense;
import rs.edu.raf.fragmentsbasic.viewmodel.MainViewModel;

public class DetailActivity extends AppCompatActivity {

    public static void setmViewModel(MainViewModel mViewModel) {
        DetailActivity.mViewModel = mViewModel;
    }

    private static MainViewModel mViewModel;
    private static final String URL = "https://picsum.photos/1080/1920/?random";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        // Remove button
        Button removeButton = findViewById(R.id.btn_remove);
        removeButton.setOnClickListener(v -> {
            mViewModel.removeExpense(Integer.valueOf(id));
            this.finish();
        });

        Expense expense = mViewModel.getExpense(Integer.valueOf(id));

        TextView textView = findViewById(R.id.tv_expense_info);
        textView.setText(String.format("%s\n%s\n%s", expense.getName(), expense.getmCategory(), expense.getmPrice()));

        ImageView imageView = findViewById(R.id.iv_expence_image);
        Picasso.get()
                .load(expense.getmImageUrl())
                .into(imageView);
    }
}
