import {useEffect, useState} from 'react';
import {createLogin, createRecovery, createRegister} from "@api/createRequests";
import {getUser} from "@api/readRequests";

const GUEST_ROLE = 'guest';

export const useUser = () => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(true);
    const [requestMsg, setRequestMsg] = useState('');

    useEffect(() => {
        const fetchUser = async () => {
            const storedUser = sessionStorage.getItem('user_data');
            const storedToken = localStorage.getItem('access_token');
            if (storedUser) {
                setUser(JSON.parse(storedUser));
            } else if (storedToken) {
                const requestUser = await getUser();
                await saveUser(requestUser);
            } else {
                await login(GUEST_ROLE, GUEST_ROLE);
            }
        };
        fetchUser();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const saveUser = async (request) => {
        const userData = request.data;

        setUser(userData);
        setLoading(false);
        setError(request.status === 'error');
        setRequestMsg(request.message);

        if (userData) {
            localStorage.setItem('access_token', userData.accessToken);
            sessionStorage.setItem('user_data', JSON.stringify(userData));
        }
    }

    const login = async (username, password) => {
        setLoading(true);

        const request = await createLogin({
            username,
            password
        });

        await saveUser(request);
    };

    const logout = async () => {
        setUser(null);
        setLoading(false);
        setRequestMsg('');
        setError(false);
        localStorage.removeItem('access_token');
        sessionStorage.removeItem('user_data');

        await login(GUEST_ROLE, GUEST_ROLE);
    };

    const register = async (formData) => {
        setLoading(true);
        const request = await createRegister(formData);

        setLoading(false);
        setError(request.status === 'error');
        setRequestMsg(request.message);
    };

    const recovery = async (email) => {
        setLoading(true);

        const request = await createRecovery(email);

        setLoading(false);
        setError(request.status === 'error');
        setRequestMsg(request.message);
    };

    return {
        user,
        loading,
        error,
        requestMsg,
        setRequestMsg,
        login,
        logout,
        register,
        recovery
    };
};