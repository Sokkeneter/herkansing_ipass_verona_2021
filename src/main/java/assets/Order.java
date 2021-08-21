package assets;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

public @Data
class Order implements Serializable {
    private int orderNumber;
    private int tablenr;
    private ArrayList<OrderItem> items = new ArrayList<>();
    private String status = "not send";

    public Order(int tablenr) {
        Table table = Restaurant.getCurrentRestaurant().getTableFromNumber(tablenr);
        this.orderNumber = table.getOrders().size() + 1;
        this.tablenr = tablenr;
    }

    public void addDish(Dish dish){
        //if theres an orderitem in this order with this dish, addportion, else, create orderItem with this dish
        OrderItem orderItem = getOrderItemFromFromDish(dish);
        if(orderItem != null){
            orderItem.addPortion();
        }
        else{
            OrderItem newOrderItem = new OrderItem(dish);
            items.add(newOrderItem);
        }


    }
    public void sendOrder(){
        //send the order to the kitchen
        status = "send";
    }

    public OrderItem getOrderItemFromFromDish(Dish dish){
        for( OrderItem item : items){
            if (item.getDish().equals(dish)){
                return item;
            }
        }
        return null;
    }


}
