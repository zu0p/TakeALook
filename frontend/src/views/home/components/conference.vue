<template>
  <!-- 로그인한 경우 -->
  <div v-if="info.isLogin" style="text-align:center">
      <!-- 거래가 끝난 경우 -->
      <el-card :body-style="{ padding: '0px' }" v-if="info.isSold">
        <div class="image-wrapper enddeal">
            <!-- 내 거래인 경우 -->
            <a class="custom-icon2" v-if="info.mine" style="color:red;"  @click="deleteDeal"><i class="el-icon-delete-solid"></i></a>
            <!-- 찜한 거래인 경우 -->
            <a class="custom-icon2" v-else style="color:red;"  @click="deleteLike"><i class="el-icon-delete-solid"></i></a>
            <img :src="imgUrl" alt="" style="width: 100%; display: block;">
        </div>
      <div style="text-align:left; padding: 14px;">
        <span class="title enddeal">{{ deal.productName }}<span style="color:lightgray; font-size:11px; margin-left:5px;">{{ deal.categories }}</span></span>
        <div class="loginbottom">
          <p style="margin-bottom:0;">{{ deal.basePrice }}원⠀|⠀
              {{ deal.reserveTime.slice(5, 7) }}월 {{ deal.reserveTime.slice(8, 10) }}일
              {{ deal.reserveTime.slice(11, 13) }}시 {{ deal.reserveTime.slice(14, 16) }}분 </p>
          <div>
            <!-- 내가 생성한 거래인 경우 -->
            <div style="text-align:center" v-if="info.mine">
              <el-button class="buyer" type="info" plain disabled style="margin-top:20px; text-align:center;" @click="deleteDeal">구매자: {{ deal.buyer }}</el-button>
            </div>
            <!-- 찜한 거래인 경우 -->
            <div style="text-align:center" v-else>
              <el-button class="buyer" type="info" plain disabled style="margin-top:20px; text-align:center;">거래가 완료된 상품입니다</el-button>
            </div>
          </div>
        </div>
      </div>
    </el-card>


      <!-- 거래가 시작되지 않은 경우 -->
    <el-card :body-style="{ padding: '0px' }" shadow="hover" v-else>
      <div class="image-wrapper">
        <img :src="imgUrl" alt="" style="width: 100%; display: block;">
      </div>
      <div style="text-align:left; padding: 14px;" @click="dealDetail">
        <span class="title">{{ deal.productName }}<span style="color:lightgray; font-size:11px; margin-left:5px;">{{ deal.categories }}</span></span>
        <div class="loginbottom">
          <p style="margin-bottom:0;">{{ deal.basePrice }}원⠀|⠀
              {{ deal.reserveTime.slice(5, 7) }}월 {{ deal.reserveTime.slice(8, 10) }}일
              {{ deal.reserveTime.slice(11, 13) }}시 {{ deal.reserveTime.slice(14, 16) }}분 </p>
            <!-- 내가 생성한 거래인 경우 -->
          <div v-if="info.mine">
            <div style="text-align:right">
              <i class="el-icon-message-solid" style="color:#ffd04b; margin-right:10px;"></i>
              <span style="color:#ffd04b; margin-right:70px;">{{ info.wishCount }}</span>
              <el-button type="info" @click="updateDeal" style="margin-top:30px;" size="small">거래 수정</el-button>
              <el-button type="primary" @click="startDeal" style="margin-top:30px" size="small">거래 시작</el-button>
            </div>
          </div>
            <!-- 내가 생성한 거래가 아닌 경우 -->
          <div v-else>
            <!-- 찜한 경우 -->
            <div style="text-align:right; margin-top:30px" v-if="info.wish">
              <el-button type="primary" @click="joinTrade" size="small">입장</el-button>
              <span style="color:#ffd04b; margin-right:10px;">{{ info.wishCount }}</span>
              <a class="custom-icon" @click="deletelikeDeal" style="color:#ffd04b;"><i class="el-icon-message-solid"></i></a>
            </div>
              <!-- 찜하지 않은 경우 -->
            <div style="text-align:right; margin-top:30px" v-else>
              <span style="color:#ffd04b; margin-right:10px;">{{ info.wishCount }}</span>
              <a class="custom-icon" @click="likeDeal" style="color:#ffd04b;"><i class="el-icon-bell"></i></a>
            </div>
          </div>
        </div>
      </div>
    </el-card>
  </div>


  <!-- 로그인을 하지 않은 경우 -->
  <div v-else style="text-align:center">
    <el-card :body-style="{ padding: '0px' }" shadow="hover">
      <div class="image-wrapper">
        <img :src="imgUrl" alt="" style="width: 100%; display: block;">
      </div>
      <div style="text-align: left; padding: 14px;">
        <span class="title">{{ deal.productName }}<span style="color:lightgray; font-size:11px; margin-left:5px;">{{ deal.categories }}</span></span>
        <div class="bottom" style="margin-top:10px;">
          <p style="margin-bottom:0;">{{ deal.basePrice }}원⠀|⠀
              {{ deal.reserveTime.slice(5, 7) }}월 {{ deal.reserveTime.slice(8, 10) }}일
              {{ deal.reserveTime.slice(11, 13) }}시 {{ deal.reserveTime.slice(14, 16) }}분 </p>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'

