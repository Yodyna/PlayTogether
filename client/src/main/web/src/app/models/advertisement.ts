import { TimeOfGame } from './timeOfGame';

export interface Advertisement {
    id?: number;
    sport: string;
    city: string;
    description: string;
    street: string;
    url?: string;
    dateofCreate: Date;
    date: Date;
    person?: number;
    card?: boolean;
    timeOfGame?: Array<TimeOfGame>;
    minNumberOfParticipants?: number;
    maxNumberOfParticipants?: number;
    actualNumberOfParticipants?: number;
}
