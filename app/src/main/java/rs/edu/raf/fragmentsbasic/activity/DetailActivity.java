package rs.edu.raf.fragmentsbasic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import rs.edu.raf.fragmentsbasic.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        String message = intent.getStringExtra("id");

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.tv_expense_info);
        textView.setText(message);
    }
}
