import {unshortenId} from "@utils/idShortener";
import {ProductDetails} from "@components/products/ProductDetails";
import {useContext, useEffect, useState} from "react";
import {UserContext} from "@context/UserProvider";
import useProducts from "@hooks/useProducts";
import useCart from "@hooks/useCart";

export const ProductContainer = ({productId}) => {
    const {
        products,
    } = useProducts();

    const [loading, setLoading] = useState(true);

    const {addToCart} = useCart();

    const id = productId ? unshortenId(productId) : null;
    const product = products.find(prod => prod.id === id);
    const {user} = useContext(UserContext);

    useEffect(() => {
        if (product) setLoading(false);
    }, [product]);

    const handleAddToCart = (variant, prodName, prodId) => {
        addToCart(variant, prodName, prodId);

    };

    const handleEdit = (variantId) => {
        console.log("edita variante", variantId);
    };

    return <ProductDetails product={product} handleAddToCart={handleAddToCart} handleEdit={handleEdit}
                           role={user?.role} loading={loading} setLoading={setLoading}/>;
};