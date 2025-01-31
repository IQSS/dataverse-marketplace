import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

import Search from './Search';
import ThemeSwitcher from './ThemeSwitcher';

const Navigation: React.FC = () =>  {
    return (
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
                    <Nav className="ms-auto d-flex align-items-right" >
                        <Search />
                        <button  style={{ backgroundColor: '#c55b28', 
                            color: 'white', 
                            padding: '0rem 1rem', 
                            borderRadius: '0.5rem ',
                            border: '1px solid lightgray',
                            marginBottom: '1rem',
                            marginLeft: '1rem'
                        }}>Login</button>
                        <ThemeSwitcher /> 
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default Navigation;