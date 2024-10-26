import UserProfileIcon from "@components/icons/UserProfileIcon";
import {handleNavigation} from "@utils/navigation";
import {router} from "next/client";
import {NavLinkCard} from "@components/buttons/NavLinkCard";
import {NavIcon} from "@components/icons/NavIcon";

const Header = () => (<header className="flex justify-between items-center bg-gray-700 p-4 w-full">
    <NavLinkCard onClick={() => handleNavigation(router, '')}>
        <NavIcon type="view" iconId="home"/>
    </NavLinkCard>
    <UserProfileIcon/>
</header>);

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