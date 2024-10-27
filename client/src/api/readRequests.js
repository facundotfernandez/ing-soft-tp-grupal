import {read} from "@api/apiService";
import {data as users} from "@mocks/users";

export const getOrders = async (accessToken) => {
    return await read('orders', accessToken);
};

export const getUser = async (accessToken) => {
    // return await read('users', accessToken); TODO: API PENDIENTE
    const user = users.find(user => user.accessToken === accessToken);
    if (!user) throw new Error('Usuario no encontrado o token inválido');
    return user;
};

export const getProducts = async () => {
    return await read('products');
};