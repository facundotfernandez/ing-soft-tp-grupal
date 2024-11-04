import { unshortenId } from "@utils/idShortener";
import {ProductDetails} from "@components/products/ProductDetails";
import {useContext} from "react";
import {UserContext} from "@context/UserProvider";
import useProducts from "@hooks/useProducts";

export const ProductContainer = ({ productId }) => {
    const {
        products,
    } = useProducts();

    const id = productId ? unshortenId(productId) : null;
    // const product = products.find(prod => prod._id === id);
    const product = products.find(prod => prod.name === id); // TODO
    const {user} = useContext(UserContext);

    const handleAddToCart = (variantId) => {
        console.log("añade al carrito", variantId);
    };

    const handleEdit = (variantId) => {
        console.log("edita variante", variantId);
    };

    return <ProductDetails product={product} handleAddToCart={handleAddToCart} handleEdit={handleEdit} role={user?.role}/>;
};