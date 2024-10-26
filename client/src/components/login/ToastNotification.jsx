import * as Toast from "@radix-ui/react-toast";

export const ToastNotification = ({ message, isVisible }) => (
    <Toast.Provider swipeDirection="right">
        <Toast.Root
            className="p-4 bg-dark-tremor-background-muted flex flex-col transition-transform duration-200 ease-out transform translate-x-full data-[state=open]:translate-x-0 data-[state=closed]:translate-x-full"
            open={isVisible}
        >
            <Toast.Description className="text-sm font-semibold ">
                {message}
            </Toast.Description>
        </Toast.Root>
        <Toast.Viewport className="fixed top-3 right-3 z-[9999]" />
    </Toast.Provider>
);