import {useCallback, useEffect, useState} from 'react'
import {Holiday} from '../../lib/Holiday'

export function useFetchHolidays() {
    const [holidays, setHolidays] = useState<Holiday[]>()
    const fetchHolidays = useCallback(
        async () => {
            const response = await fetch(
                'http://localhost:8080/holidays',
                {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json',
                    },
                }
            )
            if (!response.ok) {
                throw new Error()
            }
            setHolidays(await response.json())
        },
        []
    )
    useEffect(
        () => {
            fetchHolidays()
        },
        [fetchHolidays]
    )
    return holidays
}