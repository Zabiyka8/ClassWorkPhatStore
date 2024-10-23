'use strict';

const selectColor = document.getElementById('select_color');
const selectSize = document.getElementById('select_size');
const inputItemId = document.getElementById('input_itemId');

const addToCartRequest  = () => {
    fetch('/item/addToCart', {
        method: 'POST',
        headers: {
            "Content-type": 'application/json; charset=utf-8'
        },
        body: JSON.stringify({
            color: selectColor.value,
            size: selectSize.value,
            itemId: inputItemId.value
        })
    }).then(response => response.json()).then(data => console.log(data));
    console.log(";lj;lkj;lkj")
};

const button = document.querySelector('button');

button.addEventListener('click', addToCartRequest);