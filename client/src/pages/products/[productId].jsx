import {ProductContainer} from "@components/products/ProductContainer";

const ProductId = ({ productId }) => {
    return <ProductContainer productId={productId} />;
};

export async function getServerSideProps(context) {
    const { productId } = context.query;
    return { props: { productId } };
}

export default ProductId;