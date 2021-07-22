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
        <el-form-item label="e-mail">
          <el-input v-model="info.form.email"></el-input>
        </el-form-item>
        <el-form-item label="Address">
          <el-input v-model="info.form.address"></el-input>
        </el-form-item>
        <el-form-item label="PW">
          <el-input v-model="info.form.password" autocomplete="off" show-password></el-input>
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
        id:'',
        password:'',
        email:'',
        address:''
      },
      rules:{

      },
    })

    onBeforeMount(()=>{
      //console.log('before mount')
      // 회원 정보 받아와서 폼의 prop로 보여주기
      store.dispatch('root/requestUserInfo')
        .then(res=>{
          //console.log(res)
          info.form.id = res.data.userId
          info.form.name = res.data.name
          info.form.email = res.data.email
          info.form.address = res.data.address
        })
    })

    const modifyUserInfo = function(){
      // 회원 정보 수정
      //console.log('modify user info')
      const user = {
        id: info.form.id,
        name: info.form.name,
        email: info.form.email,
        address: info.form.address
      }
      store.dispatch('root/requestModifyUserInfo', user)
        .then(res=>{
          //console.log('success')
          alert("수정이 완료되었습니다.")
          window.location = '/userInfo'
        })
    }

    const dropoutUser = function(){
        // 회원 탈퇴
        let answer = confirm('탈퇴하시겠습니까?')
        if(answer){
          store.dispatch('root/requestDropoutUser', {id: info.form.id})
            .then(res=>{
              console.log(res)
              alert('탈퇴되었습니다.')
              localStorage.removeItem('accessToken')
              window.location = '/'
            })
        }
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
