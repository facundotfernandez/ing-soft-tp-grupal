import {TabGroup, TabPanels} from '@tremor/react';
import {useNavigation} from "@hooks/useNavigation";
import {ProductCard} from './ProductCard';
import {shortenId} from "@utils/idShortener";
import Loader from "@components/notifications/Loader";
import {showToast} from "@components/notifications/ToastManager";
import Grid from "@components/structures/Grid";
import NotFound from "next/dist/client/components/not-found-error";
import {useContext} from "react";
import {ProductsContext} from "@context/ProductsProvider";

export default function ProductsGrid() {
    const {
        products,
        loading,
        error
    } = useContext(ProductsContext);
    const {goToProduct} = useNavigation();

    const handleClick = (prodId) => {
        goToProduct(shortenId(prodId));
    };

    if (loading) return (<Loader/>);
    if (error) {
        showToast.error("Error al procesar el producto no existe");
        return <NotFound/>;
    }

    return (<TabGroup className="text-right">
        <TabPanels>
            <Grid>
                {products.map((product, index) => (<ProductCard
                    key={index + "-" + product.id}
                    product={product}
                    onClick={() => handleClick(product.id)}
                />))}
            </Grid>
        </TabPanels>
    </TabGroup>);
}
