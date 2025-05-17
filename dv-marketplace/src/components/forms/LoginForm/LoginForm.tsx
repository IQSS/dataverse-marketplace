import { Button, Form, Modal } from "react-bootstrap";
import useLoginCustomForm from "./useLoginCustomForm";

const LoginForm = () => {
	const {
		username,
		setUsername,
		password,
		setPassword,
		userContext,
		handleClose,
		handleSubmit,
	} = useLoginCustomForm();

	return (
		<Modal show={userContext.showLogin} onHide={handleClose}>
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
					<Button variant="primary" type="submit" style={{ marginTop: "1rem" }}>
						Submit
					</Button>
				</Form>
			</Modal.Body>
		</Modal>
	);
};

export default LoginForm;
