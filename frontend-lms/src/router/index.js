import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import About from '../views/About.vue'
import SignUpInstructor from '../views/SignUpInstructor.vue'
import SignUpStudent from '@/views/SignUpStudent.vue'
import LogIn from '../views/Login.vue'
import Admin from '../views/Admin.vue'
import Instructor from '../views/Instructor.vue'
import Student from '../views/Student.vue'
import UserManagement from '../views/UserManagement.vue'
import CourseManagement from '../views/CourseManagement.vue'
import UsageManagement from '../views/UsageManagement.vue'
import CourseDetails from '../views/CourseDetails.vue'
import CourseEnrollment from "@/views/CourseEnrollment.vue";
import StudentsCourseDetails from "@/views/StudentsCourseDetails.vue"

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    component: About
  },
  {
    path: '/sign-up-instructor',
    name: 'SignUpInstructor',
    component: SignUpInstructor
  },
  {
    path: '/sign-up-student',
    name: 'SignUpStudent',
    component: SignUpStudent
  },
  {
    path: '/log-in',
    name: 'LogIn',
    component: LogIn
  },
  {
    path: '/admin/:id',
    name: 'admin',
    component: Admin,
    props: true
  },
  {
    path: '/instructor/:id',
    name: 'instructor',
    component: Instructor,
    props: true
  },
  {
    path: '/student/:id',
    name: 'student',
    component: Student,
    props: true
  },
  {
    path: '/UserManagement/:id',
    name: 'UserManagement',
    component: UserManagement,
    props: true
  },
  {
    path: '/CourseManagement/:id',
    name: 'CourseManagement',
    component: CourseManagement,
    props: true
  },
  {
    path: '/UsageManagement/:id',
    name: 'UsageManagement',
    component: UsageManagement,
    props: true
  }
  ,
  {
    path: '/course/:courseData',
    name: 'CourseDetails',
    component: CourseDetails,
    props: true
  },
  {
    path: '/course/:courseId/:instructorId/enrollment',
    name: 'CourseEnrollment',
    component: CourseEnrollment,
    props: true
  },
  ,  {
    path: '/course/:studentId/:courseData',
    name: 'StudentsCourseDetails',
    component: StudentsCourseDetails,
    props: true
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router