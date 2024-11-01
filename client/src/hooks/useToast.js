import { useEffect, useState } from "react";

export const useToast = (initialMessage = '') => {
    const [toastMessage, setToastMessage] = useState(initialMessage);
    const [showToast, setShowToast] = useState(false);

    useEffect(() => {
        setToastMessage('');
    }, [initialMessage]);

    useEffect(() => {
        if (showToast) {
            const timer = setTimeout(() => setShowToast(false), 3000);
            return () => clearTimeout(timer);
        }
    }, [showToast]);

    return {
        toastMessage,
        showToast,
        setToastMessage,
        setShowToast,
    };
};