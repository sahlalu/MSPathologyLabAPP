package com.example.mspathologylab;

import java.util.ArrayList;
import java.util.List;

public class CartManager2 {

    private static List<SVGCardItem> SVGcartItems = new ArrayList<>();

    public static List<SVGCardItem> getSVGCartItems() {
        return SVGcartItems;
    }
  
    public static void addToCart(SVGCardItem item) {
        SVGcartItems.add(item);
    }

    public static void removeItem(int position) {
        if (position >= 0 && position < SVGcartItems.size()){
            SVGcartItems.remove(position);
        }
    }

    public static void clearCart() {
        SVGcartItems.clear();
    }

    public static boolean isInCart(SVGCardItem item) {
        return SVGcartItems.contains(item);
    }

    public static int getCartSize() {
        return SVGcartItems.size();
    }


}
