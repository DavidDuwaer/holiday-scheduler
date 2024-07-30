import {useEffect, useState} from 'react'

export function useNow(): number {
    const [currentSecond, setCurrentSecond] = useState(Date.now())
    useEffect(
        () => {
            const update = () => setCurrentSecond(Date.now())
            const interval = setInterval(update, 1000)
            return () => {
                clearInterval(interval)
            }
        }
    )
    return currentSecond
}