package com.example.gifts_identity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class CartDetails extends AppCompatActivity {

    private Button buttonHome, buttonConfirm, buttonDelete;
    private ListView listView;
    private List<Item> itemList;
    private Item[] items;
    private ArrayAdapter<Item> itemAdapter;
    private TextView textView;
    private int totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonHome = findViewById(R.id.button2);
        listView = findViewById(R.id.listView);
        buttonConfirm = findViewById(R.id.buttonConfirm);
        buttonDelete = findViewById(R.id.buttonDelete);
        textView = findViewById(R.id.textView6);
        totalPrice = 0;

        // Use SharedPreferences to store the quantity of the product after it is purchased
        SharedPreferences prefsItem = getSharedPreferences("ItemQuantity", MODE_PRIVATE);
        SharedPreferences.Editor editorItem = prefsItem.edit();

        // Use SharedPreferences to store the contents of the cart
        SharedPreferences prefs = getSharedPreferences("CartDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        items = Item.items;
        itemList = new ArrayList<>();
        // Display the contents of the cart
        Map<String, ?> allEntries = prefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Object value = entry.getValue();
            if (value != null && !value.toString().isEmpty()){
                String[] str = value.toString().split(",");
                if (str.length >= 2) {
                    int id = Integer.parseInt(str[0]);
                    Item itemFinded = findItem(id);
                    if(itemFinded != null){
                        itemList.add(itemFinded);
                        totalPrice += (itemFinded.getPrice() * Integer.parseInt(str[1]));
                    }
                }
            }
        }
        itemAdapter = new ArrayAdapter<>(CartDetails.this, R.layout.my_simple_list_item_1, itemList);
        listView.setAdapter(itemAdapter);
        textView.setText("Total price " + totalPrice + "$");

        // When an item from the cart is clicked, display its details (price and quantity), with the option to delete the item or modify its quantity
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item clickedItem = (Item) parent.getItemAtPosition(position);
                Intent intent = new Intent(CartDetails.this, ItemCartDetails.class);
                intent.putExtra("item_id", clickedItem.getId());
                startActivity(intent);
            }
        };
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(itemClickListener);


        // Delete the order
        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!prefs.getAll().isEmpty()) {
                    editor.clear();
                    editor.apply();
                    itemList.clear();
                    totalPrice = 0;
                    itemAdapter = new ArrayAdapter<>(CartDetails.this, R.layout.my_simple_list_item_1, itemList);
                    listView.setAdapter(itemAdapter);
                    textView.setText("Total price " + totalPrice + "$");
                    Toast.makeText(CartDetails.this, "Delete Order", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Open the main interface
        buttonHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartDetails.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        // Confirm the order
        buttonConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!prefs.getAll().isEmpty()) {
                    Map<String, ?> allEntries = prefs.getAll();
                    for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                        Object value = entry.getValue();
                        if (value != null && !value.toString().isEmpty()){
                            String[] str = value.toString().split(",");
                            if (str.length >= 2) {
                                int id = Integer.parseInt(str[0]);
                                Item itemFinded = findItem(id);
                                if(itemFinded != null){
                                    // Save the new content value in SharedPreferences
                                    itemFinded.setQuantity(itemFinded.getQuantity() - Integer.parseInt(str[1]));
                                    editorItem.putString("" + id, "" + itemFinded.getQuantity());
                                    editorItem.apply();
                                }
                            }
                        }
                    }
                    totalPrice = 0;
                    textView.setText("Total price " + totalPrice + "$");
                    editor.clear();
                    editor.apply();
                    itemList.clear();
                    itemAdapter = new ArrayAdapter<>(CartDetails.this, R.layout.my_simple_list_item_1, itemList);
                    listView.setAdapter(itemAdapter);
                    Toast.makeText(CartDetails.this, "Order Confirmation", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Item findItem(int id){
        for (Item item : items){
            if (item.getId() == id){
                return item;
            }
        }
        return null;
    }
}