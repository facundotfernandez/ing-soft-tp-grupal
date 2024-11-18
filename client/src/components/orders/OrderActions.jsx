import orderId from "@pages/orders/[orderId]";

export const OrderActions = ({
                                 order,
                                 userRole,
                                 handleNewStatus
                             }) => {
    const getNextStatus = (currentStatus) => {
        switch (currentStatus) {
            case "confirmado":
                return "en proceso";
            case "en proceso":
                return "enviado";
        }
    };

    const getButtonClasses = (status) => {
        switch (status) {
            case "en proceso":
                return "bg-yellow-600 hover:bg-yellow-600/20";
            case "enviado":
                return "bg-blue-600 hover:bg-blue-600/30";
            case "confirmado":
                return "bg-green-600 hover:bg-green-600/30";
            default:
                return "bg-gray-600 hover:bg-gray-600/20";
        }
    };

    const nextStatus = getNextStatus(order.status);
    const buttonClasses = getButtonClasses(nextStatus);

    return ((userRole === "admin") && !["cancelado", "enviado"].includes(order.status) && (<div className="ml-auto">
        <button
            onClick={(e) => {
                handleNewStatus(e, order?.id, nextStatus);
            }}
            className={`text-xs text-white bg-blue-500 px-2 py-1 rounded-full ${buttonClasses}`}
        >
            {`Cambiar a ${nextStatus}`}
        </button>
    </div>));
};
