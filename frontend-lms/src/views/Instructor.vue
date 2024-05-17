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
    <!-- Search section -->
    <div class="search-and-sort-container">
      <!-- Search section -->
      <section class="section-search">
        <div class="container">
          <!-- Search by Name -->
          <div class="field has-addons">
            <div class="control">
              <button class="button" @click="toggleSearch('name')">Search by Name</button>
            </div>
            <div class="control">
              <input class="input" type="text" v-model="searchName" placeholder="Enter course name"
                v-show="searchType === 'name'">
            </div>
          </div>

          <!-- Search by Category -->
          <div class="field has-addons">
            <div class="control">
              <button class="button" @click="toggleSearch('category')">Search by Category</button>
            </div>
            <div class="control">
              <input class="input" type="text" v-model="searchCategory" placeholder="Enter course category"
                v-show="searchType === 'category'">
            </div>
          </div>

          <!-- Search button -->
          <div class="field">
            <div class="control">
              <button class="button is-info" @click="searchCourses">Search</button>
            </div>
          </div>
        </div>
      </section>

      <!-- Sort by Ratings Button -->
      <div class="field sort-button-container">
        <div class="control">
          <button class="button is-info sort-button" @click="sortCoursesByRatings">Sort by Ratings</button>
        </div>
      </div>
    </div>

    <section class="section">
      <div class="container">
        <div class="columns is-multiline">
          <!-- Existing course cards -->
          <div v-for="course in courses" :key="course.courseId" class="column is-one-third course-card"
            @click="openCourse(course)">
            <div class="card" @mouseover="hover = true" @mouseleave="hover = false" :class="{ 'hover': hover }">
              <header class="card-header">
                <div class="card-header-icon">
                  <span class="icon">
                    <i class="fas fa-user-circle"></i>
                  </span>
                </div>
                <p class="card-header-title">
                  {{ course.name }}
                </p>
                <div class="card-header-end">
                  <span class="icon">
                    <svg xmlns="http://www.w3.org/2000/svg" height="17" width="15"
                      viewBox="0 0 576 512"><!--!Font Awesome Free 6.5.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.-->
                      <path fill="#FFD43B"
                        d="M316.9 18C311.6 7 300.4 0 288.1 0s-23.4 7-28.8 18L195 150.3 51.4 171.5c-12 1.8-22 10.2-25.7 21.7s-.7 24.2 7.9 32.7L137.8 329 113.2 474.7c-2 12 3 24.2 12.9 31.3s23 8 33.8 2.3l128.3-68.5 128.3 68.5c10.8 5.7 23.9 4.9 33.8-2.3s14.9-19.3 12.9-31.3L438.5 329 542.7 225.9c8.6-8.5 11.7-21.2 7.9-32.7s-13.7-19.9-25.7-21.7L381.2 150.3 316.9 18z" />
                    </svg>
                  </span>
                  <span>{{ course.rating }}</span>

                </div>
              </header>
              <div class="card-content">
                <div class="content">
                  <p><strong>Duration:</strong> {{ course.duration }}</p>
                  <p><strong>Category:</strong> {{ course.category }}</p>
                  <span class="icon" @click.stop="openEnrolledStudents(course.courseId, course.instructorId)">
