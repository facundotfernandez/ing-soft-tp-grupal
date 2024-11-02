import {Card} from '@tremor/react';
import {NavIcon} from '@components/icons/NavIcon';

export const OrderCard = ({
                       order,
                       index,
                       onClick,
                   }) => {

    const uniqueProductNames = Array.from(new Set(order.products.map(product => product.name)));
    let productNames = uniqueProductNames.join(', ');

    return (<div className="shadow hover:shadow-blue-900 hover:shadow-lg">
        <Card className="group p-4 hover:cursor-pointer" onClick={onClick}>
            <div className="flex items-center gap-x-4">
                <NavIcon
                    className="fa-solid bg-blue-100 bg-blue-500/20 text-blue-500 flex h-12 w-12 shrink-0 items-center justify-center rounded-tremor-full text-tremor-default"
                    aria-hidden={true}
                    type="icon"
                    iconId="bag-shopping"
                />
                <div className="truncate">
                    <p className="text-sm text-dark-tremor-content-strong">
                        {productNames}
                    </p>
                    <p className="truncate text-tremor-default text-dark-tremor-content">
                        {`Orden ${index + 1}`}
                    </p>
                </div>
            </div>
        </Card>
    </div>);
};

export default OrderCard;