import {useNavigation} from "@hooks/useNavigation";
import {useEffect} from "react";

const Home = () => {
    const {goToProducts} = useNavigation();

    useEffect(() => {
        goToProducts();
    }, [goToProducts]);

    return (
        <></>
    );
};

export default Home;