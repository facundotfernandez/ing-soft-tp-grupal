import { useState } from 'react';
import axios from 'axios';

const useAuth = () => {
    const [error, setError] = useState(null);

    const login = async (username, password) => {
        try {
            const response = await axios.post('/api/login', { username, password });
            const { authToken, clientId } = response.data;

            localStorage.setItem('authToken', authToken);
            localStorage.setItem('clientId', clientId);
        } catch (err) {
            setError(err.response.data.message || 'Error al iniciar sesión');
        }
    };

    const logout = () => {
        localStorage.removeItem('authToken');
        localStorage.removeItem('clientId');
    };

    return { login, logout, error };
};

export default useAuth;
