import axios from "axios";
import {getAuthHeaders} from '@api/authService';

export const getApiUrl = () => {
    return process.env.NODE_ENV === "devLocal" ? process.env.NEXT_PUBLIC_LOCAL_API_URL : process.env.NEXT_PUBLIC_API_URL;
};

export const create = async (key, data) => {
    const apiUrl = `${getApiUrl()}/${key}`;
    const response = await axios.post(apiUrl, data, {headers: await getAuthHeaders()});
    return response.data;
};

export const read = async (key, id) => {
    try {
        const apiUrl = `${getApiUrl()}/${key}${id ? `/${id}` : ''}`;
        const response = await axios.get(apiUrl, {headers: await getAuthHeaders()});
        return response.data;
    } catch (error) {
        console.error(`Error consultando ${key} ${id ? `con id ${id}` : ''}:`, error);
        throw error;
    }
};

export const update = async (key, id, data) => {
    try {
        const apiUrl = `${getApiUrl()}/${key}/${id}`;
        const response = await axios.put(apiUrl, data, {headers: await getAuthHeaders()});
        return response.data;
    } catch (error) {
        console.error(`Error actualizando ${key} con id ${id}:`, error);
        throw error;
    }
};

export const remove = async (key, id) => {
    try {
        const apiUrl = `${getApiUrl()}/${key}/${id}`;
        const response = await axios.delete(apiUrl, {headers: await getAuthHeaders()});
        return response.data;
    } catch (error) {
        console.error(`Error eliminando ${key} con id ${id}:`, error);
        throw error;
    }
};