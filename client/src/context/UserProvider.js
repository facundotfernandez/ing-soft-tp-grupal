import {createContext, useEffect, useState} from 'react';
import {createLogin} from "@api/createRequests";

export const UserContext = createContext();

export const UserProvider = ({children}) => {
    const [user, setUser] = useState(null);
    const GUEST_INPUT_DATA = 'guest';

    useEffect(() => {
        const storedUser = sessionStorage.getItem('user_data');
        if (storedUser) setUser(JSON.parse(storedUser)); else login(GUEST_INPUT_DATA, GUEST_INPUT_DATA);
    }, []);

    useEffect(() => {
        if (user) {
            localStorage.setItem('access_token', user.accessToken);
            sessionStorage.setItem('user_data', JSON.stringify(user));
        } else {
            localStorage.removeItem('access_token');
            sessionStorage.removeItem('user_data');
        }
    }, [user]);

    const login = async (username, password) => {
        const userData = await createLogin({
            username,
            password
        });
        setUser(userData);

        localStorage.setItem('access_token', userData.accessToken);
        sessionStorage.setItem('user_data', JSON.stringify(userData));
    };

    return (<UserContext.Provider value={{
        user,
        setUser,
        login,
    }}>
        {children}
    </UserContext.Provider>);
};