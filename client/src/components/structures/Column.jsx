import classNames from "classnames";

const Column = ({className, children}) => {

    return (<div className={classNames("flex flex-col flex-wrap gap-y-4 w-full", className)}>
        {children}
    </div>);
}

export default Column;