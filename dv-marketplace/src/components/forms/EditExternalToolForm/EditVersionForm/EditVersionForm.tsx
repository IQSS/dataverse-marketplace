import useEditVersionForm from "./useEditVersionForm";
import { Alert, Button, Form } from "react-bootstrap";
import type { ExternalTool } from "../../../../types/MarketplaceTypes";
import {
	FormInputTextArea,
	FormInputTextField,
} from "../../../UI/FormInputFields";
import { InnerCardDeck } from "../../../UI/CardDeck";
import SectionHeader from "../../../UI/SectionHeader";
import EditVersionCard from "./EditVersionCard";

const EditVersionForm = ({ tool }: { tool: ExternalTool | undefined }) => {
	const {
		handleVersionSubmit,
		addVersionFormIsOpen,
		setAddVersionFormIsOpen,
	} = useEditVersionForm({ tool });

	return (
		<>
			<SectionHeader header="Versions:" setAddFormIsOpen={setAddVersionFormIsOpen} />

			<Alert variant="info" show={addVersionFormIsOpen}>
				<Form onSubmit={handleVersionSubmit} encType="multipart/form-data">
					<FormInputTextArea
						label="Release Note"
						name="releaseNote"
						id="releaseNote"
					/>
					<FormInputTextField
						label="DV Min Version"
						name="dvMinVersion"
						id="dvMinVersion"
					/>
					<FormInputTextField label="Version" name="version" id="version" />
					<Form.Group className="mb-3" controlId="formBasicEmail">
						<Form.Label>Manifests</Form.Label>
						<Form.Control type="file" name="jsonData" multiple />
					</Form.Group>
					<Button variant="primary" type="submit">
						Save
					</Button>
					<Button variant="outline-secondary" className="ms-2"
						onClick={() => setAddVersionFormIsOpen(false)}>
						Cancel
					</Button>
				</Form>
			</Alert>
			<ul>
				<InnerCardDeck>
					{tool?.versions.map((version) => (
						<EditVersionCard
							key={version.id}
							version={version}
							tool={tool}
						/>
					))}
				</InnerCardDeck>
			</ul>
		</>
	);
};

export default EditVersionForm;
