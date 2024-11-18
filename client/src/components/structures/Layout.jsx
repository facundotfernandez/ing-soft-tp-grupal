import UserProfileIcon from "@components/icons/UserProfileIcon";
import {useNavigation} from "@hooks/useNavigation";
import {NavViewLinks} from "@components/buttons/NavViewLinks";
import {useEffect} from "react";

const Header = () => {
    const {
        goToHome,
        goToOrders,
        goToProducts,
        goToProductCreate,
        goToCart
    } = useNavigation();

    useEffect(() => {
        document.title = `FiubApp`;
    }, []);

    return (<header className="flex justify-between items-center bg-gray-700 p-4 w-full">
        <NavViewLinks
            goToHome={goToHome}
            goToProducts={goToProducts}
            goToOrders={goToOrders}
            goToCart={goToCart}
            goToProductCreate={goToProductCreate}
        />
        <UserProfileIcon/>
    </header>);
};

const Footer = () => (<footer className="bg-gray-700 p-3 w-full">
    <p className="select-none h-4"></p>
</footer>);

const Layout = ({children}) => (<div className="dark min-h-screen flex flex-col justify-between">
    <Header/>
    <main className="p-4 flex-grow">
        {children}
    </main>
    <Footer/>
</div>);

export default Layout;