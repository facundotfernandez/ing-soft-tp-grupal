export const shortenId = (id) => {
    return btoa(id);
};

export const unshortenId = (id) => {
    return atob(id);
};