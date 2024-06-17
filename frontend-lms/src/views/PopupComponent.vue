<template>
    <div class="popup is-active">
      <div class="popup-background" @click="$emit('close')"></div>
      <div class="popup-content">
        <div class="box">
          <button class="delete is-pulled-right" @click="$emit('close')"></button>
          <h2 class="title">Courses</h2>
          <div v-for="course in courses" :key="course.id" class="course-item">
            <h3>Course name: {{ course.name }}</h3>
            <p>Enrolled Students: {{ course.enrolledStudents }}</p>
            <p>Rating: {{ course.rating }} / 10</p><br><br>
            <ul>
              Reviews:
              <li v-for="review in course.reviews" :key="review.id">
                 {{ review.studentName || 'Loading...' }} - {{ review.comment }}
              </li>
            </ul>
            <hr class="white-separator">
          </div>
        </div>
      </div>
    </div>
  </template>
  
  
  <script>
  import { courseMicroSvc, userMicroSvc } from '../apiClients.js';
  
  export default {
    data() {
      return {
        courses: []
      };
    },
    created() {
      this.fetchReviewedCourses();
    },
    methods: {
      async fetchReviewedCourses() {
        try {
          const response = await courseMicroSvc.get("/course/getReviewedCourses");
          this.courses = response.data;
          this.fetchStudentNames();
        } catch (error) {
          console.error(error);
        }
      },
      async fetchStudentNames() {
        for (const course of this.courses) {
          for (const review of course.reviews) {
            try {
              const userResponse = await userMicroSvc.get(`/users/Student/${review.studentId}`);
              review.studentName = userResponse.data.name;
            } catch (error) {
              console.error(`Failed to fetch name for student ID ${review.studentId}:`, error);
            }
          }
        }
        this.$forceUpdate();
      }
    }
  };
  </script>
  
  
  <style scoped>
  .popup {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 9999;
  }
  
  .popup-background {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
  }
  
  .popup-content {
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
    max-width: 90%; 
    max-height: 80%;
    overflow-y: auto;
  }
  
  .box {
    position: relative;
  }
  
  .delete {
    cursor: pointer;
  }
  
  .course-item {
    margin-bottom: 20px;
  }
  
  .course-item h3 {
    margin-bottom: 10px;
  }
  
  .course-item ul {
    padding-left: 20px;
  }
  
  .white-separator {
    border: 0;
    height: 1px;
    background: white;
    margin: 20px 0;
  }
  </style>
  