import { NavLinkCard } from "@components/buttons/NavLinkCard";

const LoginButton = ({ onSubmit }) => {
    return (
        <NavLinkCard className="text-xs">
            <button type="submit" onClick={onSubmit}>Iniciar Sesión</button>
        </NavLinkCard>
    );
};

export default LoginButton;
