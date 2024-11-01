import classNames from "classnames";

const Column = ({
                    className,
                    children
                }) => {

    const gapClass = className && className.includes("gap-y-") ? "" : "gap-y-4";

    return (<div className={classNames("flex flex-col flex-wrap w-full", gapClass, className)}>
            {children}
        </div>);
}

export default Column;