import {data as products} from "@mocks/products"
import {Card, TabGroup, TabPanels,} from '@tremor/react';
import {useNavigation} from "@hooks/useNavigation";
import {NavIcon} from "@components/icons/NavIcon";
import {shortenId} from "@utils/idShortener";

export default function ProductsGrid() {

    const handleNavigation = useNavigation();

    const handleClick = (prodId) => {
        handleNavigation(`products/${shortenId(prodId)}`);
    };

    return (<>
        <TabGroup className="text-right">
            <TabPanels>
                <div className="mt-6 grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
                    {products.map((product) => (<div key={product._id}>
                        <Card className="group p-4" onClick={() => handleClick(product._id)}>
                            <div className="flex items-center space-x-4">
                                <NavIcon
                                    className={`fa-solid bg-blue-100 dark:bg-blue-500/20 text-blue-800 dark:text-blue-500 flex h-12 w-12 shrink-0 items-center justify-center rounded-tremor-full text-tremor-default`}
                                    aria-hidden={true}
                                    type={"icon"}
                                    iconId={"bag-shopping"}
                                />
                                <div className="truncate">
                                    <p className="truncate text-tremor-default font-medium text-tremor-content-strong dark:text-dark-tremor-content-strong">
                                        <a href={product.href} className="focus:outline-none">
                                            <span className="absolute inset-0" aria-hidden={true}/>
                                            {product.name}
                                        </a>
                                    </p>
                                    <p className="truncate text-tremor-default text-tremor-content dark:text-dark-tremor-content">
                                        {`Stock: ${product.stock}`}
                                    </p>
                                </div>
                            </div>
                        </Card>
                    </div>))}
                </div>
            </TabPanels>
        </TabGroup>
    </>);
}