document.addEventListener('DOMContentLoaded', function() {
    updateCartItemCount();
});

window.addEventListener('load', function() {
    updateCartItemCount();
});

document.addEventListener("DOMContentLoaded", function() {
    updateProductsInBag();
});

function addToCart(button){
    const productId = button.getAttribute('data-id');

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    fetch('/bag/add-to-cart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(productId)
    })
        .then(response => {
            if (response.ok) {
                console.log('Товар успешно добавлен в корзину.');
                updateCartItemCount();
            } else {
                console.error('Ошибка при добавлении товара в корзину.');
            }
        })
        .catch(error => {
            console.error('Ошибка сети:', error);
        });
}

function updateCartItemCount() {
    fetch('/cart-count', {
        method: 'GET',
    })
        .then(function(response) {
            if (!response.ok) {
                throw new Error('Ошибка при получении количества товаров в корзине');
            }
            return response.json();
        })
        .then(function(data) {
            let cartItemCountElement = document.getElementById('cartItemCount');
            cartItemCountElement.textContent = data;
        })
        .catch(function(error) {
            console.log(error);
        });
}

function updateProductsInBag(){

    const currencySelect = document.getElementById('currency');
    const currency = currencySelect.value;
    console.log("ВАЛЮТА" + currency);
    console.log("ИЗМЕНИЛИ ВАЛЮТУ")

    fetch("/bag/cart-products?currency=" + encodeURIComponent(currency))
        .then(response => response.json())
        .then(data => {
            let totalPrice = 0;
            const tableBody = document.querySelector(".cart-table tbody");
            tableBody.innerHTML = "";

            data.forEach(cartItem => {
                const row = document.createElement("tr");
                console.log("NEWPRODUCT", cartItem);
                console.log("PHOTO" + cartItem.product.photoName)
                const price = cartItem.product.price * cartItem.count;
                totalPrice = totalPrice + price;
                row.innerHTML = `
                    <td><img src="/file/${cartItem.product.photoName}" alt=""></td>
                    <td>${cartItem.product.nameOfProduct}</td>
                    <td>${price}</td>
                    <td><button class="btn-minus" data-product-id="${cartItem.product.id}">-</button></td>
                    <td>${cartItem.count}</td>
                    <td><button class="btn-plus" data-product-id="${cartItem.product.id}">+</button></td>
                    <td><button class="btn-delete"><img src="/images/delete.jpg" alt=""></button></td>
                `;

                // Добавление обработчика события для кнопки "btn-minus"
                const btnPlus = row.querySelector('.btn-plus');
                btnPlus.addEventListener('click', () => {
                    console.log("event");
                    plusProductInCart(cartItem.product.id);
                });

                // Добавление обработчика события для кнопки "btn-plus"
                const btnMinus = row.querySelector('.btn-minus');
                btnMinus.addEventListener('click', () => {
                    minusProductInCart(cartItem.product.id);
                });

                const btnDelete = row.querySelector('.btn-delete');
                btnDelete.addEventListener('click', () => {
                    deleteCartItemFromCart(cartItem.product.id);
                });

                tableBody.appendChild(row);
            });

            let cartItemCountElement = document.getElementById('total-price');
            cartItemCountElement.textContent = totalPrice;
        })
        .catch(error => console.log(error));
}

function plusProductInCart(productId){
    console.log(productId);
    console.log("try add");

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    fetch('/bag/add-to-cart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(productId)
    })
        .then(response => {
            if (response.ok) {
                console.log('Товар успешно добавлен в корзину.');
                updateCartItemCount();
                updateProductsInBag();
            } else {
                console.error('Ошибка при добавлении товара в корзину.');
            }
        })
        .catch(error => {
            console.error('Ошибка сети:', error);
        });
}

function minusProductInCart(productId){
    console.log(productId);
    console.log("try delete");

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    fetch('/bag/delete-from-cart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(productId)
    })
        .then(response => {
            if (response.ok) {
                console.log('Одна единица товара успешно удалена из корзины');
                updateCartItemCount();
                updateProductsInBag();
            } else {
                console.error('Ошибка при удалении товара в корзину.');
            }
        })
        .catch(error => {
            console.error('Ошибка сети:', error);
        });
}

function deleteCartItemFromCart(productId){
    console.log("try delete full");

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    fetch('/bag/remove', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(productId)
    })
        .then(response => {
            if (response.ok) {
                console.log('Товар удален полностью из корзины');
                updateCartItemCount();
                updateProductsInBag();
            } else {
                console.error('Ошибка при удалении товара в корзину.');
            }
        })
        .catch(error => {
            console.error('Ошибка сети:', error);
        });
}