import {useContext, useEffect} from "react";
import {UserContext} from "@context/UserProvider";
import {ToastNotification} from "@components/notifications/ToastNotification";
import Column from "@components/structures/Column";
import {useToast} from "@hooks/useToast";
import {useFormField} from "@hooks/useFormField";
import LoginButton from "@components/login/LoginButton";
import {ActionLinks} from "@components/login/ActionLinks";
import {areFieldsFilled} from "@utils/validations";
import {useNavigation} from "@hooks/useNavigation";
import {InputField} from "@components/inputs/InputField";

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

    const {
        toastMessage,
        showToast,
        setShowToast
    } = useToast(error, requestMsg);

    const [username, handleUsernameChange] = useFormField('', setShowToast);
    const [password, handlePasswordChange] = useFormField('', setShowToast);

    useEffect(() => {
        if (!error && user && user?.username !== 'guest') {
            goToHome();
        }
    }, [user, error, requestMsg, setShowToast, setRequestMsg, goToHome]);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (user && user.role !== 'guest') {
            setRequestMsg('Ya iniciaste sesión');
            setShowToast(true);
            return;
        }

        if (!areFieldsFilled([username, password])) {
            setRequestMsg('Por favor, completa todos los campos');
            setShowToast(true);
            return;
        }

        await login(username, password);
        setShowToast(true);
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
        <ToastNotification message={toastMessage} isVisible={showToast}/>
    </>);
};
