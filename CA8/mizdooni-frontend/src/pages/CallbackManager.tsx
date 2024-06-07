import { ReactNode, useEffect, useMemo, useState } from "react";
import queryString from "query-string";
import { Navigate, useNavigate } from "react-router-dom";
import { loginManagerProps } from "./loginManagerProps";
import { getMizdooni } from "../mizdooni";
import { handleError, showError } from "../utils";
import { toSession } from "../models/Session";
import * as configs from '../configs'

export default function CallbackManager({login}: loginManagerProps): ReactNode {

    const navigate = useNavigate();

    useEffect(() => {
        const getGoogleAuth = async () => {
            const args = queryString.parse(location.search)
            const code = args['code'] as string|null
            if(code){
                getMizdooni()
                .manageGoogleOauth2Callback({
                    userCode: code
                })
                .then(
                    (response) => {
                        const session = toSession(response.data)
                        if(session){
                            login(session)
                        }else{
                            showError('Failed to login user, invalid response from server')
                        }
                    },
                    (reasen) => {
                        handleError(reasen)
                    }
                )
            }
        }
       
        getGoogleAuth();
        navigate('/');
    }, [navigate, login]);

    return <h1>Authenticating User With OAUTH ....</h1>;
}
