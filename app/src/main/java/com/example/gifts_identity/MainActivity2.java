package com.example.gifts_identity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private Item[] items;
    private ArrayAdapter<Item> itemAdapter;
    private List<String> itemName;
    private List<Item> itemList;
    private ListView listView;
    private Spinner spinner;
    private EditText editText;
    private Button buttonSearch, buttonCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        items = Item.items;

        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.editTextText);
        buttonSearch = findViewById(R.id.button1);
        spinner = findViewById(R.id.spinner);
        buttonCart = findViewById(R.id.button3);

        // Display the items in the ListView and Spinner.
        itemList = new ArrayList<>();
        itemName = new ArrayList<>();
        itemName.add("All");
        for (Item item : items){
            itemList.add(item);

            if (itemName == null){
                itemName.add(item.getName());
            }else {
                boolean isSet = false;
                for (String name : itemName){
                    if(name.toLowerCase().equals(item.getName().toLowerCase())){
                        isSet = true;
                        break;
                    }
                }
                if (!isSet){
                    itemName.add(item.getName());
                }
            }
        }
        itemAdapter = new ArrayAdapter<Item>(MainActivity2.this, R.layout.my_simple_list_item_1, itemList);
        listView.setAdapter(itemAdapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Update the content when the value in the Spinner is changed
        // Take into account the selected color when the value in the Spinner is changed
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedColor = editText.getText().toString();
                String selectedItem = parent.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity2.this, selectedItem + " " + selectedColor, Toast.LENGTH_SHORT).show();

                itemList.clear();
                if(selectedColor.equals("")){
                    if(!selectedItem.equals("All")){
                        for (Item item : items){
                            if (item.getName().toLowerCase().equals(selectedItem.toLowerCase())){
                                itemList.add(item);
                            }
                        }
                    }else {
                        for (Item item : items){
                            itemList.add(item);
                        }
                    }
                } else {
                    if(!selectedItem.equals("All")){
                        for (Item item : items){
                            if (item.getName().toLowerCase().equals(selectedItem.toLowerCase())
                                    && selectedColor.toLowerCase().equals(item.getColor().toLowerCase())){
                                itemList.add(item);
                            }
                        }
                    }else {
                        for (Item item : items){
                            if (selectedColor.toLowerCase().equals(item.getColor().toLowerCase())){
                                itemList.add(item);
                            }
                        }
                    }
                }
                itemAdapter = new ArrayAdapter<>(MainActivity2.this, R.layout.my_simple_list_item_1, itemList);
                listView.setAdapter(itemAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Display the list based on the color selected by the user, and take into account the value inside the Spinner
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected subject
                String selectedColor = editText.getText().toString();
                String selectedItem = spinner.getSelectedItem().toString();
                itemList.clear();
                // Display the boo ks in the ListView
                if (!selectedColor.toLowerCase().equals("")){
                    if (selectedItem.toLowerCase().equals("All".toLowerCase())){
                        for (Item item : items) {
                            if (item.getColor().toLowerCase().equals(selectedColor.toLowerCase())){
                                itemList.add(item);
                            }
                        }
                    }else {
                        for (Item item : items) {
                            if (item.getColor().toLowerCase().equals(selectedColor.toLowerCase())
                                    && item.getName().toLowerCase().equals(selectedItem.toLowerCase())){
                                itemList.add(item);
                            }
                        }
                    }
                }else {
                    if (selectedItem.toLowerCase().equals("All".toLowerCase())){
                        for (Item item : items) {
                            itemList.add(item);
                        }
                    }else {
                        for (Item item : items) {
                            if (item.getName().toLowerCase().equals(selectedItem.toLowerCase())){
                                itemList.add(item);
                            }
                        }
                    }
                }
                // Update the ListView with book titles and prices
                itemAdapter = new ArrayAdapter<>(MainActivity2.this, R.layout.my_simple_list_item_1, itemList);
                listView.setAdapter(itemAdapter);
                Toast.makeText(MainActivity2.this, selectedItem + " " + selectedColor, Toast.LENGTH_SHORT).show();
            }
        });

        // When an item is clicked, open the item’s details and allow the user to add it to the cart
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item clickedItem = (Item) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity2.this, SpecificItemType.class);
                intent.putExtra("item_id", clickedItem.getId());
                startActivity(intent);
            }
        };
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(itemClickListener);

        // Open the cart
        buttonCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, CartDetails.class);
                startActivity(intent);
            }
        });
    }
}