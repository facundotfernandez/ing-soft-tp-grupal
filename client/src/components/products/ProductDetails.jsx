import {useState} from 'react';
import {Table, TableBody, TableCell, TableHead, TableHeaderCell, TableRow,} from '@tremor/react';
import {NavIcon} from "@components/icons/NavIcon";
import {NavLinkCard} from "@components/buttons/NavLinkCard";
import Column from "@components/structures/Column";
import {showToast} from "@components/notifications/ToastManager";
import {InputField} from "@components/inputs/InputField";
import {patchVariant} from "@api/patchRequests";
import Loader from "@components/notifications/Loader";
import NotFound from "next/dist/client/components/not-found-error";

export const ProductDetails = ({
                                   product,
                                   handleAddToCart,
                                   role,
                                   loading,
                               }) => {
    const [editingVariant, setEditingVariant] = useState(null);
    const [variantData, setVariantData] = useState({});

    if (loading) return <Loader/>;
    if (!product) return <NotFound/>;

    const {variants} = product;
    const variantKeys = Object.keys(variants[0].specs || {});
    const availableVariants = role === 'admin' ? variants : variants.filter(variant => variant.stock > 0);

    const handleInputChange = (key, value) => {
        setVariantData(prev => ({
            ...prev,
            [key]: value
        }));
    };

    const handleSave = async (variantId) => {
        const newStock = variantData.stock !== undefined ? variantData.stock : product.stock;
        if (newStock < 0) {
            showToast.error("El stock no puede ser menor a 0");
            return;
        }

        try {
            await patchVariant(product.id, variantId, newStock);
            setEditingVariant(null);
        } catch (error) {
            showToast.error("Error al actualizar la variante");
            console.error(error);
        }
    };

    const renderTableCell = (variant, key) => variant.specs[key];

    const renderStockCell = (variant) => (editingVariant === variant.vid ? (<InputField
        type="number"
        value={variantData.stock || variant.stock}
        onChange={(e) => handleInputChange('stock', e.target.value)}
        className="border rounded p-1"
    />) : (variant.stock));

    const renderActionCell = (variant) => {
        if (role === "client") {
            return (<NavLinkCard
                onClick={() => handleAddToCart(variant, product.name, product.id)}
                className={"flex justify-center place-items-center w-20"}
            >
                <NavIcon type={"view"} iconId={"cart-plus"}/>
            </NavLinkCard>);
        } else if (role === "admin") {
            return (<NavLinkCard
                onClick={() => {
                    if (editingVariant === variant.vid) {
                        handleSave(variant.vid);
                    } else {
                        setEditingVariant(variant.vid);
                        setVariantData({
                            ...variant.specs,
                            stock: variant.stock
                        });
                    }
                }}
                className={"flex justify-center place-items-center w-20"}
            >
                <NavIcon type={"view"} iconId={editingVariant === variant.vid ? "save" : "pen"}/>
            </NavLinkCard>);
        }
        return null;
    };

    return (<Column className={"overflow-x-auto p-4"}>
        <h1 className="text-xl font-bold">{product.name}</h1>
        <div className="overflow-hidden border rounded-md">
            <Table className="min-w-full">
                <TableHead>
                    <TableRow className="border-b border-dark-tremor-border select-none">
                        <TableHeaderCell className="text-dark-tremor-content-strong text-center">
                            {"Stock"}
                        </TableHeaderCell>
                        {variantKeys.map((key) => (
                            <TableHeaderCell key={key} className="text-dark-tremor-content-strong text-center">
                                {key.charAt(0).toUpperCase() + key.slice(1)}
                            </TableHeaderCell>))}
                        <TableHeaderCell/>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {availableVariants.map(
                        (variant) => (<TableRow key={variant.vid} className="hover:bg-dark-tremor-background-muted">
                            <TableCell className="font-medium text-dark-tremor-content-strong text-center">
                                {renderStockCell(variant)}
                            </TableCell>
                            {variantKeys.map((key) => (<TableCell key={key}
                                                                  className="font-medium text-dark-tremor-content-strong text-center">
                                {renderTableCell(variant, key)}
                            </TableCell>))}
                            <TableCell>
                                {renderActionCell(variant)}
                            </TableCell>
                        </TableRow>))}
                </TableBody>
            </Table>
        </div>
    </Column>);
};