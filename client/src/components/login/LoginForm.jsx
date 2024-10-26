import {useContext, useState} from "react";
import {AuthContext} from "@context/AuthProvider";
import {useRouter} from "next/router";
import {ToastNotification} from "@components/login/ToastNotification";
import {InputField} from "@components/login/InputField";
import {ActionLinks} from "@components/login/ActionLinks";
import Column from "@components/structures/Column";
import {NavLinkCard} from "@components/buttons/NavLinkCard";
import {handleNavigation} from "@utils/navigation";
import {useToast} from "@hooks/useToast";

export const LoginForm = () => {
    const {setAuth} = useContext(AuthContext);
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
            const rol = 'admin';
            const accessToken = username;

            setAuth({
                username,
                password,
                rol,
                accessToken
            });

            // TODO: API EN DESARROLLO
            // const response = await axios.post(LOGIN_URL, JSON.stringify({
            //     username,
            //     password
            // }), {
            //     headers: {'Content-Type': 'application/json'},
            //     withCredentials: true
            // });
            // console.log(JSON.stringify(response?.data));
            //
            // const accessToken = response?.data?.accessToken;
            // const rol = response?.data?.rol;
            //
            // setAuth({
            //     username,
            //     password,
            //     rol,
            //     accessToken
            // });
            //

            handleNavigation(router, '');

        } catch (err) {
            let msg = 'Falló la autenticación';
            if (err?.response) {
                if (err.response.status === 400) {
                    msg = 'Falta información';
                } else if (err.response.status === 401) {
                    msg = 'Sin autorización';
                } else {
                    msg = 'Servidor sin respuesta';
                }
            }

            setToastMessage(msg);
            setShowToast(true);
        }
    };

    const handleGuestSubmit = async (e) => {
        e.preventDefault();
        setUsername('guest');
        setPassword('guest');
    }

    return (<>
            <ToastNotification message={toastMessage} isVisible={showToast}/>
            <form onSubmit={handleSubmit}
                  className="bg-gray-950 rounded-lg flex flex-col w-fit gap-y-4 justify-center items-center p-4">
                <InputField id="username" type="text" placeholder="Ingresa tu usuario" value={username}
                            onChange={(e) => setUsername(e.target.value)}/>
                <InputField id="password" type="password" placeholder="Ingresa tu contraseña" value={password}
                            onChange={(e) => setPassword(e.target.value)}/>

                <Column className="flex w-full place-items-center ">
                    <NavLinkCard className="text-xs">
                        <button type="submit">Iniciar Sesión</button>
                    </NavLinkCard>
                    <ActionLinks onRegisterClick={() => handleNavigation(router, 'register')} onGuestClick={handleGuestSubmit}/>
                </Column>
            </form>
        </>);
};
