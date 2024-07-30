import {useCallback} from 'react'
import {Holiday} from '../../lib/Holiday'

export function useDeleteHoliday() {
    return useCallback(
        async ({holidayId}: Pick<Holiday, 'holidayId'>) => {
            const response = await fetch(
                'http://localhost:8080/holidays',
                {
                    method: 'DELETE',
                    body: JSON.stringify({holidayId}),
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