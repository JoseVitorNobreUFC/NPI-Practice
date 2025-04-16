<template>
  <v-dialog v-model="dialog" max-width="500">
    <v-card>
      <v-card-title>
        <span class="text-h6">{{ usuario ? 'Editar Usuário' : 'Criar Usuário' }}</span>
      </v-card-title>

      <v-card-text>
        <v-form ref="formRef">
          <v-text-field v-model="form.nome" label="Nome" required />
          <v-text-field v-model="form.email" label="Email" required type="email" />
          <v-text-field v-model="form.password" label="Senha" type="password"
            :placeholder="usuario ? 'Deixe em branco para manter' : ''" />

          <v-select v-model="form.cursoId" :items="cursos" item-value="id" item-title="label" label="Curso" required />
          <v-switch v-model="form.habilitado" label="Habilitado" />

        </v-form>
      </v-card-text>

      <v-card-actions>
        <v-spacer />
        <v-btn text @click="dialog = false">Cancelar</v-btn>
        <v-btn color="primary" @click="salvar" :disabled="!podeSalvar">Salvar</v-btn>
      </v-card-actions>
    </v-card>
    <v-snackbar v-model="snackbar" :color="snackbarColor" timeout="3000" location="bottom center">
      <v-icon class="mr-2" icon="mdi-check-circle" v-if="snackbarColor === 'success'" />
      <v-icon class="mr-2" icon="mdi-alert-circle" v-else />
      {{ snackbarMessage }}
    </v-snackbar>

  </v-dialog>
</template>

<script setup lang="ts">
import { ref, watch, computed, onMounted } from 'vue'
const snackbar = ref(false)
const snackbarMessage = ref('')
const snackbarColor = ref('error')

const emailValido = computed(() => {
  const email = form.value.email
  const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  return regex.test(email)
})

const podeSalvar = computed(() => {
  const f = form.value
  const criando = !props.usuario
  const camposValidos = f.nome && f.email && f.cursoId !== null && emailValido.value
  return criando ? camposValidos : emailValido.value
})

const props = defineProps<{
  modelValue: boolean
  usuario: any | null
}>()

const emit = defineEmits(['update:modelValue', 'atualizarLista'])

const dialog = computed({
  get: () => props.modelValue,
  set: (val: boolean) => emit('update:modelValue', val),
})

const form = ref({
  nome: '',
  email: '',
  password: '',
  habilitado: true,
  cursoId: null,
})

const cursos = ref<any[]>([])

const buscarCursos = async () => {
  const token = localStorage.getItem('token')
  if (!token) return

  try {
    const response = await fetch('/api/cursos', {
      headers: {
        Authorization: token,
      },
    })
    if (!response.ok) throw new Error('Erro ao buscar cursos')
    const data = await response.json()
    cursos.value = data.map((curso: any) => ({
      ...curso,
      label: `${curso.nome} (${curso.sigla})`,
    }))
  } catch (error) {
    console.error('Erro ao carregar cursos:', error)
  }
}

const formRef = ref()

watch(
  () => props.usuario,
  (novo) => {
    if (novo) {
      form.value = {
        nome: novo.nome || '',
        email: novo.email || '',
        password: '',
        habilitado: novo.habilitado ?? true,
        cursoId: novo.curso?.id || null,
      }
    } else {
      form.value = {
        nome: '',
        email: '',
        password: '',
        habilitado: true,
        cursoId: null,
      }
    }
  },
  { immediate: true }
)

const salvar = async () => {
  const token = localStorage.getItem('token')
  if (!token) return

  const criando = !props.usuario

  if (criando && (!form.value.nome || !form.value.email || !form.value.cursoId || !emailValido.value)) {
    snackbarMessage.value = 'Preencha todos os campos obrigatórios com informações válidas.'
    snackbarColor.value = 'error'
    snackbar.value = true
    return
  }

  const payload: any = {
    nome: form.value.nome,
    email: form.value.email,
    habilitado: form.value.habilitado,
    cursoId: form.value.cursoId,
  }

  if (form.value.password) {
    payload.password = form.value.password
  }

  try {
    let response
    if (props.usuario) {
      response = await fetch(`/api/usuarios/${props.usuario.id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
        body: JSON.stringify(payload),
      })
    } else {
      response = await fetch('/api/usuarios', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
        body: JSON.stringify(payload),
      })
    }

    const data = await response.json()

    if (!response.ok) {
      snackbarMessage.value = data.error || 'Erro ao salvar usuário'
      snackbarColor.value = 'error'
      snackbar.value = true
      return
    }

    snackbarMessage.value = props.usuario ? 'Usuário atualizado com sucesso!' : 'Usuário criado com sucesso!'
    snackbarColor.value = 'success'
    snackbar.value = true

    dialog.value = false
    emit('atualizarLista')
    form.value = {
        nome: '',
        email: '',
        password: '',
        habilitado: true,
        cursoId: null,
    }
  } catch (error) {
    console.error('Erro ao salvar usuário:', error)
    snackbarMessage.value = 'Erro de conexão com o servidor'
    snackbarColor.value = 'error'
    snackbar.value = true
  }
}


onMounted(buscarCursos)
</script>
