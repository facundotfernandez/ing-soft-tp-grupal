import {Button, Dialog, DialogPanel} from '@tremor/react';
import {RiCloseLine} from '@remixicon/react';

export function ConfirmationModal({
                                              isOpen,
                                              onClose,
                                              onConfirm,
                                              message
                                          }) {
    return (<Dialog
            open={isOpen}
            onClose={onClose}
            static={true}
            className="z-[100]"
        >
            <DialogPanel
                className="relative sm:max-w-md rounded-tremor-default bg-gray-900 ring-tremor-ring shadow-tremor-card">
                <div className="absolute right-0 top-0 pr-3 pt-3">
                    <button
                        type="button"
                        className="rounded-tremor-small p-2 text-tremor-content-subtle hover:bg-dark-tremor-background-subtle hover:text-tremor-content"
                        aria-label="Close"
                    >
                        <RiCloseLine className="size-5 shrink-0" aria-hidden={true} />
                    </button>
                </div>

                <div className="p-4">
                    <h4 className="font-semibold text-tremor-content-inverted">
                        Confirmación de Eliminación
                    </h4>
                    <p className="mt-2 text-tremor-default leading-6 text-tremor-background">
                        {message}
                    </p>

                    <div className="mt-6 flex justify-center gap-4">
                        <Button
                            onClick={() => {
                                onConfirm();
                                onClose();
                            }}
                            className="bg-red-700 hover:bg-red-600 border-none"
                        >
                            Confirmar
                        </Button>
                        <Button
                            onClick={onClose}
                            className="bg-gray-700 hover:bg-gray-600 border-none"
                        >
                            Cancelar
                        </Button>
                    </div>
                </div>
            </DialogPanel>
        </Dialog>);
}
