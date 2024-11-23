import {useContext, useState} from "react";
import {UserContext} from "@context/UserProvider";
import {showToast} from "@components/notifications/ToastManager";
import Column from "@components/structures/Column";
import RecoveryButton from "./RecoveryButton";
import {useNavigation} from "@hooks/useNavigation";
import {InputField} from "@components/inputs/InputField";
import {createEmail} from "@api/createRequests";

export const RecoveryForm = () => {
    const {goToHome} = useNavigation();
    const {recovery} = useContext(UserContext);
    const [email, setEmail] = useState('');


    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!email) {
            showToast.error('Por favor, ingresa un mail válido.');
            return;
        }

        try {
            const pwRecovery = await recovery(email);
            await createEmail(email, "Recuperación", `Su contraseña es: ${pwRecovery}`);
            showToast.info("Datos enviados al correo");
            goToHome();
        } catch (err) {
            showToast.error("El email no pertenece a un usuario");
        }
    };

    return (<>
        <form onSubmit={handleSubmit}
              className="bg-gray-950 rounded-lg flex flex-col w-fit gap-y-4 justify-center items-center p-4">
            <InputField
                id="email"
                type="email"
                placeholder="Ingresa tu email"
                autoComplete="email"
                value={email}
                onChange={(e) => {
                    setEmail(e.target.value);
                }}
            />
            <Column className="flex w-full place-items-center ">
                <RecoveryButton onSubmit={handleSubmit}/>
            </Column>
        </form>
    </>);
};