import {v4 as uuid} from 'uuid'

export function randomUuid(): string {
    return uuid()
}