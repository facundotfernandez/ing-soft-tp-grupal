import {useState} from "react";
import {Card} from '@tremor/react';
import {ConfirmationModal} from "@components/notifications/ConfirmationModal";
import {useUser} from "@hooks/useUser";
import {OrderActions} from "@components/orders/OrderActions";
import {OrderIcon} from "@components/orders/OrderIcon";
import {OrderCardDetails} from "@components/orders/OrderCardDetails";

export const OrderCard = ({
                              order,
                              onClick
                          }) => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [orderToDelete, setOrderToDelete] = useState(null);
    const {user} = useUser();

    const handleDeleteOrder = (orderId, e) => {
        e.stopPropagation();
        setOrderToDelete(orderId);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setOrderToDelete(null);
    };

    const confirmDelete = () => {
        if (orderToDelete) {
            console.log(`Order deleted: ${orderToDelete}`);
        }
    };

    const handleNewStatus = async (e, orderId, newStatus) => {
        e.stopPropagation();
        // const response = await patchOrder(orderId, newStatus);
        // alert(response?.message);
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