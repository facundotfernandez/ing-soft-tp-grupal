import React, {useEffect} from 'react';
import useAuth from '@hooks/useAuth';
import {fetchOrders} from '@api/orders';

const Orders = (clientId) => {
    const {logout} = useAuth();

    useEffect(() => {
        const getOrders = async () => {
            try {
                const orders = await fetchOrders(clientId);
                console.log(orders);
            } catch (error) {
                console.error('Error fetching orders:', error);
                logout();
            }
        };
        getOrders();
    }, [clientId, logout]);

    return (<div>
            <h2>Mis Órdenes</h2>
        </div>);
};

export default Orders;
