import { useContext, useState } from "react";
import { Button, Form, Modal } from "react-bootstrap";
import { UserContext } from "./context/UserContextProvider";






const LoginForm = () => {

    const userContext = useContext(UserContext);
    const handleClose = () => userContext.setShow(false);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        const response = await fetch('http://localhost:8081/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });
        if (response.ok) {
            const data = await response.json();
            //userContext.login(data);
            userContext.setUser(data)
            console.log('Login successful');
        } else {
            
            console.error('Login failed');
        }
        handleClose();
    };

    return (
        <Modal show={userContext.show} onHide={handleClose}>
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
    );
};

export default LoginForm;