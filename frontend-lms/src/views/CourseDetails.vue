<template>
  <div class="course-details">
    <section class="hero is-info">
      <div class="hero-body">
        <p class="title">Course Name: {{ courseData.name }}</p>
        <p class="subtitle"> Category: {{ courseData.category }}</p>
      </div>
    </section>

    <div class="box">
      <div class="content">x
        <h3 class="title is-4">Course Information</h3>
        <p><strong>Duration:</strong> {{ courseData.duration }}</p>
        <p><strong>Capacity:</strong> {{ courseData.capacity }}</p>
        <p><strong>Enrolled Students:</strong> {{ courseData.enrolledStudents }}</p>
        <p><strong>Rating:</strong> {{ courseData.rating }}</p>
        <p><strong>Content Reviewed:</strong> {{ contentStatus }}</p>
        <!-- Add enrolled students link -->
        <p><strong>Enrolled Students:</strong> <a @click="openEnrolledStudents(courseData.courseId,courseData.instructorId)">View Enrolled Students</a></p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      courseData: null,
    };
  },
  created() {
    this.courseData = JSON.parse(this.$route.params.courseData);
  },
  computed: {
    contentStatus() {
      return this.courseData.contentReviewed === 'ACCEPTED' ? 'Reviewed and Accepted' : 'Pending Review';
    },
  },
  methods: {
    openEnrolledStudents(courseId,instructorId) {
      this.$router.push({ name: 'CourseEnrollment', params: { courseId, instructorId } });
    }
  }
};
</script>

<style scoped>
.course-details {
  max-width: 2000px;
  margin: auto;
}

.box {
  margin-top: 20px;
}
</style>
