package com.jobpost;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView title = findViewById(R.id.title);
        TextView description = findViewById(R.id.description);
        ImageView image = findViewById(R.id.image);

        // Get data from intent
        String titleText = getIntent().getStringExtra("Title");
        String descriptionText = getIntent().getStringExtra("Description");
        int imageRes = getIntent().getIntExtra("Image", 0);

        // Set data to views
        title.setText(titleText);
        description.setText(descriptionText);
        image.setImageResource(imageRes);
    }
}
