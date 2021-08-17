<template>
  <div style="margin-left:20px; margin-right:20px">
    <el-container style="text-align:center; justify-content:center">
      <img :src="info.imageUrl" alt="" style="max-width:700px; max-height:700px;">
      <el-form ref="form" label-width="120px" style="text-align:left; margin-left:10%;" label-position="left">
        <h1 style="font-size:28px; margin-bottom:5px;">{{ info.productName }}</h1>
        <div style="text-align:right;" v-if="info.mine">
          <i class="el-icon-message-solid" style="color:#ffd04b; margin-right:10px;"></i>
          <span style="color:#ffd04b;">{{ info.wishCount }}</span>
        </div>
        <el-form-item label="판매자">
          <span style="margin-top:0; margin-left:20px; font-weight: 900;">{{ info.seller }}</span>
        </el-form-item>
        <el-form-item label="카테고리">
          <span style="margin-top:0; margin-left:20px; font-weight: 900;">{{ info.categories }}</span>
        </el-form-item>
        <el-form-item label="기준가">
         <span style="margin-top:0; margin-left:20px; font-weight: 900;">{{ info.basePrice }}원</span>
        </el-form-item>
        <el-form-item label="거래 시간">
         <span style="margin-top:0; margin-left:20px; font-weight: 900;">{{ info.reserveTime.slice(5, 7) }}월 {{ info.reserveTime.slice(8, 10) }}일
              {{ info.reserveTime.slice(11, 13) }}시 {{ info.reserveTime.slice(14, 16) }}분</span>
        </el-form-item>
        <el-form-item label="상세 설명">
         <span style="margin-top:0; margin-left:20px; font-weight: 900;">{{ info.description }}</span>
        </el-form-item>
        <!-- 거래가 종료된 경우 -->
        <div v-if="info.isSold" style="text-align:center">
          <el-button class="soldout" type="info" plain disabled>거래가 완료된 상품입니다</el-button>
          <br>
        </div>
        <div v-else>
          <div v-if="info.isLogin">
            <!-- 내가 작성한 거래인 경우 -->
            <div v-if="info.mine" style="text-align:center;">
              <el-button @click="updateDeal" type="primary" style="width:95px;" icon="el-icon-edit">수정</el-button>
              <el-button @click="deleteDeal" type="danger" style="width:95px;" icon="el-icon-delete">삭제</el-button>
              <br>
              <el-button @click="backMy" type="info" style="width:95px; margin-top:5px;" icon="el-icon-sell">판매</el-button>
              <el-button @click="backHome" type="info" style="width:95px; margin-top:5px;" icon="el-icon-menu">목록</el-button>
            </div>
            <!-- 내가 작성한 거래가 아닌 경우 -->
            <div v-else style="text-align:center">
              <el-button v-if="info.like" @click="deletelikeDeal" type="danger" style="width: 200px" icon="el-icon-message-solid">찜 취소 {{ info.wishCount }}</el-button>
              <el-button v-else @click="likeDeal" type="danger" style="width: 200px;" icon="el-icon-bell">찜 {{ info.wishCount }}</el-button>
              <br>
              <el-button @click="backKeep" type="info" style="width:95px; margin-top:5px;" icon="el-icon-message-solid">찜</el-button>
              <el-button @click="backHome" type="info" style="width:95px; margin-top:5px;" icon="el-icon-menu">목록</el-button>
            </div>
          </div>
        </div>
      </el-form>
    </el-container>
    <div style="text-align:center;">
      <h1 v-if="info.recommend" style="text-align:left; margin-top:80px; margin-bottom:30px; margin-left:5%;">이런 상품은 어떠세요?</h1>
      <el-row style="text-align:center; display:inline-flex" justify="space-evenly">
        <ul v-for="deal in info.recommend" :key="deal.productId" @click="dealDetail(deal.productId)" >
          <recommend :deal="deal"/>
        </ul>
      </el-row>
    </div>
  </div>
</template>

<script>
import Recommend from './components/recommend'
import { reactive, onBeforeMount } from '@vue/runtime-core'
import { useRouter, useRoute } from 'vue-router'
import { useStore } from 'vuex'

