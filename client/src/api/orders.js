import {fetchFromApi} from './apiFetch';

export const fetchOrders = async (clientId) => {
    return await fetchFromApi('orders', clientId);
};