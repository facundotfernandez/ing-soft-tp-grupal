import {createContext, useState, useEffect, useContext} from 'react';
import useCart from "@hooks/useCart";
import {data as cartMockData} from '@mocks/cart';

const CartContext = createContext(undefined, undefined); //const CartContext = createContext(, );

//CartProvider tiene useCart y clearCart
export const CartProvider = ({children}) => {
    const {cart, setCart, clearCart, addToCart, removeFromCart, buyCart} = useCart();

    return (<CartContext.Provider value={{
        cart,
        setCart,
        clearCart,
        addToCart,
        buyCart,
        removeFromCart
    }}>
        {children}
    </CartContext.Provider>);
};

export default CartProvider;
