import {useRouter} from 'next/router';

export const useNavigation = (url) => {
    const router = useRouter();

    return (url) => {
        const targetPath = `/${url}`;
        const currentPath = router.asPath.split('#')[0];
        if (currentPath !== targetPath) {
            router.replace(targetPath).catch(e => console.error(e));
        } else {
            window.scrollTo(0, 0);
        }
    };
};