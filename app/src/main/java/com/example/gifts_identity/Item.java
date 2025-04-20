package com.example.gifts_identity;

public class Item {
    private int id;
    private String name;
    private String color;
    private int price;
    private int imageID;
    private int quantity;
    private String description;

    public static final Item[] items = {
        new Item(0,"Bracelet", "Gold", 10, R.drawable.bracelet1, 15,
                "An elegant gold-tone bracelet inspired by traditional Palestinian embroidery patterns. Its design combines heritage and elegance, adding a distinctive cultural touch to your look."),
        new Item(1, "Bracelet", "Gold", 12, R.drawable.bracelet2, 10,
                "An elegant gold-tone bracelet inspired by traditional Palestinian embroidery patterns. Its design combines heritage and elegance, adding a distinctive cultural touch to your look."),
        new Item(2, "Bracelet and Ring", "Gold", 16, R.drawable.bracelet_ring, 9,
                "An elegant gold-tone bracelet and ring inspired by traditional Palestinian embroidery patterns. Its design combines heritage and elegance, adding a distinctive cultural touch to your look."),
        new Item(3, "Dress", "White", 45, R.drawable.dress1, 6,
                "An elegant Palestinian heritage dress, in white with colorful embroidery, decorated with shiny green fabric. Perfect for special occasions, it combines authenticity and elegance."),
        new Item(4, "Dress", "Purple", 60, R.drawable.dress10, 3,
                "An elegant Palestinian heritage dress in purple with colorful embroidery. Perfect for special occasions, it combines authenticity and sophistication."),
        new Item(5, "Dress", "White", 55, R.drawable.dress2, 7,
                "An elegant Palestinian heritage dress, in white with colorful embroidery. Perfect for special occasions, it combines authenticity and sophistication."),
        new Item(6, "Dress", "Purple", 60, R.drawable.dress3, 7,
                "An elegant Palestinian heritage dress in purple with colorful embroidery. Perfect for special occasions, it combines authenticity and sophistication."),
        new Item(7, "Dress", "black", 50, R.drawable.dress4, 5,
                "An elegant Palestinian heritage dress in black with colorful embroidery. Perfect for special occasions, it combines authenticity and sophistication."),
        new Item(8, "Earring", "Gold", 7, R.drawable.earring1, 10,
                "An elegant gold earring inspired by traditional Palestinian embroidery patterns. Its design combines heritage and elegance, adding a distinctive cultural touch to your look."),
        new Item(9, "Earring", "Gold", 5, R.drawable.earring2, 10,
                "An elegant gold earring inspired by traditional Palestinian embroidery patterns. Its design combines heritage and elegance, adding a distinctive cultural touch to your look."),
        new Item(10, "Dress", "blue", 55, R.drawable.dress9, 4,
                "An elegant Palestinian heritage dress in dark blue with distinctive white embroidery. Perfect for special occasions, it combines authenticity and elegance."),
        new Item(11, "Earring", "Gold", 15, R.drawable.earring3, 8,
                "An elegant gold earring inspired by traditional Palestinian embroidery patterns. Its design combines heritage and elegance, adding a distinctive cultural touch to your look."),
        new Item(12, "Dress", "gray", 40, R.drawable.dress5, 2,
                "An elegant Palestinian heritage dress in a skin-toned color with colorful embroidery, adorned with shiny green fabric. Perfect for special occasions, it combines authenticity and sophistication."),
        new Item(13, "Necklace", "Gold", 20, R.drawable.necklace, 12,
                "An elegant gold necklace inspired by traditional Palestinian embroidery patterns. Its design combines heritage and elegance, adding a distinctive cultural touch to your look."),
        new Item(14, "Ring", "Gold", 6, R.drawable.ring1, 9,
                "An elegant gold-tone ring inspired by traditional Palestinian embroidery patterns. Its design combines heritage and elegance, adding a distinctive cultural touch to your look."),
        new Item(15, "Dress", "blue", 45, R.drawable.dress6, 5,
                "An elegant Palestinian heritage dress in dark blue with colorful embroidery. Perfect for special occasions, it combines authenticity and elegance."),
        new Item(16, "Ring", "Gold", 6, R.drawable.ring2, 11,
                "An elegant gold-tone ring inspired by traditional Palestinian embroidery patterns. Its design combines heritage and elegance, adding a distinctive cultural touch to your look."),
        new Item(17, "Dress", "black", 65, R.drawable.dress8, 6,
                "An elegant Palestinian heritage dress in black with colorful embroidery. Perfect for special occasions, it combines authenticity and sophistication."),
        new Item(18, "Ring", "Gold", 8, R.drawable.ring3, 9,
                "An elegant gold-tone ring inspired by traditional Palestinian embroidery patterns. Its design combines heritage and elegance, adding a distinctive cultural touch to your look."),
        new Item(19, "Dress", "black", 60, R.drawable.dress7, 5,
                "An elegant Palestinian heritage dress in black with colorful embroidery. Perfect for special occasions, it combines authenticity and sophistication.")
    };

    private Item(int id, String name, String color, int price, int imageID, int quantity, String description){
        this.name = name;
        this.color = color;
        this.price = price;
        this.imageID = imageID;
        this.quantity = quantity;
        this.description = description;
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public String getColor(){
        return color;
    }

    public int getPrice(){
        return price;
    }

    public int getImageID(){
        return imageID;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public String getDescription(){
        return description;
    }

    public int getId(){
        return id;
    }

    @Override
    public String toString(){
        return getName() + " " + getColor() + " - Manage";
    }

}
