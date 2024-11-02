import {Card} from '@tremor/react';
import {NavIcon} from '@components/icons/NavIcon';

export const ProductCard = ({
                         product,
                         onClick
                     }) => {
    return (<div className="shadow hover:shadow-blue-900 hover:shadow-lg">
            <Card className="group p-4 hover:cursor-pointer" onClick={onClick}>
                <div className="flex items-center space-x-4">
                    <NavIcon
                        className="fa-solid bg-blue-100 bg-blue-500/20 flex h-12 w-12 shrink-0 items-center justify-center rounded-tremor-full text-tremor-default"
                        aria-hidden={true}
                        type="icon"
                        iconId="bag-shopping"
                    />
                    <div className="truncate">
                        <p className="truncate text-tremor-default font-medium text-dark-tremor-content-strong">
                            {product.name}
                        </p>
                        <p className="truncate text-tremor-default text-dark-tremor-content">
                            {`Stock: ${product.stock}`}
                        </p>
                    </div>
                </div>
            </Card>
        </div>);
};