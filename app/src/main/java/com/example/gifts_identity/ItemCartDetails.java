package com.example.gifts_identity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemCartDetails extends AppCompatActivity {

    private ImageView imageView;
    private TextView textViewItemDetails, textViewTotalPrice;
    private NumberPicker numberPicker;
    private Button buttonSavechanges, buttonDelete, buttonHome, buttonCart;
    private Item[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_cart_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.imageView);
        textViewItemDetails = findViewById(R.id.textViewItemDetails);
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);
        numberPicker = findViewById(R.id.numberPicker);
        buttonSavechanges = findViewById(R.id.buttonSavechanges);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonHome = findViewById(R.id.buttonHome);
        buttonCart = findViewById(R.id.buttonCart);

        SharedPreferences prefs = getSharedPreferences("CartDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Intent intent = getIntent();
        // Display the details of this item, including the total price and quantity
        int id = intent.getIntExtra("item_id", -1);
        if (id != -1) {
            Item item = Item.items[id];
            textViewItemDetails.setText(item.getName() + " - " + item.getColor());
            imageView.setImageResource(item.getImageID());

            items = Item.items;
            int quantity = 0;
            Map<String, ?> allEntries = prefs.getAll();
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                Object value = entry.getValue();
                if (value != null && !value.toString().isEmpty()){
                    String[] str = value.toString().split(",");
                    if (str.length >= 2) {
                        int id2 = Integer.parseInt(str[0]);
                        Item itemFinded = findItem(id2);
                        if(itemFinded != null){
                            quantity = Integer.parseInt(str[1]);
                            break;
                        }
                    }
                }
            }
            int totalPrice = quantity * item.getPrice();
            textViewTotalPrice.setText("Total price " + totalPrice + "$");

            // Set the allowed quantity based on the available stock of the product
            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(item.getQuantity());
            numberPicker.setValue(quantity); // Set the quantity to the initially selected value

            // Modify the quantity
            // note: Keep the product page open
            buttonSavechanges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putString("Item id" + id, id + "," + numberPicker.getValue());
                    editor.apply();
                    Toast.makeText(ItemCartDetails.this, "Change on " + item.getName() + " - " + item.getColor(), Toast.LENGTH_SHORT).show();
                }
            });

            // Delete the item from the order
            // note: Return to the cart
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.remove("Item id" + id);
                    editor.apply();
                    Toast.makeText(ItemCartDetails.this, "Delete " + item.getName() + " - " + item.getColor(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ItemCartDetails.this, CartDetails.class);
                    startActivity(intent);
                }
            });

            // Open the main page
            buttonHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ItemCartDetails.this, MainActivity2.class);
                    startActivity(intent);
                }
            });

            // Open the cart
            buttonCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ItemCartDetails.this, CartDetails.class);
                    startActivity(intent);
                }
            });
        }
    }
    private Item findItem(int id){
        for (Item it : items){
            if (it.getId() == id){
                return it;
            }
        }
        return null;
    }
}