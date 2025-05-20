import React from 'react';
import { Alert, Button, Form } from "react-bootstrap";
import type { ExternalTool } from "../../../../types/MarketplaceTypes";
import { FormInputTextArea, FormInputTextField } from "../../../UI/FormInputFields";
import { InnerCardDeck } from "../../../UI/CardDeck";
import SectionHeader from "../../../UI/SectionHeader";
import useEditVersionForm from "./useEditVersionForm";
import EditVersionCard from "./EditVersionCard";
import EditManifestForm from './EditManifestForm';


const EditVersionForm = ({ tool }: { tool: ExternalTool | undefined }) => {
	const {
		handleVersionSubmit,
		addVersionFormIsOpen,
		setAddVersionFormIsOpen,
		emptyVersion,
		versionData,
		setVersionData,
        showManifestEdit,
        setShowManifestEdit,
        manifestForm,
        setManifestForm  		
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

					<p>Manifest Data:{" "}
						<button
							type="button"
							className="btn bi-pen px-0"
							onClick={() => {
								setShowManifestEdit(1);
							}}
						/>

						<EditManifestForm
							show={showManifestEdit === 1}
							initialManifest={manifestForm ?? undefined}
							onSave={(newManifest) => {
								setManifestForm(newManifest);
								setShowManifestEdit(0);
							}}
							onCancel={() => setShowManifestEdit(0)}
						/>
					</p>

					<Button variant="primary" type="submit">
						Save
					</Button>
					<Button variant="outline-secondary" className="ms-2"
						onClick={() => {
							setManifestForm(null);
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
