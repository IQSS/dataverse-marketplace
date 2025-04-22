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
    };
}