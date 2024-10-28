import {Divider, TextInput} from '@tremor/react';
import {ToastNotification} from "@components/login/ToastNotification";
import {useContext, useState} from "react";
import {useRouter} from "next/router";
import {useToast} from "@hooks/useToast";
import {handleNavigation} from "@utils/navigation";
import {NavLinkCard} from "@components/buttons/NavLinkCard";
import {UserContext} from "@context/UserProvider";

export const RegisterForm = () => {
    const {register} = useContext(UserContext);
    const router = useRouter();
    const [formData, setFormData] = useState({
        email: '',
        name: '',
        lastname: '',
        gender: '',
        profilePic: '',
        address: '',
        username: '',
        password: ''
    });

    const {
        toastMessage,
        showToast,
        setToastMessage,
        setShowToast
    } = useToast();

    const handleChange = (e) => {
        const {
            name,
            value
        } = e.target;
        setFormData(prevData => ({
            ...prevData,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const {
            username,
            password,
            email,
            name,
            lastname
        } = formData;

        if (!username || !password || !email || !name || !lastname) {
            setToastMessage('Por favor, completa todos los campos obligatorios.');
            setShowToast(true);
            return;
        }

        try {
            setShowToast(false);
            await register(formData);
            handleNavigation(router, 'login');

        } catch (err) {
            let msg = err.response.data;
            setToastMessage(msg);
            setShowToast(true);
        }
    };

    return (<>
        <ToastNotification message={toastMessage} isVisible={showToast}/>
        <form onSubmit={handleSubmit}
              className="bg-gray-950 rounded-lg flex flex-col w-fit gap-y-4 justify-center items-center p-4">
            <div className="grid grid-cols-1 gap-x-4 gap-y-6 sm:grid-cols-6">
                <div className="col-span-full sm:col-span-3">
                    <label
                        htmlFor="name"
                        className="text-tremor-default font-medium text-tremor-content-strong dark:text-dark-tremor-content-strong"
                    >
                        Nombre
                        <span className="text-red-500 ml-1">*</span>
                    </label>
                    <TextInput
                        type="text"
                        id="name"
                        name="name"
                        autoComplete="given-name"
                        placeholder=""
                        className="mt-2"

                        value={formData.name}
                        onChange={handleChange}
                    />
                </div>
                <div className="col-span-full sm:col-span-3">
                    <label
                        htmlFor="lastname"
                        className="text-tremor-default font-medium text-tremor-content-strong dark:text-dark-tremor-content-strong"
                    >
                        Apellido
                        <span className="text-red-500 ml-1">*</span>
                    </label>
                    <TextInput
                        type="text"
                        id="lastname"
                        name="lastname"
                        autoComplete="family-name"
                        placeholder=""
                        className="mt-2"

                        value={formData.lastname}
                        onChange={handleChange}
                    />
                </div>
                <div className="col-span-full sm:col-span-3">
                    <label
                        htmlFor="username"
                        className="text-tremor-default font-medium text-tremor-content-strong dark:text-dark-tremor-content-strong"
                    >
                        Nombre de Usuario
                        <span className="text-red-500 ml-1">*</span>
                    </label>
                    <TextInput
                        type="text"
                        id="username"
                        name="username"
                        placeholder=""
                        className="mt-2"

                        value={formData.username}
                        onChange={handleChange}
                    />
                </div>
                <div className="col-span-full sm:col-span-3">
                    <label
                        htmlFor="password"
                        className="text-tremor-default font-medium text-tremor-content-strong dark:text-dark-tremor-content-strong"
                    >
                        Contraseña
                        <span className="text-red-500 ml-1">*</span>
                    </label>
                    <TextInput
                        type="password"
                        id="password"
                        name="password"
                        placeholder=""
                        className="mt-2"

                        value={formData.password}
                        onChange={handleChange}
                    />
                </div>
                <div className="col-span-full">
                    <label
                        htmlFor="email"
                        className="text-tremor-default font-medium text-tremor-content-strong dark:text-dark-tremor-content-strong"
                    >
                        Email
                        <span className="text-red-500 ml-1">*</span>
                    </label>
                    <TextInput
                        type="email"
                        id="email"
                        name="email"
                        autoComplete="email"
                        placeholder=""
                        className="mt-2"

                        value={formData.email}
                        onChange={handleChange}
                    />
                </div>
                <div className="col-span-full">
                    <label
                        htmlFor="address"
                        className="text-tremor-default font-medium text-tremor-content-strong dark:text-dark-tremor-content-strong"
                    >
                        Dirección
                    </label>
                    <TextInput
                        type="text"
                        id="address"
                        name="address"
                        autoComplete="street-address"
                        placeholder=""
                        className="mt-2"
                        value={formData.address}
                        onChange={handleChange}
                    />
                </div>
                <div className="col-span-full">
                    <label
                        htmlFor="gender"
                        className="text-tremor-default font-medium text-tremor-content-strong dark:text-dark-tremor-content-strong"
                    >
                        Género
                    </label>
                    <TextInput
                        type="text"
                        id="gender"
                        name="gender"
                        placeholder=""
                        className="mt-2"
                        value={formData.gender}
                        onChange={handleChange}
                    />
                </div>
                <div className="col-span-full">
                    <label
                        htmlFor="profilePic"
                        className="text-tremor-default font-medium text-tremor-content-strong dark:text-dark-tremor-content-strong"
                    >
                        Foto de Perfil
                    </label>
                    <TextInput
                        type="text"
                        id="profilePic"
                        name="profilePic"
                        placeholder=""
                        className="mt-2"
                        value={formData.profilePic}
                        onChange={handleChange}
                    />
                </div>
            </div>
            <Divider/>
            <NavLinkCard>
                <button type="submit">
                    Enviar
                </button>
            </NavLinkCard>
        </form>
    </>);
};
