<template>
    <div class="signup">
      <div class="hero is-info">
        <div class="hero-body has-text-centered">
          <h1 class="title">Sign up - Instructor</h1>
        </div>
      </div>
  
      <section class="section">
          <div class="container">
              <div class="columns">
                <div v-if="showSuccessAlert" class="notification is-success">
                        Registration successful! You can now log in.
                    </div>
                    <div v-if="showErrorAlert" class="notification is-danger">
                        Something went wrong. Please try again. (Email already exists or service is down right now)
                    </div>
                  <div class="column is-4 is-offset-4">
                      <form @submit.prevent="registerUser">
                          <div class="field">
                              <label>Name</label>
                              <div class="control">
                                  <input type="text" class="input" v-model="name">
                              </div>
                          </div>

                          <div class="field">
                              <label>Email</label>
                              <div class="control">
                                  <input type="email" class="input" v-model="email">
                              </div>
                          </div>
  
                          <div class="field">
                              <label>Password</label>
                              <div class="control">
                                  <input type="password" class="input" v-model="password">
                              </div>
                          </div>
  
                          <div class="field">
                              <label>Affiliation</label>
                              <div class="control">
                                  <input type="text" class="input" v-model="affiliation">
                              </div>
                          </div>

                          <div class="field">
                              <label>Bio</label>
                              <div class="control">
                                  <textarea class="textarea" v-model="bio"></textarea>
                              </div>
                          </div>

                          <div class="field">
                              <label>Years of Experience</label>
                              <div class="control">
                                  <input type="number" class="input" v-model="yearsOfExperience">
                              </div>
                          </div>
  
                          <div class="field">
                              <div class="control">
                                  <button class="button is-dark">Sign up</button>
                              </div>
                          </div>
                      </form>
  
                      <hr>
  
                      Or <router-link to="/log-in">click here</router-link> to log in!
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
            name: '',
            email: '',
            password: '',
            affiliation: '',
            bio: '',
            yearsOfExperience: '',
            showSuccessAlert: false,
            showErrorAlert: false
        };
    },
    methods: {
        registerUser() {
            const user = {
                name: this.name,
                email: this.email,
                password: this.password,
                affiliation: this.affiliation,
                bio: this.bio,
                userType: 'Instructor',
                yearsOfExperience: this.yearsOfExperience
            };

            userMicroSvc.post('/auth/register', user)
                .then(response => {
                    // Clear form fields
                    this.name = '';
                    this.email = '';
                    this.password = '';
                    this.affiliation = '';
                    this.bio = '';
                    this.yearsOfExperience = '';

                    // Show success alert
                    this.showSuccessAlert = true;
                    this.showErrorAlert = false;
                })
                .catch(error => {
                    console.error(error);
                    // Show error alert
                    this.showErrorAlert = true;
                    this.showSuccessAlert = false;
                });
        }
    }
}
</script>

<style>
.notification {
    margin-top: 20px;
}
</style>