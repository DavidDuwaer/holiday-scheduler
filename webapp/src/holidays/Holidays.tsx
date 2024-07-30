/** @jsxImportSource @emotion/react */
import {FC} from 'react'
import {css} from '@emotion/react'
import {useEmployees} from './employees.context'
import {useHolidaysContext} from './holiday-context/holidays.context'
import {AddHolidayButton} from './add-holiday-button/AddHolidayButton'
import {HolidayRow} from './holiday-row/HolidayRow'

type Props = {
};

export const Holidays: FC<Props> =
    () => {
        const employees = useEmployees()
        const {holidays} = useHolidaysContext()
        if (employees === undefined || holidays === undefined) {
            return <div>
                Loading...
            </div>
        }
        return <div css={css`
            background-color: lightgrey;
            position: absolute;
            top: 50px;
            left: calc(max(50px, (100% - 500px) / 2));
            right: calc(max(50px, (100% - 500px) / 2));
            min-height: calc(100% - 100px);
            padding: 16px;
            display: flex;
            flex-direction: column;
            gap: 8px;
        `}>
            {holidays.map(holiday => <HolidayRow
                key={holiday.holidayId}
                holiday={holiday}
            />)}
            <AddHolidayButton/>
        </div>
    }