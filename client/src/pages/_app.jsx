import '@styles/globals.css';
import Layout from '@components/structures/Layout';
import {UserProvider} from "@context/UserProvider";
import {ProductsProvider} from "@context/ProductsProvider";
import {OrdersProvider} from "@context/OrdersProvider";

function App({
                 Component,
                 pageProps
             }) {
    return (<ProductsProvider>
        <OrdersProvider>
            <UserProvider>
                <Layout>
                    <Component {...pageProps} />
                </Layout>
            </UserProvider>
        </OrdersProvider>
    </ProductsProvider>);
}

export default App;