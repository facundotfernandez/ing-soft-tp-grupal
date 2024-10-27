import jwt from 'jsonwebtoken';
import axios from 'axios';
import {getApiUrl} from '@api/apiService';

const {v4: uuidv4} = require('uuid');

export const getAccessToken = () => {
    return localStorage.getItem('auth_token');
};

export const saveAccessToken = (token) => {
    localStorage.setItem('auth_token', token);
};

export const isTokenExpired = (token) => {
    if (!token) return true;

    try {
        const decoded = jwt.decode(token);
        if (!decoded || !decoded.exp) return true;

        const currentTime = Math.floor(Date.now() / 1000);
        return decoded.exp < currentTime;
    } catch (error) {
        console.error('Error de validación del token de acceso:', error);
        return true;
    }
};

export const refreshAccessToken = async () => {
    try {
        const response = await axios.post(`${getApiUrl()}/auth/refresh`, {token: getAccessToken()});
        const refreshToken = response.data.token;
        saveAccessToken(refreshToken);
        return refreshToken;
    } catch (error) {
        console.error('Error refrescando el token de acceso:', error);
        throw error;
    }
};

export const getAuthHeaders = async () => {
    let token = getAccessToken();

    if (!token || isTokenExpired(token)) {
        console.log('Token expirado o no encontrado. Refrescando token de acceso');
        try {
            token = await refreshAccessToken();
        } catch (error) {
            console.error('Error al refrescar el token:', error);
            throw new Error('No se pudo refrescar el token de acceso');
        }
    }

    return {
        Authorization: `Bearer ${token || ''}`,
        'Content-Type': 'application/json',
        withCredentials: true
    };
};

export const createAccessToken = async (username, password) => {
    const secretKey = process.env.TOKEN_SECRET;
    const userId = uuidv4();
    const payload = {
        userId: userId,
        username: username,
        password: password
    };
    const options = {
        expiresIn: '30d',
        algorithm: 'HS256'
    };
    return jwt.sign(payload, secretKey, options);
}