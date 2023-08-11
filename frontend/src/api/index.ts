import { worker } from '../mocks/browser';

const request = async (path: string, init?: RequestInit) => {
  let BASE_URL = process.env.REACT_APP_BASE_URL;

  if (process.env.NODE_ENV === 'development') {
    worker.start({ onUnhandledRequest: 'bypass' });
    BASE_URL = '';
  }

  const response = await fetch(`${BASE_URL}${path}`, {
    ...init,
    headers: {
      // Authorization: `Basic ${token}`,
      'Content-Type': 'application/json',
      ...init?.headers,
    },
  });

  if (!response.ok) throw new Error(response.status.toString());
  return response;
};

export const api = {
  get: (path: string, init?: RequestInit) =>
    request(path, init).then((response) => response.json()),

  patch: (path: string, init?: RequestInit, payload?: unknown) =>
    request(path, {
      headers: init?.headers,
      method: 'PATCH',
      body: JSON.stringify(payload),
    }),

  post: (path: string, init?: RequestInit, payload?: unknown) =>
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
