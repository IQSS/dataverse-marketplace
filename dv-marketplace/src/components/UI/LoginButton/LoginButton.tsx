import useLoginButton from './useLoginButton';

const LoginButton = () => {
 
  const { loginState,
          handleLogButton} = useLoginButton();

  return (
    <button id='loginButton' type="button" onClick={handleLogButton}>{loginState}</button>
  );
}

export default LoginButton;