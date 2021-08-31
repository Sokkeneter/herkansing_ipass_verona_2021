package listeners;


import assets.Dish;
import assets.Restaurant;
import assets.Table;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.HttpResources;
import setup.PersistenceManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

@WebListener
public class PersistenceServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("contextListener - initialized");
        try {
            PersistenceManager.LoadRestaurantFromAzure();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ContextListenenr - destroyed");
        try {
            PersistenceManager.saveRestaurantToAzure();
        } catch (IOException e) {e.printStackTrace();
        }

        Schedulers.shutdownNow();
        HttpResources.disposeLoopsAndConnectionsLater(Duration.ZERO, Duration.ZERO).block();

    }
}
