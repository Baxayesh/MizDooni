import { TokenModel } from "../contracts"
import User from "./User"
import { toUserRole } from "./UserRole"

export default interface Session {

    expiration: Date
    authToken: string
    user: User

}

export function toSession(tokenModel: TokenModel): Session|null{
    if(tokenModel.expiration && tokenModel.jwtToken && tokenModel.user?.username && tokenModel.user.email){

        var session = {
            expiration: new Date(tokenModel.expiration),
            authToken: tokenModel.jwtToken,
            user: {
                username: tokenModel.user.username,
                email: tokenModel.user.email,
                role: toUserRole(tokenModel.user.role)
            }
        }
    
        return session;
    }else{
        return null;
    }

}