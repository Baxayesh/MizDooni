import { toast } from "react-toastify"
import type { Error } from "./contracts"


export const _toError = (error: any): Error => {
    return {
        details: error.details,
        exceptionType: error.exceptionType,
        message: error.message,
        statusCode: error.statusCode
    }
}


export const handleError = (reason: any) => {
    if(reason.response){
        var error = _toError(reason.response.data)
        var anyDetail = false
        if(error.details){
            for(var detail in error.details){
                anyDetail = true
                showError('Invalid Field(' + detail + '): ' + error.details[detail])
            }
        }
        if(!anyDetail){
            showError(error.message)
        }
    }else{
        showError('Failed to connect to backend server');
    }
    
}

export const showError = (message: string|undefined) => {
    toast.error(message || 'Some Error Occuered :(' ,{
        position: "top-right",
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "colored"

    })
}

export const showSuccess = (message: string) =>{
    toast.success('Successfull Login!')
}