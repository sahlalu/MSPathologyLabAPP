package com.example.mspathologylab;

import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private static List<CardItem> cartItems = new ArrayList<>();


    public static List<CardItem> getCartItems() {
        return cartItems;
    }

    public static void addToCart(CardItem item) {
        cartItems.add(item);
    }

    public static void removeItem(int position) {
        if (position >=0 && position < cartItems.size()){
            cartItems.remove(position);
        }
    }

    public static void clearCart() {
        cartItems.clear();
    }

    public static boolean isInCart(CardItem item) {
        return cartItems.contains(item);
    }

    public static int getCartSize() {
        return cartItems.size();
    }
}
