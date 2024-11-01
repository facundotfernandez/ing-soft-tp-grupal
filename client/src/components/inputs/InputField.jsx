import {TextInput} from "@tremor/react";
import Column from "@components/structures/Column";

export const InputField = ({
                               id,
                               type,
                               placeholder,
                               autoComplete,
                               label,
                               value,
                               onChange
                           }) => (<Column className={"gap-y-1"}>
    <label
        htmlFor={id}
        className="text-tremor-default font-medium text-tremor-content-strong dark:text-dark-tremor-content-strong"
    >
        {label}
    </label>
    <TextInput
        type={type}
        id={id}
        name={id}
        autoComplete={autoComplete}
        placeholder={placeholder}
        onChange={onChange}
        value={value}
    />
</Column>);