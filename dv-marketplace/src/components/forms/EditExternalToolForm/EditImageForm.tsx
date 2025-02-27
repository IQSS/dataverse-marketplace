import { Alert, Button, Form } from "react-bootstrap";
import useEditImageForm from "./useEditImageForm";
import { InnerCardDeck } from "../../UI/CardDeck";
import MarketplaceCard from "../../UI/MarketplaceCard";
import type { ExternalTool, Image } from "../../../types/MarketplaceTypes";


const EditImageForm = ({ tool }: { tool: ExternalTool | undefined }) => {

    const {
        addImageFormIsOpen,
        setAddImageFormIsOpen,
        handleImageSubmit,
        handleImageDelete
    } = useEditImageForm({ tool });

    return (
        <>
        <Alert variant='light'>
            <div className='container '>
                <div className='row'>
                <h3 className='col-6'>Images:</h3>
                <div className='col-6 d-flex justify-content-end align-items-center'>
                    <button type='button' className='btn btn-secondary bi-plus' onClick={() => setAddImageFormIsOpen(true)}> Add new </button>       
                </div>
                </div>
            </div>
        </Alert>

        <Alert variant='info' show={addImageFormIsOpen}>
            <Form onSubmit={handleImageSubmit} encType="multipart/form-data">
                <Form.Group className="mb-3" controlId="formBasicEmail">
                    <Form.Label>Image</Form.Label>
                    <Form.Control type="file" name="images" multiple/>
                </Form.Group>
                <Button variant="primary" type="submit" >
                    Submit
                </Button>
            </Form>
        </Alert>

        <InnerCardDeck>

            {tool?.images.map((image: Image) => (
                <MarketplaceCard
                    key={image.id}
                    imageId={image.storedResourceId}>
                    <button type='button' className='btn bi-trash px-0' onClick={() => handleImageDelete(image.storedResourceId)}><span/></button>
                </MarketplaceCard>
            ))}  
        </InnerCardDeck>

       
        </>
    );
};

export default EditImageForm;