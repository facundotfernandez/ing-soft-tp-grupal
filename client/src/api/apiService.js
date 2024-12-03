import axios from "axios";
import {getAuthHeaders} from '@api/authService';

export const getApiUrl = () => {
    return process.env.NEXT_PUBLIC_LOCAL_API_URL;
};

const unknownError = {
    "code": 400,
    "status": "error",
    "message": "Ocurrió un error al procesar la solicitud",
    "data": null
};

export const create = async (key, data, basicAuthHeader = null) => {
    try {
        const apiUrl = `${getApiUrl()}/${key}`;

        const headers = await getAuthHeaders();
        if (basicAuthHeader) {
            headers['Authorization'] = basicAuthHeader;
        }

        const response = await axios.post(apiUrl, data, {headers});
        return response.data;
    } catch (error) {
        if (error.response && error.response.data) {
            return error.response.data;
        }
        return unknownError;
    }
};

export const read = async (key, id, basicAuthHeader = null) => {
    try {
        const apiUrl = `${getApiUrl()}/${key}${id ? `/${id}` : ''}`;
        const headers = await getAuthHeaders();
        if (basicAuthHeader) {
            headers['Authorization'] = basicAuthHeader;
        }
        const response = await axios.get(apiUrl, {headers});
        return response.data;
    } catch (error) {
        if (error.response && error.response.data) {
            return error.response.data;
        }
        return unknownError;
    }
};

export const update = async (key, id, data) => {
    try {
        const apiUrl = `${getApiUrl()}/${key}/${id}`;
        const response = await axios.put(apiUrl, data, {headers: await getAuthHeaders()});
        return response.data;
    } catch (error) {
        if (error.response && error.response.data) {
            return error.response.data;
        }
        return unknownError;
    }
};

export const patch = async (key, id, data) => {
    try {
        const apiUrl = `${getApiUrl()}/${key}/${id}`;
        const response = await axios.patch(apiUrl, data, {headers: await getAuthHeaders()});
        return response.data;
    } catch (error) {
        if (error.response && error.response.data) {
            return error.response.data;
        }
        return unknownError;
    }
};

export const remove = async (key, id) => {
    try {
        const apiUrl = `${getApiUrl()}/${key}/${id}`;
        const response = await axios.delete(apiUrl, {headers: await getAuthHeaders()});
        return response.data;
    } catch (error) {
        if (error.response && error.response.data) {
            return error.response.data;
        }
        return unknownError;
    }
};