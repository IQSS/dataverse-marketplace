import { createHashRouter } from "react-router-dom";
import MarketplaceHome from "../components/pages/MarketplaceHome/MarketplaceHome";
import Install from "../components/forms/Install";
import AddExtToolForm from "../components/forms/AddExternalToolForm/AddExtToolForm";
import AppIndex from "../components/pages/AppIndex";
import ViewExternalTool from "../components/pages/ViewExternalTool";
import EditExtToolForm from "../components/forms/EditExternalToolForm/EditExtToolForm";
import EditVersionForm from "../components/forms/EditExternalToolForm/EditVersionForm/EditVersionForm";
const router = createHashRouter([
    {
        path: '/',
        element: <AppIndex />,
        children: [
          {
            index: true,
            element: <MarketplaceHome />,
          },{
            path: 'install',
            element: <Install />,
          },{
            path: 'addExtTool',
            element: <AddExtToolForm />,
          },{
            path: 'view/:id',
            element: <ViewExternalTool />,
          },{
            path: 'edit/:id',
            element: <EditExtToolForm />,
          }, {
            path: 'edit/tools/:id/version/:versionId',
            element: <EditVersionForm />,
          }
          
        ],
    },
  ]);

  export default router;