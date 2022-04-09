import axios, { AxiosInstance } from 'axios';

const getToken = () => {
  console.log(localStorage.getItem('accessToken'));
  if (localStorage.getItem('accessToken') === null) {
    return '';
  } else {
    return localStorage.getItem('accessToken');
  }
};

export const API: AxiosInstance = axios.create({
  baseURL: 'http://52.79.231.137:9999/profit',
  headers: {
    accessToken: getToken() as string,
  },
});
