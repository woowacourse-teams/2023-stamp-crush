import { PropsWithChildren, createContext, useState } from 'react';
import { ownerInstance } from '../api/axios';

interface AdminAccessTokenContextProps {
  setAdminAccessToken: (token: string) => void;
}

export const AdminAccessTokenContext = createContext<AdminAccessTokenContextProps>({
  setAdminAccessToken: (token) => {
    console.warn(
      `this token setter is invalid. it's seem to initialize function.\n token value : ${
        !token ? 'empty' : token
      }`,
    );
  },
});

export const AdminAccessTokenProvider = ({ children }: PropsWithChildren) => {
  const setAdminAccessToken = (token: string) => {
    ownerInstance.defaults.headers.common.Authorization = `Bearer ${token}`;
  };

  return (
    <AdminAccessTokenContext.Provider value={{ setAdminAccessToken }}>
      {children}
    </AdminAccessTokenContext.Provider>
  );
};
