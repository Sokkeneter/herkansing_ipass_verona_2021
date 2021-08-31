package webservices;

import assets.Dish;
import assets.Restaurant;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("restaurant")
public class RestaurantResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurant() {

        Restaurant restaurant = Restaurant.getCurrentRestaurant();

        if(restaurant != null){
            return Response.ok(restaurant).build();
        }
        return Response.noContent().entity("no restaurant").build();
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCategory(@FormParam("name") String name) {
        System.out.println("addCategory has been called");
        Restaurant restaurant = Restaurant.getCurrentRestaurant();

        if(restaurant != null){
            try{
                restaurant.addCategory(name);
                return Response.ok().build();
            }catch (IllegalArgumentException iae){
                return Response.status(409).build();
            }
        }
        return Response.noContent().entity("no restaurant").build();
    }
}
