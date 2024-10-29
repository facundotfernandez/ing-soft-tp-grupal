// components/recovery/LoginForm.js
import {useContext, useState} from "react";
import {UserContext} from "@context/UserProvider";
import {useRouter} from "next/router";
import {ToastNotification} from "@components/recovery/ToastNotification";
import Column from "@components/structures/Column";
import {handleNavigation} from "@utils/navigation";
import {useToast} from "@hooks/useToast";
import RecoveryInput from "./RecoveryInput";
import RecoveryButton from "./RecoveryButton";
import {ActionLinks} from "@components/recovery/ActionLinks";

export const RecoveryForm = () => {
    const {recovery} = useContext(UserContext);
    const router = useRouter();
    const [email, setEmail] = useState('');
    //const [password, setPassword] = useState('');
    const {
        toastMessage,
        showToast,
        setToastMessage,
        setShowToast
    } = useToast();

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!email ) {
            setToastMessage('Por favor, completa todos los campos.');
            setShowToast(true);
            return;
        }

        try {
            setShowToast(false);
            await recovery(email);
            handleNavigation(router, '');
        } catch (err) {
            let msg = "El Usuario no existe";
            setToastMessage(msg);
            setShowToast(true);
        }
    };

    return (<>
        <form onSubmit={handleSubmit}
              className="bg-gray-950 rounded-lg flex flex-col w-fit gap-y-4 justify-center items-center p-4">
            <RecoveryInput
                id="email"
                type="text"
                placeholder="Ingresa tu email"
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