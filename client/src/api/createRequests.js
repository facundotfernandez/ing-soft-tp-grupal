import {create} from "@api/apiService";

// export const createOrder = async (data) => {
//     return await create('orders', data);
// };

export const createLogin = async (data) => {
    return await create('login', data);
};

export const createRegister = async (data) => {
    return await create('register', data);
};
//
// export const createProduct = async (data) => {
//     return await create('products', data);
// };