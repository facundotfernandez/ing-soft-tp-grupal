import axios from "axios";
import {getAuthHeaders} from '@api/authService';

export const getApiUrl = () => {
    return process.env.NEXT_PUBLIC_LOCAL_API_URL;
};

export const create = async (key, data) => {
    const apiUrl = `${getApiUrl()}/${key}`;
    const response = await axios.post(apiUrl, data, {headers: await getAuthHeaders()});
    return response.data;
};

export const read = async (key, id) => {
    const apiUrl = `${getApiUrl()}/${key}${id ? `/${id}` : ''}`;
    const response = await axios.get(apiUrl, {headers: await getAuthHeaders()});
    return response.data;
};

export const update = async (key, id, data) => {
    const apiUrl = `${getApiUrl()}/${key}/${id}`;
    const response = await axios.put(apiUrl, data, {headers: await getAuthHeaders()});
    return response.data;
};

export const patch = async (key, id, data) => {
    const apiUrl = `${getApiUrl()}/${key}/${id}`;
    const response = await axios.patch(apiUrl, data, {headers: await getAuthHeaders()});
    return response.data;
};

export const remove = async (key, id) => {
    const apiUrl = `${getApiUrl()}/${key}/${id}`;
    const response = await axios.delete(apiUrl, {headers: await getAuthHeaders()});
    return response.data;
};