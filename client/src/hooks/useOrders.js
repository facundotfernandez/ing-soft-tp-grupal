import {useContext, useEffect, useState} from 'react';
import {getOrders} from "@api/readRequests";
import {UserContext} from "@context/UserProvider";

export const useOrders = () => {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const {user} = useContext(UserContext);

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
    }, [user]);


    return {
        orders,
        loading,
        error
    };
};