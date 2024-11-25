import {createContext} from 'react';
import useOrders from "@hooks/useOrders";

export const OrdersContext = createContext(undefined, undefined);

export const OrdersProvider = ({children}) => {
    const {
        orders,
        loading,
        error
    } = useContext(OrdersContext);

    return (<OrdersContext.Provider value={{
        orders,
        loading,
        error
    }}>
        {children}
    </OrdersContext.Provider>);
};