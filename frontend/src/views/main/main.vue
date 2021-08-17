<template>
  <el-container class="main-wrapper">
    <main-header
      :isLogin="isLogin"
      :height="`70px`"
      @openLoginDialog="onOpenLoginDialog"
      @openSignupDialog="onOpenSignupDialog"/>
    <el-container class="main-container">
      <el-aside class="hide-on-small" width="240px">
        <main-sidebar
          :width="`240px`"/>
      </el-aside>
      <el-main>
        <main-dm v-if="isLogin"/>
        <router-view></router-view>
      </el-main>
    </el-container>
    <main-footer :height="`110px`"/>
  </el-container>
  <login-dialog
    :open="loginDialogOpen"
    @closeLoginDialog="onCloseLoginDialog"/>
  <signup-dialog
    :open="signupDialogOpen"
    @closeSignupDialog="onCloseSignupDialog"/>

</template>

<style>
  @import "https://unpkg.com/element-plus/lib/theme-chalk/index.css";
  @import './main.css';
  @import '../../common/css/common.css';
  @import '../../common/css/element-plus.css';

  /* DM 아이콘 */
.chat-launcher {
  width: 60px;
  height: 60px;
  background-position: center;
  background-repeat: no-repeat;
  position: fixed;
  right: 25px;
  bottom: 25px;
  border-radius: 50%;
  box-shadow: none;
  transition: box-shadow 0.2s ease-in-out;
  cursor: pointer;
}
</style>
<script>
import LoginDialog from './components/login-dialog'
import SignupDialog from './components/signup-dialog'
import MainHeader from './components/main-header'
import MainSidebar from './components/main-sidebar'
import MainFooter from './components/main-footer'
import MainDm from './components/main-dm'
import {mapActions} from 'vuex';
// DM 아이콘
import CloseIcon from '@/assets/images/close-icon.png'
import OpenIcon from '@/assets/images/chat-icon.svg'


export default {
  name: 'Main',
  components: {
    MainHeader,
    MainSidebar,
    MainFooter,
    LoginDialog,
    SignupDialog,
    MainDm,
  },
  props: {
    icons: {
      type: Object,
      default: function () {
        return {
          open: {
            img: OpenIcon,
            name: 'default'
          },
          close: {
            img: CloseIcon,
            name: 'default'
          }
        }
      }
    },
  },
  data () {
    return {
      loginDialogOpen: false,
      signupDialogOpen: false,
      isLogin: false
    }
  },
  methods: {
    ...mapActions(['requestLogin', 'requestUserInfo']),
    onOpenLoginDialog () {
      this.loginDialogOpen = true
    },
    onCloseLoginDialog () {
      this.loginDialogOpen = false
    },
    onOpenSignupDialog(){
      this.signupDialogOpen = true
    },
    onCloseSignupDialog(){
      this.signupDialogOpen = false
    }
  },
  created(){
    const userToken = localStorage.getItem('accessToken');

    console.log("logined user: "+userToken)
    console.log("1"+this.isLogin)
    if(userToken){//userToken이 있다면 == 로그인했었다면
      this.isLogin = true
    console.log("2"+this.isLogin)
      // const userData = JSON.parse(userToken)
      // requestUserInfo()
    }
  },
  // DM 아이콘
}
</script>
