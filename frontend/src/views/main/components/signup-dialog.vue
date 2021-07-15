<template>
  <el-dialog custom-class="signup-dialog" title="회원 가입" v-model="state.dialogVisible" @close="handleClose">

    <img v-if="state.loading" src="https://i.imgur.com/JfPpwOA.gif">

    <el-form v-if="!state.loading" :model="state.form" :rules="state.rules" ref="signupForm" :label-position="state.form.align">
      <el-form-item prop="department" label="소속" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.department" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="position" label="직책" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.position" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="name" label="이름" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="id" label="아이디" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.id" autocomplete="off" @change="changeId"></el-input>
        <el-button @click="clickCheckDupl">중복 확인</el-button>
      </el-form-item>
      <el-form-item prop="password" label="비밀번호" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.password" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item prop="chk_password" label="비밀번호 확인" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.chk_password" autocomplete="off" show-password></el-input>
      </el-form-item>
    </el-form>

    <template #footer v-if="!state.loading">
      <span class="dialog-footer">
        <el-button
          type="primary"
          @click="clickSignup"
          :disabled="false"
          >
          가입하기
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed, ref, onMounted, watch } from 'vue'
import { useStore } from 'vuex'

export default {
  name: 'signup-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore()
    // 독립적인 반응형 값 생성 ref()
    const signupForm = ref(null)

    /*
      // Element UI Validator
      // rules의 객체 키 값과 form의 객체 키 값이 같아야 매칭되어 적용됨
      //
    */
    const state = reactive({
      form: {
        department:'',
        position:'',
        name:'',
        id: '',
        password: '',
        chk_password:'',
        align: 'left',
      },
      rules: {
        department:[
          {max: 30, message: '최대 30자까지 입력 가능합니다.'}
        ],
        position:[
          {max: 30, message: '최대 30자까지 입력 가능합니다.'}
        ],
        name:[
          {required: true, message: '필수 입력 항목입니다.', trigger: 'blur'},
          {max:30, message: '최대 30자까지 입력 가능합니다.'}
        ],
        id: [
          {required: true, message: '필수 입력 항목입니다.', trigger: 'blur'},
          {max:16, message: '최대 16자까지 입력 가능합니다.'}
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
        ],
        chk_password:[
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
                if(value==state.form.password)
                  callback()
                else
                  callback(new Error('입력한 비밀번호와 일치하지 않습니다.'))
              }
              else{
                callback(new Error('비밀번호는 영문, 숫자, 특수문자가 조합되어야 합니다.'))
              }
            }
          },
        ],
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px',
      idValicate: false,
      disableButton: true,
      loading: false
    })

    watch(()=>{
      // console.log(state.form.id)
      // console.log(signupForm.value)
      console.log(state.disableButton+" // "+state.idValicate)
    })

    onMounted(() => {
      // console.log(signupForm.value)
    })

    const changeId = function(){
      state.idValicate = false
    }

    const changeForm = function(){
      signupForm.value.validate(v=>{
      console.log(v)
        if(v && state.idValicate){
          state.disableButton = false
          console.log(state.disableButton)
        }
      })
    }
    const clickCheckDupl = function(){
      //console.log("duplication check!!")
      store.dispatch('root/requestCheckDupl', {id: state.form.id})
        .then(res=>{
          //console.log(res)
          if(res.data.statusCode == 409){
            alert(res.data.message)
            state.form.id = ''
          }
          else{
            alert("사용 가능한 아이디 입니다.")
            state.idValicate = true
          }
        })
        .catch(err=>{
          console.log(err)
        })
    }

    const clickSignup = function () {
      state.loading = true
      // 가입하기 클릭 시 validate 체크 후 그 결과 값에 따라, 회원가입 API 호출 또는 경고창 표시
      signupForm.value.validate((valid) => {
        if (valid && state.idValicate) {
          //console.log('submit')
          const body={
            user_id:state.form.id,
            password:state.form.password,
            name:state.form.name,
            department:state.form.department,
            position:state.form.position
          }
          store.dispatch('root/requestSignup', body)
            .then(res=>{
              //console.log(res.data)
              if(res.data.statusCode==200){
                alert('회원 가입이 완료되었습니다.')
                emit('closeSignupDialog')
                //window.location="/"
              }
            })
            .then(()=>{
              state.loading = false
            })
            .catch(err=>{
              state.loading = false
              alert("회원 가입에 실패하였습니다.")
              console.log(err)
            })
        } else if(!valid){
          alert('필수 항목을 입력하세요.')
        } else if(!state.idValicate){
          alert('아이디 중복확인이 필요합니다.')
        }
      });
    }

    const handleClose = function () {
      state.form.department = ''
      state.form.position = ''
      state.form.name =''
      state.form.id = ''
      state.form.password = ''
      state.form.chk_password = ''
      emit('closeSignupDialog')
    }

    return { signupForm, state, clickSignup, changeId, changeForm, clickCheckDupl, handleClose }
  }
}
</script>

<style>
.login-dialog {
  width: 400px !important;
  height: 300px;
}
.login-dialog .el-dialog__headerbtn {
  float: right;
}
.login-dialog .el-form-item__content {
  margin-left: 0 !important;
  float: right;
  width: 200px;
  display: inline-block;
}
.login-dialog .el-form-item {
  margin-bottom: 20px;
}
.login-dialog .el-form-item__error {
  font-size: 12px;
  color: red;
}
.login-dialog .el-input__suffix {
  display: none;
}
.login-dialog .el-dialog__footer {
  margin: 0 calc(50% - 80px);
  padding-top: 0;
  display: inline-block;
}
.login-dialog .dialog-footer .el-button {
  width: 120px;
}
</style>
