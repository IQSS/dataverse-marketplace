import { useState, useContext } from "react";
import { UserContext } from "../../context/UserContextProvider";
import axios from "axios";
import { toast } from "react-toastify";
import useMarketplaceApiRepo from "../../../repositories/useMarketplaceApiRepo";

export default function useSignupCustomForm() {
    const userContext = useContext(UserContext);
    const { BASE_URL } = useMarketplaceApiRepo();

    const [formData, setFormData] = useState({
        username: "",
        email: "",
        password: "",
        confirmPassword: ""
    });

    const handleClose = () => {
        userContext.setShowSignup(false);
        setFormData({ username: "", email: "", password: "", confirmPassword: "" });
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        // Client-side validation
        if (formData.password !== formData.confirmPassword) {
            toast.error("Passwords do not match");
            return;
        }

        if (formData.password.length < 5) {
            toast.error("Password must be at least 5 characters");
            return;
        }

        try {
            const response = await axios.post(`${BASE_URL}/api/auth/signup`, {
                username: formData.username,
                email: formData.email,
                password: formData.password
            });

            toast.success(response.data.message || "Account created successfully! You can now log in.");
            handleClose();

            // Optionally auto-open login modal
            userContext.setShowLogin(true);
        } catch (error: any) {
            if (axios.isAxiosError(error) && error.response) {
                toast.error(error.response.data.message || "Error creating account");
            } else {
                toast.error("Error creating account");
            }
        }
    };

    return {
        formData,
        handleChange,
        handleSubmit,
        handleClose
    };
}
