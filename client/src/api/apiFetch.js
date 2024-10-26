import axios from 'axios';
import {API_URL, LOCAL_API_URL} from "@utils/common";

export const fetchFromApi = async (key, id) => {
    const mockDataProducts = {
        products: [{
            _id: 0x000001,
            idProduct: "613561565",
            category: 'Calzados',
            subCategory: 'Botines',
            brand: 'Adidas',
            title: 'F50 Elite',
            imageUrl: '',
            price: 100,
            stock: [{
                size: '40',
                color: 'Rojo',
                amount: 10,
            }, {
                size: '41',
                color: 'Azul',
                amount: 5,
            }]
        }, {
            _id: 0x000002,
            category: 'Calzados',
            subCategory: 'Zapatillas',
            brand: 'Adidas',
            title: 'Galaxy Star',
            imageUrl: '',
            stock: [{
                size: '39',
                color: 'Negro',
                amount: 15,
            }, {
                size: '40',
                color: 'Blanco',
                amount: 8,
            }]
        }, {
            _id: 0x000003,
            category: 'Indumentaria',
            subCategory: 'Remera',
            brand: 'Puma',
            title: 'Remera',
            imageUrl: '',
            stock: [{
                size: 'M',
                color: 'Rojo',
                sleeves: 'Corta',
                amount: 20,
            }, {
                size: 'L',
                color: 'Verde',
                sleeves: 'Larga',
                amount: 12,
            }]
        }, {
            _id: 0x000004,
            category: 'Calzados',
            subCategory: 'Botines',
            brand: 'Puma',
            title: 'Borussia',
            imageUrl: '',
            stock: [{
                size: '42',
                color: 'Negro',
                amount: 6,
            }, {
                size: '43',
                color: 'Blanco',
                amount: 4,
            }]
        }]
    };

    return new Promise((resolve) => {
        setTimeout(() => resolve(mockDataProducts[key] || []), 1000); // Simula retardo de red
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