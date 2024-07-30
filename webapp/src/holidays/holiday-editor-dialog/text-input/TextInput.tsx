/** @jsxImportSource @emotion/react */
import React, {ChangeEvent, FC, useCallback, useMemo} from 'react'
import {TextField} from '@material-ui/core'
import {css} from '@emotion/react'

type Props = {
    value: string | undefined
    onChange: (value: string | undefined) => void
    placeholder?: string
    error?: string | boolean
};

export const TextInput: FC<Props> =
    (
        {
            value,
            onChange,
            placeholder,
            error,
        },
    ) => {
        const inputValue = useMemo(
            () => value !== undefined ? value : '',
            [value]
        )
        const onInputChange = useCallback(
            (e: ChangeEvent<HTMLInputElement>) => {
                const value = e.target.value
                onChange(value.length === 0 ? undefined : value)
            },
            [onChange]
        )
        return <div>
            <input
                value={inputValue}
                onChange={onInputChange}
                placeholder={placeholder}
                css={error ? css`
                    border-color: red;
                    color: red;
                ` : undefined}
            />
            {typeof error === 'string' ? <div css={css`font-size: 12px; color: red;`}>
                {error}
            </div> : null}
        </div>
    }