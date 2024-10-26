import '@styles/globals.css';
import Layout from '@components/structures/Layout';
import {UserProvider} from "@context/UserProvider";
import {AuthProvider} from "@context/AuthProvider";

function App({
                 Component,
                 pageProps
             }) {
    return (<AuthProvider>
        <UserProvider>
            <Layout>
                <Component {...pageProps} />
            </Layout>
        </UserProvider>
    </AuthProvider>);
}

export default App;