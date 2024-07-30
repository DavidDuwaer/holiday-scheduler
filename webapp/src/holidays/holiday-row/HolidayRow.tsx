/** @jsxImportSource @emotion/react */
import React, {FC, useMemo} from 'react'
import {Holiday} from '../../lib/Holiday'
import {useEmployees} from '../employees.context'
import {css} from '@emotion/react'
import {toDateString} from '../../lib/toDateString'
import {EditHolidayButton} from './edit-holiday-button/EditHolidayButton'
import {DeleteHolidayButton} from './delete-holiday-button/DeleteHolidayButton'

type Props = {
    holiday: Holiday
};

export const HolidayRow: FC<Props> =
    (
        {
            holiday,
        },
    ) => {
        const employees = useEmployees(true)
        const employeeName = useMemo(
            () => employees.find(e => e.employeeId === holiday.employeeId)!.name,
            [employees, holiday.employeeId]
        )
        return <div css={css`
            display: grid;
            grid-template-columns: 0.2fr 0.8fr 50px 50px;
            gap: 8px;
            background-color: rgba(0,0,0,0.1);
            border-radius: 8px;
            padding: 12px 16px;
        `}>
            <div css={css`
                text-align: left;
            `}>
                <div>{employeeName}</div>
                <div>{holiday.label}</div>
            </div>
            <div css={css`
                text-align: right`
            }>
                {toDateString(new Date(holiday.startOfHoliday))} to {toDateString(new Date(holiday.endOfHoliday))}
            </div>
            <DeleteHolidayButton holiday={holiday}/>
            <EditHolidayButton holiday={holiday}/>
        </div>
    }