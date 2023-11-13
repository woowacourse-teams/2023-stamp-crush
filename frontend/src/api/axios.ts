import axios from 'axios';
import { getReissuedToken } from './get';

const BASE_URL =
  process.env.NODE_ENV === 'development'
    ? process.env.REACT_APP_DEV_URL
    : process.env.REACT_APP_BASE_URL;

export const setAdminAccessToken = (token: string) => {
  ownerInstance.defaults.headers.common.Authorization = `Bearer ${token}`;
};

const reissueToken = async (error: any) => {
  if (error.response.status === 401) {
    try {
      const { accessToken } = await getReissuedToken();
      setAdminAccessToken(accessToken);
    } catch {
      location.href = `${location.origin}/admin/login`;
    }
  }
};

export const ownerInstance = axios.create({
  baseURL: BASE_URL + '/admin',
});

export const customerInstance = axios.create({
  baseURL: BASE_URL,
});

export const instance = axios.create({
  baseURL: BASE_URL,
});

ownerInstance.interceptors.response.use(
  function (res) {
    return res;
  },
  function (error) {
    reissueToken(error);
    return Promise.reject(error);
  },
);
