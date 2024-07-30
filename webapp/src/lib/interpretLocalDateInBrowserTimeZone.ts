export function interpretLocalDateInBrowserTimeZone(localDate: string): Date {
    const parts = localDate.split('-')
    if (parts.length !== 3) {
        throw new Error('illegal state')
    }
    const year = parseInt(parts[0])
    const month = parseInt(parts[1]) - 1
    const day = parseInt(parts[2])
    return new Date(year, month, day, 0, 0, 0)
}