<svg xmlns="http://www.w3.org/2000/svg" height="17" width="15" viewBox="0 0 320 512"><!--!Font Awesome Free 6.5.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.--><path d="M112 48a48 48 0 1 1 96 0 48 48 0 1 1 -96 0zm40 304V480c0 17.7-14.3 32-32 32s-32-14.3-32-32V256.9L59.4 304.5c-9.1 15.1-28.8 20-43.9 10.9s-20-28.8-10.9-43.9l58.3-97c17.4-28.9 48.6-46.6 82.3-46.6h29.7c33.7 0 64.9 17.7 82.3 46.6l58.3 97c9.1 15.1 4.2 34.8-10.9 43.9s-34.8 4.2-43.9-10.9L232 256.9V480c0 17.7-14.3 32-32 32s-32-14.3-32-32V352H152z"/></svg>
                  </span>
                  <span>{{ course.enrolledStudents }}</span>
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
                <input class="input" type="text" placeholder="Enter course duration" v-model="newCourse.duration"
                  required>
              </div>
            </div>

            <div class="field">
              <label class="label">Category</label>
              <div class="control">
                <input class="input" type="text" placeholder="Enter course category" v-model="newCourse.category"
                  required>
              </div>
            </div>
            <div class="field">
              <label class="label">Capacity</label>
              <div class="control">
                <input class="input" type="text" placeholder="Enter course Capacity" v-model="newCourse.capacity"
                  required>
              </div>
            </div>

            <!-- Hidden fields for instructorId and other default values -->
            <input type="hidden" v-model="newCourse.instructorId">
            <input type="hidden" v-model="newCourse.rating" value="0">
            <input type="hidden" v-model="newCourse.totalRates" value="0">
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
        name: "",
        duration: "",
        category: "",
        instructorId: id, // Assuming the instructorId is known
        rating: 0.0,
        totalRates: 0,
        capacity: "",
        enrolledStudents: 0,
        contentReviewed: "ACCEPTED"
      },
      searchType: '', // 'name' or 'category'
      searchName: '',
      searchCategory: '',
      searchResults: [],
      sortByRatings: false // Initially not sorting by ratings

    };
  },
  methods: {
    toggleSearch(type) {
      // Clear the search field value based on the selected search type
      if (type === 'name') {
        this.searchName = '';
      } else if (type === 'category') {
        this.searchCategory = '';
      }
      this.searchType = type;
    },
    // toggleSortByRatings() {
    //   this.sortByRatings = !this.sortByRatings;
    //   if (this.sortByRatings) {
    //     this.sortCoursesByRatings();
    //   } else {
    //     // Fetch courses by instructor ID again to reset the order
    //     this.fetchCoursesByInstructorId();
    //   }
    // },
    sortCoursesByRatings() {
      // Call the backend API to sort courses by ratings
      const instructorId = this.$route.params.id;
      courseMicroSvc.get(`/course/sortByRatingsInstructor/${instructorId}`)
        .then(response => {
          this.courses = response.data;
        })
        .catch(error => {
          console.error(error);
        });
    },
    searchCourses() {
      if (this.searchType === 'name') {
        this.searchCoursesByName();
      } else if (this.searchType === 'category') {
        this.searchCoursesByCategory();
      }
    },
    searchCoursesByName() {
      courseMicroSvc.get(`/course/searchCoursesByName/${this.searchName}`)
        .then(response => {
          this.courses = response.data;
        })
        .catch(error => {
          console.error(error);
        });
    },

    searchCoursesByCategory() {
      courseMicroSvc.get(`/course/searchCoursesByCategory/${this.searchCategory}`)
        .then(response => {
          this.courses = response.data;
        })
        .catch(error => {
          console.error(error);
        });
    },

    openCourse(course) {
      this.$router.push({ name: 'CourseDetails', params: { courseData: JSON.stringify(course) } });
    },
    openEnrolledStudents(courseId, instructorId) {
      this.$router.push({ name: 'CourseEnrollment', params: { courseId, instructorId } });
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
        instructorId: this.$route.params.id // Use the logged-in instructor's ID
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
    },

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
  },
  computed: {
    filteredCourses() {
      // Filter courses based on searchName and searchCategory
      if (this.searchName) {
        return this.courses.filter(course => course.name.toLowerCase().includes(this.searchName.toLowerCase()));
      } else if (this.searchCategory) {
        return this.courses.filter(course => course.category.toLowerCase().includes(this.searchCategory.toLowerCase()));
      } else {
        return this.courses;
      }
    },
  },
};
</script>
<style>
/* Style for the course cards similar to Google Classroom */
.card {
  margin-bottom: 1rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  background-color: #333333;
  /* Change card background to white for visibility */
  color: white;
  /* Text color for better visibility */
}

.card:hover {
  transform: translateY(-5px);
  /* Moves the card up slightly on hover */
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
  /* Enhance shadow effect on hover */
}

.card-header-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2rem;
  width: 3rem;
  height: 3rem;
  border-radius: 50%;
  background-color: #36454f;
  /* Example blue background for icon circle */
  color: #36454f;
  /* White color for icon */
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
  color: white;
  /* White color for icons */
}

/* Style for the Add New Course card */
.add-course-card {
  background-color: dimgrey;
  /* Light background for the card */
  border: dashed 2px #4a90e2;
  /* Dashed border */
  color: #36454f;
  /* Text color */
}

.search-and-sort-container {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  /* Adjust as needed */
  margin: 20px;
  /* Add margin to create space */
}

.section-search {
  flex: 1;
  /* Take up remaining space */
  margin-right: 10px;
  /* Adjust spacing */
}

.sort-button-container {
  flex: 0;
  /* Don't grow or shrink */
  margin-left: 10px;
  /* Adjust spacing */
}

.sort-button,
.button.is-info {
  background-color: #36454f;
  color: white;
}

.sort-button:hover,
.button.is-info:hover {
  background-color: #36454f;
  /* Darken on hover */
}
</style>
