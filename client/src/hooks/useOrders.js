import {useEffect, useState} from 'react';
import {getOrders} from "@api/readRequests";
import useProducts from "@hooks/useProducts";
import {patchVariant} from "@api/patchRequests";

const useOrders = () => {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const {products} = useProducts();

    useEffect(() => {
        const fetchOrders = async () => {
            setLoading(true);
            try {
                const response = await getOrders();
                setOrders(Array.isArray(response.data) ? response.data : []);
            } catch (err) {
                setError(err.message || 'Error desconocido');
            } finally {
                setLoading(false);
            }
        };

        fetchOrders();
    }, []);




    return {
        orders,
        loading,
        error
    };
};

export default useOrders;
