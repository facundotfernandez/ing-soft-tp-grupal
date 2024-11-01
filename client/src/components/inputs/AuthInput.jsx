import {InputField} from "@components/inputs/InputField";

const AuthInput = ({
                       id,
                       type,
                       placeholder,
                       autoComplete,
                       value,
                       onChange
                   }) => {
    return (<InputField
        id={id}
        type={type}
        autoComplete={autoComplete}
        placeholder={placeholder}
        value={value}
        onChange={onChange}
    />);
};

export default AuthInput;
