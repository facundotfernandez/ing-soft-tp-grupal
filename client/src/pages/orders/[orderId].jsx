import OrderContainer from "@components/orders/OrderContainer";

const OrderId = ({ orderId }) => {
    return <OrderContainer orderId={orderId} />;
};

export async function getServerSideProps(context) {
    const { orderId } = context.query;
    return { props: { orderId } };
}

export default OrderId;