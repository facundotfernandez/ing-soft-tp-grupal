import {unshortenId} from "@utils/idShortener";
import {OrderDetails} from "@components/orders/OrderDetails";
import useOrders from "@hooks/useOrders";
import {useEffect, useState} from "react";

const OrderContainer = ({orderId}) => {
    const {
        orders,
    } = useOrders();
    const [loading, setLoading] = useState(true);

    const id = orderId ? unshortenId(orderId) : null;
    const order = orders.find(ord => ord.id === id);

    useEffect(() => {
        if (order) setLoading(false);
    }, [order]);

    return <OrderDetails order={order} loading={loading}/>;
};

export default OrderContainer;