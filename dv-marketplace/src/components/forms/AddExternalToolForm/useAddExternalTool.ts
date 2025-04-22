import { useContext, useState } from "react";
import { UserContext } from "../../context/UserContextProvider";
import axios from "axios";
import useMarketplaceApiRepo from "../../../repositories/useMarketplaceApiRepo";


export default function useAddExternalTool() {

    const userContext = useContext(UserContext);
    const jwtToken = userContext.user ? userContext.user.accessToken : '';
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [modalMessage, setModalMessage] = useState('');
    const { BASE_URL } = useMarketplaceApiRepo();

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);

        try {
            const response = await axios.post(`${BASE_URL}/api/tools`, formData, {
                headers: {
                    'Authorization': `Bearer ${jwtToken}`
                }
            });
            console.log(response.data);
            setModalMessage("Tool added successfully");
        } catch (error) {
            if (axios.isAxiosError(error)) {
                setModalMessage(error.response?.data?.message || error.message);
            } else {
                setModalMessage(String(error));
            }
        } finally {
            setModalIsOpen(true);
        }
    };

    const closeModal = () => {
        setModalIsOpen(false);
    };

    return {
        userContext,
        handleSubmit,
        modalIsOpen,
        modalMessage,
        closeModal
    };

}