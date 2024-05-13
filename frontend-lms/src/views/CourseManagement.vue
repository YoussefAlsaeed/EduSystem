<template>
    <div class="course-management">
        <div class="hero is-info">
            <div class="hero-body has-text-centered">
                <h1 class="title">Course Management</h1>
                <p class="subtitle">Review and manage course content</p>
            </div>
        </div>
        <section class="section">
            <div class="container">
                <table class="table is-fullwidth">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Status</th>
                            <th>ID</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="course in courses" :key="course.courseId">
                            <td>{{ course.name }}</td>
                            <td>{{ course.contentReviewed }}</td>
                            <td>{{ course.courseId }}</td>
                            <td>
                                <button v-if="course.contentReviewed === 'PENDING'" class="button is-success"
                                    @click="acceptCourseContent(course.courseId)">Accept</button>
                                <button v-if="course.contentReviewed === 'PENDING'" class="button is-danger"
                                    @click="rejectCourseContent(course.courseId)">Reject</button>
                                <button v-if="course.contentReviewed === 'ACCEPTED'" class="button is-warning"
                                    @click="editCourse(course.courseId)">Edit</button>
                                <button v-if="course.contentReviewed === 'ACCEPTED'" class="button is-danger"
                                    @click="deleteCourse(course.courseId)">Delete</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </section>

        <!-- Modal for editing course -->
        <div class="modal" :class="{ 'is-active': isEditing }">
            <div class="modal-background"></div>
            <div class="modal-content">
                <!-- Form for editing course -->
                <form @submit.prevent="submitEdit">
                    <div class="field">
                        <label class="label">New Name</label>
                        <div class="control">
                            <input class="input" v-model="editedCourse.newName" type="text" placeholder="Enter new name"
                                required>
                        </div>
                    </div>
                    <div class="field">
                        <label class="label">New Duration</label>
                        <div class="control">
                            <input class="input" v-model="editedCourse.newDuration" type="text"
                                placeholder="Enter new duration" required>
                        </div>
                    </div>
                    <div class="field">
                        <label class="label">New Category</label>
                        <div class="control">
                            <input class="input" v-model="editedCourse.newCategory" type="text"
                                placeholder="Enter new category" required>
                        </div>
                    </div>
                    <div class="field">
                        <label class="label">New Capacity</label>
                        <div class="control">
                            <input class="input" v-model="editedCourse.newCapacity" type="number"
                                placeholder="Enter new capacity" required>
                        </div>
                    </div>
                    <button type="submit" class="button is-primary">Submit</button>
                </form>
            </div>
            <button class="modal-close is-large" aria-label="close" @click="isEditing = false"></button>
        </div>
    </div>
</template>

<script>
import { courseMicroSvc } from '../apiClients.js';

export default {
    data() {
        return {
            courses: [],
            editedCourse: {
                courseId: null,
                newName: '',
                newDuration: '',
                newCategory: '',
                newCapacity: 0
            },
            isEditing: false
        };
    },
    created() {
        this.fetchCourses();
    },
    methods: {
        fetchCourses() {
            courseMicroSvc.get(`/course/getCourses`)
                .then(response => {
                    this.courses = response.data;
                })
                .catch(error => {
                    console.error(error);
                });
        },
        acceptCourseContent(courseId) {
            const adminId = this.$route.params.id;
            courseMicroSvc.put(`/course/acceptContent/${courseId}/${adminId}`)
                .then(() => {
                    this.fetchCourses();
                })
                .catch(error => {
                    console.error(error);
                });
        },
        rejectCourseContent(courseId) {
            const adminId = this.$route.params.id;
            courseMicroSvc.put(`/course/rejectContent/${courseId}/${adminId}`)
                .then(() => {
                    this.fetchCourses();
                })
                .catch(error => {
                    console.error(error);
                });
        },
        editCourse(courseId) {
            this.editedCourse = this.courses.find(course => course.courseId === courseId);
            this.isEditing = true;
        },
        submitEdit() {
            const adminId = this.$route.params.id;
            const payload = {
                courseId: this.editedCourse.courseId,
                newName: this.editedCourse.newName,
                newDuration: this.editedCourse.newDuration,
                newCategory: this.editedCourse.newCategory,
                newCapacity: this.editedCourse.newCapacity,
                adminId: adminId
            };

            courseMicroSvc.put(`/course/editCourse`, payload)
                .then(() => {
                    this.fetchCourses();
                    this.isEditing = false;
                })
                .catch(error => {
                    console.error(error);
                    // Handle error
                });
        },
        deleteCourse(courseId) {
            const adminId = this.$route.params.id;
            courseMicroSvc.delete(`/course/deleteCourse`, { data: { courseId, adminId } })
                .then(() => {
                    this.fetchCourses();
                })
                .catch(error => {
                    console.error(error);
                });
        }
    }
}
</script>


<style scoped>
/* Add component-specific styles here */
</style>