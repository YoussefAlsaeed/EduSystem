import axios from 'axios';

const userMicroSvc = axios.create({
  baseURL: 'http://usersvc:8085',
});

const courseMicroSvc = axios.create({
  baseURL: 'http://coursesvc:8080/CourseMgmtMs-1.0/api',
});

const notifyMicroSvc = axios.create({
    baseURL: 'http://notifysvc:8090',
  });

export { userMicroSvc, courseMicroSvc, notifyMicroSvc };
