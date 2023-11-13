import { PropsWithChildren, createContext, useState } from 'react';

interface AdminAccessTokenContextProps {
  adminAccessToken: string;
  setAdminAccessToken: (token: string) => void;
}

export const AdminAccessTokenContext = createContext<AdminAccessTokenContextProps>({
  adminAccessToken: '',
  setAdminAccessToken: (token) => {
    console.warn(
      `this token setter is invalid. it's seem to initialize function.\n token value : ${
        !token ? 'empty' : token
      }`,
    );
  },
});

export const AdminAccessTokenProvider = ({ children }: PropsWithChildren) => {
  const [adminAccessToken, setAdminAccessToken] = useState('');
  const setAdminAccessTokenWrapper = (token: string) => {
    setAdminAccessToken(token);
  };

  return (
    <AdminAccessTokenContext.Provider
      value={{ adminAccessToken, setAdminAccessToken: setAdminAccessTokenWrapper }}
    >
      {children}
    </AdminAccessTokenContext.Provider>
  );
};
