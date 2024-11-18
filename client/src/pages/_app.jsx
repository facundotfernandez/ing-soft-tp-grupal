import '@styles/globals.css';
import Layout from '@components/structures/Layout';
import {UserProvider} from "@context/UserProvider";
import {ProductsProvider} from "@context/ProductsProvider";
import {OrdersProvider} from "@context/OrdersProvider";
import {CartProvider} from "@context/CartProvider";

function App({
                 Component,
                 pageProps
             }) {
    return (<ProductsProvider>
        <OrdersProvider>
            <UserProvider>
                <CartProvider>
                <Layout>
                    <Component {...pageProps} />
                </Layout>
                </CartProvider>
            </UserProvider>
        </OrdersProvider>
    </ProductsProvider>);
}

export default App;