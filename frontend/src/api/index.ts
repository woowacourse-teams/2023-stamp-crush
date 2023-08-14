import { worker } from '../mocks/browser';

const request = async (path: string, init?: RequestInit) => {
  const BASE_URL = process.env.REACT_APP_BASE_URL;

  // if (process.env.NODE_ENV === 'development') {
  //   worker.start({ onUnhandledRequest: 'bypass' });
  //   BASE_URL = '';
  // }

  const accessToken = localStorage.getItem('login-token');

  if (!accessToken) throw new Error('토큰 없음');

  const response = await fetch(`${BASE_URL}${path}`, {
    ...init,
    headers: {
      Authorization: `Bearer ${accessToken}`,
      'Content-Type': 'application/json',
      ...init?.headers,
    },
  });

  if (!response.ok) throw new Error(response.status.toString());
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

export const customerHeader = {
  headers: {
    Authorization: `Basic ${btoa('leo:1234')}`,
    'Content-Type': 'application/json',
  },
};

export const ownerHeader = {
  headers: {
    Authorization: `Basic ${btoa('owner1:owner1')}`,
    'Content-Type': 'application/json',
  },
};
