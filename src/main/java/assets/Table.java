package assets;

import lombok.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public @Data
class Table implements Serializable {
    private int tableNumber;
    private float total;
    private static int tableTime = 240; //amount of time a table is allowed to eat in miliseconds
    private ArrayList<Order> orders = new ArrayList<>();

    private static ArrayList<Table> allTables = new ArrayList<>();

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.total = 0;
        allTables.add(this);
    }


    public float endBill(){
        //customer cant order anymore food and the total is shown
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return tableNumber == table.tableNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableNumber);
    }

    public void addOrder(Order order){
        orders.add(order);
        if(!order.getItems().isEmpty()) {
            for (OrderItem item : order.getItems()) {
                this.total = +item.getDish().getPrice();
            }
        }
    }

    public Order getCurrentOrder(){
        for (Order order : orders){
            if(!order.getStatus().equals("send")){
                return order;
            }
        }
        Order order = new Order(this.tableNumber);
        orders.add(order);
        return order;
    }

    public void deleteLatestOrder(){
//        Order orderToRemove = orders.get(-1); //select order to remove
        orders.remove((orders.size()-1));
    }
}
