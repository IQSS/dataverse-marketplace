import { useContext, useEffect, useState } from "react";
import { UserContext } from "../../context/UserContextProvider";
import axios from "axios";
import { useParams } from "react-router-dom";
import { BASE_URL } from "/config";
import { ExternalTool } from "../../../types/MarketplaceTypes";


export default function useEditToolForm() {

    const userContext = useContext(UserContext);
    const jwtToken = userContext.user ? userContext.user.accessToken : '';
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [modalMessage, setModalMessage] = useState('');

    const { id } = useParams();

    const [tool, setTool] = useState<ExternalTool | null>(null);

    useEffect(() => {
        const fetchTool = async () => {
            try {
                const response = await axios.get(`${BASE_URL}/api/tools/${id}`);
                setTool(response.data as ExternalTool);
            } catch (error) {
                console.error("Error fetching the tool:", error);
            }
        };
        fetchTool();
    }, [id]);


    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);

        try {
            const response = await axios.post('${BASE_URL}/api/tools', formData, {
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
        closeModal,
        tool
    };

}