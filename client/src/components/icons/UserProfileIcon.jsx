import {useContext, useState} from 'react';
import {NavLinkCard} from "@components/buttons/NavLinkCard";
import {useNavigation} from "@hooks/useNavigation"
import Image from 'next/image'
import {UserContext} from "@context/UserProvider";

const UserProfileIcon = () => {
    const {
        goToLogin,
        goToRegister
    } = useNavigation();
    const [showLogout, setShowLogout] = useState(false);

    const {
        user,
        logout,
    } = useContext(UserContext);

    const handleLogout = () => {
        logout();
        goToLogin();
    };

    if (!user || user.role === 'guest') {
        return (<div className={"flex gap-x-2"}>
            <NavLinkCard
                className="text-xs text-center"
                onClick={() => goToLogin()}
            >
                Iniciar sesión
            </NavLinkCard>
            <NavLinkCard
                className="text-xs bg-dark-tremor-background text-center"
                onClick={() => goToRegister()}
            >
                Registrarse
            </NavLinkCard>
        </div>);
    }

    return (<>
        <NavLinkCard
            className="flex items-center rounded"
            onClick={() => setShowLogout(!showLogout)}
        >
            <Image
                priority={false}
                src={user.profilePic}
                width={24}
                height={24}
                alt={`Perfil de ${user.username}`}
                className={"rounded-full w-6 h-6 border border-matisse-900"}
            />
            <h2 className={" text-xs p-0"}>Hola {user.name}!</h2>
        </NavLinkCard>
        {showLogout && (<div className="absolute mt-20 right-4 bg-white shadow-lg rounded">
            <NavLinkCard
                onClick={handleLogout}
                className="text-xs p-2 bg-red-900 hover:bg-red-700"
            >
                Cerrar sesión
            </NavLinkCard>
        </div>)}
    </>);
};

export default UserProfileIcon;