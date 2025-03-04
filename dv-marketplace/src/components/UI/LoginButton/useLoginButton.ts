import { useContext, useEffect, useState } from "react";
import { UserContext } from "../../context/UserContextProvider";

export default function useLoginButton() {

    const userContext = useContext(UserContext);
    const [loginState, setLoginState] = useState("Log In");

    useEffect(() => {
        userContext.user !== null ? setLoginState("Log Out") : setLoginState("Log In");
    }, [userContext.user]);

    const handleLogButton = () => {
        userContext.user === null ? userContext.setShowLogin(true) : userContext.setUser(null);
    };

    return {
        userContext,
        loginState,
        setLoginState,
        handleLogButton
    };


}