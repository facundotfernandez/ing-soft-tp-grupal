import {unshortenId} from "@utils/idShortener";
import {OrderDetails} from "@components/orders/OrderDetails";
import useOrders from "@hooks/useOrders";

const OrderContainer = ({orderId}) => {
    const {
        orders,
    } = useOrders();

    const id = orderId ? unshortenId(orderId) : null;
    const order = orders.find(ord => ord.id === id);

    return <OrderDetails order={order}/>;
};

export default OrderContainer;