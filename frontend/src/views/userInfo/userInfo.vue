<template>
  <el-container>
    <div  class="user-info">
      <el-form class="user-info-form" :model="info.form" :rules="info.rules" ref="userInfoForm" label-width="100px" label-position="left">
        <h1 class="form-title">회원 정보 수정</h1>
        <el-form-item prop="name" label="name">
            <el-input v-model="info.form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="id" label="id">
          <el-input disabled v-model="info.form.id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="email" label="e-mail">
          <el-input v-model="info.form.email" style="width:45%" autocomplete="off"></el-input>
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
        <el-form-item prop="address" label="address">
          <el-input v-model="info.form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="password"  label="password">
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
        address:[
          {required: true, message: '필수 입력 항목입니다.', trigger: ['blur','change']},
          {max: 30, message: '최대 30자까지 입력 가능합니다.'}
        ],
        email:[
          {required: true, message: '필수 입력 항목입니다.', trigger: ['blur','change']},
          {max: 30, message: '최대 30자까지 입력 가능합니다.'}
        ],
        name:[
          {required: true, message: '필수 입력 항목입니다.', trigger: ['blur','change']},
          {max:30, message: '최대 30자까지 입력 가능합니다.'}
        ],
        password: [
          {required:true, message:'필수 입력 항목입니다.'},
          {
            validator(rule, value, callback){
              if(!value)
                callback(new Error('필수 입력 항목입니다.'))
              if(value.length>16) //max
                callback(new Error('최대 16글자까지 입력 가능합니다.'))

              if(value.length<9)  //min
                callback(new Error('최소 9글자를 입력해야 합니다.'))

              if(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{9,}$/.test(value)){
                callback()
              }
              else{
                callback(new Error('비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다.'))
              }
            }
          },
        ]
      },
    })

    onBeforeMount(()=>{
      //console.log('before mount')
      // 회원 정보 받아와서 폼의 prop로 보여주기
      store.dispatch('root/requestUserInfo')
        .then(res=>{
          // console.log(res)
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
        userId: info.form.id,
        name: info.form.name,
        address: info.form.address,
        email: `${info.form.email}@${info.form.emailUrl.fix}`,
        password: info.form.password
      }
      // console.log(user)
      store.dispatch('root/requestModifyUserInfo', user)
        .then(res=>{
          // console.log(res)
          alert("수정이 완료되었습니다.")
          //window.location = '/userInfo'
        })
    }

    const dropoutUser = function(){
        // 회원 탈퇴
        let answer = confirm('탈퇴하시겠습니까?')
        if(answer){
          store.dispatch('root/requestDropoutUser')
            .then(res=>{
              // console.log(res)
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
