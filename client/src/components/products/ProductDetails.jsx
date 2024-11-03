import { useState } from 'react';
import { Table, TableBody, TableCell, TableHead, TableHeaderCell, TableRow } from '@tremor/react';
import { NavIcon } from "@components/icons/NavIcon";
import { NavLinkCard } from "@components/buttons/NavLinkCard";
import Column from "@components/structures/Column";
import { ToastNotification } from "@components/notifications/ToastNotification";
import { InputField } from "@components/inputs/InputField";
import { updateVariant } from "@api/updateRequests";

export const ProductDetails = ({ product, handleAddToCart, handleEdit, role }) => {
    const [editingVariant, setEditingVariant] = useState(null);
    const [variantData, setVariantData] = useState({});

    if (!product) return <ToastNotification message={"El producto no existe"} isVisible={true} />;

    const variantKeys = Object.keys(product.variants[0].specs || {}).filter(key => key !== 'id');
    const availableVariants = role === 'admin'
        ? product.variants
        : product.variants.filter(variant => variant.stock > 0);

    const handleInputChange = (key, value) => {
        setVariantData(prev => ({ ...prev, [key]: value }));
    };

    const handleSave = async (variantId) => {
        const newStock = variantData.stock || product.stock;
        if (newStock < 0) {
            alert("El stock no puede ser menor a 0");
            return;
        }

        try {
            await updateVariant(product._id, variantId, variantData);
            setEditingVariant(null);
        } catch (error) {
            console.error("Error al actualizar la variante:", error);
        }
    };

    const renderTableCell = (variant, key) => {
        return editingVariant === variant.id ? (
            <InputField
                type="text"
                value={variantData[key] || variant.specs[key]}
                onChange={(e) => handleInputChange(key, e.target.value)}
                className="border rounded p-1 w-full"
            />
        ) : (
            variant.specs[key]
        );
    };

    const renderStockCell = (variant) => {
        return editingVariant === variant.id ? (
            <InputField
                type="number"
                value={variantData.stock || variant.stock}
                onChange={(e) => handleInputChange('stock', e.target.value)}
                className="border rounded p-1 w-full"
            />
        ) : (
            variant.stock
        );
    };

    const renderActionCell = (variant) => {
        if (role === "client") {
            return (
                <NavLinkCard
                    onClick={() => handleAddToCart(variant.id)}
                    className={"flex justify-center place-items-center w-20"}
                >
                    <NavIcon type={"view"} iconId={"cart"} />
                </NavLinkCard>
            );
        } else if (role === "admin") {
            return editingVariant === variant.id ? (
                <NavLinkCard
                    onClick={() => handleSave(variant.id)}
                    className={"flex justify-center place-items-center w-20"}
                >
                    <NavIcon type={"view"} iconId={"save"} />
                </NavLinkCard>
            ) : (
                <NavLinkCard
                    onClick={() => {
                        setEditingVariant(variant.id);
                        setVariantData({ ...variant.specs, stock: variant.stock });
                    }}
                    className={"flex justify-center place-items-center w-20"}
                >
                    <NavIcon type={"view"} iconId={"pen"} />
                </NavLinkCard>
            );
        }
        return null;
    };

    return (
        <Column className={"overflow-x-auto p-4"}>
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
                                    {key.charAt(0).toUpperCase() + key.slice(1)} {/* Capitalize first letter */}
                                </TableHeaderCell>
                            ))}
                            <TableHeaderCell/>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {availableVariants.map((variant) => (
                            <TableRow key={variant.id} className="hover:bg-dark-tremor-background-muted">
                                <TableCell className="font-medium text-dark-tremor-content-strong text-center">
                                    {renderStockCell(variant)}
                                </TableCell>
                                {variantKeys.map((key) => (
                                    <TableCell key={key} className="font-medium text-dark-tremor-content-strong text-center">
                                        {renderTableCell(variant, key)}
                                    </TableCell>
                                ))}
                                <TableCell>
                                    {renderActionCell(variant)}
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </div>
        </Column>
    );
};