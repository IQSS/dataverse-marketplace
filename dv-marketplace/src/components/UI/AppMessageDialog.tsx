import { Modal } from "react-bootstrap";
import { UserContext } from "../context/UserContextProvider";
import { useContext } from "react";

const AppMessageDialog = () => {

  const userContext = useContext(UserContext);
  return (<>
    <Modal show={userContext.showMessage} >
        <Modal.Header closeButton>
            <Modal.Title>{userContext.modalTitle}</Modal.Title>
        </Modal.Header>
        <Modal.Body>{userContext.modalMessage}</Modal.Body>
        <Modal.Footer>
            <button type="button" className="btn btn-secondary" onClick={() => userContext.setShowMessage(false)}>Close</button>
        </Modal.Footer>
    </Modal>
    </>
  );
};

export default AppMessageDialog;