import PropTypes from "prop-types";
import classNames from 'classnames';

export const NavLinkCard = ({children, className, ...props}) => {
    return (
        <div
            className={classNames("flex gap-2 text-wrap place-items-center select-none hover:cursor-pointer bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 max-h-12 rounded text-sm", className)}
            {...props}
        >
            {children}
        </div>)
}

NavLinkCard.propTypes = {
    children: PropTypes.node,
    className: PropTypes.string,
};