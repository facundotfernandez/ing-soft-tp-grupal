import UserProfileIcon from "@components/icons/UserProfileIcon";
import {useNavigation} from "@hooks/useNavigation";
import {NavLinkCard} from "@components/buttons/NavLinkCard";
import {NavIcon} from "@components/icons/NavIcon";

const Header = () => {
    const handleNavigation = useNavigation();

    return (<header className="flex justify-between items-center bg-gray-700 p-4 w-full">
            <div className="flex gap-x-2">
                <NavLinkCard onClick={() => handleNavigation('')}>
                    <NavIcon type="view" iconId="home"/>
                </NavLinkCard>
                <NavLinkCard onClick={() => handleNavigation('products')}
                             className={"bg-dark-tremor-background-subtle"}>
                    <NavIcon type="view" iconId="store"/>
                </NavLinkCard>
                <NavLinkCard onClick={() => handleNavigation('cart')} className={"bg-dark-tremor-background-subtle"}>
                    <NavIcon type="view" iconId="cart-shopping"/>
                </NavLinkCard>
            </div>
            <UserProfileIcon/>
        </header>);
};

const Footer = () => (<footer className="bg-gray-700 p-3 w-full">
    <p className="select-none  h-4"></p>
</footer>);

const Layout = ({children}) => (<div className="dark min-h-screen flex flex-col justify-between">
    <Header/>
    <main className="p-4 flex-grow">
        {children}
    </main>
    <Footer/>
</div>);

export default Layout;