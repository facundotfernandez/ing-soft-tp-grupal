import {createContext, useEffect, useState} from "react";

export const AuthContext = createContext({});

export const AuthProvider = ({children}) => {
    const [auth, setAuth] = useState({});

    useEffect(() => {
        if (Object.keys(auth).length > 0) localStorage.setItem('accessToken', auth.accessToken);
    }, [auth]);

    useEffect(() => {
        const savedAuth = localStorage.getItem('accessToken');
        if (savedAuth) setAuth({accessToken: savedAuth});
    }, []);

    return (<AuthContext.Provider value={{
        auth,
        setAuth
    }}>
        {children}
    </AuthContext.Provider>)
}