import Column from "@components/structures/Column";
import OrdersGrid from "@components/orders/OrdersGrid";

const Orders = () => {
    return (
        <Column className="justify-center align-middle items-center">
            <OrdersGrid/>
        </Column>
    );
};

export default Orders;