export default {
  name: 'Home',

  props: ["deal"],

  setup (props,{emit}) {
    // console.log(props)
    const store = useStore()
    const router = useRouter()

    const imgUrl = require(`@/assets/pimages/${deal.productId}.jpg`)
    const info = reactive({
      wishCount: 0,
      isLogin: false,
      mine: false,
      isSold: false,
      wish: false
    })

    if(localStorage.accessToken){
      store.dispatch('root/requestUserInfo')
        .then(res=>{
          info.isLogin = true
          if(res.data.userId == props.deal.seller){
            info.mine = true
          }
        })
        store.dispatch('root/requestWishCount', props.deal.productId)
          .then(res=>{
            info.wishCount = res.data.wishCount
            store.dispatch('root/requestCheckWish', props.deal.productId)
              .then(res=>{
                if (res.data.statusCode == 200) {
                  info.wish = true
                }
              })
          })
      }

    const updateDeal = function (e) {
      e.stopPropagation()
      router.push({
        name: 'update-deal-form',
        params: {
          productId: props.deal.productId,
        }
      })
    }

    const startDeal = function (e) {
      e.stopPropagation()
      console.log("거래 시작")
      emit('startTrade', props.deal)
    }

    const deletelikeDeal = function (e) {
      e.stopPropagation()
      store.dispatch('root/requestDeleteLikeDeal', props.deal.productId)
      .then(res=>{
        info.wish = false
        store.dispatch('root/requestWishCount', props.deal.productId)
          .then(res=>{
            info.wishCount = res.data.wishCount
            router.go()
          })
      })
    }

    const likeDeal = function (e) {
      e.stopPropagation()
      store.dispatch('root/requestLikeDeal', props.deal.productId)
      .then(res=>{
        info.wish = true
        store.dispatch('root/requestWishCount', props.deal.productId)
          .then(res=>{
            info.wishCount = res.data.wishCount
          })
      })
    }

    const joinTrade = function(e){
      e.stopPropagation()
      emit('buyerJoin', props.deal.productId, props.deal.basePrice)
    }

  return { imgUrl, info, updateDeal, startDeal, deletelikeDeal, likeDeal, joinTrade }
  }
}
</script>

<style>
.el-card {
  margin: 0 8px;
  margin-bottom: 30px;
}

.enddeal {
  color: lightgray;
  opacity: 0.4;
}

.buyer {
  pointer-events: none;
}

.el-card .image-wrapper {
  width: 100%;
  height: 190px;
}

.el-card .title {
  font-weight: bold;
}

.el-card .bottom .loginbottom {
  margin-top: 5px;
  display:-webkit-box;
  word-wrap:break-word;
  -webkit-box-orient:vertical;
  overflow:hidden;
  text-overflow:ellipsis;
}

.custom-icon {
  font-size: 30px;
}

.custom-icon2 {
  font-size: 30px;
  position: absolute;
  margin-left:65%;
  margin-top:15px;
}

.image-wrapper {
  position: relative;
}

/* 테블릿, 모바일의 경우 두 줄 말줄임표시 */
@media (max-width: 1269px) {
  .el-card .bottom .loginbottom {
    -webkit-line-clamp: 2;
    height:90px;
  }
}
/* 데스크탑의 경우 세 줄 말줄임표시 */
@media (min-width: 1270px) {
  .el-card .loginbottom {
    -webkit-line-clamp: 3;
    height:90px;
  }
}

/* 테블릿, 모바일의 경우 두 줄 말줄임표시 */
@media (max-width: 1269px) {
  .el-card .loginbottom {
    -webkit-line-clamp: 2;
    height:80px;
  }
}
/* 데스크탑의 경우 세 줄 말줄임표시 */
@media (min-width: 1270px) {
  .el-card .bottom {
    -webkit-line-clamp: 3;
    height:40px;
  }
}


</style>
