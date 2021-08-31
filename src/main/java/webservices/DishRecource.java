package webservices;

import assets.Restaurant;
import assets.Dish;
import org.glassfish.jersey.media.multipart.FormDataParam;

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
//        System.out.println(restaurant.getDishes());

        if(restaurant.getDishes() != null){
            ArrayList<Dish> dishes = restaurant.getDishes();
            return Response.ok(dishes).build();
//            return Response.ok(restaurant).build();
        }
        return Response.noContent().entity("no dishes").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{dishNr}")
    public Response getDishFromNumber(@PathParam("dishNr") String dishnNr) {
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
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDish(@FormParam("dishname") String name,
                               @FormParam("dishnumber") String number,
                               @FormParam("price") String price,
                               @FormParam("category") String category){
        try{
            Dish dish = new Dish(name,Integer.parseInt(number), Float.parseFloat(price), category);
            Restaurant.getCurrentRestaurant().addDishToRestaurant(dish);
            return Response.ok().entity(dish).build();
        }catch (IllegalArgumentException iae){
            return Response.status(409).entity("dish with this number already exists").build();
        }
    }
    @PUT
    @Path("{dishNr}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDish(@PathParam("dishNr") String number,
                               @FormParam("dishname") String dishName,
                               @FormParam("dishnumber") String dishNumber,
                               @FormParam("price") String price,
                               @FormParam("category") String category){
        try{
//            System.out.println(number + dishName + dishNumber + price + category);
            int oldnumber = Integer.parseInt(number);
            Dish oldDish = Restaurant.getCurrentRestaurant().getDishFromNumber(oldnumber);
            if(oldDish == null){
                return Response.status(404).build();
            }
            int newNumber = Integer.parseInt(dishNumber);
            float newPrice = Float.parseFloat(price);
            Dish newDish = new Dish(dishName, newNumber, newPrice, category);
            oldDish.updateDish(newDish);
            return Response.ok(newDish).build();
        }catch (Exception e) {
            e.printStackTrace();
            return Response.noContent().entity("something went wrong").build();
        }
    }
    
}
