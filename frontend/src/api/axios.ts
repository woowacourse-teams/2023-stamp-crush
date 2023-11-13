import axios from 'axios';

const BASE_URL =
  process.env.NODE_ENV === 'development'
    ? process.env.REACT_APP_DEV_URL
    : process.env.REACT_APP_BASE_URL;

export const ownerInstance = axios.create({
  baseURL: BASE_URL,
});

export const customerInstance = axios.create({
  baseURL: BASE_URL,
});

export const commonInstance = axios.create({
  baseURL: BASE_URL,
});
