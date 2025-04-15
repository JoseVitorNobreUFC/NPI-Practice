<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const usuarios = ref<any[]>([])
const loading = ref(true)

const fetchUsuarios = async () => {
  const token = localStorage.getItem('token')
  if (!token) {
    router.push('/login')
    return
  }

  try {
    const response = await fetch('/api/usuarios', {
      headers: {
        Authorization: token || '',
      },
    })

    if (!response.ok) {
      throw new Error('Erro ao buscar usuários')
    }

    const data = await response.json()
    usuarios.value = data
  
  } catch (error) {
    console.error(error)
    router.push('/login') // caso o token seja inválido
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchUsuarios()
})
</script>
<template>
  <main class="pa-6">
    <h1 class="text-h5 mb-4">Usuários Cadastrados</h1>
    <v-table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Nome</th>
          <th>Email</th>
          <th>Habilitado</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="usuario in usuarios" :key="usuario.id">
          <td>{{ usuario.id }}</td>
          <td>{{ usuario.nome }}</td>
          <td>{{ usuario.email }}</td>
          <td>{{ usuario.habilitado ? 'Sim' : 'Não' }}</td>
        </tr>
      </tbody>
    </v-table>
  </main>
</template>
