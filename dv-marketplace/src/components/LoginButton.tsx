import { useContext, useEffect, useState } from 'react';
import { UserContext } from './context/UserContextProvider';



const LoginButton = () => {
 
  const userContext = useContext(UserContext);
  const [loginState, setLoginState] = useState("Log In");

  useEffect(() => {
    userContext.user !== null ? setLoginState("Log Out") : setLoginState("Log In");
  }, [userContext.user]);

  const handleLogButton = () => {
    userContext.user === null ? userContext.setShow(true) : userContext.setUser(null);
  };

  return (
    <button id='loginButton' type="button" onClick={handleLogButton}>{loginState}</button>
  );
}

export default LoginButton;