/** @jsxImportSource @emotion/react */
import React, {FC, useCallback, useEffect, useMemo, useState} from 'react'
import {Holiday} from '../../lib/Holiday'
import {Button, Dialog, DialogActions, DialogContent, DialogTitle} from '@material-ui/core'
import {css} from '@emotion/react'
import {EmployeeInput} from './employee-input/EmployeeInput'
import {DateInput} from './date-input/DateInput'
import {TextInput} from './text-input/TextInput'
import {toDateString} from '../../lib/toDateString'
import {randomUuid} from '../../lib/randomUuid'
import {interpretLocalDateInBrowserTimeZone} from '../../lib/interpretLocalDateInBrowserTimeZone'
import {useHolidaysContext} from '../holiday-context/holidays.context'
import {useNow} from '../../lib/useNow'

type Props = {
    initialValue?: Holiday
    onSave: (value: Holiday) => Promise<void>
    dialogOpen: boolean
    setDialogOpen: (open: boolean) => void
}

export const HolidayEditor: FC<Props> =
    (
        {
            initialValue,
            onSave,
            dialogOpen,
            setDialogOpen,
        },
    ) => {
        const initialEmployeeId = useMemo(() => initialValue?.employeeId, [])
        const initialStartOfHoliday = useMemo(
            () => toDateString(
                initialValue?.startOfHoliday
                    ? new Date(initialValue.startOfHoliday)
                    : new Date(),
            ),
            []
        )
        const initialEndOfHoliday = useMemo(
            () => toDateString(
                initialValue?.endOfHoliday
                    ? new Date(initialValue.endOfHoliday)
                    : new Date(),
            ),
            []
        )
        const initialLabel = useMemo(() => initialValue?.label, [])

        const [employeeId, setEmployeeId] = useState(initialEmployeeId)
        const [startOfHoliday, setStartOfHoliday] = useState(initialStartOfHoliday)
        const [endOfHoliday, setEndOfHoliday] = useState(initialEndOfHoliday)
        const [label, setLabel] = useState(initialLabel)
        const start = useMemo(
            () => interpretLocalDateInBrowserTimeZone(startOfHoliday),
            [startOfHoliday]
        )
        const end = useMemo(
            () => interpretLocalDateInBrowserTimeZone(endOfHoliday),
            [endOfHoliday]
        )
        const now = useNow()
        const lastMidnight = useMemo(
            () => new Date(now).setHours(0, 0, 0),
            [now]
        )
        const holiday5DaysAhead = useMemo(
            () => start.getTime() - lastMidnight >= 5 * MS_PER_DAY,
            [start, now]
        )
        const endNotBeforeStart = useMemo(
            () => end.getTime() >= start.getTime(),
            [end, start]
        )
        const {holidays} = useHolidaysContext()
        const noOverlapWithAnyOtherHoliday = useMemo(
            () => !holidays?.some(holiday => {
                const otherStart = interpretLocalDateInBrowserTimeZone(holiday.startOfHoliday).getTime()
                const otherEnd = interpretLocalDateInBrowserTimeZone(holiday.endOfHoliday).getTime()
                return holiday.holidayId !== initialValue?.holidayId
                    && otherEnd > start.getTime() && otherStart < end.getTime()
            }),
            []
        )
        const threeDaysApartFromOtherHolidaysOfSameEmployee = useMemo(
            () => !holidays?.some(holiday => {
                const threeDays = 3 * MS_PER_DAY
                const otherStart = interpretLocalDateInBrowserTimeZone(holiday.startOfHoliday).getTime()
                const otherEnd = interpretLocalDateInBrowserTimeZone(holiday.endOfHoliday).getTime()
                return holiday.holidayId !== initialValue?.holidayId
                    && holiday.employeeId === employeeId
                    && otherEnd > (start.getTime() - threeDays) && otherStart < (end.getTime() + threeDays)
            }),
            []
        )
        const [showValidation, setShowValidation] = useState(false)
        useEffect(
            () => {
                if (dialogOpen) {
                    setShowValidation(false)
                    setEmployeeId(initialEmployeeId)
                    setStartOfHoliday(initialStartOfHoliday)
                    setEndOfHoliday(initialEndOfHoliday)
                    setLabel(initialLabel)
                }
            },
            []
        )
        const onSubmit = useCallback(
            (e: React.FormEvent) => {
                e.preventDefault()
                setShowValidation(true)
                if (employeeId === undefined) {
                    return
                }
                if (label === undefined) {
                    return
                }
                if (!holiday5DaysAhead) {
                    return
                }
                if (!endNotBeforeStart) {
                    return
                }
                if (!noOverlapWithAnyOtherHoliday) {
                    return
                }
                if (!threeDaysApartFromOtherHolidaysOfSameEmployee) {
                    return
                }
                onSave({
                    holidayId: initialValue?.holidayId ?? randomUuid(),
                    employeeId,
                    startOfHoliday: start.toISOString(),
                    endOfHoliday: end.toISOString(),
                    label,
                    status: 'REQUESTED'
                })
                setDialogOpen(false)
            },
            [onSave, employeeId, label, holiday5DaysAhead, endNotBeforeStart, noOverlapWithAnyOtherHoliday, threeDaysApartFromOtherHolidaysOfSameEmployee, startOfHoliday, endOfHoliday],
        )
        const setDialogOpenToFalse = useCallback(
            () => setDialogOpen(false),
            [setDialogOpen],
        )
        return <>
            <Dialog open={dialogOpen} onClose={setDialogOpenToFalse}>
                <DialogTitle>Register holiday</DialogTitle>
                <form onSubmit={onSubmit} onAbort={setDialogOpenToFalse} action={undefined}>
                    <DialogContent css={css`
                        display: grid;
                        grid-template-columns: 90px 1fr;
                        gap: 8px;
                    `}>
                        <div>Your name</div>
                        <EmployeeInput
                            employeeId={employeeId}
                            onChange={setEmployeeId}
                            error={showValidation && employeeId === undefined ? 'You must select a name' : undefined}
                        />
                        <div>Start date</div>
                        <DateInput
                            value={startOfHoliday}
                            onChange={setStartOfHoliday}
                            error={showValidation ? (() => {
                                if (!holiday5DaysAhead) {
                                    return 'Your holiday must be at least 5 days in the future'
                                }
                                if (!noOverlapWithAnyOtherHoliday) {
                                    return 'The holiday may not overlap with any existing holiday, including from coworkers'
                                }
                                if (!threeDaysApartFromOtherHolidaysOfSameEmployee) {
                                    return 'The holiday must be at least 3 days apart from any other holiday you booked'
                                }
                            })() : undefined}
                        />
                        <div>End date</div>
                        <DateInput
                            value={endOfHoliday}
                            onChange={setEndOfHoliday}
                            error={showValidation ? (() => {
                                if (!endNotBeforeStart) {
                                    return 'The holiday cannot end before it starts'
                                }
                                if (!noOverlapWithAnyOtherHoliday) {
                                    return 'The holiday may not overlap with any existing holiday, including from coworkers'
                                }
                                if (!threeDaysApartFromOtherHolidaysOfSameEmployee) {
                                    return 'The holiday must be at least 3 days apart from any other holiday you booked'
                                }
                            })() : undefined}
                        />
                        <div>Description</div>
                        <TextInput
                            value={label}
                            onChange={setLabel}
                            error={showValidation && label === undefined ? 'You give a description' : undefined}
                        />
                    </DialogContent>
                    <DialogActions>
                        <Button type="submit">Submit</Button>
                    </DialogActions>
                </form>
            </Dialog>
        </>
    }

const MS_PER_DAY = 1000 * 3600 * 24