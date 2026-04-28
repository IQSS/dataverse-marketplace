import { Modal, Button, Form } from "react-bootstrap";
import { useContext } from "react";
import { UserContext } from "../../context/UserContextProvider";
import useSignupCustomForm from "./useSignupCustomForm";

export default function SignupForm() {
    const userContext = useContext(UserContext);
    const { formData, handleChange, handleSubmit, handleClose } = useSignupCustomForm();

    return (
        <Modal show={userContext.showSignup} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Create Account</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-3">
                        <Form.Label>Username</Form.Label>
                        <Form.Control
                            type="text"
                            name="username"
                            placeholder="Enter username"
                            value={formData.username}
                            onChange={handleChange}
                            required
                            minLength={3}
                            maxLength={20}
                        />
                        <Form.Text className="text-muted">
                            3-20 characters
                        </Form.Text>
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Email</Form.Label>
                        <Form.Control
                            type="email"
                            name="email"
                            placeholder="Enter email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                            maxLength={50}
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Password</Form.Label>
                        <Form.Control
                            type="password"
                            name="password"
                            placeholder="Password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                            minLength={5}
                            maxLength={40}
                        />
                        <Form.Text className="text-muted">
                            At least 5 characters
                        </Form.Text>
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Confirm Password</Form.Label>
                        <Form.Control
                            type="password"
                            name="confirmPassword"
                            placeholder="Confirm password"
                            value={formData.confirmPassword}
                            onChange={handleChange}
                            required
                        />
                    </Form.Group>

                    <Button variant="primary" type="submit" className="w-100">
                        Create Account
                    </Button>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <div className="w-100 text-center">
                    Already have an account?{" "}
                    <Button
                        variant="link"
                        onClick={() => {
                            handleClose();
                            userContext.setShowLogin(true);
                        }}
                    >
                        Log In
                    </Button>
                </div>
            </Modal.Footer>
        </Modal>
    );
}
