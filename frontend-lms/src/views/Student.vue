    <template>
        <div class="about">
            <!-- User information section -->
            <div class="hero is-info">
                <div class="hero-body">
                    <div class="container">
                        <div class="columns is-vcentered">
                            <div class="column">
                                <h1 class="title">Hello, {{ user.name }}</h1>
                                <p class="subtitle">{{ user.email }}</p>
                                <p>Bio --- {{ user.bio }}</p>
                                <p>Affiliation -- {{ user.affiliation }}</p>
                                <p>Years of experience -- {{ user.yearsOfExperience }}</p>
                            </div>
                            <div class="column is-narrow">
                                <!-- Button to navigate to Students' Course Enrollments pop-up page -->
                                <button class="button is-primary" @click="openEnrollmentsPopup">
                                    <span class="icon">
                                        <svg xmlns="http://www.w3.org/2000/svg"
                                            viewBox="0 0 640 512"><!--!Font Awesome Free 6.5.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.-->
                                            <path
                                                d="M320 32c-8.1 0-16.1 1.4-23.7 4.1L15.8 137.4C6.3 140.9 0 149.9 0 160s6.3 19.1 15.8 22.6l57.9 20.9C57.3 229.3 48 259.8 48 291.9v28.1c0 28.4-10.8 57.7-22.3 80.8c-6.5 13-13.9 25.8-22.5 37.6C0 442.7-.9 448.3 .9 453.4s6 8.9 11.2 10.2l64 16c4.2 1.1 8.7 .3 12.4-2s6.3-6.1 7.1-10.4c8.6-42.8 4.3-81.2-2.1-108.7C90.3 344.3 86 329.8 80 316.5V291.9c0-30.2 10.2-58.7 27.9-81.5c12.9-15.5 29.6-28 49.2-35.7l157-61.7c8.2-3.2 17.5 .8 20.7 9s-.8 17.5-9 20.7l-157 61.7c-12.4 4.9-23.3 12.4-32.2 21.6l159.6 57.6c7.6 2.7 15.6 4.1 23.7 4.1s16.1-1.4 23.7-4.1L624.2 182.6c9.5-3.4 15.8-12.5 15.8-22.6s-6.3-19.1-15.8-22.6L343.7 36.1C336.1 33.4 328.1 32 320 32zM128 408c0 35.3 86 72 192 72s192-36.7 192-72L496.7 262.6 354.5 314c-11.1 4-22.8 6-34.5 6s-23.5-2-34.5-6L143.3 262.6 128 408z" />
                                        </svg>
                                    </span>
                                    Enrollments
                                </button>
                                <button class="button is-info" @click="openNotifications">Notifications</button>

                                <!-- Notification Slide Screen -->
                                <div v-if="showNotifications" class="notification-slide-screen">
                                    <button class="close-button" @click="closeNotifications">X</button>

                                    <div class="notification-box" v-for="notification in notifications"
                                        :key="notification.notificationId">
                                        <p class="notification-text">{{ notification.message }}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
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
                                <input class="input" type="text" v-model="searchCategory"
                                    placeholder="Enter course category" v-show="searchType === 'category'">
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
                        <button class="button is-info sort-button" @click="sortCoursesByRatings">Sort by
                            Ratings</button>
                    </div>
                </div>
            </div>
            <section class="section">
                <div class="container">
                    <div class="columns is-multiline">
                        <!-- Course cards -->
                        <div v-for="course in courses" :key="course.courseId" class="column is-one-third course-card"
                            @click="openCourse(course)">
                            <div class="card">
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
                                        <span class="icon">
                                            <svg xmlns="http://www.w3.org/2000/svg" height="17" width="15"
                                                viewBox="0 0 320 512"><!--!Font Awesome Free 6.5.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.-->
                                                <path
                                                    d="M112 48a48 48 0 1 1 96 0 48 48 0 1 1 -96 0zm40 304V480c0 17.7-14.3 32-32 32s-32-14.3-32-32V256.9L59.4 304.5c-9.1 15.1-28.8 20-43.9 10.9s-20-28.8-10.9-43.9l58.3-97c17.4-28.9 48.6-46.6 82.3-46.6h29.7c33.7 0 64.9 17.7 82.3 46.6l58.3 97c9.1 15.1 4.2 34.8-10.9 43.9s-34.8 4.2-43.9-10.9L232 256.9V480c0 17.7-14.3 32-32 32s-32-14.3-32-32V352H152z" />
                                            </svg>
                                        </span>
                                        <span>{{ course.enrolledStudents }}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <button class="button is-primary" @click="openEnrollmentsPopup">Enrollments</button>

            <!-- Popup content -->
            <!-- Popup content -->
            <!-- Popup content -->
            <!-- Popup content -->
            <div v-if="showEnrollmentsPopup" class="enrollments-popup">
                <div class="enrollments-content">
                    <h2>Current Course Enrollments</h2>
                    <ul>
                        <li v-for="enrollment in enrollments" :key="enrollment.enrollmentId">
                            <div class="enrollment-item">
                                <span>
                                    Course ID: {{ enrollment.courseId }} - Status: {{ enrollment.status }}
                                </span>
                                <button v-if="enrollment.status !== 'CANCELLED'" class="button is-danger is-small"
                                    @click="cancelEnrollment(enrollment)">Cancel</button>
                            </div>
                        </li>
                    </ul>
                    <!-- Button to close the popup -->
                    <button class="button close-button" @click="closeEnrollmentsPopup">Close</button>
                </div>
            </div>



        </div>
    </template>

