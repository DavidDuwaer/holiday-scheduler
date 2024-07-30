import {createContext, FC, ReactNode, useCallback, useEffect, useMemo, useState} from 'react'
import {useAndRequireContext} from '../lib/useAndRequireContext'

type Context = {
    employees: Employee[] | undefined
}

const ContextRef = createContext<Context | undefined>(undefined)

type Props = {
    children: ReactNode
}

export const EmployeesProvider: FC<Props> =
    (
        {
            children,
        },
    ) => {
        const [employees, setEmployees] = useState<Employee[]>()
        const fetchEmployees = useCallback(
            async () => {
                const response = await fetch(
                    'http://localhost:8080/employees',
                    {method: 'GET'}
                )
                if (!response.ok) {
                    throw new Error()
                }
                setEmployees(await response.json())
            },
            []
        )
        useEffect(
            () => {
                fetchEmployees()
            },
            []
        )
        return <ContextRef.Provider value={useMemo(() => ({
            employees,
        }), [employees])}>
            {children}
        </ContextRef.Provider>
    }

export function useEmployees(require: true): Employee[]
export function useEmployees(require?: boolean): Employee[] | undefined
export function useEmployees(require = false) {
    const {employees} = useAndRequireContext(ContextRef)
    return useMemo(
        () => {
            if (employees === undefined && require) {
                throw new Error('employees are required here')
            }
            return employees
        },
        [employees, require]
    )
}

type Employee = {
    employeeId: string
    name: string
}