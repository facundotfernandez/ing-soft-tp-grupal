import {read} from "@api/apiService";

export const getOrders = async () => {
    return await read('orders');
};

export const getUser = async () => {
    return await read('login');
};

export const getProducts = async () => {
    return await read('products');
};