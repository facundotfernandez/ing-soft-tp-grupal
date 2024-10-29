import { NavLinkCard } from "@components/buttons/NavLinkCard";

const RecoveryButton = ({ onSubmit }) => {
    return (
        <NavLinkCard className="text-xs">
            <button type="submit" onClick={onSubmit}>Recuperar contraseña</button>
        </NavLinkCard>
    );
};

export default RecoveryButton;
