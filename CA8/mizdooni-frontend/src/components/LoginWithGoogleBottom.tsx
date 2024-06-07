import * as configs from "../configs";

function LoginWithGoogleBottom() {

    return (
        <form method="GET" action={configs.googleOauth2Endpoint}>
            <input type="hidden" name='client_id' value={configs.googleOauth2ClientId} />
            <input type="hidden" name='redirect_uri' value={configs.googleOauth2RedirectUri} />
            <input type="hidden" name='response_type' value={configs.googleOauth2ResponseType} />
            <input type="hidden" name='scope' value={configs.googleOauth2Scopes} />    
            <button type="submit" className="btn btn-primary">Sign-In With Google</button>
        </form>
        
    );
}

export default LoginWithGoogleBottom