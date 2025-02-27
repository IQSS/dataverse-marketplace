import { useContext } from "react";
import { UserContext } from "../components/context/UserContextProvider";
import axios from "axios";

const BASE_URL = 'http://localhost:8081';

export default function useMarketplaceApiRepo() {

    const userContext = useContext(UserContext);
    const BASE_URL = 'http://localhost:8081';
    const jwtToken = userContext.user ? userContext.user.accessToken : '';

    const deleteRequest = async (url: string) => {
        makeApiRequest(url, 'DELETE', new FormData());
        
    }

    const postRequest = async (url: string, formData: FormData) => {
        return makeApiRequest(url, 'POST', formData);
    }

    const putRequest = async (url: string, formData: FormData) => {
        makeApiRequest(url, 'PUT', formData);
    }

    const makeApiRequest = async (url: string, method: string, formData: FormData) => {
        try {
            const response = await axios({
                method: method,
                url: `${BASE_URL}${url}`,
                data: formData,
                headers: {
                    "Authorization": `Bearer ${jwtToken}`
                }
            });                        

            userContext.setModalTitle("Success");
            userContext.setModalMessage(response.data.message);
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
        deleteRequest,
        postRequest,
        getImageUrl,
        fetchFromApi,
        putRequest,
        makeApiRequest
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