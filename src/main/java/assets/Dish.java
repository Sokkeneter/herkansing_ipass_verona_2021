package assets;

import lombok.Data;

import java.io.Serializable;

public @Data
class Dish implements Serializable {
    private String dishName;
    private int dishNumber; // includes drinks //system from which i took inspiration didn't have this and it was annoying
    private float price; //can be zero if its part of All You Can Eat
    private String category;//maybe i can add a 'new category' function

    public Dish(String dishName, int dishNumber, float price, String category) {
        this.dishName = dishName;
        this.dishNumber = dishNumber;
        this.price = price;
        this.category = category;
    }


}
