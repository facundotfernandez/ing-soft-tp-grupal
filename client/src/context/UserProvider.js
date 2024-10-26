import {createContext, useContext, useEffect, useState} from "react";
import {AuthContext} from "@context/AuthProvider";
import {data as usersData} from "@mocks/users";

export const UserContext = createContext({});

export const UserProvider = ({children}) => {
    const [user, setUser] = useState({});
    const {auth} = useContext(AuthContext);

    useEffect(() => {
        const fetchUserData = async () => {
            const userData = usersData.find(user => user.accessToken === auth.accessToken);
            setUser(userData);
        };
        fetchUserData().catch(err => console.error('Error fetching user data:', err));
    }, [auth]);

    return (<UserContext.Provider value={{
        user,
        setUser,
    }}>
        {children}
    </UserContext.Provider>);
};
