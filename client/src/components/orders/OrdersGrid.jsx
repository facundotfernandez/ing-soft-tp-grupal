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
import useProducts from "@hooks/useProducts";
import {patchVariant} from "@api/patchRequests";
import {OrdersContext} from "@context/OrdersProvider";

export default function OrdersGrid() {
    const {
        orders,
        loading,
        error
    } = useContext(OrdersContext);
    const {goToOrder} = useNavigation();
    const {products} = useProducts();

    const handleClick = (orderId) => {
        goToOrder(shortenId(orderId));
    };

    const cancelOrder = async (order) => {
        await Promise.all(
            order.items.map(async (item) => {
                const variant = products
                    .flatMap((p) => p.variants)
                    .find((v) => v.vid === item.vid);

                if (variant) {
                    const newStock = variant.stock + item.quantity;
                    await patchVariant(item.pid, item.vid, newStock);
                }
            })
        );
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
                    cancelOrder={cancelOrder}
                />))}
            </Grid>
        </TabPanels>
    </TabGroup>);
}
