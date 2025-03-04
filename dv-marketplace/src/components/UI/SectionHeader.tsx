import { Alert } from "react-bootstrap"

interface SectionHeaderProps {
    header: string;
    setAddFormIsOpen: (arg0: boolean) => void;
}


const SectionHeader = ({header, setAddFormIsOpen} : SectionHeaderProps) =>  {

    return (
        <Alert variant="light">
            <div className="container ">
                <div className="row">
                    <h3 className="col-6">{header}</h3>
                    <div className="col-6 d-flex justify-content-end align-items-center">
                        <button type="button"
                            className="btn btn-secondary bi-plus"
                            onClick={() => setAddFormIsOpen(true)}>Add new
                        </button>
                    </div>
                </div>
            </div>
        </Alert>
    )
}

export default SectionHeader;