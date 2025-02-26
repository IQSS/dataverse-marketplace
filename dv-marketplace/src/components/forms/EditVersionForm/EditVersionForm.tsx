import Modal from 'react-bootstrap/Modal';
import useEditVersionForm from './useEditVersionForm';
import { FormInputTextField, FormInputTextArea } from '../../UI/FormInputFields';

const EditExtToolForm = () => {
    

    const {
        handleSubmit,
        modalIsOpen,
        modalMessage,
        closeModal,
        tool
    } = useEditVersionForm();

    return (
        <div className="container">
            <h1>Edit: {tool?.name}</h1>
            <form onSubmit={handleSubmit} encType="multipart/form-data">
                <FormInputTextField label="Tool Name" name="name" id="toolName" value={tool?.name} />
                <FormInputTextArea label="Description" name="description" id="toolDescription" value={tool?.description} />
                <button type="submit" className="btn btn-primary">Submit</button>
            </form>   
            <hr/>
            <h1>Edit Versions:</h1>
            <button type="button" className="btn btn-primary bi-plus"><span/></button>
            <ul>
                <li>Version: {tool?.versions[0].version} 
                    <a href={tool?.versions[0].id} className='btn btn-primary bi-pen px-0'><span/></a>
                    <a href={tool?.versions[0].id} className='btn btn-danger bi-trash px-0'><span/></a>
                </li>
            </ul>
            <h1>Edit Images:</h1>
            <a href={tool?.imagesResourceId[0]} className='btn btn-primary bi-plus'><span/></a>
            <ul>
                <li>
                    {tool?.name}
                    <a href={tool?.imagesResourceId[0]} className='btn btn-primary bi-pen px-0'><span/></a>
                    <a href={tool?.imagesResourceId[0]} className='btn btn-danger bi-trash px-0'><span/></a>
                </li>
            </ul>

            <Modal show={modalIsOpen} onHide={closeModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Response</Modal.Title>
                </Modal.Header>
                <Modal.Body>{modalMessage}</Modal.Body>
                <Modal.Footer>
                    <button type="button" className="btn btn-secondary" onClick={closeModal}>Close</button>
                </Modal.Footer>
            </Modal>
        </div>
    );
};

export default EditExtToolForm;
