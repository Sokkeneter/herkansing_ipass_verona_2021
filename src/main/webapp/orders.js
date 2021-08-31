





function getTable(tableNumber) {
    return fetch(`http://localhost:8080/restservices/tables/${tableNumber}`)
        .then ((response) => {
            if (!response.ok) {
                throw new Error(response.status);
            }
            return response.json();
        });
}

function fillOrdersList(){
    console.log('asdfajhdjlsg');
    getTable(1).then((table)=>{
        document.querySelector('#tableNumber').textContent = table.tableNumber;
        const orders = table.orders;
        const orderList = document.querySelector('#orderList');
        const orderTemplate = document.getElementById('orderTemplate');
        const orderTemplateClone = document.importNode(orderTemplate.content, true);

        const itemsList = orderTemplateClone.querySelector('#itemsList');
        const itemTemplate = document.getElementById('itemTemplate');
        const itemTemplateClone = document.importNode(itemTemplate.content, true);
        orders.forEach((order)=>{
            orderTemplateClone.querySelector('#ordernumber').textContent = `order number: ${order.orderNumber}`;
            orderTemplateClone.querySelector('#status').textContent = `status: ${order.status}`;
            const items = order.items;

        try{
            if(order.status === 'send'){

            if(items.length >0){
                items.forEach((item)=>{
                    itemTemplateClone.querySelector('#dishname').textContent = `dish: ${item.dish.dishName}`;
                    itemTemplateClone.querySelector('#portions').textContent = `portsions: ${item.portions}`;
                    itemTemplateClone.querySelector('#commentary').textContent = `commentary${item.commentary}`;

                    itemsList.append(itemTemplateClone);
                });
            }else{
                itemsList.append('no dishes in this order.');
            }

        }
        else {

            if(items.length >0){
                items.forEach((item)=>{
                    itemTemplateClone.querySelector('#dishname').textContent = `dish: ${item.dish.dishName}`;
                    const portionsNumberInput = itemTemplateClone.querySelector('#portionsNumberInput');
                    portionsNumberInput.hidden = false;
                    portionsNumberInput.value = item.portions;
                    const commentaryTextarea = itemTemplateClone.querySelector('#commentaryTextarea');
                    commentaryTextarea.hidden = false;
                    commentaryTextarea.value = item.commentary;

                    itemsList.append(itemTemplateClone);
                });
            }else{
                itemsList.append('no dishes in this order.'); //this is so frickin inefficient
            }
        }
        }catch (error){
            console.log(error);

            if(items.length >0){
                items.forEach((item)=>{
                    itemTemplateClone.querySelector('#dishname').textContent = `dish: ${item.dish.dishName}`;
                    itemTemplateClone.querySelector('#portions').textContent = `portsions: ${item.portions}`;
                    itemTemplateClone.querySelector('#commentary').textContent = `commentary${item.commentary}`;

                    itemsList.append(itemTemplateClone);
                });
            }else{
                itemsList.append('no dishes in this order.');
            }
        }


        })

        orderList.append(orderTemplateClone);
    })
}


fillOrdersList();