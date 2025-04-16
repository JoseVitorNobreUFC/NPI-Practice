<template>
  <v-btn
    color="error"
    @click="handleLogout"
    variant="outlined"
    class="logout-btn"
  >
    <v-icon start>mdi-logout</v-icon>
    Logout
  </v-btn>
</template>

<script setup lang="ts">
const emit = defineEmits(['logout'])

const handleLogout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('username')
  emit('logout')
}
</script>

<style scoped>
.logout-btn {
  position: fixed;
  top: 16px;
  right: 16px;
  z-index: 999; /* garante que fique acima de tudo */
}
</style>
