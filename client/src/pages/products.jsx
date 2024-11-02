import Column from "@components/structures/Column";
import ProductsGrid from "@components/products/ProductsGrid";

const Products = () => {
    return (
        <Column className="justify-center align-middle items-center">
            <ProductsGrid/>
        </Column>
    );
};

export default Products;