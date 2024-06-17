<template>
    <nav class="navbar is-dark" role="navigation" aria-label="main navigation" style="min-height: 5rem;">
        <div class="navbar-brand">
            <a class="navbar-item is-size-4" href="/">Online Learning</a>
        </div>

        <div id="navbar-item" class="navbar-menu">
            <div class="navbar-start">
                <a href="/" class="navbar-item">Home</a>
                <a href="/about" class="navbar-item">About</a>
            </div>

            <div class="navbar-end">
                    <div class="buttons">
                        <a href="/sign-up-student" class="button is-primary is-dark"><strong>Sign up Student</strong></a> <!-- Added is-dark class -->
                        <a href="/sign-up-instructor" class="button is-primary is-dark"><strong>Sign up Instructor</strong></a> <!-- Added is-dark class -->
                        <a href="/log-in" class="button is-light is-dark">Log in</a> <!-- Added is-dark class -->
                    </div>
            </div>
        </div>
    </nav>
</template>

<style>
.navbar.is-dark {
  background-color: #36454F; /* Dark gray background */
}

.navbar-item,
.navbar-link {
  color: #ffffff; /* White text */
}

.navbar-item:hover,
.navbar-link:hover {
  background-color: #1e2a31; /* Darker background on hover */
}

/* Custom button styles */
.button.is-dark {
  background-color: #36454F; /* Dark gray background */
  border-color: #36454F; /* Dark gray border */
  color: #ffffff; /* White text */
}

.button.is-dark:hover {
  background-color: #1e2a31; /* Darker gray on hover */
}
</style>
