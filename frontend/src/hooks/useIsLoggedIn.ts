import { useState } from 'react';
import { getLocalStorage } from '../utils';

type Mode = 'owner' | 'customer';

export const useIsLoggedIn = (mode: Mode) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  if (mode == 'owner') {
    const adminLoginToken = localStorage.getItem('admin-login-token');
    console.log('! ', adminLoginToken);
    if (adminLoginToken !== '') {
      setIsLoggedIn(true);
      return { isLoggedIn };
    }
  }

  const loginToken = getLocalStorage('login-token', '');
  if (loginToken !== '') {
    setIsLoggedIn(true);
    return { isLoggedIn };
  }

  return { isLoggedIn };
};
