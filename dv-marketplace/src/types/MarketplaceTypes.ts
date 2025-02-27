
export interface Version {
  id: number;
  version: string;
  releaseNote: string;
  dataverseMinVersion: string;
  manifests: {
    manifestId: number;
    storedResourceId: number;
  }[];
}

export interface Image {
  id: number;
  storedResourceId: number;
}

export interface ExternalTool {
  id: number;
  name: string;
  description: string;
  versions: Version[];
  images: Image[];
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
};

