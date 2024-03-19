package com.example.mspathologylab;

import java.util.ArrayList;
import java.util.List;

public class CartManager3 {

    private static List<TestCardItem> TestcartItems = new ArrayList<>();

    public static List<TestCardItem> getTestCartItems() {
        return TestcartItems;
    }

    public static void addToCart(TestCardItem item) {
        TestcartItems.add(item);
    }

    public static void removeItem(int position) {
        if (position >= 0 && position < TestcartItems.size()){
            TestcartItems.remove(position);
        }
    }

    public static void clearCart() {

        TestcartItems.clear();
    }


    public static boolean isInCart(TestCardItem item) {
        return TestcartItems.contains(item);
    }

    public static int getCartSize() {
        return TestcartItems.size();
    }


}
