import { ErrorBoundary } from "react-error-boundary";
import UserContextProvider from "../context/UserContextProvider";
import ErrorView from "../UI/ErrorView";
import { Outlet } from "react-router-dom";
import Navigation from '../UI/Navigation/NavigationBar';

function ErrorFallback({ error, resetErrorBoundary }: { error: Error; resetErrorBoundary: () => void }) {
  return <ErrorView error={error} resetErrorBoundary={resetErrorBoundary} />;
};

const AppIndex = () => {
  return (
        <ErrorBoundary FallbackComponent={ErrorFallback}>
          <UserContextProvider>
            <Navigation />
            <ErrorBoundary FallbackComponent={ErrorFallback}>
              <Outlet />
            </ErrorBoundary>
          </UserContextProvider>
        </ErrorBoundary>
  );
};

export default AppIndex;