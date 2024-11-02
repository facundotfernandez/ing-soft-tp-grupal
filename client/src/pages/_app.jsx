import '@styles/globals.css';
import Layout from '@components/structures/Layout';
import {UserProvider} from "@context/UserProvider";
import {ProductsProvider} from "@context/ProductsProvider";
import {OrdersProvider} from "@context/OrdersProvider";

function App({
                 Component,
                 pageProps
             }) {
    return (<UserProvider>
        <ProductsProvider>
            <OrdersProvider>
                <Layout>
                    <Component {...pageProps} />
                </Layout>
            </OrdersProvider>
        </ProductsProvider>
    </UserProvider>);
}

export default App;