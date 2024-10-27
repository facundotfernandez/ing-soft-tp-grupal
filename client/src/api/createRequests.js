import {create} from "@api/apiService";
import {data as users} from "@mocks/users";

export const createOrder = async (data) => {
    return await create('orders', data);
};

export const createLogin = async (data) => {
    // return await create('login', data);
    const user = users.find(user => (user.username === data.username && user.password === data.password));
    if (!user) throw new Error('Contraseña Incorrecta!');
    return user;
};

export const createUser = async (data) => {
    return await create('users', data);
};

export const createProduct = async (data) => {
    return await create('products', data);
};