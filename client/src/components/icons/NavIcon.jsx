import PropTypes from "prop-types";
import classNames from 'classnames';

export const NavIcon = ({type, iconId, className, ...props}) => {

    const TYPES = {
        "social": "fab fa",
        "view": "fas fa",
        "icon": "fa",
        "custom": ""
    }

    return (
        <i
            className={classNames(`text-xl ${TYPES[type]}-${iconId}`, className)}
            role={"img"}
            {...props}
        />
    )
}

NavIcon.propTypes = {
    className: PropTypes.string,
    iconId: PropTypes.string,
    type: PropTypes.string,
};