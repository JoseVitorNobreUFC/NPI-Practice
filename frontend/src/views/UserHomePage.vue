<template>
  <LogoutButton @logout="logout" />
  <main class="pa-6">
    <div class="d-flex justify-space-between align-center mb-4">
      <h1 class="text-h5">Usuários Cadastrados</h1>
      <div>
        <v-btn color="success" @click="abrirDialog()" class="mr-4">
          <v-icon start>mdi-account-plus</v-icon>
          Criar Usuário
        </v-btn>
        <v-btn color="primary" @click="irParaCursos()">
          <v-icon start>mdi-home</v-icon>
          Pagina de Cursos
        </v-btn>
      </div>
    </div>

    <v-data-table :headers="headers" :items="usuarios" class="mt-4" item-value="id">
      <template #item.habilitado="{ item }">
        <div class="d-flex align-center justify-center">
          <v-icon :color="item.habilitado ? 'success' : 'error'">
            {{ item.habilitado ? 'mdi-check-circle' : 'mdi-close-circle' }}
          </v-icon>
        </div>
      </template>
      <template #item.curso="{ item }">
        {{ item.curso ? `${item.curso.nome} (${item.curso.sigla})` : 'Não informado' }}
      </template>

      <template #item.actions="{ item }">
        <div class="d-flex ga-4">
          <v-btn icon color="primary" @click="abrirDialog(item)">
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
          <v-btn icon color="error" @click="deletarUsuario(item.id)">
            <v-icon>mdi-delete</v-icon>
          </v-btn>
        </div>
      </template>
    </v-data-table>

    <UsuarioFormDialog v-model="dialog" :usuario="usuarioSelecionado" @atualizarLista="fetchUsuarios" />
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

  </main>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import UsuarioFormDialog from '@/components/UsuarioFormDialog.vue'
import LogoutButton from '@/components/LogoutButtom.vue'

const usuarios = ref<any[]>([])
const dialog = ref(false)
const usuarioSelecionado = ref<any | null>(null)
const snackbar = ref(false)
const snackbarMessage = ref('')
const snackbarColor = ref('error')

const router = useRouter()

const headers = [
  { title: 'Nome', key: 'nome' },
  { title: 'Email', key: 'email' },
  { title: 'Habilitado', key: 'habilitado' },
  { title: 'Curso', key: 'curso' },
  { title: 'Ações', key: 'actions', sortable: false },
]


const fetchUsuarios = async () => {
  try {
    const token = localStorage.getItem('token')
    const res = await fetch('/api/usuarios', {
      headers: {
        Authorization: token || '',
      },
    })

    if (!res.ok) throw new Error('Erro ao buscar usuários')

    usuarios.value = await res.json()
  } catch (error) {
    console.error(error)
  }
}

const abrirDialog = (usuario: any | null = null) => {
  usuarioSelecionado.value = usuario
  dialog.value = true
}

const deletarUsuario = async (id: number) => {
  const confirmar = confirm('Tem certeza que deseja deletar este usuário?')
  if (!confirmar) return

  try {
    const token = localStorage.getItem('token')
    const res = await fetch(`/api/usuarios/${id}`, {
      method: 'DELETE',
      headers: {
        Authorization: token || '',
      },
    })

    const data = await res.json()

    if (!res.ok) {
      snackbarMessage.value = data.error || 'Erro ao deletar usuário'
      snackbarColor.value = 'error'
      snackbar.value = true
      return
    }

    snackbarMessage.value = 'Usuário deletado com sucesso!'
    snackbarColor.value = 'success'
    snackbar.value = true

    await fetchUsuarios()
  } catch (error) {
    console.error(error)
    snackbarMessage.value = 'Erro de conexão com o servidor'
    snackbarColor.value = 'error'
    snackbar.value = true
  }
}


const irParaCursos = () => {
  router.push('/cursos')
}
const logout = () => {
  snackbarMessage.value = 'Logout realizado com sucesso!'
  snackbarColor.value = 'success'
  snackbar.value = true

  setTimeout(() => {
    router.push('/')
  }, 500)
}

onMounted(() => {
  fetchUsuarios()
})
</script>