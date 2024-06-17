<template>
  <Nav/>

  <div class="home">
    <div class="hero is-info is-medium">
      <div class="hero-body has-text-centered">
        <h1 class="title">Welcome to Online Learning</h1>

        <h2 class="subtitle">An online place for learning what you want</h2>
      </div>
    </div>
  </div>
</template>

<script>
import Nav from '@/components/Nav'

export default {
  name: 'Home',
      components: {
      Nav
    }
}
</script>