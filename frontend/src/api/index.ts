import { worker } from '../mocks/browser';

const request = async (path: string, init?: RequestInit) => {
  // let BASE_URL = process.env.REACT_APP_BASE_URL;
  const BASE_URL = '';

  if (process.env.NODE_ENV === 'development') {
    worker.start({ onUnhandledRequest: 'bypass' });
  }

  const response = await fetch(`${BASE_URL}${path}`, {
    ...init,
    headers: {
      //   Authorization: `Basic ${token}`,
      'Content-Type': 'application/json',
      ...init?.headers,
    },
  });

  if (!response.ok) throw new Error(response.status.toString());
  return response;
};

export const api = {
  get: (path: string) => request(path).then((response) => response.json()),

  patch: (path: string, payload?: unknown) =>
    request(path, {
      method: 'PATCH',
      body: JSON.stringify(payload),
    }),

  post: (path: string, payload?: unknown) =>
    request(path, {
      method: 'POST',
      body: JSON.stringify(payload),
    }),

  delete: (path: string) =>
    request(path, {
      method: 'DELETE',
    }),
};
