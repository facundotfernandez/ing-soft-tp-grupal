import {create} from "@api/apiService";

export const createOrder = async (data) => {
    return await create('orders', data);
};

export const createLogin = async (data) => {
    const basicAuthHeader = 'Basic ' + btoa(data.username + ':' + data.password);
    return await create('login', data, basicAuthHeader);
};

export const createRegister = async (data) => {
    return await create('register', data);
};

export const createRecovery = async (data) => {
    return await create('recovery', data);
};

export const createProduct = async (data) => {
    return await create('products', data);
};