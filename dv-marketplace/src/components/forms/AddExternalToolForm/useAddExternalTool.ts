import { useContext, useState } from "react";
import { UserContext } from "../../context/UserContextProvider";
import axios from "axios";
import useMarketplaceApiRepo from "../../../repositories/useMarketplaceApiRepo";
import { toast } from "react-toastify";

export default function useAddExternalTool() {

    const userContext = useContext(UserContext);
    const jwtToken = userContext.user ? userContext.user.accessToken : '';
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [modalMessage, setModalMessage] = useState('');
    const { BASE_URL } = useMarketplaceApiRepo();

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);

       
        const response = await axios.post(`${BASE_URL}/api/tools`, formData, {
            headers: {
                'Authorization': `Bearer ${jwtToken}`
            }
        }).then(() => {
            toast.success("Tool added successfully");
        }).catch((error) => {
            if (error.response) {
                toast.error(error.response.data.message);
            } else {
                toast.error("An error occurred");
            }
        });
            
            
    };

   

    return {
        userContext,
        handleSubmit,
        modalIsOpen,
        modalMessage
    };

}