import {useEffect, useState} from 'react';
import {create as createApi} from "@api/apiService";
const useCart = () => {
    const [cart, setCart] = useState([]);

    //Limpiar el carrito cuando se desloggea el usuario
    const clearCart = () =>{
        setCart([]);
    };
    /*
     const addToCart = (variant, quantity) => {
            setCart((prevCart) => {
                console.log("Agregando a carrito");
                const isAlreadyInCart = prevCart.some(
                    (item) => item.vid === variant.vid && item.name === variant.name
                );//checkeo si la variante ya esta agregada
                if (isAlreadyInCart) {
                    console.log("Producto ya agregado, no se agrega de vuelta");
                    return prevCart; // Si ya existe, no lo agrega
                }
                console.log("Agregado correctamente");

                return [...prevCart, variant]; // Si no existe, lo agrega
            });
     };
    */
    const addToCart = (variant, prodName, prodId) => {
        setCart((prevCart) => {
            console.log("Agregando a carrito");

            // Verificar si ya existe el mismo variant asociado al producto
            const alreadyAdded = prevCart.some(
                (item) => item.variant.vid === variant.vid && item.prodName === prodName
            );

            if (alreadyAdded) {
                console.log("Producto ya agregado, sumo cantidad");
                return prevCart.map((item) =>
                    item.variant.vid === variant.vid
                        ? { ...item, quantity: item.quantity + 1 }
                        : item
                );
            }

            console.log("Agregado correctamente");
            // Si no existe, se agrega una nueva entrada
            return [
                ...prevCart,
                { variant, prodName ,prodId, quantity: 1 }
            ];
        });
    };
    // Eliminar del carrito
    const removeFromCart = (variant) => {
        setCart((prevCart) => prevCart.filter((item) => item.variant.vid !== variant.vid));

    };

    const buyCart = async (username) => {
        const orderData = {
            username,
            items: cart.map(item => ({
                vid: item.variant.vid,
                pid: item.prodId,
                name: item.prodName,
                //product.name
                quantity: item.quantity,
                specs: item.variant.specs
            })),
        };
        console.log("ORDEN: ",orderData);
        try {
            const response = await createApi("orders", orderData); //mando la key y la lista
            console.log("response: ",response.status);
            if (response.status === "success") {
                alert("Orden creada exitosamente");
                setCart([]);
            } else {
                alert(`Hubo un problema al procesar tu orden: ${response.message}`);
            }
        } catch (error) {
            alert("Error al procesar tu orden");
            console.error(error);
        }

    };



    useEffect(() => {
            try {
                console.log("Cargando carrito desde localStorage...");
                const storedCart = localStorage.getItem('cart_data');
                if (storedCart) {
                    const parsedCart = JSON.parse(storedCart);
                    if (Array.isArray(parsedCart)) {
                        setCart(parsedCart);
                    }
                }
            } catch (error) {
                console.error("Error cargando el carrito desde localStorage:", error);
                setCart([]);
            }
        }, []);

    useEffect(() => {
        console.log("Carrito actual:", cart);
        if (cart.length > 0) {
            localStorage.setItem('cart_data', JSON.stringify(cart));
        }
        else{
            localStorage.removeItem('cart_data'); //lo seteo a cero directamente para que no buguee
        }
    }, [cart]);

    return {cart , setCart, clearCart, addToCart, removeFromCart, buyCart };
    };

export default useCart;