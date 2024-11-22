import {TabGroup, TabPanels} from '@tremor/react';
import {useNavigation} from "@hooks/useNavigation";
import {OrderCard} from './OrderCard';
import Grid from "@components/structures/Grid";
import Loader from "@components/notifications/Loader";
import {showToast} from "@components/notifications/ToastManager";
import useOrders from "@hooks/useOrders";
import {shortenId} from "@utils/idShortener";
import NotFound from "next/dist/client/components/not-found-error";
import React from "react";

export default function OrdersGrid() {
    const {
        orders,
        loading,
        error
    } = useOrders();
    const {goToOrder} = useNavigation();

    const handleClick = (orderId) => {
        goToOrder(shortenId(orderId));
    };


    if (loading) return (<Loader/>);
    if (error) {
        showToast.error("Error al procesar las órdenes");
        return (<NotFound/>);
    }

    return (<TabGroup className="text-right">
        <TabPanels>
            <Grid>
                {orders.map((order, index) => (<OrderCard
                    key={order.id}
                    index={index}
                    order={order}
                    onClick={() => handleClick(order.id)}
                />))}
            </Grid>
        </TabPanels>
    </TabGroup>);
}
