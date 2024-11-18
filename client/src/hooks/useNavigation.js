import {useRouter} from 'next/router';
import {routes} from "@routes/navigation";

export const useNavigation = () => {
    const router = useRouter();

    const handleNavigation = ((url) => {
        const targetPath = `/${url}`;
        const currentPath = router.asPath.split('#')[0];
        if (currentPath !== targetPath) {
            router.replace(targetPath).catch(e => console.error(e));
        } else {
            window.scrollTo(0, 0);
        }
    });

    const goToCart = (() => {
        handleNavigation(routes.cart);
    });

    const goToHome = (() => {
        handleNavigation(routes.home);
    });

    const goToLogin = (() => {
        handleNavigation(routes.login);
    });

    const goToRecovery = (() => {
        handleNavigation(routes.recovery);
    });

    const goToRegister = (() => {
        handleNavigation(routes.register);
    });

    const goToProduct = ((id) => {
        handleNavigation(routes.product + id);
    });

    const goToProductCreate = ((id) => {
        handleNavigation(routes.productCreate);
    });

    const goToProducts = (() => {
        handleNavigation(routes.products);
    });

    const goToOrder = ((id) => {
        handleNavigation(routes.order + id);
    });

    const goToOrders = (() => {
        handleNavigation(routes.orders);
    });

    return {
        goToCart,
        goToHome,
        goToLogin,
        goToRecovery,
        goToRegister,
        goToProduct,
        goToProductCreate,
        goToProducts,
        goToOrder,
        goToOrders,
    };
};