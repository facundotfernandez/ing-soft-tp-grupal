import { Divider } from "@tremor/react";
import { NavLinkCard } from "@components/buttons/NavLinkCard";

export const ActionLinks = ({ onRegisterClick, onGuestClick }) => (
    <div className="flex flex-col w-fit gap-y-1 ">
        <NavLinkCard className="justify-center text-xs ">
            Olvidaste tu contraseña?
        </NavLinkCard>
        <Divider/>
        <NavLinkCard className="justify-center text-xs bg-dark-tremor-background-muted" onClick={onRegisterClick}>
            No tenés cuenta? Registrate
        </NavLinkCard>
        <NavLinkCard className="justify-center text-xs bg-dark-tremor-background-muted" onClick={onGuestClick}>
            Continuar como invitado
        </NavLinkCard>
    </div>
);