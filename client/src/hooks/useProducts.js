import { useEffect, useState } from 'react';
import { data as productsLocalData } from '@mocks/products';
// import { getProducts } from "@api/readRequests";

const useProducts = () => {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchProducts = async () => {
            setLoading(true);
            try {
                // const response = await getProducts();
                // setProducts(response); TODO API HANDLE
                setProducts(productsLocalData);
            } catch (err) {
                setError(err.message || 'Error desconocido');
            } finally {
                setLoading(false);
            }
        };

        fetchProducts();
    }, []);

    return { products, loading, error };
};

export default useProducts;
