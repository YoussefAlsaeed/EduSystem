<template>
    <div class="course-details">
        <section class="hero is-info">
            <div class="hero-body">
                <p class="title">
                    Course Name: {{ courseData.name }}
                    <button class="button" :class="{ 'is-success': isEnrolled }" @click="enrollCourse">{{ isEnrolled ? 'Enrolled' : 'Enroll' }}</button>
                </p>
                <p class="subtitle">Category: {{ courseData.category }}</p>
            </div>
        </section>

        <div class="box">
            <div class="content">
                <h3 class="title is-4">Course Information</h3>
                <p><strong>Duration:</strong> {{ courseData.duration }}</p>
                <p><strong>Capacity:</strong> {{ courseData.capacity }}</p>
                <p><strong>Enrolled Students:</strong> {{ courseData.enrolledStudents }}</p>
                <p><strong>Rating:</strong> {{ courseData.rating }}</p>
                <p><strong>Content Reviewed:</strong> {{ contentStatus }}</p>
            </div>
        </div>

        <div class="box">
            <div class="content">
                <h3 class="title is-4">Make a Review</h3>
                <div class="field">
                    <label class="label">Your Review:</label>
                    <div class="control">
                        <textarea class="textarea" v-model="reviewComment" placeholder="Enter your review comment"></textarea>
                    </div>
                </div>
                <div class="field">
                    <div class="control">
                        <button class="button is-info" @click="submitReview">Submit Review</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="box">
            <div class="content">
                <h3 class="title is-4">Rate the Course</h3>
                <div class="field">
                    <label class="label">Rating (1-10):</label>
                    <div class="control">
                        <input class="input" type="number" v-model="rating" min="1" max="10" placeholder="Enter your rating">
                    </div>
                </div>
                <div class="field">
                    <div class="control">
                        <button class="button is-info" @click="submitRating">Submit Rating</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { courseMicroSvc } from '@/apiClients';

export default {
    data() {
        return {
            courseData: null,
            reviewComment: '',
            rating: null,
            isEnrolled: false
        };
    },
    created() {
        this.courseData = JSON.parse(this.$route.params.courseData);
        this.checkEnrollmentStatus();
    },
    computed: {
        contentStatus() {
            return this.courseData.contentReviewed === 'ACCEPTED' ? 'Reviewed and Accepted' : 'Pending Review';
        },
    },
    methods: {
        submitReview() {
            const courseId = this.courseData.courseId;
            const studentId = this.$route.params.studentId;
            if (this.reviewComment.trim() !== '') {
                courseMicroSvc.post('/course/addReview', {
                    courseId: courseId,
                    studentId: studentId,
                    comment: this.reviewComment
                }).then(response => {
                    console.log('Review submitted successfully');
                    this.reviewComment = '';
                }).catch(error => {
                    console.error('Error submitting review:', error);
                });
            }
        },
        submitRating() {
            const courseId = this.courseData.courseId;
            if (this.rating !== null) {
                courseMicroSvc.put(`/course/rateCourse/${courseId}/${this.rating}`)
                    .then(response => {
                        console.log('Rating submitted successfully');
                        this.rating = null;
                    }).catch(error => {
                        console.error('Error submitting rating:', error);
                    });
            }
        },
        enrollCourse() {
            const courseId = this.courseData.courseId;
            const studentId = this.$route.params.studentId;
            const status = 'PENDING';
            courseMicroSvc.post('/enroll/course', {
                courseId,
                studentId,
                status
            }).then(response => {
                console.log('Course enrolled successfully');
                this.isEnrolled = true;
            }).catch(error => {
                console.error('Error enrolling in course:', error);
            });
        },
        checkEnrollmentStatus() {
            this.isEnrolled = false; // Assuming user is not enrolled by default
        },
        openEnrolledStudents(courseId, instructorId) {
            this.$router.push({ name: 'CourseEnrollment', params: { courseId, instructorId } });
        }
    }
};
</script>

<style scoped>
.course-details {
    max-width: 800px;
    margin: auto;
    padding: 20px;
}

.box {
    margin-top: 20px;
}

.content {
    padding: 20px;
}

textarea {
    min-height: 150px;
}
</style>
