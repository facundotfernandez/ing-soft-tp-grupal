import * as Toast from "@radix-ui/react-toast";

export const ToastNotification = ({ message, isVisible, onDismiss }) => (
    <Toast.Provider swipeDirection="right">
        <Toast.Root
            className="p-4 bg-dark-tremor-background-muted flex flex-col transition-transform duration-200 ease-out transform translate-x-full data-[state=open]:translate-x-0 data-[state=closed]:translate-x-full"
            open={isVisible}
            onOpenChange={onDismiss}
        >
            <Toast.Description className="text-sm font-semibold ">
                {message}
            </Toast.Description>
        </Toast.Root>
        <Toast.Viewport className="relative bottom-0 z-[9999]" />
    </Toast.Provider>
);