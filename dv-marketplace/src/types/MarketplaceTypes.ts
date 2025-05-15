import { types } from "storybook/internal/babel";

export interface Version {
  id: number;
  version: string;
  releaseNote: string;
  dataverseMinVersion: string;
  manifests: Manifest[];
}

export interface Manifest {
  [key: string]: any; // Add index signature
  manifestId: number;  
  displayName: string;
  description: string;
  scope: string;
  toolUrl: string;
  httpMethod: string;
  toolName: string;  
  contentType: string;
  contentTypes: string[];
  types: string[];
  toolParameters: {
    queryParameters: { [key: string]: string }[];
  manifestSet: Manifest[];
  };
}

export interface Image {
  imageId: number;
  storedResourceId: number;
}

export interface ExternalTool {
  id: number;
  name: string;
  description: string;
  versions: Version[];
  images: Image[];
  owner: User;
}

export type User = {
    id: number;
    username: string;
    email: string;
    roles: string[];
    accessToken: string;
    tokenType: string;
};

export type UserContextType = {
    user: User | null;
    setUser: (user: User | null) => void;
    showLogin: boolean;
    setShowLogin: (show: boolean) => void;
    showMessage: boolean;
    setShowMessage: (show: boolean) => void;
    modalMessage: string;
    setModalMessage: (message: string) => void;
    modalTitle: string;
    setModalTitle: (title: string) => void;
    theme: Theme;
    setTheme: (theme: Theme) => void;
};

export enum Theme {
    LIGHT = "light",
    DARK = "dark",
    AUTO = "auto"
}

