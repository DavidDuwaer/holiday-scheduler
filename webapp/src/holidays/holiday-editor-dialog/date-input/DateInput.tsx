/** @jsxImportSource @emotion/react */
import React, {ChangeEvent, FC, useCallback, useMemo} from 'react'
import {toDateString} from '../../../lib/toDateString'
import {css} from '@emotion/react'

type Props = {
    value: string
    onChange: (value: string) => void
    placeholder?: string
    error?: string | boolean
};

export const DateInput: FC<Props> =
    (
        {
            value,
            onChange,
            placeholder,
            error,
        },
    ) => {
        const inputValue = useMemo(
            () => value,
            [value]
        )
        const onInputChange = useCallback(
            (e: ChangeEvent<HTMLInputElement>) => {
                onChange(e.target.value ?? toDateString(new Date()))
            },
            [onChange]
        )
        return <div>
            <input
                type="date"
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

