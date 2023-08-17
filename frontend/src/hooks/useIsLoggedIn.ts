import { useState } from 'react';
import { getLocalStorage } from '../utils';

type Mode = 'owner' | 'customer';

export const useIsLoggedIn = (mode: Mode) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  if (mode === 'owner') {
    const adminLoginToken = getLocalStorage('admin-login-token', '');
    if (adminLoginToken !== '') {
      setIsLoggedIn(true);
    }
  }

  if (mode === 'customer') {
    const loginToken = getLocalStorage('login-token', '');
    if (loginToken !== '') {
      setIsLoggedIn(true);
    }
  }

  return { isLoggedIn };
};
