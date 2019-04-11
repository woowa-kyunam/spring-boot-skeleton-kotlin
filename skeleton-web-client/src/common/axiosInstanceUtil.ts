import { baseURL } from './baseURL';
import axios from 'axios';

//TODO header 값에 의미 없는 값이 들어있음. 이후 변경 필요
export default axios.create({
  baseURL: baseURL(),
  timeout: 1000,
  headers: { 'X-Custom-Header': 'no' }
});
