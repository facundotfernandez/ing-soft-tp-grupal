import {update} from "@api/apiService";

export const updateVariant = async (idProd, idVar, data) => {
    return await update(`products/${idProd}`, idVar, data);
};