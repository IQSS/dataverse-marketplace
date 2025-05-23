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
        console.error("Error installing external tool:", error);
      });

  }

  return {
    handleClose,
    hostnames,
    setHostnames,
    sendData,
  };
}