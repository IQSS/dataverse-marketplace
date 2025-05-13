import { useContext, useEffect, useState } from "react";
import type { Manifest } from "../../types/MarketplaceTypes";
import axios from "axios";
import useMarketplaceApiRepo from "../../repositories/useMarketplaceApiRepo";
import { UserContext } from "../context/UserContextProvider";

export default function useInstallExToolFrame(
    {setShowModal}: {setShowModal?: (show: boolean) => void} = {}
) {

    const handleClose = () => setShowModal?.(false);
    const [hostnames, setHostnames] = useState<string[]>([]);
    const { BASE_URL } = useMarketplaceApiRepo();
    const userContext = useContext(UserContext);

    useEffect(() => {
        const fetchHostnames = async () => {
          try {
            const response = await axios.get(
              "https://hub.dataverse.org/api/installation",
            );
            interface Installation {
              hostname: string;
            }
            const fetchedHostnames = response.data.map(
              (installation: Installation) => installation.hostname,
            );
            setHostnames(fetchedHostnames);        
          } catch (error) {
            console.error("Error fetching hostnames:", error);
          }
          setHostnames((prevHostnames) => ["localhost:8080", ...prevHostnames]);
        };
    
        fetchHostnames();
      }, []);

    const sendData = (manifest: Manifest | undefined): void => {
        const form = document.querySelector("#installForm") as HTMLFormElement;

        const formData = new FormData(form);
        const hostname = formData.get("hostname") as string;
        const apiKey = formData.get("apiKey") as string;
        const useHttps = formData.get("useHttps") === "on";        
        const protocol = useHttps ? "https" : "http";
        const url = `${protocol}://${hostname}/api/externalTools?key=${apiKey}`;

        console.log(manifest?.storedResourceId)
        console.log("Sending data to:", url);

        const blob =JSON.parse(JSON.stringify(manifest, null, 2));


        axios.get(`${BASE_URL}/api/tools/${manifest?.toolId}/versions/${manifest?.versionId}/manifests/${manifest?.manifestId}`)
        .then((response) => {
          console.log(response.data);
          console.log("--------------------")
          console.log(blob);
            axios.post(url, response.data, {
                headers: {
                "Content-Type": "application/json",
                },
            })
            .then(() => {
                userContext.setModalTitle("Success");
                userContext.setShowMessage(true);
                userContext.setModalMessage("External Tool installed successfully.");
                handleClose();
            })
            .catch((error) => {
                userContext.setModalTitle(error.code || "Error");
                userContext.setModalMessage(error.message || "Error installing external tool.");
                userContext.setShowMessage(true);                
            });
        })
        .catch((error) => {
            userContext.setModalTitle(error.code || "Error");
            userContext.setModalMessage(error.message || "Error installing external tool.");
            userContext.setShowMessage(true);
        });

    }

    return {
        handleClose,
        hostnames,
        setHostnames,
        sendData,
    };
}