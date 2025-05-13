import { useContext, useState } from "react";
import type { ExternalTool, Manifest } from "../../types/MarketplaceTypes";
import { useParams } from "react-router-dom";
import useMarketplaceApiRepo from "../../repositories/useMarketplaceApiRepo";
import { UserContext } from "../context/UserContextProvider";

export default function useViewExternalTool() {

    const [showModal, setShowModal] = useState(false);
    const { id } = useParams();
    const [tool, setTool] = useState<ExternalTool | undefined>();
    const userContext = useContext(UserContext);
    const { BASE_URL } = useMarketplaceApiRepo();
    const [toolToInstall, setToolToInstall] = useState<Manifest | undefined>();

    const downloadManifest = (subManifest: Manifest): void => {
        const blob = new Blob([JSON.stringify(subManifest, null, 2)], { type: 'application/json' });
        const url = URL.createObjectURL(blob);
      
        const link = document.createElement('a');
        link.href = url;
        link.download  = (subManifest?.toolName ?? 'manifest') + "__" + subManifest?.contentType + ".json";
        link.click();
      
        URL.revokeObjectURL(url);
    }

    return {
        showModal,
        setShowModal,
        id,
        tool,
        setTool,
        userContext,
        BASE_URL,
        toolToInstall,
        setToolToInstall,
        downloadManifest
    };
}