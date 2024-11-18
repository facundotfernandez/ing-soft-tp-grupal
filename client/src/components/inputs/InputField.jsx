import {TextInput} from "@tremor/react";
import Column from "@components/structures/Column";

export const InputField = ({
                               id,
                               type,
                               placeholder,
                               autoComplete,
                               label,
                               value,
                               onChange,
                               required = false,
                               disabled = false,
                           }) => (<Column className={"gap-y-1"}>
    <label
        htmlFor={id}
        className="text-tremor-default font-medium text-dark-tremor-content-strong"
    >
        {label}
        {required && <span className="text-red-500 ml-1">*</span>}
    </label>
    <TextInput
        type={type}
        id={id}
        name={id}
        autoComplete={autoComplete}
        placeholder={placeholder || ""}
        onChange={onChange}
        value={value}
        disabled={disabled || false}
    />
</Column>);