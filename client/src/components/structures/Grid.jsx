import classNames from "classnames";

const Column = ({
                    className,
                    children
                }) => {

    return (<div className={classNames("grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3", className)}>
        {children}
    </div>);
}

export default Column;