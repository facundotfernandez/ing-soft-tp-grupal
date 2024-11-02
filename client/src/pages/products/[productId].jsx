import {data as products} from "@mocks/products";
import {Table, TableBody, TableCell, TableHead, TableHeaderCell, TableRow} from '@tremor/react';
import {unshortenId} from "@utils/idShortener";
import {NavIcon} from "@components/icons/NavIcon";
import {NavLinkCard} from "@components/buttons/NavLinkCard";
import Column from "@components/structures/Column";

const ProductId = ({productId}) => {
    const id = productId ? unshortenId(productId) : null;
    const product = products.find(prod => prod._id === id);

    if (!product) {
        return <div>El producto no existe</div>;
    }

    const handleAddToCart = (variantId) => {
    };

    const variantKeys = Object.keys(product.variants[0] || {}).filter(key => key !== 'id');
    const availableVariants = product.variants.filter(variant => variant.stock > 0);

    return (<Column className={"overflow-x-auto p-4"}>
            <h1 className="text-xl font-bold">{product.name}</h1>
            <p>{`Stock: ${product.stock}`}</p>
            <div className="overflow-hidden border rounded-md">
                <Table className="min-w-full">
                    <TableHead>
                        <TableRow className="border-b border-tremor-border dark:border-dark-tremor-border select-none">
                            {variantKeys.map((key) => (<TableHeaderCell key={key}
                                                                        className="text-tremor-content-strong dark:text-dark-tremor-content-strong text-center">
                                    {key.charAt(0).toUpperCase() + key.slice(1)} {/* Capitalize first letter */}
                                </TableHeaderCell>))}
                            <TableHeaderCell>
                            </TableHeaderCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {availableVariants.map((variant) => (<TableRow key={variant.id}
                                                                       className="hover:bg-tremor-background-muted hover:dark:bg-dark-tremor-background-muted">
                                {variantKeys.map((key) => (<TableCell key={key}
                                                                      className="font-medium text-tremor-content-strong dark:text-dark-tremor-content-strong text-center">
                                        {variant[key]}
                                    </TableCell>))}
                                <TableCell>
                                    <NavLinkCard
                                        onClick={() => handleAddToCart(variant.id)}
                                        className={"flex justify-center place-items-center w-20"}
                                    >
                                        <NavIcon type={"view"} iconId={"cart-plus"}/>
                                    </NavLinkCard>
                                </TableCell>
                            </TableRow>))}
                    </TableBody>
                </Table>
            </div>
        </Column>);
};

export async function getServerSideProps(context) {
    const {productId} = context.query;
    return {props: {productId}};
}

export default ProductId;
