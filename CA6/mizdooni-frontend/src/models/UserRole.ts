import { UserModelRole } from "../contracts";

export enum UserRole {
    Client,
    Manager
}

export const toUserRole = (role: UserModelRole| undefined): UserRole =>{
    if(role === UserModelRole.Manager)
        return UserRole.Manager

    return UserRole.Client
}
