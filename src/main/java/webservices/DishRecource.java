package webservices;

import assets.OrderItem;
import assets.Restaurant;
import assets.Dish;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Path("dishes")
public class DishRecource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDishes() {

        Restaurant restaurant = Restaurant.getCurrentRestaurant();
        System.out.println(restaurant.getDishes());

        if(restaurant.getDishes() != null){
            ArrayList<Dish> dishes = restaurant.getDishes();
            return Response.ok(dishes).build();
//            return Response.ok(restaurant).build();
        }
        return Response.noContent().entity("no dishes").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{dish}")
    public Response getDishFromNumber(@PathParam("dish") String dishnNr) {
        Restaurant restaurant = Restaurant.getCurrentRestaurant();
        int dishnr = Integer.parseInt(dishnNr);
        Dish dish = restaurant.getDishFromNumber(dishnr);
        if(dish != null){
            return Response.ok(dish).build();
        }else {
            return Response.noContent().entity("dish not found").build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{dishNr}")
    public Response deleteDishByNumber(@PathParam("dishNr") String tablenNr) {
        Restaurant restaurant = Restaurant.getCurrentRestaurant(); // get current restaurant
        int dishnr = Integer.parseInt(tablenNr); //parse dishNr
        restaurant.deleteDishFromNumber(dishnr); // delete dish from number

        return Response.ok().build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDish(@FormParam("name") String name,
                               @FormParam("number") String number,
                               @FormParam("price") String price,
                               @FormParam("category") String category){
        try{
            Dish dish = new Dish(name,Integer.parseInt(number), Float.parseFloat(price), category);
            Restaurant.getCurrentRestaurant().addDishToRestaurant(dish);
            return Response.ok().entity(dish).build();
        }catch (Exception e){
            Map<String, String> messages = new HashMap<>();
            messages.put("error", e.getMessage());

            return Response.noContent().entity(messages).build();
        }
    }
    
}
