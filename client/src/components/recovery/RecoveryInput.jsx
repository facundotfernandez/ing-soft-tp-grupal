import {InputField} from "@components/recovery/InputField";
import LoginInput from "@components/login/LoginInput";

const RecoveryInput = ({
                        id,
                        type,
                        placeholder,
                        value,
                        onChange
                    }) => {
    return (<LoginInput
            id={id}
            type={type}
            placeholder={placeholder}
            value={value}
            onChange={onChange}
        />);
};

export default RecoveryInput;
