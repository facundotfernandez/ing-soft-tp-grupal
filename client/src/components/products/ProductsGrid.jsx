import {TabGroup, TabPanels} from '@tremor/react';
import {useNavigation} from "@hooks/useNavigation";
import {ProductCard} from './ProductCard';
import {shortenId} from "@utils/idShortener";
import Loader from "@components/notifications/Loader";
import {ToastNotification} from "@components/notifications/ToastNotification";
import Grid from "@components/structures/Grid";
import useProducts from "@hooks/useProducts";

export default function ProductsGrid() {
    const {
        products,
        loading,
        error
    } = useProducts();
    const {goToProduct} = useNavigation();

    const handleClick = (prodId) => {
        goToProduct(shortenId(prodId));
    };

    if (loading) return (<Loader/>);
    if (error) return <ToastNotification message={`Error: ${error.message}`}/>;

    return (<TabGroup className="text-right">
        <TabPanels>
            <Grid>
                {products.map((product) => (<ProductCard
                    key={product._id}
                    product={product}
                    onClick={() => handleClick(product._id)}
                />))}
            </Grid>
        </TabPanels>
    </TabGroup>);
}
