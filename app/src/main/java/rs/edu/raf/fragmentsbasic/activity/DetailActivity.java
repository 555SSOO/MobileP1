package rs.edu.raf.fragmentsbasic.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Optional;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import rs.edu.raf.fragmentsbasic.R;
import rs.edu.raf.fragmentsbasic.model.Expense;
import rs.edu.raf.fragmentsbasic.viewmodel.MainViewModel;

public class DetailActivity extends AppCompatActivity {

    public static void setExpense(Optional<Expense> expense) {
        DetailActivity.expense = expense;
    }

    private static Optional<Expense> expense;
    private static final String URL = "https://picsum.photos/1080/1920/?random";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if(expense.isPresent()) {
            init();
        }
        else{
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void init() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        // Remove button
        Button removeButton = findViewById(R.id.btn_remove);
        removeButton.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result",id);
            setResult(Activity.RESULT_OK,returnIntent);
            this.finish();
        });

        TextView textView = findViewById(R.id.tv_expense_info);
        textView.setText(String.format("%s\n%s\n%s", expense.get().getName(), expense.get().getmCategory(), expense.get().getmPrice()));

        ImageView imageView = findViewById(R.id.iv_expence_image);
        Picasso.get()
                .load(expense.get().getmImageUrl())
                .into(imageView);
    }

}
