import { UserDetail } from './userDetail';

export interface User {
    username: string;
    password?: string;
    userDetail?: UserDetail;
}
