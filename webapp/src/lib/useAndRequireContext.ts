import {Context, useContext, useMemo} from 'react'

export function useAndRequireContext<T>(context: Context<T | undefined>): T {
    const contextValue = useContext(context);
    return useMemo(
        () => {
            if (contextValue === undefined) {
                throw new Error(
                    'Context is undefined! Are you sure the provider is an ancestor of the component where you are consuming it?'
                )
            }
            return contextValue
        },
        [contextValue]
    )
}