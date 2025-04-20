package com.example.gifts_identity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Animation left, right, reverse_left, reverse_right;
    private TextView textView1, textView2;
    private ImageView imageView1, imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        right  = AnimationUtils.loadAnimation(this, R.anim.right_animation);
        left = AnimationUtils.loadAnimation(this, R.anim.left_animation);
        reverse_right  = AnimationUtils.loadAnimation(this, R.anim.reverse_right_animation);
        reverse_left = AnimationUtils.loadAnimation(this, R.anim.reverse_left_animation);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);

        // Implement animation for the application interface.
        imageView1.setAnimation(right);
        imageView2.setAnimation(left);

        // Retrieve input values from the user after operations and update the content object accordingly
        Item[] items = Item.items;
        SharedPreferences prefsItem = getSharedPreferences("ItemQuantity", MODE_PRIVATE);
        Map<String, ?> allEntries = prefsItem.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Object value = entry.getValue();
            Object key = entry.getKey();
            if (value != null && !value.toString().isEmpty() && key != null){
                int id = Integer.parseInt(key.toString());
                items[id].setQuantity(Integer.parseInt(value.toString()));
            }
        }
    }

    // Create animation for image and interface transitions, and open the application interface that the user interacts with
    @Override
    protected void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView1.startAnimation(reverse_left);
                imageView2.startAnimation(reverse_right);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                        startActivity(intent);
                        finish();
                    }
                }, 1940);
            }
        }, 4500);

    }
}