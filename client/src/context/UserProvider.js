import {createContext, useEffect, useState} from 'react';
import {createLogin, createRegister} from "@api/createRequests";

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
            localStorage.setItem('accessToken', user.accessToken);
            sessionStorage.setItem('user_data', JSON.stringify(user));
        } else {
            localStorage.removeItem('accessToken');
            sessionStorage.removeItem('user_data');
        }
    }, [user]);

    const login = async (username, password) => {
        const userData = await createLogin({
            username,
            password
        });
        setUser(userData);

        localStorage.setItem('accessToken', userData.accessToken);
        sessionStorage.setItem('user_data', JSON.stringify(userData));
    };

    const register = async (formData) => {
        await createRegister(formData);
    };

    return (<UserContext.Provider value={{
        user,
        setUser,
        login,
        register
    }}>
        {children}
    </UserContext.Provider>);
};