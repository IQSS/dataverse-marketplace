import { useEffect, useState } from "react";
import type { Manifest } from "../../types/MarketplaceTypes";
import axios from "axios";

import { toast } from "react-toastify";

export default function useInstallExToolFrame(
  { setShowModal }: { setShowModal?: (show: boolean) => void } = {}
) {

  const handleClose = () => setShowModal?.(false);
  const [hostnames, setHostnames] = useState<string[]>([]);

  useEffect(() => {
    const fetchHostnames = async () => {
      try {
        const response = await axios.get(
          "https://hub.dataverse.org/api/installation",
          { timeout: 5000 } // 5 second timeout
        );
        interface Installation {
          hostname: string;
        }
        const fetchedHostnames = response.data.map(
          (installation: Installation) => installation.hostname,
        );
        setHostnames((prevHostnames) => [...fetchedHostnames, "localhost:8080", ...prevHostnames]);
      } catch (error) {
        console.warn("Could not fetch hostnames from hub.dataverse.org:", error);
        // Silently fail - just use localhost as default
        setHostnames(["localhost:8080"]);
      }
    };

    fetchHostnames();
  }, []);


  const sendData = (
    manifest: Manifest | undefined,
    formData: { hostname: string; apiKey: string; useHttps: boolean }
  ): void => {
    const { hostname, apiKey, useHttps } = formData;
    const protocol = useHttps ? "https" : "http";
    const url = `${protocol}://${hostname}/api/externalTools?key=${apiKey}`;

    const json = JSON.parse(JSON.stringify(manifest, null, 2));

    axios.post(url, json, {
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then(() => {
        toast.success("External Tool installed successfully.");
        handleClose();
      })
      .catch((error) => {
        toast.error(`Error installing External Tool: ${error.response?.data?.message || error.message}`);        
      });

  }

  return {
    handleClose,
    hostnames,
    setHostnames,
    sendData,
  };
}