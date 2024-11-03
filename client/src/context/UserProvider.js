import {createContext, useEffect, useState} from 'react';
import {createLogin, createRecovery, createRegister} from "@api/createRequests";
import PropTypes from "prop-types";

export const UserContext = createContext(undefined, undefined);

export const UserProvider = ({children}) => {
    const [user, setUser] = useState(null);
    const GUEST_ROLE = 'guest';

    useEffect(() => {
        const storedUser = sessionStorage.getItem('user_data');
        if (storedUser) {
            setUser(JSON.parse(storedUser));
        } else {
            login(GUEST_ROLE, GUEST_ROLE);
        }
    }, []);

    const login = async (username, password) => {
        const userData = await createLogin({
            username,
            password
        });

        setUser(userData);

        localStorage.setItem('access_token', userData.accessToken);
        sessionStorage.setItem('user_data', JSON.stringify(userData));
    };

    const logout = () => {
        login(GUEST_ROLE, GUEST_ROLE);
    };

    const register = async (formData) => {
        await createRegister(formData);
    };

    const recovery = async (email) => {
        await createRecovery(email);
    };

    return (<UserContext.Provider value={{
        user,
        setUser,
        login,
        logout,
        register,
        recovery
    }}>
        {children}
    </UserContext.Provider>);
};

UserProvider.propTypes = {
    children: PropTypes.node.isRequired,
};

UserContext.propTypes = {
    user: PropTypes.shape({
        accessToken: PropTypes.string.isRequired,
    }),
    setUser: PropTypes.func.isRequired,
    login: PropTypes.func.isRequired,
    register: PropTypes.func.isRequired,
    recovery: PropTypes.func.isRequired,
};