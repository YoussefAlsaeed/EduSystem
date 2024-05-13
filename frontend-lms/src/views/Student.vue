<template>
    <div class="about">
        <div class="hero is-info">
            <div class="hero-body has-text-centered">
                <h1 class="title">Hello, {{ user.name }}</h1>
                <p class="subtitle">{{ user.email }}</p>
                <p>{{ user.bio }}</p>
                <p>{{ user.affiliation }}</p>
            </div>
        </div>
        <section class="section">
            <div class="container">

            </div>
        </section>
    </div>
</template>

<script>
export default {
    data() {
        return {
            user: {}
        };
    },
    created() {
        this.user = JSON.parse(this.$route.query.userData);
    }
}
</script>

<style>
/* Add your styles here */
</style>
