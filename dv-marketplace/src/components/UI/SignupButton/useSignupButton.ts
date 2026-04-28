import { useContext, useEffect, useState } from "react";
import { UserContext } from "../../context/UserContextProvider";
import axios from "axios";
import useMarketplaceApiRepo from "../../../repositories/useMarketplaceApiRepo";

export default function useSignupButton() {
    const userContext = useContext(UserContext);
    const { BASE_URL } = useMarketplaceApiRepo();
    const [registrationEnabled, setRegistrationEnabled] = useState(true);

    useEffect(() => {
        const fetchRegistrationStatus = async () => {
            try {
                const response = await axios.get(`${BASE_URL}/api/auth/registration-status`);
                setRegistrationEnabled(response.data.enabled);
            } catch (error) {
                console.warn("Could not fetch registration status:", error);
                // Default to enabled if fetch fails
                setRegistrationEnabled(true);
            }
        };

        fetchRegistrationStatus();
    }, [BASE_URL]);

    const handleSignupButton = () => {
        userContext.setShowSignup(true);
    };

    return {
        handleSignupButton,
        registrationEnabled
    };
}
