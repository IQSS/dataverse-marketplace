import React, { useContext, useState } from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';

import Search from './Search';
import ThemeSwitcher from './ThemeSwitcher';
import { UserContext } from './context/UserContextProvider';

const Navigation: React.FC = () => {
    
    const userContext = useContext(UserContext);

    const [show, setShow] = useState(false);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleClose = () => setShow(false);
    const handleShow = () => {
        console.log('userContext.user:', userContext.userRef.current);
        userContext.userRef.current === null ? setShow(true) : userContext.logout();
    }

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        const response = await fetch('http://localhost:8080/api/auth/signin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });
        if (response.ok) {
            const data = await response.json();
            userContext.login(data);
            console.log('Login successful');
        } else {
            
            console.error('Login failed');
        }
        handleClose();
    };

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
                            <button
                                style={{
                                    backgroundColor: '#c55b28',
                                    color: 'white',
                                    padding: '0rem 1rem',
                                    borderRadius: '0.5rem ',
                                    border: '1px solid lightgray',
                                    marginBottom: '1rem',
                                    marginLeft: '1rem'
                                }}
                                onClick={handleShow}
                            >
                                {userContext.user === null ? 'Logout' : 'Login'}
                            </button>
                            <ThemeSwitcher />
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Login</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group controlId="formUsername">
                            <Form.Label>Username</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Enter username"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                            />
                        </Form.Group>
                        <Form.Group controlId="formPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control
                                type="password"
                                placeholder="Enter password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </Form.Group>
                        <Button variant="primary" type="submit" style={{ marginTop: '1rem' }}>
                            Submit
                        </Button>
                    </Form>
                </Modal.Body>
            </Modal>
        </>
    );
}

export default Navigation;