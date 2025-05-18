import React from 'react';
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
		emptyVersion,
        versionData,
        setVersionData		
	} = useEditVersionForm({ tool });

	const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
		const { name, value } = e.target;
		setVersionData(prev => ({ ...prev, [name]: value }));
	};

	return (
		<>
			<SectionHeader header="Versions:" setAddFormIsOpen={setAddVersionFormIsOpen} />

			<Alert variant="info" show={addVersionFormIsOpen}>
				<Form onSubmit={handleVersionSubmit} encType="multipart/form-data">
					<FormInputTextField label="Version" name="version" id="version" value={versionData.version} onChange={handleChange} />
					<FormInputTextArea label="Release Note" name="releaseNote" id="releaseNote" value={versionData.releaseNote} onChange={handleChange} />
					<FormInputTextField label="DV Min Version" name="dvMinVersion" id="dvMinVersion" value={versionData.dvMinVersion} onChange={handleChange} />
					<Button variant="primary" type="submit">
						Save
					</Button>
					<Button variant="outline-secondary" className="ms-2"
						onClick={() => {
							setVersionData(emptyVersion);
							setAddVersionFormIsOpen(false);
						}}>
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
