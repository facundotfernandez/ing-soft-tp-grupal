import {data as orders} from "@mocks/orders";
import {unshortenId} from "@utils/idShortener";
import {OrderDetails} from "@components/orders/OrderDetails";

const OrderContainer = ({orderId}) => {
    const id = orderId ? unshortenId(orderId) : null;
    const order = orders.find(ord => ord._id === id);

    return <OrderDetails order={order}/>;
};

export default OrderContainer;