import {unshortenId} from "@utils/idShortener";
import {OrderDetails} from "@components/orders/OrderDetails";
import {useContext, useEffect, useState} from "react";
import {OrdersContext} from "@context/OrdersProvider";

const OrderContainer = ({orderId}) => {
    const {
        orders,
    } = useContext(OrdersContext);

    const [loading, setLoading] = useState(true);

    const id = orderId ? unshortenId(orderId) : null;
    const order = orders.find(ord => ord.id === id);

    useEffect(() => {
        if (order) setLoading(false);
    }, [order]);

    return <OrderDetails order={order} loading={loading}/>;
};

export default OrderContainer;