import {TabGroup, TabPanels} from '@tremor/react';
import {useNavigation} from "@hooks/useNavigation";
import {OrderCard} from './OrderCard';
import {shortenId} from "@utils/idShortener";
import Grid from "@components/structures/Grid";
import Loader from "@components/notifications/Loader";
import {ToastNotification} from "@components/notifications/ToastNotification";
import useOrders from "@hooks/useOrders";

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
    if (error) return <ToastNotification message={`Error: ${error.message}`} isVisible={true}/>;

    return (<TabGroup className="text-right">
        <TabPanels>
            <Grid>
                {orders.map((order, index) => (<OrderCard
                    key={order._id}
                    index={index}
                    order={order}
                    onClick={() => handleClick(order._id)}
                />))}
            </Grid>
        </TabPanels>
    </TabGroup>);
}
