export const OrderCardDetails = ({order}) => {
    const uniqueProductNames = Array.from(new Set(order.items.map(product => product.name)));
    const productNames = uniqueProductNames.join(', ');

    return (<div className="truncate">
        <p className="text-sm text-dark-tremor-content-strong">{productNames}</p>
        <p className="truncate text-tremor-default text-dark-tremor-content">
            {`${order.status.charAt(0).toUpperCase() + order.status.slice(1).toLowerCase()}`}
        </p>
        <p className="truncate text-tremor-default text-dark-tremor-content">
            {`Confirmación: ${new Date(order.confirmationDate).toLocaleDateString('es-ES', {
                day: '2-digit',
                month: '2-digit',
                year: '2-digit'
            })}`}
        </p>
    </div>);
};