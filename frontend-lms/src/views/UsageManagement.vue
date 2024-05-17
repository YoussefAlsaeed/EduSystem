<template>
    <div class="user-management">
      <div class="hero is-info">
        <div class="hero-body has-text-centered">
          <h1 class="title">Tracking Usage</h1>
        </div>
      </div>
      <section class="section">
        <div class="container">
          <table class="table is-fullwidth">
            <thead>
              <tr>
                <th>ID</th>
                <th>Message</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="log in logs" :key="log.logId">
                <td>{{ log.logId }}</td>
                <td>{{ log.message }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </section>
      <div class="button-container">
        <button class="button is-primary is-rounded is-large" @click="openPopup">
          View Courses
        </button>
      </div>
      <popup-component v-if="showPopup" @close="showPopup = false"></popup-component>
    </div>
  </template>
  
  <script>
  import PopupComponent from './PopupComponent.vue';
  import { courseMicroSvc } from '../apiClients.js';
  
  export default {
    components: {
      PopupComponent
    },
    data() {
      return {
        user: {},
        logs: [],
        showPopup: false
      };
    },
    created() {
      this.fetchUserData();
    },
    methods: {
      fetchUserData() {
        const id = this.$route.params.id;
        courseMicroSvc.get(`/track/getUsage/${id}`)
          .then(response => {
            this.logs = response.data;
          })
          .catch(error => {
            console.error(error);
          });
      },
      openPopup() {
        this.showPopup = true;
      }
    }
  };
  </script>
  
  <style scoped>
  .user-management {
    padding: 20px;
  }
  
  .button-container {
    display: flex;
    justify-content: center;
    margin-top: 20px;
  }
  
  .button.is-primary {
    background-color: #3273dc;
    border-color: transparent;
    color: white;
  }
  
  .button.is-rounded {
    border-radius: 25px;
  }
  
  .button.is-large {
    font-size: 1.25rem;
    padding: 10px 20px;
  }
  </style>
  