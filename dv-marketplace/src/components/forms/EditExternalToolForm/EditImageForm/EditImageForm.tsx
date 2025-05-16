import { Alert, Button, Form } from "react-bootstrap";
import useEditImageForm from "./useEditImageForm";
import { InnerCardDeck } from "../../../UI/CardDeck";
import MarketplaceCard from "../../../UI/MarketplaceCard";
import type { ExternalTool, Image } from "../../../../types/MarketplaceTypes";
import { useContext } from "react";
import { UserContext } from "../../../context/UserContextProvider";
import SectionHeader from "../../../UI/SectionHeader";


const EditImageForm = ({ tool }: { tool: ExternalTool | undefined }) => {

    const {
        addImageFormIsOpen,
        setAddImageFormIsOpen,
        handleImageSubmit,
        handleImageDelete,
        images
    } = useEditImageForm({ tool });

    const userContext = useContext(UserContext);


    return (
        <>
            <SectionHeader header="Images:" setAddFormIsOpen={setAddImageFormIsOpen} />

            <Alert variant='info' show={addImageFormIsOpen}>
                <Form onSubmit={handleImageSubmit} encType="multipart/form-data">
                    <Form.Group className="mb-3">
                        <Form.Label>Image</Form.Label>
                        <Form.Control type="file" name="images" multiple />
                    </Form.Group>
                    <Button variant="primary" type="submit" >
                        Save
                    </Button>
                    <Button variant="outline-secondary" className="ms-2"
                        onClick={() => setAddImageFormIsOpen(false)}>
                        Cancel
                    </Button>
                </Form>
            </Alert>

            <InnerCardDeck>

                {images.map((image: Image) => (
                    <MarketplaceCard
                        key={image.imageId}
                        imageId={image.storedResourceId}>
                        {userContext.user &&
                            <button type='button' className='btn bi-trash px-0' onClick={() => handleImageDelete(image.storedResourceId)}><span /></button>
                        }
                    </MarketplaceCard>
                ))}
            </InnerCardDeck>





        </>
    );
};

export default EditImageForm;