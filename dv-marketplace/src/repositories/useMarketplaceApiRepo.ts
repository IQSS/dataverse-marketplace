import { useContext } from "react";
import { UserContext } from "../components/context/UserContextProvider";
import axios from "axios";


export default function useMarketplaceApiRepo() {

    const userContext = useContext(UserContext);
    const BASE_URL = 'http://localhost:8081';
    const jwtToken = userContext.user ? userContext.user.accessToken : '';

    const deleteRequest = async (url: string) => {
        
        try {
            const response = await axios.delete(`${BASE_URL}${url}`, {
                headers: {
                    "Authorization": `Bearer ${jwtToken}`
                }
            });
            userContext.setModalTitle("Success");
            userContext.setModalMessage(response.data.message);
            userContext.setShowMessage(true);
        } catch (error) {
            if (axios.isAxiosError(error)) {
                userContext.setModalMessage(error.response?.data?.message || error.message);
            } else {
                userContext.setModalMessage(String(error));
            }
            userContext.setModalTitle("Error");
            userContext.setShowMessage(true);
        }
    }

    const postRequest = async (url: string, formData: FormData) => {
        try {
            const response = await axios.post(`${BASE_URL}${url}`, formData, {
                headers: {
                    "Authorization": `Bearer ${jwtToken}`
                }
            });
            userContext.setModalTitle("Success");
            userContext.setModalMessage(response.data.message);
            userContext.setShowMessage(true);
        } catch (error) {
            if (axios.isAxiosError(error)) {
                userContext.setModalMessage(error.response?.data?.message || error.message);
            }  else {
                userContext.setModalMessage(String(error));
            }
            userContext.setModalTitle("Error");
            userContext.setShowMessage(true);
        }
    }

    return {
        deleteRequest,
        postRequest
    };


}