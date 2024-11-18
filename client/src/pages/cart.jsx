import React, { useEffect, useState } from 'react';
import apiService from '@api/apiService';
import ReactDOM from 'react-dom'
import Column from "@components/structures/Column";
import {CartDetails} from "@components/cart/CartDetails";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
const Cart = () => {
    //const [cartItems, setCartItems] = useState([]);

    return (
            <Column className="justify-center align-middle items-center">
                <CartDetails/>
            </Column>
        );
};

export default Cart;