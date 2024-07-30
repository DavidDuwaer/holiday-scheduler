/** @jsxImportSource @emotion/react */
import React, {FC, useCallback, useState} from 'react'
import {useHolidaysContext} from '../holiday-context/holidays.context'
import {HolidayEditor} from '../holiday-editor-dialog/HolidayEditor'

export const AddHolidayButton: FC =
    () => {
        const [dialogOpen, setDialogOpen] = useState(false)
        const onClickButton = useCallback(() => setDialogOpen(true), [])
        const {createHoliday} = useHolidaysContext()
        return <>
            <button onClick={onClickButton}>Register holiday</button>
            <HolidayEditor
                onSave={createHoliday}
                dialogOpen={dialogOpen}
                setDialogOpen={setDialogOpen}
            />
        </>
    }