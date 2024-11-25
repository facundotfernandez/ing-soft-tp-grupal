import {createContext} from 'react';
import {useProducts} from "@hooks/useProducts";

export const ProductsContext = createContext(undefined, undefined);

export const ProductsProvider = ({children}) => {
    const {
        products,
        loading,
        error
    } = useProducts();

    return (<ProductsContext.Provider value={{
        products,
        loading,
        error
    }}>
        {children}
    </ProductsContext.Provider>);
};