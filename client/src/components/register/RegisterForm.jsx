import {Divider} from '@tremor/react';
import {showToast} from "@components/notifications/ToastManager";
import {useContext, useState} from "react";
import {NavLinkCard} from "@components/buttons/NavLinkCard";
import {UserContext} from "@context/UserProvider";
import {InputField} from "@components/inputs/InputField";

export const RegisterForm = () => {
    const {
        register,
        error,
        requestMsg
    } = useContext(UserContext);


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
            showToast.error('Por favor, completa todos los campos obligatorios.');
            return;
        }

        const {
            status,
            message
        } = await register(formData);

        if (status === 'error') {
            showToast.error(message);
        } else {
            showToast.success(message);
        }
    };

    return (<>
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
