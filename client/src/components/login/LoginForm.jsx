import {useContext, useState} from "react";
import {UserContext} from "@context/UserProvider";
import {ToastNotification} from "@components/notifications/ToastNotification";
import Column from "@components/structures/Column";
import {useToast} from "@hooks/useToast";
import LoginButton from "./LoginButton";
import {ActionLinks} from "@components/login/ActionLinks";
import {InputField} from "@components/inputs/InputField";
import PropTypes from "prop-types";
import {areFieldsFilled} from "@utils/validations";
import {useNavigation} from "@hooks/useNavigation";

export const LoginForm = () => {
    const {
        user,
        login
    } = useContext(UserContext);
    const {goToHome, goToRegister, goToRecovery} = useNavigation();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
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

    const handleInputChange = (setter) => (e) => {
        setter(e.target.value);
        setShowToast(false);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (user && user.username !== "guest") {
            showToastMessage('Ya iniciaste sesión');
            return;
        }

        if (!areFieldsFilled([username, password])) {
            showToastMessage('Por favor, completa todos los campos.');
            return;
        }

        try {
            setShowToast(false);
            await login(username, password);
            goToHome();
        } catch (err) {
            showToastMessage("El Usuario no existe");
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
                onChange={handleInputChange(setUsername)}
            />
            <InputField
                id="password"
                type="password"
                placeholder="Ingresa tu contraseña"
                value={password}
                onChange={handleInputChange(setPassword)}
            />
            <Column className="place-items-center">
                <LoginButton onSubmit={handleSubmit}/>
                <ActionLinks
                    onRegisterClick={() => goToRegister()}
                    onForgetPassword={() => goToRecovery()}
                />
            </Column>
        </form>
        <ToastNotification message={toastMessage} isVisible={showToast}/>
    </>);
};


UserContext.propTypes = {
    user: PropTypes.shape({
        username: PropTypes.string.isRequired,
        password: PropTypes.string.isRequired,
    }),
};