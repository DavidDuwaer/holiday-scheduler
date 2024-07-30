export function toDateString(date: Date) {
    const year = date.getFullYear()
    const day = ('0' + date.getDate()).slice(-2)
    const month = ('0' + (date.getMonth() + 1)).slice(-2)
    return `${year}-${month}-${day}`
}