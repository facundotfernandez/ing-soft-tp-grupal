import { TextInput } from "@tremor/react";

export const InputField = ({ id, type, placeholder, value, onChange }) => (
    <div className="flex flex-col w-full gap-y-1 ">
        <label
            htmlFor={id}
            className="text-tremor-default font-medium text-tremor-content-strong dark:text-dark-tremor-content-strong"
        />
        <TextInput
            type={type}
            id={id}
            name={id}
            autoComplete="off"
            placeholder={placeholder}
            onChange={onChange}
            value={value}
        />
    </div>
);