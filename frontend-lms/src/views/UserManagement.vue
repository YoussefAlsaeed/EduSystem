<template>
    <div class="user-management">
      <div class="hero is-info">
        <div class="hero-body has-text-centered">
          <h1 class="title">Hello, {{ user.name }}</h1>
          <p class="subtitle">{{ user.email }}</p>
        </div>
      </div>
      <section class="section">
        <div class="container">
          <table class="table is-fullwidth">
            <thead>
              <tr>
                <th>Name</th>
                <th>Role</th>
                <th>ID</th>
                <th>Email</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="user in users" :key="user.id">
                <td>{{ user.name }}</td>
                <td>{{ user.userType }}</td>
                <td>{{ user.userID }}</td>
                <td>{{ user.email }}</td>
                <td>
                  <button class="button is-danger" @click="deleteUser(user.userID)">Delete</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </section>
    </div>

  </template>
  
  <script>
  import { userMicroSvc } from '../apiClients.js';

  export default {
    data() {
      return {
        user: {},
        users: []
      };
    },
    created() {
      this.fetchUserData();
    },
    methods: {
      fetchUserData() {
        const id = this.$route.params.id;
        const type = 'Admin';
        
        userMicroSvc.get(`/users/${type}/${id}`)
          .then(response => {
            this.user = response.data;
          })
          .catch(error => {
            console.error(error);
          });
  
        userMicroSvc.get(`/users`)
          .then(response => {
            this.users = response.data;
          })
          .catch(error => {
            console.error(error);
          });
      },
      deleteUser(id) {
        userMicroSvc.delete(`/users/${id}`)
          .then(() => {
            this.fetchUserData();
          })
          .catch(error => {
            console.error(error);
          });
      }
    }
  }
  </script>
  
  <style scoped>
  </style>
  