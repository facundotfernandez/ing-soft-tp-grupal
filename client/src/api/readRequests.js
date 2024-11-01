import {read} from "@api/apiService";

export const getOrders = async (accessToken) => {
    return await read('orders', accessToken);
};

export const getUser = async (accessToken) => {
    return await read('users', accessToken);
};

export const getProducts = async () => {
    return await read('products');
};