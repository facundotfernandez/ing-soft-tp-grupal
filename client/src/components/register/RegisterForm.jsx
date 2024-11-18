import {Divider} from '@tremor/react';
import {ToastNotification} from "@components/notifications/ToastNotification";
import {useContext, useEffect, useState} from "react";
import {useToast} from "@hooks/useToast";
import {useNavigation} from "@hooks/useNavigation";
import {NavLinkCard} from "@components/buttons/NavLinkCard";
import {UserContext} from "@context/UserProvider";
import {InputField} from "@components/inputs/InputField";

export const RegisterForm = () => {
    const {
        user,
        register,
        error,
        requestMsg,
        setRequestMsg
    } = useContext(UserContext);


    const {goToLogin} = useNavigation();
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
    } = useToast(error, requestMsg);

    const handleChange = (e) => {
        const {
            name,
            value
        } = e.target;
        setFormData(prevData => ({
            ...prevData,
            [name]: value
        }));
        setShowToast(false);
    };

    useEffect(() => {
        if (!error && requestMsg === 'Usuario registrado exitosamente') {
            goToLogin();
        }
    }, [error, requestMsg, setShowToast, setToastMessage, goToLogin, user]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setShowToast(true);

        const {
            username,
            password,
            email,
            name,
            lastname
        } = formData;

        if (!username || !password || !email || !name || !lastname) {
            setToastMessage('Por favor, completa todos los campos obligatorios.');
            return;
        }

        await register(formData);
    };

    return (<>
        <ToastNotification message={toastMessage} isVisible={showToast}/>
        <form onSubmit={handleSubmit}
              className="bg-gray-950 rounded-lg flex flex-col w-fit gap-y-4 justify-center items-center p-4">
            <div className="grid grid-cols-1 gap-x-4 gap-y-6 sm:grid-cols-6">
                <div className="col-span-full sm:col-span-3">
                    <InputField id="name" type="text" label="Nombre" value={formData.name}
                                onChange={handleChange} required/>
                </div>
                <div className="col-span-full sm:col-span-3">
                    <InputField id="lastname" type="text" label="Apellido"
                                value={formData.lastname} onChange={handleChange} required/>
                </div>
                <div className="col-span-full sm:col-span-3">
                    <InputField id="username" type="text" label="Nombre de Usuario"
                                value={formData.username} onChange={handleChange} required/>
                </div>
                <div className="col-span-full sm:col-span-3">
                    <InputField id="password" type="password" label="Contraseña"
                                value={formData.password} onChange={handleChange} required/>
                </div>
                <div className="col-span-full">
                    <InputField id="email" type="email" label="Email" value={formData.email}
                                onChange={handleChange} required/>
                </div>
                <div className="col-span-full">
                    <InputField id="address" type="text" label="Dirección" value={formData.address}
                                onChange={handleChange}/>
                </div>
                <div className="col-span-full">
                    <InputField id="gender" type="text" label="Género" value={formData.gender}
                                onChange={handleChange}/>
                </div>
                {/*<div className="col-span-full">*/}
                {/*    <InputField id="profilePic" type="text" label="Foto de Perfil"*/}
                {/*                value={formData.profilePic} onChange={handleChange}/>*/}
                {/*</div>*/}
                <div className="col-span-full">
                    <InputField
                        id="file_input"
                        type="file"
                        label="Foto de perfil"
                        onChange={handleChange}
                    />
                </div>
            </div>
            <Divider/>
            <NavLinkCard>
                <button type="submit">Enviar</button>
            </NavLinkCard>
        </form>
    </>);
};
