export default class RestaurantService {
    static getDishes() {
        return fetch('http://localhost:8080/restservices/dishes')
            .then((response) => response.json());
    }

    static getDish(dishNumber) {
        return fetch(`http://localhost:8080/restservices/dishes/${dishNumber}`)
            .then ((response) => {
                if (!response.ok) {
                    throw new Error(response.status);
                }
                return response.json();
            });
    }

    static getRestaurant(){
        return fetch(`http://localhost:8080/restservices/restaurant`)
            .then ((response) => {
                if (!response.ok) {
                    throw new Error(response.status);
                }
                console.table(response)
                return response.json();
            });
    }


    static postDish(number, formData) {
        return fetch(`http://localhost:8080/restservices/dishes/${number}`, {
            method: 'POST',
            body: formData,
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error(response.status);
                }
                return response;
            });
    }


}