import {createContext} from 'react';
import {useCart} from "@hooks/useCart";

export const CartContext = createContext(undefined, undefined); //const CartContext = createContext(, );

export const CartProvider = ({children}) => {
    const {
        cart,
        setCart,
        clearCart,
        addToCart,
        removeFromCart,
        buyCart
    } = useCart();

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
