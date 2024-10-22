import axios from 'axios';
import {API_URL, LOCAL_API_URL} from "@utils/common";

export const fetchFromApi = async (key, id) => {
    const mockData = {
        orders: [
            { id: 1, name: 'Order 1' },
            { id: 2, name: 'Order 2' }
        ]
    };

    return new Promise((resolve) => {
        setTimeout(() => resolve(mockData[key] || []), 1000); // Simular retardo de red
    });

    // Probar cuando esté la API armada
    // try {
    //     const url = `${getApiUrl()}/${key}${id ? `/${id}` : ''}`;
    //     const response = await axios.get(url, {headers: getAuthHeaders()});
    //     return response.data;
    // } catch (error) {
    //     console.error(`Error fetching ${key} ${id ? `with id ${id}` : ''}:`, error);
    //     throw error;
    // }
}

const getApiUrl = () => {
    return process.env.NODE_ENV === "development" ? LOCAL_API_URL : API_URL;
}

const getAuthToken = () => {
    return localStorage.getItem('authToken');
};

const saveAuthToken = (token) => {
    localStorage.setItem('authToken', token);
};

const isTokenExpired = (token) => {
    return !token;
};

const getAuthHeaders = async () => {
    let token = getAuthToken();

    if (isTokenExpired(token)) {
        console.log('Token expired or not found. Refreshing token...');
        token = await refreshAuthToken();
    }

    return {Authorization: `Bearer ${token || ''}`,};
}

const refreshAuthToken = async () => {
    try {
        const response = await axios.post(`${getApiUrl()}/auth/refresh`);
        const newToken = response.data.token;
        saveAuthToken(newToken);
        return newToken;
    } catch (error) {
        console.error('Error refreshing token:', error);
        throw error;
    }
};