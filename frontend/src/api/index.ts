export const BASE_URL =
  process.env.NODE_ENV === 'development'
    ? process.env.REACT_APP_DEV_URL
    : process.env.REACT_APP_BASE_URL;

const request = async (path: string, init?: RequestInit) => {
  const response = await fetch(`${BASE_URL}${path}`, {
    ...init,
    headers: {
      'Content-Type': 'application/json',
      ...init?.headers,
    },
  });

  if (!response.ok) {
    if (response.status === 401) {
      const isAdminRequest = path.startsWith('/admin');
      const expiredTokenKey = isAdminRequest ? 'admin-login-token' : 'login-token';
      localStorage.setItem(expiredTokenKey, '');
      const redirectUrl = `${location.origin}${isAdminRequest ? '/admin' : ''}`;
      if (!location.pathname.endsWith('/login')) {
        location.href = `${redirectUrl}/login`;
      }
    }

    throw new Error(response.status.toString());
  }
  return response;
};

export const api = {
  get: <T = unknown>(path: string, init?: RequestInit) =>
    request(path, init).then<T>((response) => response.json()),

  patch: <T = unknown>(path: string, init?: RequestInit, payload?: T) =>
    request(path, {
      headers: init?.headers,
      method: 'PATCH',
      body: JSON.stringify(payload),
    }),

  post: <T = unknown>(path: string, init?: RequestInit, payload?: T) =>
    request(path, {
      headers: init?.headers,
      method: 'POST',
      body: JSON.stringify(payload),
    }),

  delete: (path: string, init?: RequestInit) =>
    request(path, {
      headers: init?.headers,
      method: 'DELETE',
    }),
};

export const customerHeader = () => ({
  headers: {
    Authorization: `Bearer ${localStorage.getItem('login-token')}`,
  },
});

export const ownerHeader = () => ({
  headers: {
    Authorization: `Bearer ${localStorage.getItem('admin-login-token')}`,
  },
});
