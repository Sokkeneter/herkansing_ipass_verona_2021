// import RestaurantService from "./service/RestaurantService";


function getDishes() {
    return fetch('http://localhost:8080/restservices/dishes')
        .then((response) => response.json());
}

function updateList(){
    const dishesList = document.querySelector('.dishesList');
    // dishesList.textContent = '';

    getDishes()
        .then((dishes) => {
            console.table(dishes);
            for (const dish in dishes) {
                addListItem(dish, dishes[dish]);
            }
        });
}
function addListItem(key, dish){
    const dishesList = document.querySelector('.dishesList');
    const dishesTemplate = document.getElementById('dishTemplate');
    const DishesTemplateClone = document.importNode(dishesTemplate.content, true);

    // console.log('dish:'+dish);

    DishesTemplateClone.querySelector('.dishNumber').textContent = dish.dishNumber;
    DishesTemplateClone.querySelector('.name').textContent = dish.dishName;
    DishesTemplateClone.querySelector('.price').textContent = dish.price;
    DishesTemplateClone.querySelector('.category').textContent = dish.category;

    const addOrderButton = document.querySelector('#addToOrderButton');
    // addOrderButton.addEventListener('click', (_event) => window.location.assign(`/dishForm.html?key=${dish.dishNumber}`));

    document.querySelector('#viewOrdersButton').addEventListener('click', ()=> window.location.assign('/orders.html'));
    const liElement = DishesTemplateClone.querySelector('li');
    liElement.setAttribute('id', key);

    dishesList.append(DishesTemplateClone);
}
updateList();