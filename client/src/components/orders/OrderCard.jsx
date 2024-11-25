import {useState} from "react";
import {Card} from '@tremor/react';
import {ConfirmationModal} from "@components/notifications/ConfirmationModal";
import {useUser} from "@hooks/useUser";
import {OrderActions} from "@components/orders/OrderActions";
import {OrderIcon} from "@components/orders/OrderIcon";
import {OrderCardDetails} from "@components/orders/OrderCardDetails";
import {showToast} from "@components/notifications/ToastManager";
import {patchOrder} from "@api/patchRequests";
import {OrdersContext} from "@context/OrdersProvider";

export const OrderCard = ({
                              order,
                              onClick,
                              cancelOrder
                          }) => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const {user} = useUser();

    const handleDeleteOrder = () => {
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };

    const confirmDelete = () => {
        handleNewStatus(order?.id, "cancelado");
    };

    const handleNewStatus = async (orderId, newStatus) => {

        if (newStatus === "cancelado") {
            await cancelOrder(order);
        }
        const response = await patchOrder(orderId, {status: newStatus});
        showToast.success(response?.message);

    };


    return (<>
        <div className="shadow hover:shadow-blue-900 hover:shadow-lg">
            <Card className="group p-4 hover:cursor-pointer" onClick={onClick}>
                <div className="flex items-center gap-x-4">
                    <OrderIcon
                        order={order}
                        userRole={user?.role}
                        onDelete={handleDeleteOrder}
                    />
                    <OrderCardDetails order={order}/>
                    <OrderActions order={order} userRole={user?.role} handleNewStatus={handleNewStatus}/>
                </div>
            </Card>
        </div>
        <ConfirmationModal
            isOpen={isModalOpen}
            onClose={closeModal}
            onConfirm={confirmDelete}
            message="¿Estás muy muy muy seguro de que querés eliminar esta orden?"
        />
    </>);
};

export default OrderCard;