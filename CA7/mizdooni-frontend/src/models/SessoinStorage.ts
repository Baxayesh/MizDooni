import type { TokenModel } from "../contracts";
import User from "./User";
import Session from "./Session";
import { UserRole, toUserRole } from "./UserRole";
import { showError } from "../utils";
import * as configs from '../configs'

export default class SessionStorage {

    static instance = new SessionStorage()

    private constructor(){

    }

    saveSession(session: Session) {    
        localStorage.setItem(
            configs.localStorageKeyForSession,
            JSON.stringify(session)
        )
    }

    removeSession() {
        localStorage.removeItem(
            configs.localStorageKeyForSession
        )
    }

    getSession(){
        var sessionJson = localStorage.getItem(configs.localStorageKeyForSession)
        if(sessionJson){
            var session = JSON.parse(sessionJson)

            session.expiration = new Date(session.expiration)

            if(session && session.expiration > new Date()){
                return session;
            }
        }
        return null;
    }

}