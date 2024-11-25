import React from 'react';
import Column from "@components/structures/Column";
import {CartDetails} from "@components/cart/CartDetails";

const Cart = () => {
    return (<Column className="justify-center align-middle items-center">
        <CartDetails/>
    </Column>);
};

export default Cart;