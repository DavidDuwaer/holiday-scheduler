/** @jsxImportSource @emotion/react */
import React, {FC, useCallback, useState} from 'react'
import {useHolidaysContext} from '../../holiday-context/holidays.context'
import {HolidayEditor} from '../../holiday-editor-dialog/HolidayEditor'
import {Holiday} from '../../../lib/Holiday'

type Props = {
    holiday: Holiday
}

export const EditHolidayButton: FC<Props> =
    (
        {
            holiday,
        },
    ) => {
        const [dialogOpen, setDialogOpen] = useState(false)
        const onClickButton = useCallback(() => setDialogOpen(true), [])
        const {updateHoliday} = useHolidaysContext()
        return <>
            <button onClick={onClickButton}>Edit</button>
            <HolidayEditor
                onSave={updateHoliday}
                dialogOpen={dialogOpen}
                setDialogOpen={setDialogOpen}
                initialValue={holiday}
            />
        </>
    }