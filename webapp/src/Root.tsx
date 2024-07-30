import React, {FC} from 'react'
import './App.css'
import {Holidays} from './holidays/Holidays'
import {EmployeesProvider} from './holidays/employees.context'
import {HolidaysProvider} from './holidays/holiday-context/holidays.context'

export const Root: FC = () => {
  return <div className="App">
      <EmployeesProvider>
          <HolidaysProvider>
              <Holidays/>
          </HolidaysProvider>
      </EmployeesProvider>
  </div>;
}