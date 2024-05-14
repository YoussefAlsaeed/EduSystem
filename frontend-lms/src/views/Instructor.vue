<template>
  <div class="about">
    <div class="hero is-info">
      <div class="hero-body has-text-centered">
        <h1 class="title">Hello, {{ user.name }}</h1>
        <p class="subtitle">{{ user.email }}</p>
        <p>Bio --- {{ user.bio }}</p>
        <p>Affiliation -- {{ user.affiliation }}</p>
        <p>Years of experience -- {{ user.yearsOfExperience }}</p>
      </div>
    </div>
    <section class="section">
      <div class="container">
        <div class="columns is-multiline">
          <!-- Existing course cards -->
          <div v-for="course in courses" :key="course.courseId" class="column is-one-third course-card" @click="openCourse(course)">
            <div class="card" @mouseover="hover = true" @mouseleave="hover = false" :class="{ 'hover': hover }">
              <header class="card-header">
                <div class="card-header-icon">
                    <span class="icon">
                      <i class="fas fa-user-circle"></i>
                    </span>
                  <span class="initial">{{ course.name.charAt(0) }}</span>
                </div>
                <p class="card-header-title">
                  {{ course.name }}
                </p>
                <div class="card-header-end">
                    <span class="icon">
                      <i class="fas fa-star"></i>
                    </span>
                  <span>{{ course.rating }}</span>
                  <span class="icon" @click.stop="openEnrolledStudents(course.courseId)">
                      <i class="fas fa-user-friends"></i>
                    </span>
                  <span>{{ course.enrolledStudents }}</span>
                </div>
              </header>
              <div class="card-content">
                <div class="content">
                  <p><strong>Duration:</strong> {{ course.duration }}</p>
                  <p><strong>Category:</strong> {{ course.category }}</p>
                  <!-- Add more course details here -->
                </div>
              </div>
            </div>
          </div>

          <!-- Add New Course Card -->
          <div class="column is-one-third" @click="showAddCourseForm = true">
            <div class="card add-course-card">
              <div class="card-content has-text-centered">
                  <span class="icon is-large">
                    <i class="fas fa-plus"></i>
                  </span>
                <p class="title is-4">Add New Course</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Add Course Form Popup -->
    <div class="modal" :class="{ 'is-active': showAddCourseForm }">
      <div class="modal-background" @click="showAddCourseForm = false"></div>
      <div class="modal-card">
        <header class="modal-card-head">
          <p class="modal-card-title">New Course Details</p>
          <button class="delete" aria-label="close" @click="showAddCourseForm = false"></button>
        </header>
        <section class="modal-card-body">
          <!-- Form for adding a new course -->
          <form @submit.prevent="addNewCourse">
            <div class="field">
              <label class="label">Course Name</label>
              <div class="control">
                <input class="input" type="text" placeholder="Enter course name" v-model="newCourse.name" required>
              </div>
            </div>

            <div class="field">
              <label class="label">Duration</label>
              <div class="control">
                <input class="input" type="text" placeholder="Enter course duration" v-model="newCourse.duration" required>
              </div>
            </div>

            <div class="field">
              <label class="label">Category</label>
              <div class="control">
                <input class="input" type="text" placeholder="Enter course category" v-model="newCourse.category" required>
              </div>
            </div>

            <!-- Hidden fields for instructorId and other default values -->
            <input type="hidden" v-model="newCourse.instructorId" >
            <input type="hidden" v-model="newCourse.rating" value="0">
            <input type="hidden" v-model="newCourse.totalRates" value="0">
            <input type="hidden" v-model="newCourse.capacity" value="0">
            <input type="hidden" v-model="newCourse.enrolledStudents" value="0">
            <input type="hidden" v-model="newCourse.contentReviewed" value="ACCEPTED">

            <div class="field is-grouped">
              <div class="control">
                <button type="submit" class="button is-link">Submit</button>
              </div>
              <div class="control">
                <button type="button" class="button is-link is-light" @click="showAddCourseForm = false">Cancel</button>
              </div>
            </div>
          </form>
        </section>
      </div>
    </div>
  </div>
</template>

<script>
import { userMicroSvc } from '@/apiClients';
import { courseMicroSvc } from '@/apiClients';

export default {
  data() {
    const id = this.$route.params.id;

    return {
      user: {},
      courses: [],
      hover: false,
      showAddCourseForm: false,
      newCourse: {
        name: " ",
        duration: "",
        category: "",
        instructorId: id, // Assuming the instructorId is known
        rating: 0.0,
        totalRates: 0,
        capacity: 0,
        enrolledStudents: 0,
        contentReviewed: "ACCEPTED"
      }
    };
  },
  methods: {
    openCourse(course) {
      this.$router.push({ name: 'CourseDetails', params: { courseData: JSON.stringify(course) } });
    },
    openEnrolledStudents(courseId) {
      // Logic to open popup with list of enrolled students
    },
    addNewCourse() {

      const courseData = {
        name: this.newCourse.name,
        duration: this.newCourse.duration,
        category: this.newCourse.category,
        rating: this.newCourse.rating, // Default value
        totalRates: this.newCourse.totalRates, // Default value
        capacity: this.newCourse.capacity, // Default value
        enrolledStudents: this.newCourse.enrolledStudents, // Default value
        contentReviewed: this.newCourse.contentReviewed, // Default value
        instructorId:  this.$route.params.id // Use the logged-in instructor's ID
      };
      courseMicroSvc.post('/course/createCourse', this.newCourse)
          .then(response => {
            this.courses.push({
              ...courseData,
              courseId: response.data.courseId // Use the course ID from the response if available
            });
            alert('Course created successfully!');
            this.showAddCourseForm = false;
            // Refresh the course list or add the new course to the list
          })
          .catch(error => {
            console.error(error);
            alert('Failed to create course.');
          });
    }
  },
  created() {
    const id = this.$route.params.id;
    // Fetching user
    userMicroSvc.get(`/users/Instructor/${id}`)
        .then(response => {
          this.user = response.data;
        })
        .catch(error => {
          console.error(error);
        });

    // Fetching courses by instructor ID
    courseMicroSvc.get(`/course/getCourses/${id}`)
        .then(response => {
          this.courses = response.data;
        })
        .catch(error => {
          console.error(error);
        });
  }
}
</script>

<style>
/* Style for the course cards similar to Google Classroom */
.card {
  margin-bottom: 1rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  background-color: #333333; /* Change card background to white for visibility */
  color: white; /* Text color for better visibility */
}

.card:hover {
  transform: translateY(-5px); /* Moves the card up slightly on hover */
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2); /* Enhance shadow effect on hover */
}

.card-header-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2rem;
  width: 3rem;
  height: 3rem;
  border-radius: 50%;
  background-color: #4a90e2; /* Example blue background for icon circle */
  color: dimgrey; /* White color for icon */
  margin-right: 8rem;
}

.initial {
  font-weight: bold;
}

.card-header-end {
  display: flex;
  align-items: center;
}

.icon {
  margin-left: 0.5rem;
}

/* Specific icon styles for better visibility */
.icon i {
  color: white; /* White color for icons */
}

/* Style for the Add New Course card */
.add-course-card {
  background-color: dimgrey; /* Light background for the card */
  border: dashed 2px #4a90e2; /* Dashed border */
  color: #4a90e2; /* Text color */
}

/* Style for the "+" icon */
.add-course-card .icon {
  font-size: 3rem; /* Larger icon size */
}


</style>
