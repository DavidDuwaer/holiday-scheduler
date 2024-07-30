import {createContext, FC, ReactNode, useCallback, useEffect, useMemo, useState} from 'react'
import {useAndRequireContext} from '../../lib/useAndRequireContext'
import {Holiday} from '../../lib/Holiday'
import {useFetchHolidays} from './useFetchHolidays'
import {useCreateHoliday} from './useCreateHoliday'
import {useUpdateHoliday} from './useUpdateHoliday'
import {useDeleteHoliday} from './useDeleteHoliday'

type Context = {
    holidays: Holiday[] | undefined
    createHoliday: (value: Holiday) => Promise<void>
    updateHoliday: (value: Holiday) => Promise<void>
    deleteHoliday: (value: Pick<Holiday, 'holidayId'>) => Promise<void>
}

const ContextRef = createContext<Context | undefined>(undefined)

type Props = {
    children: ReactNode
}

export const HolidaysProvider: FC<Props> =
    (
        {
            children,
        },
    ) => {
        const initialHolidays = useFetchHolidays()
        const [holidays, setHolidays] = useState<Holiday[]>()
        useEffect(
            () => {
                if (initialHolidays !== undefined) {
                    setHolidays(initialHolidays)
                }
            },
            [initialHolidays]
        )
        const createHolidayInApi = useCreateHoliday()
        const createHoliday = useCallback(
            async (holiday: Holiday) => {
                if (holidays === undefined) {
                    throw new Error('Wow! You are fast!')
                }
                setHolidays(old => [...old!, holiday])
                await createHolidayInApi(holiday)
            },
            [holidays, createHolidayInApi]
        )
        const updateHolidayInApi = useUpdateHoliday()
        const updateHoliday = useCallback(
            async (holiday: Holiday) => {
                if (holidays === undefined) {
                    throw new Error('Wow! You are fast!')
                }
                setHolidays(old => {
                    const nuw = [...old!]
                    const i = nuw.findIndex(h => h.holidayId === holiday.holidayId)
                    if (i < 0) {
                        throw new Error('Illegal state')
                    }
                    nuw[i] = holiday
                    return nuw
                })
                await updateHolidayInApi(holiday)
            },
            [holidays, updateHolidayInApi]
        )
        const deleteHolidayInApi = useDeleteHoliday()
        const deleteHoliday = useCallback(
            async (holiday: Pick<Holiday, 'holidayId'>) => {
                if (holidays === undefined) {
                    throw new Error('Wow! You are fast!')
                }
                setHolidays(old => {
                    const nuw = [...old!]
                    const i = nuw.findIndex(h => h.holidayId === holiday.holidayId)
                    if (i < 0) {

                    }
                    nuw.splice(i, 1)
                    return nuw
                })
                await deleteHolidayInApi(holiday)
            },
            [holidays, updateHolidayInApi]
        )

        return <ContextRef.Provider value={useMemo(() => ({
            holidays,
            createHoliday,
            updateHoliday,
            deleteHoliday,
        }), [createHoliday, holidays, updateHoliday, deleteHoliday])}>
            {children}
        </ContextRef.Provider>
    }

export function useHolidaysContext() {
    return useAndRequireContext(ContextRef)
}