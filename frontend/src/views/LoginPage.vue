<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const email = ref('')
const password = ref('')
const showPassword = ref(false)

const required = (value: string) => !!value || 'Required.'

// Snackbar state
const snackbar = ref(false)
const snackbarMessage = ref('')
const snackbarColor = ref('error')

const login = async () => {
  try {
    const response = await fetch('/api/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: email.value,
        password: password.value,
      }),
    })

    const data = await response.json()
    console.log(data)
    // Exibir toast de erro
    if (!response.ok) {
      snackbarMessage.value = data.error || 'Erro ao fazer login'
      snackbarColor.value = 'error'
      snackbar.value = true
      return
    }

    localStorage.setItem('token', data.token)
    localStorage.setItem('username', data.username)
    
    // Exibir toast de sucesso com ícone
    snackbarColor.value = 'success'
    snackbarMessage.value = 'Login realizado com sucesso!'
    snackbar.value = true

    // Aguarda um pequeno tempo para o toast aparecer antes do redirecionamento
    setTimeout(() => {
      router.push('/home')
    }, 500)

  } catch (error) {
    snackbarMessage.value = 'Erro de conexão com o servidor'
    snackbarColor.value = 'error'
    snackbar.value = true
    console.error(error)
  }
}


</script>

<template>
  <v-app>
    <v-main class="d-flex align-center" style="min-height: 100vh;">
      <v-card title="Login" width="400" elevation="10">
        <v-card-text>
          <v-form>
            <v-text-field
              label="Email"
              type="email"
              v-model="email"
              :rules="[required]"
              variant="underlined"
              prepend-icon="mdi-account-circle"
            />
            <v-text-field
              :type="showPassword ? 'text' : 'password'"
              label="Password"
              v-model="password"
              :rules="[required]"
              variant="underlined"
              prepend-icon="mdi-lock"
              :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
              @click:append="showPassword = !showPassword"
            />
          </v-form>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions class="d-flex justify-center">
          <v-btn 
            color="success" 
            variant="elevated" 
            class="mr-8" 
            @click="login"
            :disabled="!email || !password"
          >
            Login
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-main>
    <v-snackbar
      v-model="snackbar"
      :color="snackbarColor"
      timeout="3000"
      location="bottom center"
    >
      <v-icon class="mr-2" icon="mdi-check-circle" v-if="snackbarColor === 'success'" />
      <v-icon class="mr-2" icon="mdi-alert-circle" v-else />
      {{ snackbarMessage }}
    </v-snackbar>

  </v-app>
</template>