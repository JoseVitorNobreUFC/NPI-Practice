<template>
  <v-dialog v-model="dialog" max-width="500">
    <v-card>
      <v-card-title>
        <span class="text-h6">{{ curso ? 'Editar Curso' : 'Criar Curso' }}</span>
      </v-card-title>

      <v-card-text>
        <v-form ref="formRef">
          <v-text-field v-model="form.nome" label="Nome" required />
          <v-text-field v-model="form.sigla" label="Sigla" required />

          <v-select v-model="form.turno" :items="turnos" label="Turno" required />
        </v-form>
      </v-card-text>

      <v-card-actions>
        <v-spacer />
        <v-btn text @click="dialog = false">Cancelar</v-btn>
        <v-btn color="primary" @click="salvar">Salvar</v-btn>
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
import { ref, watch, computed } from 'vue'
import { Turno } from '@/enums/Turno'
const snackbar = ref(false)
const snackbarMessage = ref('')
const snackbarColor = ref('error')

const props = defineProps<{
  modelValue: boolean
  curso: any | null
}>()

const emit = defineEmits(['update:modelValue', 'atualizarLista'])

const dialog = computed({
  get: () => props.modelValue,
  set: (val: boolean) => emit('update:modelValue', val),
})

const formRef = ref()
const turnos = Object.values(Turno)

const form = ref({
  nome: '',
  sigla: '',
  turno: Turno.MANHA,
})

watch(
  () => props.curso,
  (novo) => {
    if (novo) {
      form.value = {
        nome: novo.nome || '',
        sigla: novo.sigla || '',
        turno: novo.turno || Turno.MANHA,
      }
    } else {
      form.value = {
        nome: '',
        sigla: '',
        turno: Turno.MANHA,
      }
    }
  },
  { immediate: true }
)

const salvar = async () => {
  const token = localStorage.getItem('token')
  if (!token) return

  const criando = !props.curso

  // Verifica se campos obrigatórios estão preenchidos
  if (criando && (!form.value.nome || !form.value.sigla)) {
    snackbarMessage.value = 'Preencha todos os campos obrigatórios.'
    snackbarColor.value = 'error'
    snackbar.value = true
    return
  }

  const payload: any = {
    nome: form.value.nome,
    sigla: form.value.sigla,
    turno: form.value.turno,
  }

  try {
    console.log(payload)
    let response
    if (props.curso) {
      response = await fetch(`/api/cursos/${props.curso.id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
        body: JSON.stringify(payload),
      })
    } else {
      response = await fetch('/api/cursos', {
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
      snackbarMessage.value = data.error || 'Erro ao salvar curso'
      snackbarColor.value = 'error'
      snackbar.value = true
      return
    }

    snackbarMessage.value = props.curso ? 'Curso atualizado com sucesso!' : 'Curso criado com sucesso!'
    snackbarColor.value = 'success'
    snackbar.value = true

    dialog.value = false
    emit('atualizarLista')
    form.value = {
        nome: '',
        sigla: '',
        turno: Turno.MANHA,
    }
  } catch (error) {
    console.error('Erro ao salvar curso:', error)
    snackbarMessage.value = 'Erro de conexão com o servidor'
    snackbarColor.value = 'error'
    snackbar.value = true
  }
}

</script>
