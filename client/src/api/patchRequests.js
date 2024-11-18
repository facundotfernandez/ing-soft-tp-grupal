import {patch} from "@api/apiService";

export const patchVariant = async (idProd, idVar, data) => {
    return await patch(`products/${idProd}`, idVar, data);
};

export const patchOrder = async (idOrder, data) => {
    return await patch("orders", idOrder, data);
};