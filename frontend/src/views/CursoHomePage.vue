<template>
  <LogoutButton @logout="logout" />
  <main class="pa-6">
    <div class="d-flex justify-space-between align-center mb-4">
      <h1 class="text-h5 mr-8">Cursos Cadastrados</h1>
      <div class="d-flex ga-2">
        <v-btn color="success" @click="abrirDialog()">
          <v-icon start>mdi-book-plus</v-icon>
          Criar Curso
        </v-btn>
        <v-btn color="primary" @click="irParaHome()">
          <v-icon start>mdi-home</v-icon>
          Voltar para Home
        </v-btn>
      </div>
    </div>


    <v-data-table :headers="headers" :items="cursos" class="mt-4" item-value="id">
      <template #item.actions="{ item }">
        <div class="d-flex ga-4">
          <v-btn icon color="primary" @click="abrirDialog(item)">
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
          <v-btn icon color="error" @click="deletarCurso(item.id)">
            <v-icon>mdi-delete</v-icon>
          </v-btn>
        </div>
      </template>
    </v-data-table>
    <v-snackbar v-model="snackbar" :color="snackbarColor" timeout="3000" location="bottom center">
      <v-icon class="mr-2" icon="mdi-check-circle" v-if="snackbarColor === 'success'" />
      <v-icon class="mr-2" icon="mdi-alert-circle" v-else />
      {{ snackbarMessage }}
    </v-snackbar>

    <CursoFormDialog v-model="dialog" :curso="cursoSelecionado" @atualizarLista="fetchCursos" />
  </main>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
const snackbar = ref(false)
const snackbarMessage = ref('')
const snackbarColor = ref('error')

import CursoFormDialog from '@/components/CursoFormDialog.vue'
import LogoutButton from '@/components/LogoutButtom.vue'

const cursos = ref<any[]>([])
const dialog = ref(false)
const cursoSelecionado = ref<any | null>(null)
const router = useRouter()

const headers = [
  { title: 'Nome', key: 'nome' },
  { title: 'Sigla', key: 'sigla' },
  { title: 'Turno', key: 'turno' },
  { title: 'Ações', key: 'actions', sortable: false },
]

const fetchCursos = async () => {
  try {
    const token = localStorage.getItem('token')
    const res = await fetch('/api/cursos', {
      headers: {
        Authorization: token || '',
      },
    })

    if (!res.ok) throw new Error('Erro ao buscar cursos')

    cursos.value = await res.json()
  } catch (error) {
    console.error('Erro ao carregar cursos:', error)
  }
}

const abrirDialog = (curso: any | null = null) => {
  cursoSelecionado.value = curso ? { ...curso } : null
  dialog.value = true
}

const deletarCurso = async (id: number) => {
  const confirmar = confirm('Tem certeza que deseja deletar este curso?')
  if (!confirmar) return

  try {
    const token = localStorage.getItem('token')
    const res = await fetch(`/api/cursos/${id}`, {
      method: 'DELETE',
      headers: {
        Authorization: token || '',
      },
    })

    const data = await res.json()

    if (!res.ok) {
      snackbarMessage.value = data.error || 'Erro ao deletar curso'
      snackbarColor.value = 'error'
      snackbar.value = true
      return
    }

    snackbarMessage.value = 'Curso deletado com sucesso!'
    snackbarColor.value = 'success'
    snackbar.value = true

    await fetchCursos()
  } catch (error) {
    console.error('Erro ao deletar curso:', error)
    snackbarMessage.value = 'Erro ao deletar curso'
    snackbarColor.value = 'error'
    snackbar.value = true
  }
}

const logout = () => {
  snackbarMessage.value = 'Logout realizado com sucesso!'
  snackbarColor.value = 'success'
  snackbar.value = true

  setTimeout(() => {
    router.push('/')
  }, 500)
}

const irParaHome = () => {
  router.push('/home')
}

onMounted(fetchCursos)
</script>