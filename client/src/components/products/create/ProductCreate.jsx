import {useState} from 'react';
import {Table, TableBody, TableCell, TableHead, TableHeaderCell, TableRow} from '@tremor/react';
import {InputField} from "@components/inputs/InputField";
import {NavLinkCard} from "@components/buttons/NavLinkCard";
import Column from "@components/structures/Column";
import {NavIcon} from "@components/icons/NavIcon";
import {createProduct} from "@api/createRequests";
import NotFound from "next/dist/client/components/not-found-error";
import {useUser} from "@hooks/useUser";

const AttributesSection = ({
                               attributes,
                               onAddAttribute,
                               onAttributeChange,
                               onRemoveAttribute,
                               locked
                           }) => (<Column className="gap-y-2">
    <h2 className="text-md font-semibold">Atributos</h2>
    {attributes.map((attribute, index) => (<div key={index} className="flex items-center w-fit gap-x-2">
        <InputField
            placeholder="Nombre del Atributo"
            value={attribute.name}
            onChange={(e) => onAttributeChange(index, 'name', e.target.value)}
            disabled={locked}
        />
        {!locked && (<NavLinkCard
            className="bg-red-700/20 hover:bg-red-700/70"
            onClick={() => onRemoveAttribute(index)}>
            <NavIcon
                type={"view"}
                iconId={"trash-can"}
                className="text-sm text-red-500"
            />
        </NavLinkCard>)}
    </div>))}
    {!locked && (<NavLinkCard
        className="w-fit"
        onClick={onAddAttribute}>
        <NavIcon
            type={"view"}
            iconId={"plus"}
            className={"text-xs"}
        />
    </NavLinkCard>)}
</Column>);

const VariantsSection = ({
                             attributes,
                             variants,
                             onAddVariant,
                             onVariantChange,
                             onRemoveVariant
                         }) => (<Column>
    <h2 className="text-md font-semibold">Variantes</h2>
    <NavLinkCard onClick={onAddVariant} className="text-xs w-fit">Agregar Variante</NavLinkCard>
    <Column className="overflow-x-auto">
        <Table className="min-w-full">
            <TableHead>
                <TableRow>
                    <TableHeaderCell>Stock</TableHeaderCell>
                    {attributes.map(
                        (attribute, index) => (<TableHeaderCell key={index}>{attribute.name}</TableHeaderCell>))}
                </TableRow>
            </TableHead>
            <TableBody>
                {variants.map((variant, variantIndex) => (<TableRow key={variantIndex}>
                    <TableCell>
                        <div className="w-fit">
                            <InputField
                                type="number"
                                value={variant.stock}
                                onChange={(e) => onVariantChange(variantIndex, 'stock', e.target.value)}
                            />
                        </div>
                    </TableCell>
                    {attributes.map((attribute, index) => (<TableCell key={index}>
                        <div className="w-fit">
                            <InputField
                                value={variant.specs[attribute.name] || ''}
                                onChange={(e) => onVariantChange(variantIndex, attribute.name, e.target.value)}
                            />
                        </div>
                    </TableCell>))}
                    <TableCell>
                        <NavLinkCard
                            className="bg-red-700/20 hover:bg-red-700/70 w-fit"
                            onClick={() => onRemoveVariant(variantIndex)}>
                            <NavIcon
                                type={"view"}
                                iconId={"trash-can"}
                                className="text-sm text-red-500"
                            />
                        </NavLinkCard>
                    </TableCell>
                </TableRow>))}
            </TableBody>
        </Table>
    </Column>
</Column>);

export const ProductCreate = () => {
    const [name, setName] = useState("");
    const [attributes, setAttributes] = useState([{name: ""}]);
    const [variants, setVariants] = useState([]);
    const [attributesLocked, setAttributesLocked] = useState(false);

    const {user} = useUser();

    const handleAttributeChange = (index, field, value) => {
        const updatedAttributes = [...attributes];
        updatedAttributes[index][field] = value;
        setAttributes(updatedAttributes);
    };

    const handleVariantChange = (variantIndex, key, value) => {
        const updatedVariants = [...variants];
        if (key === 'stock') {
            updatedVariants[variantIndex].stock = value;
        } else {
            updatedVariants[variantIndex].specs[key] = value;
        }
        setVariants(updatedVariants);
    };

    const handleAddAttribute = () => {
        setAttributes([...attributes, {name: ""}]);
    };

    const handleRemoveAttribute = (index) => {
        setAttributes(attributes.filter((_, i) => i !== index));
    };

    const handleAddVariant = () => {
        setVariants([...variants, {
            stock: "",
            specs: attributes.reduce((acc, attribute) => {
                acc[attribute.name] = "";
                return acc;
            }, {})
        }]);
    };

    const handleRemoveVariant = (variantIndex) => {
        setVariants(variants.filter((_, i) => i !== variantIndex));
    };

    const handleLockAttributes = () => {
        if (!name.trim()) {
            alert("El nombre del producto no puede estar vacío.");
            return;
        }

        const allAttributesFilled = attributes.every(attr => attr.name.trim() !== "");
        if (!allAttributesFilled) {
            alert("Todos los campos de atributos deben tener texto.");
            return;
        }

        setAttributesLocked(true);
    };

    const handleSaveProduct = async () => {
        const formattedProduct = {
            name,
            variants: variants.map(variant => ({
                stock: variant.stock,
                specs: {...variant.specs}
            }))
        };

        await createProduct(formattedProduct);
        alert("Producto creado exitosamente");

        setName("");
        setAttributes([{name: ""}]);
        setVariants([]);
        setAttributesLocked(false);
    };

    if (user?.role !== 'admin') return (<></>)

    return (<Column className="p-4 gap-y-8">
        <Column className="gap-y-2">
            <h1 className="text-xl font-bold">Crear Nuevo Producto</h1>
            <div className="w-fit">
                <InputField
                    placeholder="Nombre del Producto"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    disabled={attributesLocked}
                />
            </div>
        </Column>

        <AttributesSection
            attributes={attributes}
            onAddAttribute={handleAddAttribute}
            onAttributeChange={handleAttributeChange}
            onRemoveAttribute={handleRemoveAttribute}
            locked={attributesLocked}
        />

        {!attributesLocked && (<NavLinkCard
            className="w-fit text-md"
            onClick={handleLockAttributes}>
            <NavIcon
                type={"view"}
                iconId={"lock"}
            />
            Bloquear Información
        </NavLinkCard>)}

        {attributesLocked && (<VariantsSection
            attributes={attributes}
            variants={variants}
            onAddVariant={handleAddVariant}
            onVariantChange={handleVariantChange}
            onRemoveVariant={handleRemoveVariant}
        />)}

        <div className="flex justify-between mt-6">
            <NavLinkCard onClick={handleSaveProduct} className="text-md w-fit">
                Guardar Producto
            </NavLinkCard>
        </div>
    </Column>);
};
