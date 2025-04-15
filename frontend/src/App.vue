<template>
  <v-app>
    <v-main class="d-flex align-center" style="min-height: 100vh;">
      <v-card title="Login" width="400" elevation="10">
        <v-card-text>
          <v-form>
            <v-text-field
              label="Username"
              variant="underlined"
              :rules="[requirements.required, requirements.min]"
              prepend-icon="mdi-account-circle"
            />
            <v-text-field
              :type="showPassword ? 'text' : 'password'"
              label="Password"
              :rules="[
                requirements.required,
                requirements.passwordMin,
                requirements.passwordLowerCase,
                requirements.passwordUpperCase,
                requirements.passwordNumber,
                requirements.passwordSpecial
              ]"
              variant="underlined"
              prepend-icon="mdi-lock"
              :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
              @click:append="showPassword = !showPassword"
            />
          </v-form>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions class="d-flex justify-center">
          <v-btn color="info" variant="elevated" class="mr-8">
            Login
          </v-btn>
          <v-btn color="success" variant="elevated">
            Register
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: 'App',

  data: () => ({
    showPassword: false,
    requirements: {
      required: value => !!value || 'Required.',
      min: v => v.length >= 6 || 'Min 6 characters',
      passwordMin: v => v.length >= 8 || 'Min 8 characters',
      passwordLowerCase: v => /[a-z]/.test(v) || 'At least one lowercase letter',
      passwordUpperCase: v => /[A-Z]/.test(v) || 'At least one uppercase letter',
      passwordNumber: v => /[0-9]/.test(v) || 'At least one number',
      passwordSpecial: v => /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]/.test(v) || 'At least one special character',
    }
  }),
}
</script>
<style>



</style>