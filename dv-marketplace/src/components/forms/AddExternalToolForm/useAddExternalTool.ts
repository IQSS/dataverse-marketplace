import { useContext } from "react";
import { UserContext } from "../../context/UserContextProvider";
import axios from "axios";
import useMarketplaceApiRepo from "../../../repositories/useMarketplaceApiRepo";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

export default function useAddExternalTool() {

    const userContext = useContext(UserContext);
    const jwtToken = userContext.user ? userContext.user.accessToken : '';
    const { BASE_URL } = useMarketplaceApiRepo();
    const navigate = useNavigate();

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);

       
        await axios.post(`${BASE_URL}/api/tools`, formData, {
            headers: {
                'Authorization': `Bearer ${jwtToken}`
            }
        }).then(() => {
            toast.success("Tool added successfully");
            navigate("/");
        }).catch((error) => {
            if (error.response.data.message) {
                toast.error(error.response.data.message);
            } else {
                toast.error("An error occurred");
            }
        });

        


            
            
    };

   

    return {
        userContext,
        handleSubmit
    };

}