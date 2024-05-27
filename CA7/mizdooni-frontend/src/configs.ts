export const currentOrigin  = 'http://localhost:3001'
export const localStorageKeyForSession = 'MIZDOONI_FRONT_END_SESSION'
export const googleOauth2ClientId = "27899273446-b24tu4ocvgtsca2iknqaajq7kd20uugj.apps.googleusercontent.com"
export const googleOauth2Endpoint = 'https://accounts.google.com/o/oauth2/v2/auth';
export const googleOauth2Scopes = 'https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile'
export const googleOauth2RedirectPath = '/google'
export const googleOauth2RedirectUri = currentOrigin + googleOauth2RedirectPath
export const googleOauth2ResponseType = 'code'