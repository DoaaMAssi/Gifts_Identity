package com.example.gifts_identity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Map;

public class SpecificItemType extends AppCompatActivity {

    private TextView textName, textPrice, textOutOf, textDisc;
    private ImageView imageItem;
    private Button buttonAdd, buttonHome, buttonCart;
    NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_specific_item_type);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences prefs = getSharedPreferences("CartDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        textName = findViewById(R.id.textView);
        textPrice = findViewById(R.id.textView3);
        textOutOf = findViewById(R.id.textView4);
        imageItem = findViewById(R.id.imageView);
        buttonAdd = findViewById(R.id.button);
        buttonHome = findViewById(R.id.button2);
        textDisc = findViewById(R.id.textView5);
        buttonCart = findViewById(R.id.button3);
        numberPicker = findViewById(R.id.numberPicker);

        // Display the item details
        Intent intent = getIntent();
        int id = intent.getIntExtra("item_id", -1);
        if (id != -1){
            Item item = Item.items[id];
            textName.setText(item.getName() + " - " + item.getColor());
            textPrice.setText(item.getPrice() + "$");
            if (item.getQuantity() == 0){
                textOutOf.setText("Out of stock");
            }
            imageItem.setImageResource(item.getImageID());
            textDisc.setText(item.getDescription());

            // Set the allowed quantity based on the available stock of the product
            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(item.getQuantity());
            numberPicker.setValue(1);

            // Open the main interface
            buttonHome.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SpecificItemType.this, MainActivity2.class);
                    startActivity(intent);
                }
            });

            // Add the item to the cart, considering whether the item is already in the cart or not
            // If the item is added to the cart, store it in SharedPreference
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getQuantity() != 0){
                        boolean isSet = false;
                        int quan = 0;
                        // Check if the item is already in the cart
                        Map<String, ?> allEntries = prefs.getAll();
                        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                            Object value = entry.getValue();
                            if (value != null && !value.toString().isEmpty()){
                                String[] str = value.toString().split(",");
                                int idItem = Integer.parseInt(str[0]);
                                if (idItem == item.getId()){
                                    isSet = true;
                                    quan = Integer.parseInt(str[1]);
                                    break;
                                }
                            }
                        }
                        if (isSet){ // the product is already in the cart
                            int q = numberPicker.getValue() + quan;
                            editor.putString("Item id" + id, id + "," + q);
                        }else { // Add the product to the cart for the first time
                            editor.putString("Item id" + id, id + "," + numberPicker.getValue());
                        }
                        editor.apply();
                        Toast.makeText(SpecificItemType.this, "Added to cart", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SpecificItemType.this, "This product is not available", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // Open the cart
            buttonCart.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SpecificItemType.this, CartDetails.class);
                    startActivity(intent);
                }
            });
        }
    }
}