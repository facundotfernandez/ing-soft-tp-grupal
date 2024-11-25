import {useEffect, useState} from 'react';
import {create as createApi} from "@api/apiService";
import {patchVariant} from "@api/patchRequests";
import {showToast} from "@components/notifications/ToastManager";

const useCart = () => {
    const [cart, setCart] = useState([]);

    //Limpiar el carrito cuando se desloggea el usuario
    const clearCart = () => {
        setCart([]);
    };

    const addToCart = (variant, prodName, prodId) => {
        setCart((prevCart) => {
            // Verificar si ya existe el mismo variant asociado al producto
            const alreadyAdded = prevCart.some(
                (item) => item.variant.vid === variant.vid && item.prodName === prodName);

            if (alreadyAdded) {
                return prevCart.map((item) => item.variant.vid === variant.vid ? {
                    ...item,
                    quantity: item.quantity + 1
                } : item);
            }
            // Si no existe, se agrega una nueva entrada
            return [...prevCart, {
                variant,
                prodName,
                prodId,
                quantity: 1
            }];
        });
    };
    // Eliminar del carrito
    const removeFromCart = (variant) => {
        setCart((prevCart) => prevCart.filter((item) => item.variant.vid !== variant.vid));

    };

    const buyCart = async (username) => {
        if (cart.length == 0){
            showToast.error("Agrega objetos al carrito antes de continuar");
            return
        }
        const orderData = {
            username,
            items: cart.map(item => ({
                vid: item.variant.vid,
                pid: item.prodId,
                name: item.prodName,
                stock: item.variant.stock,
                quantity: item.quantity,
                specs: item.variant.specs
            })),
        };
        try {
            const response = await createApi("orders", orderData); //mando la key y la lista
            if (response.status === "success") {
                //cambiar el stock de los productos
                try {
                    await Promise.all(orderData.items.map(async (item) => {
                        const newStock = item.stock - item.quantity;
                        if (newStock < 0) {
                            throw new Error(`Stock insuficiente para el producto ${item.name}`);
                        }
                        await patchVariant(item.pid, item.vid, newStock);
                    }));
                    showToast.error("Orden creada exitosamente");
                    setCart([]); // Vaciar el carrito
                } catch (error) {
                    showToast.error("Error al actualizar el stock del producto");
                    console.error(error);
                }

            } else {
                showToast.error("Hubo un error creando la orden");
            }
        } catch (error) {
            showToast.error("Hubo un error creando la orden");
        }

    };


    useEffect(() => {
        try {
            const storedCart = localStorage.getItem('cart_data');
            if (storedCart) {
                const parsedCart = JSON.parse(storedCart);
                if (Array.isArray(parsedCart)) {
                    setCart(parsedCart);
                }
            }
        } catch (error) {
            setCart([]);
        }
    }, []);

    useEffect(() => {
        if (cart.length > 0) {
            localStorage.setItem('cart_data', JSON.stringify(cart));
        } else {
            localStorage.removeItem('cart_data'); //lo seteo a cero directamente para que no buguee
        }
    }, [cart]);

    return {
        cart,
        setCart,
        clearCart,
        addToCart,
        removeFromCart,
        buyCart
    };
};

export default useCart;