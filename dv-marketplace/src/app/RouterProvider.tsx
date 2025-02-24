import { createHashRouter } from "react-router-dom";
import MarketplaceHome from "../components/UI/MarketplaceHome/MarketplaceHome";
import Install from "../components/forms/Install";
import AddExtToolForm from "../components/forms/AddExternalToolForm/AddExtToolForm";
import AppIndex from "../components/pages/AppIndex";

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
          }
          
        ],
    },
  ]);

  export default router;