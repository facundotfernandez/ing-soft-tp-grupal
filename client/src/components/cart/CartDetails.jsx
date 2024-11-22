import { Table, TableBody, TableCell, TableHead, TableHeaderCell, TableRow } from '@tremor/react';
import Column from "@components/structures/Column";
import {showToast} from "@components/notifications/ToastManager";
import useCart from "@hooks/useCart";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import useProducts from "@hooks/useProducts";
import {useUser} from "@hooks/useUser";
import NotFound from "next/dist/client/components/not-found-error";

export const CartDetails = ({ }) => {

    const {cart, setCart, clearCart, removeFromCart, buyCart} = useCart();//addToCart //removeFromCart
    const {products } = useProducts(); //lo uso para hacer un search y renderizar los productos
    const {user} = useUser();
    //llamar userId desde userContext o algo

    if (!cart || cart.length === 0) {
        showToast.error("El carrito está vacío o no tiene productos.");
        return <NotFound/>;
    }
    //la decision fue guardar solos los prodsId y vid en el carrito y luego checkear en vez de que el carrito tenga el producto completo
    return (
        <Column className={"overflow-x-auto p-4"}>
            <div className="overflow-hidden border rounded-md">
                <Table className="min-w-full">
                    <TableHead>
                        <TableRow className="border-b border-dark-tremor-border select-none">
                            <TableHeaderCell className="text-dark-tremor-content-strong text-center">Producto</TableHeaderCell>
                            <TableHeaderCell className="text-dark-tremor-content-strong text-center">Cantidad</TableHeaderCell>
                            <TableHeaderCell className="text-dark-tremor-content-strong text-center">Detalles</TableHeaderCell>
                            <TableHeaderCell className="text-dark-tremor-content-strong text-center">Remover</TableHeaderCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {cart.map((item) => {

                            console.log("El producto es: ", item.prodName); //debug

                            return (
                                <TableRow key={item.variant.vid} className="select-none hover:bg-dark-tremor-background-muted">
                                    <TableCell className="font-medium text-dark-tremor-content-strong text-center">
                                        {item.prodName}
                                    </TableCell>
                                    <TableCell className="font-medium text-dark-tremor-content-strong text-center">
                                        {item.quantity}
                                    </TableCell>
                                    <TableCell className="font-medium text-dark-tremor-content-strong text-center">
                                        {Object.entries(item.variant.specs).map(([key, value]) => (
                                            <div key={`${item.variant.vid} - ${key}`}>{`${key.charAt(0).toUpperCase() + key.slice(1)}: ${value}`}</div>
                                        ))}
                                    </TableCell>
                                    <TableCell className="text-center">
                                        <button
                                            onClick={() => removeFromCart(item.variant)}
                                            className="text-red-600 hover:text-red-800 flex justify-center items-center"
                                            style={{ padding: '4px', display: 'inline-flex', alignItems: 'center' }}
                                            aria-label="Remover">
                                            <FontAwesomeIcon icon={faTrash} />
                                        </button>
                                    </TableCell>
                                </TableRow>
                            );
                        })}
                    </TableBody>
                </Table>
            </div>
            <div className="mt-4 text-center">
                <button
                    onClick={() => buyCart(user.name)}
                    className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 focus:outline-none"
                >
                    Comprar
                </button>
            </div>
        </Column>
    );
};