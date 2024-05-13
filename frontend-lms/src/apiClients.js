import axios from 'axios';

const userMicroSvc = axios.create({
  baseURL: 'http://localhost:8085',
});

const courseMicroSvc = axios.create({
  baseURL: 'http://localhost:8080/CourseMgmtMs/api',
});

const notifyMicroSvc = axios.create({
    baseURL: 'http://localhost:8090',
  });

export { userMicroSvc, courseMicroSvc, notifyMicroSvc };
