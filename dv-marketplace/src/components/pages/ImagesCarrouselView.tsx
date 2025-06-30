import { forwardRef, useImperativeHandle, useState } from "react";
import { Image } from "../../types/MarketplaceTypes";
import { Modal } from "react-bootstrap";
import useMarketplaceApiRepo from "../../repositories/useMarketplaceApiRepo";

type ImagesCarrouselViewProps = {
    images: Image[] | undefined;
    selected?: number;
};

export type ModalRef = {
    open: (selected: number) => void;
    close: () => void;
}



const ImagesCarrouselView = forwardRef<ModalRef, ImagesCarrouselViewProps>(({ images, selected = 0 }, ref) => {

    const [show, setShow] = useState(false);
    const { getImageUrl } = useMarketplaceApiRepo();
    
    const [currentIndex, setCurrentIndex] = useState(selected);
    const handleClose = () => setShow(false);
    const handleShow = (display: number) => {
        setCurrentIndex(display);
        setShow(true);
    }
        

    useImperativeHandle(ref, () => ({
        open: handleShow,
        close: handleClose,
    }));

    return (
        <Modal
            show={show}
            onHide={handleClose}
            size="lg" 
            centered
        >
            <Modal.Header closeButton>
            <Modal.Title>Image Viewer</Modal.Title>
            </Modal.Header>
            <Modal.Body className="p-0 d-flex justify-content-center align-items-center" style={{ background: "#000" }}>
            <img
                src={getImageUrl(images?.[currentIndex]?.imageId ?? 0)}
                alt="Selected"
                className="img-fluid"
                style={{ maxHeight: "80vh", maxWidth: "100%", objectFit: "contain" }}
            />
            </Modal.Body>
        </Modal>
        
        
        
    )
});

export default ImagesCarrouselView;