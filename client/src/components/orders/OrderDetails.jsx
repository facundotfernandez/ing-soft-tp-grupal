import { Table, TableBody, TableCell, TableHead, TableHeaderCell, TableRow } from '@tremor/react';
import Column from "@components/structures/Column";
import { ToastNotification } from "@components/notifications/ToastNotification";

export const OrderDetails = ({ order }) => {
    if (!order) return <ToastNotification message={"La orden no existe"} isVisible={true} />;

    return (
        <Column className={"overflow-x-auto p-4"}>
            <div className="overflow-hidden border rounded-md">
                <Table className="min-w-full">
                    <TableHead>
                        <TableRow className="border-b border-dark-tremor-border select-none">
                            <TableHeaderCell className="text-dark-tremor-content-strong text-center">Producto</TableHeaderCell>
                            <TableHeaderCell className="text-dark-tremor-content-strong text-center">Cantidad</TableHeaderCell>
                            <TableHeaderCell className="text-dark-tremor-content-strong text-center">Detalles</TableHeaderCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {order.items.map((item) => (
                            <TableRow key={item.vid} className="select-none hover:bg-dark-tremor-background-muted">
                                <TableCell className="font-medium text-dark-tremor-content-strong text-center">
                                    {item.name}
                                </TableCell>
                                <TableCell className="font-medium text-dark-tremor-content-strong text-center">
                                    {item.quantity}
                                </TableCell>
                                <TableCell className="font-medium text-dark-tremor-content-strong text-center">
                                    {Object.entries(item.specs).map(([key, value]) => (
                                        <div key={key}>{`${key.charAt(0).toUpperCase() + key.slice(1)}: ${value}`}</div>
                                    ))}
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </div>
        </Column>
    );
};