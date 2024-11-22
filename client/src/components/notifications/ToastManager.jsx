import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export const ToastManager = ({theme}) => (
    <ToastContainer
        position="bottom-left"
        autoClose={2000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme={theme}
    />
);

export const showToast = {
    default: (message) => toast(message),
    info: (message) => toast.info(message),
    success: (message) => toast.success(message),
    warning: (message) => toast.warning(message),
    error: (message) => toast.error(message),
};