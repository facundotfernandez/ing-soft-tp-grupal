import {useEffect, useState} from 'react';

export const useToast = (error, requestMsg) => {
    const [toastMessage, setToastMessage] = useState('');
    const [showToast, setShowToast] = useState(false);

    useEffect(() => {
        if (error && requestMsg) {
            setToastMessage(requestMsg || 'Error desconocido');
        }
    }, [error, requestMsg]);

    useEffect(() => {
        const timer = setTimeout(() => setShowToast(false), 3000);
        return () => clearTimeout(timer);
    }, [showToast]);

    return {
        toastMessage,
        showToast,
        setToastMessage,
        setShowToast
    };
};