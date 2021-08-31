package assets;

import lombok.Data;

import java.io.Serializable;

public @Data
class Dish implements Serializable {
    private String dishName;
    private int dishNumber; // includes drinks
    private float price; //can be zero if its part of All You Can Eat
    private String category;//maybe i can add a 'new category' function

    public Dish(String dishName, int dishNumber, float price, String category) {
        this.dishName = dishName;
        this.dishNumber = dishNumber;
        this.price = price;
        this.category = category;
    }

    public Dish updateDish(Dish replacementDish){
        this.setDishName(replacementDish.getDishName());
        this.setDishNumber(replacementDish.getDishNumber());
        this.setPrice(replacementDish.getPrice());
        this.setCategory(replacementDish.getCategory());
        return this;

    }

}
