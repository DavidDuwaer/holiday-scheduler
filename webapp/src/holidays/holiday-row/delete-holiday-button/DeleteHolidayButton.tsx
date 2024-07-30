/** @jsxImportSource @emotion/react */
import React, {FC, useCallback} from 'react'
import {useHolidaysContext} from '../../holiday-context/holidays.context'
import {Holiday} from '../../../lib/Holiday'

type Props = {
    holiday: Holiday
}

export const DeleteHolidayButton: FC<Props> =
    (
        {
            holiday,
        },
    ) => {
        const {deleteHoliday} = useHolidaysContext()
        const onClickButton = useCallback(
            () => {
                deleteHoliday(holiday)
            },
            []
        )
        return <>
            <button onClick={onClickButton}>Delete</button>
        </>
    }