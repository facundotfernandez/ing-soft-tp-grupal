import {InputField} from "@components/recovery/InputField";

const RecoveryInput = ({
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

export default RecoveryInput;
