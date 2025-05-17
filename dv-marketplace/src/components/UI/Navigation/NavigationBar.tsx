import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { useContext } from 'react';
import { UserContext } from '../../../components/context/UserContextProvider';
import ThemeSwitcher from './ThemeSwitcher/ThemeSwitcher';
import LoginForm from '../../forms/LoginForm/LoginForm';
import LoginButton from '../LoginButton/LoginButton';
import NavOptions from './NavOptions';
import {toast} from 'react-toastify';
import { ToastContainer } from "react-toastify";
import { Theme } from "../../../types/MarketplaceTypes";

const NavigationBar = () => {

    const userContext = useContext(UserContext);

    return (
        <>
            <Navbar expand="lg" className="bg-body-tertiary">
            <ToastContainer theme={userContext.theme === Theme.DARK ? "dark" : "light"} />
                <Container>
                    <Navbar.Brand href="/">
                        <span className='header-logo '>
                            <span />  Dataverse Marketplace
                        </span>
                    </Navbar.Brand>
                    <Navbar.Toggle />
                    <Navbar.Collapse >
                        <Nav className="nav-options ">
                            <NavOptions />
                            {/* <Search /> */}
                            <LoginButton />
                            <ThemeSwitcher />
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
            <LoginForm />
        </>
    );
}

export default NavigationBar;