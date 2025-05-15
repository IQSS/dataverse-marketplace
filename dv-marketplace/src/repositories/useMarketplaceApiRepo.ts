import { use, useContext } from "react";
import { UserContext } from "../components/context/UserContextProvider";
import axios from "axios";
import { toast } from "react-toastify";

const BASE_URL = 'http://localhost:8081';

export default function useMarketplaceApiRepo() {

    const userContext = useContext(UserContext);
    // Change this for deployment
    // const BASE_URL = '';
    
    const jwtToken = userContext.user ? userContext.user.accessToken : '';

    const deleteBodyRequest = async (url: string) => {
        const confirmed = window.confirm("Are you sure you want to delete this item?");
        if (!confirmed) {
            toast.error("Operation cancelled by user.");
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
            toast.error("Operation cancelled by user.");
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
            const data = bodyRequest ? convertFormDataToJson(formData) : formData;
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

            if (response.data.message) {
                
                toast.success(response.data.message);
            } else {
                toast.success("Operation successful");
            }
            return response.data;

        } catch (error) {
            if (axios.isAxiosError(error)) {
                toast.error(error.response?.data?.message || error.message);
            }
            else {
                toast.error("An error occurred");
            }
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
        BASE_URL
    };


}

  function convertFormDataToJson(formData: FormData): any {
    const obj: any = {};
  
    for (const [key, value] of formData.entries()) {
      setNestedValue(obj, key, value);
    }
  
    return obj;
  }
  
  function setNestedValue(obj: any, path: string, value: any) {
    const keys = path
      .replace(/\[(\w+)\]/g, '.$1')  // convert [0] to .0
      .replace(/^\./, '')            // strip leading dot
      .split('.');
  
    let current = obj;
  
    for (let i = 0; i < keys.length; i++) {
      const key = keys[i];
      const nextKey = keys[i + 1];
  
      const isLast = i === keys.length - 1;
      const isArrayIndex = /^\d+$/.test(nextKey || "");
  
      if (isLast) {
        if (key in current) {
          // Convert to array if multiple values under same key
          if (!Array.isArray(current[key])) {
            current[key] = [current[key]];
          }
          current[key].push(value);
        } else {
          current[key] = value;
        }
      } else {
        if (!(key in current)) {
          current[key] = isArrayIndex ? [] : {};
        }
        current = current[key];
      }
    }
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