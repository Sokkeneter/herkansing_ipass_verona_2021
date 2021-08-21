package webservices;

import assets.Dish;
import assets.OrderItem;
import assets.Restaurant;
import assets.Table;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Path("tables")
public class TableRecource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTables() {

        Restaurant restaurant = Restaurant.getCurrentRestaurant();
        System.out.println(restaurant.getTables());

        if(restaurant.getTables() != null){
            return Response.ok(restaurant.getTables()).build();

        }
        return Response.noContent().entity("no tables").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{table}")
    public Response getTableFromNumber(@PathParam("table") String tablenNr) {
        Restaurant restaurant = Restaurant.getCurrentRestaurant(); // get current restaurant
        int tablenr = Integer.parseInt(tablenNr); //parse tableNr
        Table table = restaurant.getTableFromNumber(tablenr); // get table from number
        if(table != null){ //check if it returns null
            return Response.ok(table).build();
        }else {
            return Response.noContent().entity("table not found").build();
        }
    }


    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{tableNr}")
    public Response deleteTableByNumber(@PathParam("tableNr") String tablenNr) {
        Restaurant restaurant = Restaurant.getCurrentRestaurant(); // get current restaurant

        int tablenr = Integer.parseInt(tablenNr); //parse tableNr
        try{
            restaurant.deleteTableFromNumber(tablenr); // delete table from number
        } catch (NotFoundException nfe){
            return Response.status(404).entity("table not found").build();
        }

        return Response.ok(restaurant.getTables()).build();

    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{tableNr}/deleteorder")
    public Response deleteLatestOrder(@PathParam("tableNr") String tablenNr) {
        Restaurant restaurant = Restaurant.getCurrentRestaurant(); // get current restaurant
        int tablenr = Integer.parseInt(tablenNr); //parse tableNr
        Table table = restaurant.getTableFromNumber(tablenr); // get table from number
        table.deleteLatestOrder();
        return Response.ok(table.getOrders()).build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTable(@FormParam("number") String number){
        try{

            Table table = new Table(Integer.parseInt(number)); //create table with the given number
            Restaurant.getCurrentRestaurant().addTableToRestaurant(table); // add to the restaurant
            return Response.ok().entity(table).build();
        }catch (Exception e){
            Map<String, String> messages = new HashMap<>();
            messages.put("error", e.getMessage());

            return Response.status(404).entity(messages).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{table}/orderitems")
    public Response addDishToOrder(@PathParam("table") String tablenNr, @FormParam("dishNr") String dishNr) {
        try{
        Restaurant restaurant = Restaurant.getCurrentRestaurant(); //get current restaurant
        //turn parameters into usable values
        int tablenr = Integer.parseInt(tablenNr);
        int dishnr = Integer.parseInt(dishNr);
        Table table = restaurant.getTableFromNumber(tablenr); //get table
        Dish dish = restaurant.getDishFromNumber(dishnr);     // get dish
            if(table != null && dish != null) {               //check if table and dish exist
                table.getCurrentOrder().addDish(dish);        //add dish to table's current order
                return Response.ok(dish).build();
            }else{
                return Response.status(404).entity("table or dish not found").build();
            }

        }catch(Exception e){
            return Response.noContent().entity("something went wrong:" + Arrays.toString(e.getStackTrace())).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{tableNr}/sendorder")
    public Response sendOrder(@PathParam("tableNr") String number){
        try{
            int tableNr = Integer.parseInt(number);
            Table table = Restaurant.getCurrentRestaurant().getTableFromNumber(tableNr); //create table with the given number
            table.getCurrentOrder().sendOrder();                                         // send order
            return Response.ok().entity(table.getOrders()).build();
        }catch (Exception e){
            Map<String, String> messages = new HashMap<>();
            messages.put("error", e.getMessage());

            return Response.status(404).entity(messages).build();
        }
    }


}
