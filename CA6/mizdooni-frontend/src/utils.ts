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

export const saveToken = (token: string) => {
    toast.success('successfull login!')
}

export const handleError = (reason: any) => {
    var error = _toError(reason.response.data)
    var anyDetail = false
    if(error.details){
        for(var detail in error.details){
            anyDetail = true
            toast.error(
                detail + ": " + error.details[detail] ,{
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
    }
    if(!anyDetail){
        toast.error(error.message ,{
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
}

