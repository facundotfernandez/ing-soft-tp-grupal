import {useEffect, useState} from 'react';
import {createEmail, createLogin, createRecovery, createRegister} from "@api/createRequests";
import {getUser} from "@api/readRequests";
import {useNavigation} from "@hooks/useNavigation";

const GUEST_ROLE = 'guest';

export const useUser = () => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(true);
    const [requestMsg, setRequestMsg] = useState('');

    const {
        goToHome,
        goToLogin
    } = useNavigation();

    useEffect(() => {
        const fetchUser = async () => {
            const storedToken = localStorage.getItem('access_token');
            if (storedToken) {
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

        if (request.status === 'success') {
            await saveUser(request);
        }

        setLoading(false);

        return {
            status: request.status,
            message: request.message,
            error: request.status === 'error',
        };
    };

    const logout = async () => {
        setUser(null);
        setLoading(false);
        setRequestMsg('');
        setError(false);
        localStorage.removeItem('access_token');
        sessionStorage.removeItem('user_data');

        const request = await login(GUEST_ROLE, GUEST_ROLE);

        const isError = request.status === 'error';
        const message = request.message;

        if (!isError) {
            goToLogin();
        }

        return {
            status: request.status,
            message: message
        };
    };

    const register = async (formData) => {
        setLoading(true);
        const request = await createRegister(formData);

        const isError = request.status === 'error';
        const message = request.message;

        if (!isError) {
            await login(formData.username, formData.password);
            await createEmail(formData.email, "Registración", `Usuario ${formData.username} registrado exitosamente`);
            goToHome();
        }

        setLoading(false);

        return {
            status: request.status,
            message: message
        };
    };

    const recovery = async (email) => {
        setLoading(true);

        const request = await createRecovery(email);

        setLoading(false);
        setError(request.status === 'error');
        setRequestMsg(request.message);
        return request.data;
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