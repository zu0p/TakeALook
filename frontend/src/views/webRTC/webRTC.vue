<template>
  <el-container>
    <el-form class="webRTC-form" :model="info.form" :rules="info.rules" ref="webRTCForm" label-width="100px" label-position="left">
        <h1 class="form-title">Meeting</h1>
        <el-form-item label="User id">
            <el-input v-model="info.form.id"></el-input>
        </el-form-item>
        <el-form-item label="Room id">
            <el-input v-model="info.form.roomId"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="joinMeeting">join</el-button>
        </el-form-item>
    </el-form>
  </el-container>
</template>

<script>
import { reactive, ref } from '@vue/reactivity'
import { useStore } from 'vuex'

export default {
  name: 'webRTC-page',

  setup(props, {emit}){
    const store = useStore()

    const webRTCForm = ref(null)
    const info = reactive({
      form:{
        id: '',
        roomId: ''
      }
    })

    const joinMeeting = function(){
      window.location = `/meeting/${info.form.roomId}/${info.form.id}`
    }
    return {info, joinMeeting}
  }
}
</script>

<style>

</style>
