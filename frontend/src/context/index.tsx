import { createContext, PropsWithChildren, useState } from 'react';

const INVALID_CAFE_ID = -1;

interface CafeContextValue {
  cafeId: number;
  setAdminCafeId: (id: number) => void;
}

const CafeContext = createContext<CafeContextValue | null>(null);

export const CafeIdProvider = ({ children }: PropsWithChildren) => {
  const [cafeId, setCafeId] = useState(INVALID_CAFE_ID);

  const setAdminCafeId = (id: number) => {
    setCafeId(id);
  };

  return <CafeContext.Provider value={{ cafeId, setAdminCafeId }}>{children}</CafeContext.Provider>;
};
