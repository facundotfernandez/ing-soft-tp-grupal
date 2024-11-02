import {Table, TableBody, TableCell, TableHead, TableHeaderCell, TableRow} from '@tremor/react';
import {NavIcon} from "@components/icons/NavIcon";
import {NavLinkCard} from "@components/buttons/NavLinkCard";
import Column from "@components/structures/Column";
import {ToastNotification} from "@components/notifications/ToastNotification";

export const ProductDetails = ({
                                   product,
                                   handleAddToCart,
                                   handleEdit,
                                   role
                               }) => {
    if (!product) return <ToastNotification message={"El producto no existe"} isVisible={true}/>;

    const variantKeys = Object.keys(product.variants[0] || {}).filter(key => key !== 'id');
    const availableVariants = product.variants.filter(variant => variant.stock > 0);

    return (<Column className={"overflow-x-auto p-4"}>
            <h1 className="text-xl font-bold">{product.name}</h1>
            <p>{`Stock: ${product.stock}`}</p>
            <div className="overflow-hidden border rounded-md">
                <Table className="min-w-full">
                    <TableHead>
                        <TableRow className="border-b border-dark-tremor-border select-none">
                            {variantKeys.map((key) => (
                                <TableHeaderCell key={key} className="text-dark-tremor-content-strong text-center">
                                    {key.charAt(0).toUpperCase() + key.slice(1)} {/* Capitalize first letter */}
                                </TableHeaderCell>))}
                            <TableHeaderCell/>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {availableVariants.map(
                            (variant) => (<TableRow key={variant.id} className="hover:bg-dark-tremor-background-muted">
                                    {variantKeys.map((key) => (<TableCell key={key}
                                                                          className="font-medium text-dark-tremor-content-strong text-center">
                                            {variant[key]}
                                        </TableCell>
                                    ))}
                                <TableCell>
                                    {role === "guest" ? null : (
                                        <NavLinkCard
                                            onClick={role === "admin" ? () => handleEdit(variant.id) : () => handleAddToCart(variant.id)}
                                            className={"flex justify-center place-items-center w-20"}
                                        >
                                            <NavIcon
                                                type={"view"}
                                                iconId={role === "admin" ? "pen" : "cart-plus"}
                                            />
                                        </NavLinkCard>
                                    )}
                                </TableCell>
                            </TableRow>))}
                    </TableBody>
                </Table>
            </div>
        </Column>);
};