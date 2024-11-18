import {NavLinkCard} from '@components/buttons/NavLinkCard';
import {NavIcon} from '@components/icons/NavIcon';
import {useContext} from "react";
import {UserContext} from "@context/UserProvider";

export const NavViewLinks = ({
                                 goToHome,
                                 goToProducts,
                                 goToProductCreate,
                                 goToOrders,
                                 goToCart
                             }) => {

    const {
        user
    } = useContext(UserContext);

    return (<div className="flex gap-x-2 w-fit">
            <NavLinkCard onClick={goToHome} className={"w-12 justify-center"}>
                <NavIcon type="view" iconId="home"/>
            </NavLinkCard>

            <NavLinkCard
                onClick={goToProducts}
                className={"w-12 justify-center bg-dark-tremor-background-subtle"}
            >
                <NavIcon type="view" iconId="store"/>
            </NavLinkCard>

            {(user?.role === 'client' || user?.role === 'admin') && (<NavLinkCard
                    onClick={goToOrders}
                    className={"w-12 justify-center bg-dark-tremor-background-subtle"}
                >
                    <NavIcon type="view" iconId="clipboard-list"/>
                </NavLinkCard>)}

            {user?.role === 'client' && (<NavLinkCard
                    onClick={goToCart}
                    className={"w-12 justify-center bg-dark-tremor-background-subtle"}
                >
                    <NavIcon type="view" iconId="cart-shopping"/>
                </NavLinkCard>)}

            {user?.role === 'admin' && (<NavLinkCard
                    onClick={goToProductCreate}
                    className={"w-12 justify-center bg-dark-tremor-background-subtle"}
                >
                    <NavIcon type="view" iconId="file-circle-plus"/>
                </NavLinkCard>)}
        </div>);
};