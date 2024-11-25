import {NavIcon} from '@components/icons/NavIcon';

export const OrderIcon = ({
                              order,
                              userRole,
                              onDelete
                          }) => {
    const isDeletable = () => {
        if (order.status === 'cancelado') {
            return false;
        }

        const confirmationDate = new Date(order.confirmationDate);
        const now = new Date();
        const diffInMilliseconds = now - confirmationDate;
        return diffInMilliseconds < (24 * 60 * 60 * 1000);
    };

    const getIconId = (status) => {
        if (isDeletable() && userRole === 'client') return "trash";

        switch (status) {
            case "cancelado":
                return "circle-minus";
            case "en proceso":
                return "truck-fast";
            case "enviado":
                return "bag-shopping";
            case "confirmado":
                return "check-circle";
            default:
                return "question";
        }
    };

    const getIconClasses = (status) => {
        if (isDeletable() && userRole === 'client') return "text-red-500 hover:bg-red-500/20";

        switch (status) {
            case "en proceso":
                return "text-yellow-500 hover:bg-yellow-500/20";
            case "enviado":
                return "text-blue-500 hover:bg-blue-500/30";
            case "confirmado":
                return "text-green-500 hover:bg-green-500/30";
            default:
                return "text-gray-500 hover:bg-gray-500/20";
        }
    };

    return (<NavIcon
        className={`${getIconClasses(
            order.status)} z-30 fa-solid bg-blue-100 bg-blue-500/20 flex h-12 w-12 shrink-0 items-center justify-center rounded-tremor-full text-tremor-default`}
        aria-hidden={true}
        type="icon"
        iconId={getIconId(order.status)}
        onClick={isDeletable() ? (e) => {
            e.stopPropagation();
            e.preventDefault();
            onDelete()
        } : null}
    />);
};