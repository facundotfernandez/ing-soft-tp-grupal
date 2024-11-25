import React, {createContext} from 'react';
import PropTypes from 'prop-types';
import {useUser} from '@hooks/useUser';

export const UserContext = createContext(undefined, undefined);

export const UserProvider = ({children}) => {
    const {
        user,
        loading,
        error,
        requestMsg,
        setRequestMsg,
        login,
        logout,
        register,
        recovery
    } = useUser();

    return (<UserContext.Provider
        value={{
            user,
            loading,
            error,
            requestMsg,
            setRequestMsg,
            login,
            logout,
            register,
            recovery
        }}
    >
        {children}
    </UserContext.Provider>);
};

UserProvider.propTypes = {
    children: PropTypes.node.isRequired
};

UserContext.propTypes = {
    user: PropTypes.shape({
        accessToken: PropTypes.string.isRequired,
        name: PropTypes.string.isRequired,
        profilePic: PropTypes.string.isRequired,
        role: PropTypes.string.isRequired
    }),
    loading: PropTypes.bool.isRequired,
    error: PropTypes.bool.isRequired,
    requestMsg: PropTypes.string.isRequired,
    login: PropTypes.func.isRequired,
    logout: PropTypes.func.isRequired,
    register: PropTypes.func.isRequired,
    recovery: PropTypes.func.isRequired
};