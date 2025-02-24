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

export interface ExternalTool {
  id: number;
  name: string;
  description: string;
  versions: Version[];
  imagesResourceId: number[];
}

