<template>
    <div class="about">
        <div class="hero is-info">
            <div class="hero-body has-text-centered">
                <h1 class="title">Hello, {{ user.name }}</h1>
                <p class="subtitle">{{ user.email }}</p>
                <p>{{ user.bio }}</p>
                <p>{{ user.affiliation }}</p>
                <p>{{ user.yearsOfExperience }}</p>

            </div>
        </div>
        <section class="section">
            <div class="container">

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
        const type = 'Instructor';

        userMicroSvc.get(`/users/${type}/${id}`)
            .then(response => {
                this.user = response.data;
            })
            .catch(error => {
                console.error(error);
            });
    }
}
</script>

<style>
</style>
