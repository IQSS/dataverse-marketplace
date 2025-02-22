import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Search from './Search';
import ThemeSwitcher from './ThemeSwitcher';
import LoginForm from './LoginForm';
import LoginButton from './LoginButton';

const Navigation: React.FC = () => {

    return (
        <>
            <Navbar expand="lg" className="bg-body-tertiary">
                <Container>
                    <Navbar.Brand href="/">
                        <span className='d-inline-flex align-items-center' style={{ color: '#C55B28' }}>
                            <img src="./assets/img/dv.xhtml" alt="Dataverse Marketplace Logo" style={{ marginRight: '8px' }} />
                            Dataverse Marketplace
                        </span>
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="ms-auto d-flex align-items-right">
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

export default Navigation;