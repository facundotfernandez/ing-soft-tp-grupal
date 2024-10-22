import { useState, useEffect } from 'react';
import { fetchOrders } from '@api/orders';

const useOrders = () => {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchOrders().then((data) => {
            setOrders(data);
            setLoading(false);
        });
    }, []);

    return { orders, loading };
};

export default useOrders;
