<template>
  <el-container>
    <div  class="user-info">
      <el-form class="user-info-form" :model="info.form" :rules="info.rules" ref="userInfoForm" label-width="100px" label-position="left">
        <h1 class="form-title">회원 정보 수정</h1>
        <el-form-item label="이름">
            <el-input v-model="info.form.name"></el-input>
        </el-form-item>
        <el-form-item label="ID">
          <el-input disabled v-model="info.form.id"></el-input>
        </el-form-item>
        <el-form-item label="e-mail">
          <el-input v-model="info.form.email" style="width:45%"></el-input>
          <span style="margin:0 5px 0 5px" >@</span>
          <el-select v-model="info.form.emailUrl.fix">
            <el-option
              v-for="item in info.form.emailUrl.options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
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
  </el-container>
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
        emailUrl:{
          fix:'',
          options:[
            {
              value: 'naver.com',
              label: 'naver.com',
            },
            {
              value: 'kakao.com',
              label: 'kakao.com',
            },
            {
              value: 'google.com',
              label: 'google.com',
            },
          ]
      },
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
          info.form.address = res.data.address

          let fullEmail = res.data.email.split('@')
          info.form.email = fullEmail[0]
          info.form.emailUrl.fix = fullEmail[1]
        })
    })

    const modifyUserInfo = function(){
      // 회원 정보 수정
      //console.log('modify user info')
      const user = {
        id: info.form.id,
        name: info.form.name,
        address: info.form.address,
        email: `${info.form.email}@${info.form.emailUrl.fix}`
      }
      console.log(user)
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
  margin: 0 25% 0 25%;
}

.form-title{
  margin: 50px 0 50px 0;
}
</style>
