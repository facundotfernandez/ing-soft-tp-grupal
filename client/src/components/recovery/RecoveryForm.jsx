import {useContext, useState} from "react";
import {UserContext} from "@context/UserProvider";
import {ToastNotification} from "@components/notifications/ToastNotification";
import Column from "@components/structures/Column";
import {useToast} from "@hooks/useToast";
import RecoveryButton from "./RecoveryButton";
import {useNavigation} from "@hooks/useNavigation";
import AuthInput from "@components/inputs/AuthInput";

export const RecoveryForm = () => {
    const handleNavigation = useNavigation();
    const {recovery} = useContext(UserContext);
    const [email, setEmail] = useState('');

    const {
        toastMessage,
        showToast,
        setToastMessage,
        setShowToast
    } = useToast();

    const showToastMessage = (message) => {
        setToastMessage(message);
        setShowToast(true);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!email) {
            showToastMessage('Por favor, ingresa un mail válido.');
            return;
        }

        try {
            setShowToast(false);
            await recovery(email);
            handleNavigation('');
        } catch (err) {
            let msg = "El email no pertenece a un usuario";
            showToastMessage(msg);
        }
    };

    return (<>
        <form onSubmit={handleSubmit}
              className="bg-gray-950 rounded-lg flex flex-col w-fit gap-y-4 justify-center items-center p-4">
            <AuthInput
                id="email"
                type="email"
                placeholder="Ingresa tu email"
                autoComplete="email"
                value={email}
                onChange={(e) => {
                    setShowToast(false);
                    setEmail(e.target.value);
                }}
            />
            <Column className="flex w-full place-items-center ">
                <RecoveryButton onSubmit={handleSubmit}/>
            </Column>
        </form>
        <ToastNotification message={toastMessage} isVisible={showToast}/>
    </>);
};