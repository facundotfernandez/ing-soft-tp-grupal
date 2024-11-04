import {patch} from "@api/apiService";

export const patchVariant = async (idProd, idVar, data) => {
    return await patch(`products/${idProd}`, idVar, data);
};