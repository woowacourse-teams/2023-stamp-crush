import { BASE_URL } from '../constants';

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
      // TODO: 현재는 로그인에 실패하면 모든 토큰을 비워버림. 추후 고객 페이지 요청과 사장님 페이지의 요청의 에러핸들링을 분리해야함.
      localStorage.setItem('login-token', '');
      localStorage.setItem('admin-login-token', '');
      location.href = `${location.origin}/login`;
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
