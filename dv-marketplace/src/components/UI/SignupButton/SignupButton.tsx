import useSignupButton from "./useSignupButton";
import { useContext } from "react";
import { UserContext } from "../../context/UserContextProvider";

export default function SignupButton() {
    const { handleSignupButton, registrationEnabled } = useSignupButton();
    const userContext = useContext(UserContext);

    // Only show if not logged in and registration is enabled
    if (userContext.user || !registrationEnabled) {
        return null;
    }

    return (
        <button id="signupButton" type="button" onClick={handleSignupButton}>
            Sign Up
        </button>
    );
}
