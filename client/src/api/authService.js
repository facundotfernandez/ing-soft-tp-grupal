export const getAccessToken = () => {
    return localStorage.getItem('access_token');
};

export const saveAccessToken = (token) => {
    localStorage.setItem('access_token', token);
};

export const getAuthHeaders = async () => {
    let token = getAccessToken();

    return {
        Authorization: `Bearer ${token || ''}`,
        'Content-Type': 'application/json',
        withCredentials: true
    };
};