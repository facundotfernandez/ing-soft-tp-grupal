import {InputField} from "@components/login/InputField";

const LoginInput = ({
                        id,
                        type,
                        placeholder,
                        value,
                        onChange
                    }) => {
    return (<InputField
            id={id}
            type={type}
            placeholder={placeholder}
            value={value}
            onChange={onChange}
        />);
};

export default LoginInput;
