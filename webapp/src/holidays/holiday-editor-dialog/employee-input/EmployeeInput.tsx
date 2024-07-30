/** @jsxImportSource @emotion/react */
import React, {FC, useCallback} from 'react'
import {useEmployees} from '../../employees.context'
import {css} from '@emotion/react'

type Props = {
    employeeId: string | undefined
    onChange: (employeeId: string) => void
    error?: string | boolean
};

export const EmployeeInput: FC<Props> =
    (
        {
            employeeId,
            onChange,
            error,
        }
    ) => {
        const employees = useEmployees(true)
        const onChangeSelect = useCallback(
            (e: React.ChangeEvent<HTMLSelectElement>) => {
                onChange(e.target.value)
            },
            [onChange]
        )
        return <div>
            <select
                onChange={onChangeSelect}
                css={error ? css`
                    border-color: red;
                    color: red;
                ` : undefined}
            >
                <option disabled selected={employeeId === undefined}>Select your option</option>
                {employees.map(employee => <option
                    key={employee.employeeId}
                    selected={employee.employeeId === employeeId}
                    value={employee.employeeId}
                >
                    {employee.name}
                </option>)}
            </select>
            {typeof error === 'string' ? <div css={css`font-size: 12px; color: red;`}>
                {error}
            </div> : null}
        </div>
    }