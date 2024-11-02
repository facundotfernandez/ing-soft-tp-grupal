import {NavLinkCard} from '@components/buttons/NavLinkCard';
import {NavIcon} from '@components/icons/NavIcon';

export const NavViewLinks = ({
                      goToHome,
                      goToProducts,
                      goToOrders,
                      goToCart
                  }) => {
    return (<div className="flex gap-x-2 w-fit">
            <NavLinkCard
                onClick={goToHome}
                className={"w-12 justify-center"}>
                <NavIcon type="view" iconId="home"/>
            </NavLinkCard>
            <NavLinkCard
                onClick={goToProducts}
                className={"w-12 justify-center bg-dark-tremor-background-subtle"}>
                <NavIcon type="view" iconId="store"/>
            </NavLinkCard>
            <NavLinkCard
                onClick={goToOrders}
                className={"w-12 justify-center bg-dark-tremor-background-subtle"}>
                <NavIcon type="view" iconId="clipboard-list"/>
            </NavLinkCard>
            <NavLinkCard
                onClick={goToCart}
                className={"w-12 justify-center bg-dark-tremor-background-subtle"}>
                <NavIcon type="view" iconId="cart-shopping"/>
            </NavLinkCard>
        </div>);
};