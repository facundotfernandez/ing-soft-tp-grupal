import {unshortenId} from "@utils/idShortener";
import {ProductDetails} from "@components/products/ProductDetails";
import {useContext} from "react";
import {UserContext} from "@context/UserProvider";
import useProducts from "@hooks/useProducts";
import useCart from "@hooks/useCart";

export const ProductContainer = ({productId}) => {
    const {
        products,
    } = useProducts();

    const {addToCart} = useCart();

    const id = productId ? unshortenId(productId) : null;
    const product = products.find(prod => prod.id === id);
    const {user} = useContext(UserContext);

    const handleAddToCart = (variant, prodName, prodId) => {
        console.log("añade al carrito",prodName );
        console.log("wipi");
        addToCart(variant, prodName, prodId);
        //addToCart(variant, quantity);

    };

    const handleEdit = (variantId) => {
        console.log("edita variante", variantId);
    };

    return <ProductDetails product={product} handleAddToCart={handleAddToCart} handleEdit={handleEdit}
                           role={user?.role}/>;
};