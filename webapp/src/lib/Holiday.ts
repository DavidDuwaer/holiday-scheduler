import {HolidayStatus} from './HolidayStatus'

export type Holiday = {
    holidayId: string
    employeeId: string
    startOfHoliday: string
    endOfHoliday: string
    label: string
    status: HolidayStatus
}