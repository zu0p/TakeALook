<template>
  <div class="user-info">

      <h2>회원 정보 수정</h2>
      <el-form class="user-info-form" :model="info.form" :rules="info.rules" ref="userInfoForm" label-width="100px" label-position="left">
        <el-form-item label="이름">
            <el-input v-model="info.form.name"></el-input>
        </el-form-item>
        <el-form-item label="ID">
          <el-input disabled v-model="info.form.id"></el-input>
        </el-form-item>
        <el-form-item label="PW">
          <el-input v-model="info.form.password"></el-input>
        </el-form-item>

        <el-button type="primary" @click="modifyUserInfo">수정</el-button>
        <el-button type="danger" @click="dropoutUser">탈퇴</el-button>
      </el-form>

  </div>
</template>

<script>
import { reactive, ref } from '@vue/reactivity'
import { onBeforeMount } from '@vue/runtime-core'
import { useStore } from 'vuex'

export default {
  name: 'modify-user-info-page',

  setup(props, {emit}){
    const store = useStore()
    const userInfoForm = ref(null)

    const info = reactive({
      form:{
        name: '',
        id:'user1',
        password:'',
      },
      rules:{

      },
    })

    onBeforeMount(()=>{
      console.log('before mount')
      // 회원 정보 받아와서 폼의 prop로 보여주기
      // store.dispatch('root/requestUserInfo')
      //   .then(res=>{
      //     console.log(res)
      //     //info.form.id = res.data.id
      //   })
    })

    const modifyUserInfo = function(){
      // 회원 정보 수정
      console.log('modify user info')
      // const user = {
      //   user_id: info.form.id,
      //   name: info.form.name,
      //   password: info.form.password
      // }
      // store.dispatch('root/requestModifyUserInfo', user)
      //   .then(res=>{
      //     console.log('success')
      //     window.location = '/'
      //   })
    }

    const dropoutUser = function(){
        // 회원 탈퇴
        console.log('drop out user')
    }

    return {userInfoForm, info, modifyUserInfo, dropoutUser}
  }
}
</script>

<style>
.user-info{
  margin: 10%;
}
</style>