export default {
  name: "Deal-detail",

  components: {
    Recommend,
  },

  watch: {
    '$route' (to, from) {
      if (to.path != from.path) {
        this.$router.go()
      }
    }
  },

  setup () {


    const store = useStore()
    const router = useRouter()
    const route = useRoute()
    const state = reactive({
      productId: '',
    })

    const info = reactive({
      productName:'',
      basePrice: '',
      categories: '',
      description: '',
      reserveTime: '',
      imageUrl: '',
      seller: '',
      recommend: '',
      registTime: '',
      recommend: '',
      wishCount: 0,
      myId: '',
      mine: false,
      like: false,
      isSold: false,
      isLogin: false
    })

    onBeforeMount (() => {
      state.productId = route.params.productId
      info.imageUrl = `@/assets/pimages/${state.productId}.jpg`
      console.log(info.imageUrl)
      store.dispatch('root/requestDealDetail', state.productId)
        .then(res=>{
          if(res.data.statusCode == 404){
            alert("존재하지 않는 거래입니다")
            router.push({name: 'home'})
            store.commit('root/setMenuActiveMenuName', 'home')
          } else{
            console.log(res.data)
            info.productName = res.data.productName
            info.basePrice = res.data.basePrice
            info.categories = res.data.categories
            info.description = res.data.description
            info.reserveTime = res.data.reserveTime
            info.seller = res.data.seller
            info.isSold = res.data.isSold
            info.registTime = res.data.registTime

            // 본인 등록 거래인 경우 수정, 삭제 버튼
            store.dispatch('root/requestUserInfo')
            .then(res=>{
              info.isLogin = true
              info.myId = res.data.userId
              if (info.seller == res.data.userId) {
                info.mine = true
                store.dispatch('root/requestWishCount', state.productId)
                  .then(res=>{
                    info.wishCount = res.data.wishCount
                  })
              } else {
                // 이미 찜을 한 경우와 그렇지 않은 경우
                store.dispatch('root/requestCheckWish', state.productId)
                .then(res=>{
                  console.log(res)
                  if (res.data.statusCode == 200) {
                    info.like = true
                  }
                // 찜 수 확인
                store.dispatch('root/requestWishCount', state.productId)
                .then(res=>{
                  info.wishCount = res.data.wishCount
                })
              })
            }
          })

          // 같은 카테고리 추천
          store.dispatch('root/requestAllDeal')
            .then (res => {
              if (res.data.statusCode != 404) {
                var temp = res.data.sort(()=>Math.random()-0.5)
                info.recommend = []
                for (var deal in temp) {
                  if (info.recommend.length < 4 && temp[deal].productId != state.productId) {
                    if (temp[deal].categories == info.categories) {
                      if (temp[deal].seller != info.myId) {
                        info.recommend.push(temp[deal])
                      }
                    }
                  }
                } if (info.recommend.length == 0) {
                  info.recommend = false
                }
              }
            })
          }
        })
        .catch(err => {
          alert("존재하지 않는 거래입니다")
          router.push({name: 'home'})
          store.commit('root/setMenuActiveMenuName', 'home')
        })
    })

    const backHome = function () {
      router.push({name: 'home'})
    }

    const backKeep = function () {
      router.push({name: 'keep-deal'})
    }

    const backMy = function () {
      router.push({name: 'my-deal'})
    }

    const deleteDeal = function () {
      if (info.mine) {
        store.dispatch('root/requestDeleteDeal', state.productId)
          .then(res=>{
            alert("거래가 삭제되었습니다")
            router.push({name: 'home'})
            store.commit('root/setMenuActiveMenuName', 'home')
          })
          .catch(err=>{
            console.log(err)
          })
      } else {
        alert("자신의 거래만 삭제할 수 있습니다")
      }
    }

    const likeDeal = function () {
      store.dispatch('root/requestLikeDeal', state.productId)
      .then(res=>{
        info.like = true
        store.dispatch('root/requestWishCount', state.productId)
          .then(res=>{
            info.wishCount = res.data.wishCount
          })
      })
    }

    const deletelikeDeal = function () {
      store.dispatch('root/requestDeleteLikeDeal', state.productId)
      .then(res=>{
        info.like = false
        store.dispatch('root/requestWishCount', state.productId)
          .then(res=>{
            info.wishCount = res.data.wishCount
          })
      })
    }

    const updateDeal = function(){
      router.push({
        name: 'update-deal-form',
        props: state.productId
      })
    }

    const dealDetail = function (id) {
      router.push(`/products/${id}`)
    }

    return { info, state, backHome, backKeep, backMy, deleteDeal, likeDeal, deletelikeDeal, updateDeal, dealDetail }
  }
}
</script>

<style>
  .deal-info {
    text-align: left;
    font-size: 20px;
    /* font-weight: 900; */
  }
/*
  el-form > el-form-item > p{
    margin-left: 20px;
    font-weight: 900;
  } */

  .soldout {
    width: 200px;
    height: 50px;
    font-size: 13px;
    margin-top:20px;
    text-align:center;
  }

    .el-carousel__item h3 {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>
