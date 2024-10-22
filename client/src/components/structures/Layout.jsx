import React from 'react';

const Layout = ({ children }) => {
    return (
        <div className="min-h-screen flex flex-col">
            <header className="bg-blue-600 text-white p-4">
                <h1 className="text-xl">Sistema de Pedidos</h1>
            </header>
            <main className="flex-grow p-4">
                {children}
            </main>
            <footer className="bg-gray-800 text-white p-4">
                <p>© 2024 Tu Empresa</p>
            </footer>
        </div>
    );
};

export default Layout;