import {useCallback} from 'react'
import {Holiday} from '../../lib/Holiday'

export function useUpdateHoliday() {
    return useCallback(
        async (holiday: Holiday) => {
            const response = await fetch(
                'http://localhost:8080/holidays',
                {
                    method: 'PUT',
                    body: JSON.stringify(holiday),
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json',
                    },
                }
            )
            if (!response.ok) {
                throw new Error()
            }
        },
        []
    )
}