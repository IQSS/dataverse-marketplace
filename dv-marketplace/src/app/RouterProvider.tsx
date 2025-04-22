import { createHashRouter } from "react-router-dom";
import MarketplaceHome from "../components/pages/MarketplaceHome/MarketplaceHome";
import AddExtToolForm from "../components/forms/AddExternalToolForm/AddExtToolForm";
import AppIndex from "../components/pages/AppIndex";
import ViewExternalTool from "../components/pages/ViewExternalTool";
import EditExtToolForm from "../components/forms/EditExternalToolForm/EditExtToolForm";
const router = createHashRouter([
    {
        path: '/',
        element: <AppIndex />,
        children: [
          {
            index: true,
            element: <MarketplaceHome />,
          },{
            path: 'addExtTool',
            element: <AddExtToolForm />,
          },{
            path: 'view/:id',
            element: <ViewExternalTool />,
          },{
            path: 'edit/:id',
            element: <EditExtToolForm />,
          }
        ],
    },
  ]);

  export default router;