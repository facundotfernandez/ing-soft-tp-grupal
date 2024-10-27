import '@styles/globals.css';
import Layout from '@components/structures/Layout';
import {UserProvider} from "@context/UserProvider";

function App({
                 Component,
                 pageProps
             }) {
    return (<UserProvider>
            <Layout>
                <Component {...pageProps} />
            </Layout>
        </UserProvider>);
}

export default App;