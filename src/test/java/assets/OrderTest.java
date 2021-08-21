package assets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    Table t1;
    Order o1;
    Dish d1;
    OrderItem oi1;
    @BeforeEach
    void beforeEach(){
        t1 = new Table(1);
//        o1 = new Order(t1);
        d1 = new Dish("kappa maki", 11,0,"sushi");
        oi1 = new OrderItem(d1);
    }
    @Test
    void addDish() {
        o1.addDish(d1);
        System.out.println(o1.getItems());
        assertEquals( 1, o1.getItems().size());
    }
    @Test
    void correctPortions() {
        o1.addDish(d1);
        o1.addDish(d1);
        assertEquals(2,oi1.getPortions());
    }

    @Test
    void sendOrder() {
        o1.sendOrder();
//       assertTrue( o1.getTable().getOrders().contains(o1));
    }
}