<template>
    <Nav />

    <div class="login">
        <div class="hero is-info">
            <div class="hero-body has-text-centered">
                <h1 class="title">Log in</h1>
            </div>
        </div>

        <section class="section">
            <div class="container">
                <div class="columns">
                    <div v-if="showSuccessAlert" class="notification is-success">
                        Registration successful! You can now log in.
                    </div>
                    <div v-if="showErrorAlert" class="notification is-danger">
                        Invalid Email or Password
                    </div>
                    <div class="column is-4 is-offset-4">
                        <form @submit.prevent="loginUser">
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
                                <div class="control">
                                    <button class="button is-dark">Log in</button>
                                </div>
                            </div>
                        </form>
                        <hr>
                    </div>
                </div>
            </div>
        </section>
    </div>
</template>

<script>
import { userMicroSvc } from '../apiClients.js';
import Nav from '@/components/Nav'

export default {

    components: {
        Nav
    },
    data() {
        return {
            email: '',
            password: ''
        };
    },
    methods: {
        loginUser() {
            const params = new URLSearchParams();
            params.append('email', this.email);
            params.append('password', this.password);

            userMicroSvc.get('/auth/login?' + params.toString())
                .then(response => {
                    this.email = '';
                    this.password = '';
                    this.showSuccessAlert = true;
                    this.showErrorAlert = false;

                    switch (response.data.userType) {
                        case 'Admin':
                            this.$router.push({ name: 'admin', params: { id: response.data.userID } });
                            break;
                        case 'Instructor':
                            this.$router.push({ name: 'instructor', params: { id: response.data.userID } });
                            break;
                        case 'Student':
                            this.$router.push({ name: 'student', params: { id: response.data.userID } });
                            break;
                        default:
                            console.error('Invalid userType');
                    }
                })
                .catch(error => {
                    console.error(error);
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