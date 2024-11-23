import {useContext, useEffect} from "react";
import {UserContext} from "@context/UserProvider";
import Column from "@components/structures/Column";
import {useFormField} from "@hooks/useFormField";
import LoginButton from "@components/login/LoginButton";
import {ActionLinks} from "@components/login/ActionLinks";
import {areFieldsFilled} from "@utils/validations";
import {useNavigation} from "@hooks/useNavigation";
import {InputField} from "@components/inputs/InputField";
import {showToast} from "@components/notifications/ToastManager";

export const LoginForm = () => {
    const {
        user,
        login,
        error,
        requestMsg,
        setRequestMsg,
    } = useContext(UserContext);
    const {
        goToHome,
        goToRegister,
        goToRecovery
    } = useNavigation();

    const [username, handleUsernameChange] = useFormField('');
    const [password, handlePasswordChange] = useFormField('');

    useEffect(() => {
        if (!error && user && user?.username !== 'guest') {
            goToHome();
        }
    }, [user, error, requestMsg, setRequestMsg, goToHome]);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (user && user.role !== 'guest') {
            showToast.error("Ya iniciaste sesión");
            return;
        }

        if (!areFieldsFilled([username, password])) {
            showToast.error("Por favor, completa todos los campos");
            return;
        }

        const { status, message, error } = await login(username, password);

        if (error) {
            showToast.error(`Error al iniciar sesión: ${message}`);
        } else if (status === 'success') {
            showToast.success("Iniciaste sesión exitosamente");
        }
    };

    return (<>
        <form
            onSubmit={handleSubmit}
            className="bg-gray-950 rounded-lg flex flex-col w-fit gap-y-4 justify-center items-center p-4"
        >
            <InputField
                id="username"
                type="text"
                placeholder="Ingresa tu usuario"
                value={username}
                onChange={handleUsernameChange}
            />
            <InputField
                id="password"
                type="password"
                placeholder="Ingresa tu contraseña"
                value={password}
                onChange={handlePasswordChange}
            />
            <Column className="place-items-center">
                <LoginButton onSubmit={handleSubmit}/>
                <ActionLinks
                    onRegisterClick={goToRegister}
                    onForgetPassword={goToRecovery}
                />
            </Column>
        </form>
    </>);
};
