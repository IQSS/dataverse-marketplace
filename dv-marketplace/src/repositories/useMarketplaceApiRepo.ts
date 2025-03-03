import { useContext } from "react";
import { UserContext } from "../components/context/UserContextProvider";
import axios from "axios";

const BASE_URL = 'http://localhost:8081';

export default function useMarketplaceApiRepo() {

    const userContext = useContext(UserContext);
    const BASE_URL = 'http://localhost:8081';
    const jwtToken = userContext.user ? userContext.user.accessToken : '';

    const deleteBodyRequest = async (url: string) => {
        const confirmed = window.confirm("Are you sure you want to delete this item?");
        if (!confirmed) {
            userContext.setModalTitle("Cancelled");
            userContext.setModalMessage("Operation cancelled by user.");
            userContext.setShowMessage(true);
            
        } else {
            return makeApiBodyRequest(url, 'DELETE', new FormData());
        }
        return;
    }

    const postBodyRequest = async (url: string, FormData: FormData) => {
        return makeApiBodyRequest(url, 'POST', FormData);
    }
    

    const putBodyRequest = async (url: string, formData: FormData) => {
        return makeApiBodyRequest(url, 'PUT', formData);
    }


    const deleteFormRequest = async (url: string) => {
        const confirmed = window.confirm("Are you sure you want to delete this item?");
        if (!confirmed) {
            userContext.setModalTitle("Cancelled");
            userContext.setModalMessage("Operation cancelled by user.");
            userContext.setShowMessage(true);
            
        } else {
            return makeApiFormRequest(url, 'DELETE', new FormData());
        }
        return;
    }

    const postFormRequest = async (url: string, formData: FormData) => {
        return makeApiFormRequest(url, 'POST', formData);
    }

    const putFormRequest = async (url: string, formData: FormData) => {
        return makeApiFormRequest(url, 'PUT', formData);
    }

    const makeApiBodyRequest = async (url: string, method: string, formData: FormData) => {
        return handleRequest(url, method, formData, true);
    }

    const makeApiFormRequest = async (url: string, method: string, formData: FormData) => {
         return handleRequest(url, method, formData, false);
    }
    
    const handleRequest = async (url: string, method: string, formData: FormData, bodyRequest: boolean) => {
        try {
            const data = bodyRequest ? JSON.stringify(Object.fromEntries(formData.entries())) : formData;
            const headers = bodyRequest ? {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${jwtToken}`
                } : {
                    "Authorization": `Bearer ${jwtToken}`
                };

            const response = await axios({
                method: method,
                url: `${BASE_URL}${url}`,
                data: data,
                headers: headers
            });

            userContext.setModalTitle("Success");
            if (response.data.message) {
                userContext.setModalMessage(response.data.message);
            } else {
                userContext.setModalMessage("Operation successful");
            }
            
            userContext.setShowMessage(true);

            return response.data;

        } catch (error) {
            if (axios.isAxiosError(error)) {
                userContext.setModalMessage(error.response?.data?.message || error.message);
            }
            else {
                userContext.setModalMessage(String(error));
            }
            userContext.setModalTitle("Error");
            userContext.setShowMessage(true);
        }
    }

    

    const getImageUrl = (imageId: number) => {
        return `${BASE_URL}/api/stored-resource/${imageId}`;
    }

    return {
        deleteBodyRequest,
        postBodyRequest,
        deleteFormRequest,
        postFormRequest,
        putBodyRequest,
        putFormRequest,
        getImageUrl,
        fetchFromApi,
    };


}

export async function fetchFromApi<T>(url: string): Promise<T> {
    try {
        const response = await axios.get(`${BASE_URL}${url}`);
        return response.data as T;
    } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
    }
}