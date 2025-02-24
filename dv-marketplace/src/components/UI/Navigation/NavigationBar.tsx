import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Search from './Search';
import ThemeSwitcher from './ThemeSwitcher/ThemeSwitcher';
import LoginForm from '../../forms/LoginForm/LoginForm';
import LoginButton from '../LoginButton/LoginButton';
import NavOptions from './NavOptions';

const NavigationBar = () => {

    return (
        <>
            <Navbar expand="lg" className="bg-body-tertiary">
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
                            <Search />
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