<script>
import { userMicroSvc } from '@/apiClients';
import { courseMicroSvc } from '@/apiClients';
import { notifyMicroSvc } from '@/apiClients';

export default {
    data() {
        return {
            user: {},
            courses: [],
            hover: false,
            searchType: '', // 'name' or 'category'
            searchName: '',
            searchCategory: '',
            searchResults: [],
            sortByRatings: false, // Initially not sorting by ratings
            showPopup: false,
            enrollmentsPopupVisible: false,
            currentEnrollments: [],
            pastEnrollments: [],
            enrollments: [],
            showEnrollmentsPopup: false,
            showNotifications: false,
            notifications: [],
        };
    },
    methods: {
        cancelEnrollment(enrollment) {
            const { enrollmentId, studentId } = enrollment;
            // Call the backend API to cancel enrollment
            courseMicroSvc.put(`/enroll/cancelEnrollment/${enrollmentId}/${studentId}`)
                .then(response => {
                    console.log('Enrollment canceled successfully');
                    // Update enrollment status to CANCELED
                    enrollment.status = 'CANCELLED';
                })
                .catch(error => {
                    console.error('Error canceling enrollment:', error);
                });
        }
        ,
        closeNotifications() {
            this.showNotifications = false;
        },
        openNotifications() {
    const studentId = this.$route.params.id;

    // Fetch notifications from the server
    notifyMicroSvc.get(`/getNotifications/${studentId}`)
        .then(response => {
            // Reverse the order of notifications to show the latest first
            this.notifications = response.data.reverse();
            this.showNotifications = true; // Show the notification slide screen
        })
        .catch(error => {
            console.error('Error fetching notifications:', error);
        });
},


        openEnrollmentsPopup() {
            // Fetch current and past enrollments
            const studentId = this.$route.params.id;
            this.fetchEnrollments(studentId);

            // Toggle the visibility of the enrollments popup
            this.showEnrollmentsPopup = true;
        },
        closeEnrollmentsPopup() {
            this.showEnrollmentsPopup = false;
        },
        fetchEnrollments(studentId) {
            // Fetch enrollments data from the server
            courseMicroSvc.get(`/enroll/getEnrollmentsForStudent/${studentId}`)
                .then(response => {
                    this.enrollments = response.data;
                    // Separate current and past enrollments
                    this.currentEnrollments = enrollments.filter(enrollment => enrollment.status === "ACCEPTED");
                    console.log("here:-" + this.enrollments[0].courseId);
                    this.pastEnrollments = enrollments.filter(enrollment => enrollment.status === "REJECTED");
                })
                .catch(error => {
                    console.error('Error fetching enrollments:', error);
                });
        },
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
            courseMicroSvc.get(`/course/sortByRatings`)
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
            const studentId = this.$route.params.id;
            this.$router.push({ name: 'StudentsCourseDetails', params: { courseData: JSON.stringify(course), studentId } });
        },
        fetchCourses() {
            // Fetch courses using GET /course/getReviewedCourses
            courseMicroSvc.get('/course/getReviewedCourses')
                .then(response => {
                    this.courses = response.data;
                })
                .catch(error => {
                    console.error(error);
                });
        }
    },
    created() {
        const id = this.$route.params.id;
        const type = 'Student';

        userMicroSvc.get(`/users/${type}/${id}`)
            .then(response => {
                this.user = response.data;
            })
            .catch(error => {
                console.error(error);
            });

        // Fetch courses
        this.fetchCourses();

    },
    computed: {
        // Same computed property as in instructor.vue
    }
};
</script>

<style>
/* CSS styles for the enrollments popup */
/* CSS styles for the enrollments popup */
.enrollments-popup {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    /* Semi-transparent background */
    display: flex;
    justify-content: center;
    align-items: center;
}

.enrollments-content {
    background-color: #36454f;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.close-button {
    margin-top: 10px;
}

/* CSS styles for the notification slide screen */
.notification-slide-screen {
    position: fixed;
    top: 0;
    right: 0;
    /* Adjusted to open from the right side */
    width: 300px;
    /* Adjust the width as needed */
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    /* Semi-transparent background */
    display: flex;
    flex-direction: column;
    /* Display notifications in columns */
    align-items: flex-end;
    /* Align notifications to the end */
    padding: 20px;
    /* Add padding for spacing */
    box-sizing: border-box;
    /* Include padding in width calculation */
    overflow-y: auto;
    /* Enable vertical scrolling */
    z-index: 999;
    /* Ensure it's above other content */
}

.notification-box {
    background-color: #36454f;
    padding: 10px;
    border-radius: 5px;
    margin-bottom: 10px;
    /* Add margin between notifications */
    width: 100%;
    /* Ensure notifications take full width */
    box-sizing: border-box;
    /* Include padding in width calculation */
}

.notification-text {
    color: white;
    /* Change the color to your desired color */
}

.close-button {
    position: absolute;
    top: 10px;
    right: 10px;
    background: none;
    border: none;
    cursor: pointer;
    font-size: 1.5rem;
    color: #fff;
    /* Adjust the color as needed */
}

.enrollment-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
    /* Adjust the spacing between enrollments */
}
</style>