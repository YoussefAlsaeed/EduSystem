<template>
  <div class="course-enrollment">
    <div class="hero is-info">
      <div class="hero-body has-text-centered">
        <h1 class="title">Course Enrollment</h1>
        <p class="subtitle">Review and manage course enrollments</p>
      </div>
    </div>

    <section class="section">
      <div class="container">
        <table class="table is-fullwidth">
          <thead>
          <tr>
            <th>Student ID</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="enrollment in enrollments" :key="enrollment.enrollmentId">
            <td>{{ enrollment.studentId }}</td>
            <td>
              <span :class="['status', enrollment.status]">{{ enrollment.status }}</span>
            </td>
            <td>
              <div class="action-buttons">
                <button v-if="enrollment.status === 'PENDING'" class="button is-success"
                        @click="acceptEnrollment(enrollment)">Accept</button>
                <button v-if="enrollment.status === 'PENDING'" class="button is-danger"
                        @click="rejectEnrollment(enrollment)">Reject</button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </section>
  </div>
</template>

<script>
import { courseMicroSvc } from '@/apiClients';

export default {
  data() {
    return {
      enrollments: []
    };
  },
  created() {
    this.fetchEnrollments();
  },
  methods: {
    fetchEnrollments() {
      const courseId = this.$route.params.courseId;
      courseMicroSvc.get(`/enroll/getEnrollments/${courseId}`)
          .then(response => {
            this.enrollments = response.data;
          })
          .catch(error => {
            console.error('Error fetching enrollments:', error);
          });
    },
    acceptEnrollment(enrollment) {
      const instructorId = this.$route.params.instructorId;
      // Assuming instructorId is known or can be retrieved from the component
      courseMicroSvc.put(`/enroll/acceptEnrollment/${enrollment.enrollmentId}/${instructorId}`)
          .then(() => {
            // Update status locally
            enrollment.status = 'ACCEPTED';
            console.log('Enrollment accepted successfully.');
          })
          .catch(error => {
            console.error('Error accepting enrollment:', error);
          });
    },
    rejectEnrollment(enrollment) {

      // Assuming instructorId is known or can be retrieved from the component
      const instructorId = this.$route.params.instructorId;
      courseMicroSvc.put(`/enroll/rejectEnrollment/${enrollment.enrollmentId}/${instructorId}`)
          .then(() => {
            // Update status locally
            enrollment.status = 'REJECTED';
            console.log('Enrollment rejected successfully.');
          })
          .catch(error => {
            console.error('Error rejecting enrollment:', error);
          });
    }
  }
};
</script>

<style scoped>
.course-enrollment {
  max-width: 2000px;
  margin: auto;
}

.hero {
  margin-bottom: 20px;
}

.status {
  padding: 5px 10px;
  border-radius: 5px;
}

.status.ACCEPTED {
  background-color: green;
  color: white;
}

.status.REJECTED {
  background-color: red;
  color: white;
}

.status.PENDING {
  background-color: dodgerblue;
  color: white;
}

.action-buttons {
  display: flex;
  align-items: center;
}

.action-buttons button {
  margin-left: 5px;
}
</style>
