package assets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    Table t1;
    Order o1;
    @BeforeEach
    void beforeEach(){
        t1 = new Table(1);
        o1 = new Order(t1.getTableNumber());

    }


//    @Test
//    void addOrder() {
//        t1.startBill();
//        t1.addOrder(o1);
//        System.out.println( t1.getOrders());
//        assertTrue( t1.getOrders().contains(o1));
//    }
}