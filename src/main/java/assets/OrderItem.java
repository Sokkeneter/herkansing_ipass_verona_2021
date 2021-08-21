package assets;

import lombok.Data;

import java.io.Serializable;

public @Data
class OrderItem implements Serializable {
        private Dish dish;
        private int portions;
        private String commentary; //additional commentary eg no sauce or extra cheese

        public OrderItem(Dish dish) {
                this.dish = dish;
        }
        public void addPortion(){
                portions += 1;
        }
        public void subtractPortion(){
                portions -= 1;
        }


//relatie met order

//komt aantal van een producrt in een order weer terug
//zoals vier koffie
//zodat je niet vier keer koffie in de dishlijst te zetten

//maak use case diagram
    //keuken een actor maken //nvm
//maak domeinmodel
//maak unit testen
//kijk wat er in ontwerpdocument moet komen

// use case overzicht van de orders
//use case nieuwe order toevoegen
    //html front-end
    //js fetch naar back-end
    // back-end java rest-API maken //geeft json terug
    // datastore met azure maken
    // fack-end en front-end getest
      //postman/unittests -- wave respectively;

//TODO
//        documentatie!! --poster en ontwerpdocument--
//        authenticatie & autorisatie
//        front end - test met Wave(tm)
//        kijk naar de sprints voor de eisen
}
