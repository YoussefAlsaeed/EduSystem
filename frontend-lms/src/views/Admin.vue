<template>
    <div class="admin-dashboard">
      <div class="hero is-info">
        <div class="hero-body has-text-centered">
          <h1 class="title">Admin Dashboard</h1>
          <p class="subtitle">Welcome, {{ user.name }}</p>
        </div>
      </div>
      <section class="section">
        <div class="container">
          <div class="columns">
            <!-- User Management -->
            <div class="column is-one-third">
              <div class="card">
                <div class="card-content">
                  <p class="title">User Management</p>
                  <p class="subtitle">View and manage user accounts</p>
                  <button class="button is-info is-fullwidth" @click="viewUserAccounts">View Users</button>
                </div>
              </div>
            </div>
            <!-- Course Management -->
            <div class="column is-one-third">
              <div class="card">
                <div class="card-content">
                  <p class="title">Course Management</p>
                  <p class="subtitle">Review and manage course content</p>
                  <button class="button is-info is-fullwidth" @click="reviewCourses">Review Courses</button>
                </div>
              </div>
            </div>
            <!-- Platform Usage -->
            <div class="column is-one-third">
              <div class="card">
                <div class="card-content">
                  <p class="title">Platform Usage</p>
                  <p class="subtitle">Track platform usage by students and instructors</p>
                  <button class="button is-info is-fullwidth" @click="trackUsage">Track Usage</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
    
  </template>
  
  <script>
  import { userMicroSvc } from '../apiClients.js';
  
  export default {
    data() {
      return {
        user: {}
      };
    },
    created() {
      const id = this.$route.params.id;
      const type = 'Admin';
  
      userMicroSvc.get(`/users/${type}/${id}`)
        .then(response => {
          this.user = response.data;
        })
        .catch(error => {
          console.error(error);
        });
    },
    methods: {
      viewUserAccounts() {
        this.$router.push({ name: 'UserManagement', params: { id: this.$route.params.id } });},
      reviewCourses() {
        this.$router.push({ name: 'CourseManagement', params: { id: this.$route.params.id } });      },
      trackUsage() {
        // Logic to navigate to platform usage page
      }
    }
  }
  </script>
  
  <style></style>
  