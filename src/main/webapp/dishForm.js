// import RestaurantService from "./service/RestaurantService";

function getDish(dishNumber) {
    return fetch(`http://localhost:8080/restservices/dishes/${dishNumber}`)
        .then ((response) => {
            if (!response.ok) {
                throw new Error(response.status);
            }
            return response.json();
        });
}

function getRestaurant(){
    return fetch(`http://localhost:8080/restservices/restaurant`)
        .then ((response) => {
            if (!response.ok) {
                throw new Error(response.status);
            }
            return response.json();
        });
}


function postDish(formdata) {
    const objectUrlEncoded = new URLSearchParams(Object.entries(formdata)).toString();                    // turn the object into a urlsearchparam cuz it has to url-encoded (at least thats what i think x-www-urlencoded means
    // console.log('x-www-urlencoded: '+objectUrlEncoded);
    console.log(formdata);
    return fetch(`http://localhost:8080/restservices/dishes`, {
        method: 'POST',
        body: objectUrlEncoded,
        headers: {"Content-Type":"application/x-www-form-urlencoded; charset-utf-8"}
    })
        .then((response) => {
            // if (!response.ok) {
            //     throw new Error(response.status);
            // }
            return response;
        });
}
function putDish(number,formData) {
    return fetch(`http://localhost:8080/restservices/dishes/${number}`, {
        method: 'PUT',
        body: formData,
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error(response.status);
            }
            return response;
        });
}

function deleteDish(number){
    return fetch(`http://localhost:8080/restservices/dishes/${number}`, {
        method: 'DELETE',
        body: formData,
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error(response.status);
            }
            return response;
        });
}
function addCategory(name){
    console.log('add category')
    const form = document.forms['newCategoryForm'];
    const formData = new FormData(form);
    return fetch(`http://localhost:8080/restservices/restaurant`,{
        method: 'POST',
        body: formData,
    })
        .then ((response) => {
            if (!response.ok) {
                throw new Error(response.status);
            }
            return response.json();
        });
}

//i dont know why my imports dont work :(

// function urlencodeFormData(fd){
//     var params = new URLSearchParams();
//     for(var pair of fd.entries()){
//         typeof pair[1]=='string' && params.append(pair[0], pair[1]);
//     }
//     return params.toString();
// }

function showNewCategoryButton(){
    console.log('showNewCategory')
    const form = document.forms['dishForm'];
    const formData = new FormData(form);
    const addCategoryForm = document.querySelector('#addCategoryDiv');
    addCategoryForm.hidden = false;
    console.log(addCategoryForm.hidden);
    document.querySelector('#submitCategoryButton').addEventListener(('click'), (event) => addCategory(formData)
        .then((response)=> console.log(response))
        // .catch((error) => {
        //     const CONFLICT_ERROR = 409;
        //     errorParagraph.hidden = false;
        //     if (Number(error.message) === CONFLICT_ERROR) {
        //         // set text at code-error and mark input field red
        //
        //         errorParagraph.textContent = 'categorie bestaat al';
        //     } else {
        //         errorParagraph.textContent = error.message;
        //     }
        // })
    );
}
//todo
//  fix newcategory
//  fix newDish
//  continue with updateDish
// then deleteDish

const submitButton = document.querySelector('#submitButton');
const errorParagraph = document.querySelector('.errorMessage');
const categorySelect = document.querySelector('#category');

    function fillForm(dishNumber){

    console.log('fillform');
    const newCategoryButton = document.querySelector('#newCategory');
    newCategoryButton.addEventListener('click', (event)=> showNewCategoryButton());
    getRestaurant().then((restaurant) => {
         const categoryList = restaurant.categories;

        categoryList.forEach((category)=> {
            const listItem = document.createElement('option');
            listItem.textContent = category;
            categorySelect.appendChild(listItem);

        })
    });



    if(dishNumber == null){ //its for adding new dish
        submitButton.addEventListener('click', (event)=> {
            event.preventDefault();                                                                                     //prevent page from redirection and wiping all logs i cant debug like this bruh
            const formData = new FormData(document.querySelector('form'))                                       // get the form from the html page
            const formDataObject = {};                                                                                  // initialize a new object
            for (var pair of formData.entries()) {
                // console.log(pair[0] + ': ' + pair[1]);
                formDataObject[pair[0]] = pair[1];                                                                      // put formdata into object
            }
        postDish(formData).then((response)=>{                                                          // send it to the postDish function
                // console.log(response);                                                                               // it doesnt work
            });

        });
    }else { //its for editing existing dish
        const deleteButton = document.querySelector('#deleteButton');
        deleteButton.hidden = false;
        deleteButton.addEventListener('click', (event) => deleteDish(dishNumber))

        getDish(dishNumber).then((dish) => {
            document.querySelector('#dishnumber').value = dish.dishNumber;
            document.querySelector('#dishname').value = dish.dishName;
            document.querySelector('#price').value = dish.price;
            document.querySelector('#category').value = dish.category;
        })

    }
}

let dishNumber = null;
let urlParams = (new URL(document.location)).searchParams;
dishNumber = urlParams.get('key');
document.querySelector('#backbutton').addEventListener('click', () => window.location.assign('/'));

fillForm(dishNumber);