package assets;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.NotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

@Data
@Getter
@Setter
public
class Restaurant implements Serializable{
    private boolean isAdmin = false;
    private String password;

    private ArrayList<Table> tables = new ArrayList<>();
    private ArrayList<Dish> dishes = new ArrayList<>();
    private ArrayList<String> categories = new ArrayList<>();
    static private Comparator<Dish> ascTitle;
    private static Restaurant currentRestaurant = new Restaurant();


    public static Restaurant getCurrentRestaurant() {return currentRestaurant;}
    public static void setCurrentRestaurant(Restaurant restaurant){currentRestaurant = restaurant;}


    public boolean logIn(String password){
        if(password == null){ //if a password hasn't been set yet
            this.password = password;
            isAdmin = true;
        }
        else{
            if(this.password.equals(password)){ //password check
                isAdmin = true;
                return true;

            }else{
                isAdmin = false;
                return false;
            }
        }
        return true;
    }
    public void logOut(){
        isAdmin = false;
    }

    public void addCategory(String category){
        if(!categories.contains(category)){
            categories.add(category);
        }else{
            throw new IllegalArgumentException();
        }
    }

    public void addTableToRestaurant(Table table){
        if(!tables.contains(table)){
         this.tables.add(table);
        }
    }

    public void addDishToRestaurant(Dish dish){
        if(!dishes.contains(dish)){
            this.dishes.add(dish);
        }else{
            throw new IllegalArgumentException();
        }
    }

    public Table getTableFromNumber(int number){
        for(Table table : tables){
            if (table.getTableNumber() == number){
                return table;
            }
        }
        return null;
    }

    public Dish getDishFromNumber(int number){
        for(Dish dish : dishes){
            if (dish.getDishNumber() == number){
                return dish;
            }
        }
        return null;
    }

    public void addDish(Dish dish){
        dishes.add(dish);
    }


    public void deleteTableFromNumber(int tablenr) {
        for(Table table : tables){
            if (table.getTableNumber() == tablenr){
                tables.remove(table);
                return;
            }
        }
    throw new NotFoundException();
    }

    public void deleteDishFromNumber(int dishnr) {
        for(Dish dish: dishes){
            if (dish.getDishNumber() == dishnr){
                dishes.remove(dish);
                return;
            }
        }

    }
}
