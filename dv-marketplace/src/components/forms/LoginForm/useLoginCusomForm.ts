import { useContext, useState } from "react";
import { UserContext } from "../../context/UserContextProvider";
import axios from "axios";




export default function useLoginCustomForm() {

    const userContext = useContext(UserContext);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleClose = () => {
        userContext.setShow(false)
    };
    

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        try {
            const response = await axios.post('http://localhost:8081/api/auth/login', { username, password });
            userContext.setUser(response.data);
            console.log('Login successful');
        } catch (error) {
            console.error('Login failed', error);
        }
        handleClose();
    };

    return {
        username,
        setUsername,
        password,
        setPassword,
        userContext,
        handleClose,
        handleSubmit
    };
}