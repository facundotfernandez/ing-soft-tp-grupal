// components/login/LoginForm.js
import {useContext, useState} from "react";
import {UserContext} from "@context/UserProvider";
import {useRouter} from "next/router";
import {ToastNotification} from "@components/login/ToastNotification";
import Column from "@components/structures/Column";
import {handleNavigation} from "@utils/navigation";
import {useToast} from "@hooks/useToast";
import LoginInput from "./LoginInput";
import LoginButton from "./LoginButton";
import {ActionLinks} from "@components/login/ActionLinks";

export const LoginForm = () => {
    const {login} = useContext(UserContext);
    const router = useRouter();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const {
        toastMessage,
        showToast,
        setToastMessage,
        setShowToast
    } = useToast();

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!username || !password) {
            setToastMessage('Por favor, completa todos los campos.');
            setShowToast(true);
            return;
        }

        try {
            setShowToast(false);
            await login(username, password);
            handleNavigation(router, '');
        } catch (err) {
            let msg;
            if (err?.response) {
                msg = err.response.status === 400 ? 'Falta información' : err.response.status === 401 ? 'Sin autorización' : 'Servidor sin respuesta';
            } else {
                msg = err.message;
            }
            setToastMessage(msg);
            setShowToast(true);
        }
    };

    return (<>
        <form onSubmit={handleSubmit}
              className="bg-gray-950 rounded-lg flex flex-col w-fit gap-y-4 justify-center items-center p-4">
            <LoginInput
                id="username"
                type="text"
                placeholder="Ingresa tu usuario"
                value={username}
                onChange={(e) => {
                    setShowToast(false);
                    setUsername(e.target.value);
                }}
            />
            <LoginInput
                id="password"
                type="password"
                placeholder="Ingresa tu contraseña"
                value={password}
                onChange={(e) => {
                    setShowToast(false);
                    setPassword(e.target.value);
                }}
            />
            <Column className="flex w-full place-items-center ">
                <LoginButton onSubmit={handleSubmit}/>
                <ActionLinks onRegisterClick={() => handleNavigation(router, 'register')}/>
            </Column>
        </form>
        <ToastNotification message={toastMessage} isVisible={showToast}/>
    </>);
};