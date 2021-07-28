<template>
  <div style="margin-left: 50px; margin-right: 50px;">
    <h1 style="font-size: 40px; text-align:left; margin-bottom: 0;">{{ info.productName }}</h1>
    <hr style="margin-bottom: 50px;">
    <el-container>
      <img :src="info.imageUrl" alt="">
      <div class="deal-info" style="margin-left:50px;">
        <p style="margin-top:0;">판매자: <span>{{ info.userId }}</span> </p>
        <p>카테고리: <span>{{ info.categories }}</span></p>
        <p>기준가: <span>{{ info.basePrice }}</span></p>
        <p>거래 시간: <span>{{ info.reserveTime }}</span></p>
        <p>상세 설명: <span>{{ info.description }}</span></p>
      </div>
    </el-container>
    <br><br>
    <div v-if="info.isSold" style="text-align:center">
      <el-button class="soldout" type="info" plain disabled>거래가 완료된 상품입니다</el-button>
    </div>
    <div v-else>
      <!-- 내가 작성한 거래인 경우 -->
      <div v-if="info.user">
        <el-button type="primary" style="margin-right:40px" @click="updateDeal">수정하기</el-button>
        <el-button type="danger" style="margin-left:40px; margin-right:40px;" @click="deleteDeal">삭제하기</el-button>
        <el-button type="info" style="margin-left:40px" @click="backHome">목록</el-button>
      </div>
      <!-- 내가 작성한 거래가 아닌 경우 -->
      <div v-else>
        <el-button type="danger" style="margin-right:40px" @click="deletelikeDeal" v-if="info.like">찜 취소</el-button>
        <el-button type="danger" style="margin-right:40px" @click="likeDeal" v-else>찜하기</el-button>
        <el-button type="info" style="margin-left:40px" @click="backHome">목록</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, onBeforeMount } from '@vue/runtime-core'
import { useRouter, useRoute } from 'vue-router'
import { useStore } from 'vuex'
export default {
  name: "Deal-detail",

  setup () {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()
    const state = reactive({
      productId: ''
    })

    const info =
    reactive({
      productName:'',
      basePrice: '',
      categories: '',
      description: '',
      reserveTime: '',
      productId:'',
      imageUrl: '',
      userId: '',
      user: false,
      like: false,
      isSold: false
    })

    onBeforeMount (() => {
      state.productId = route.params.productId
      store.dispatch('root/requestDealDetail', state.productId)
        .then(res=>{
          console.log(res)
          info.productName = res.data.productName
          info.basePrice = res.data.basePrice
          info.categories = res.data.categories
          info.description = res.data.description
          info.reserveTime = res.data.reserveTime
          info.userId = res.data.userId
          info.productId = res.data.productId
          info.imageUrl = res.data.imageUrl
          info.isSold = res.data.isSold
          // 본인 등록 거래인 경우 수정, 삭제 버튼
          store.dispatch('root/requestUserInfo')
          .then(res=>{
            console.log(res)
            if (info.userId == res.data.userId) {
              console.log("본인 등록")
              info.user = true
            } else {
              console.log("본인 거래 아님")
              //! 이미 찜을 한 경우와 그렇지 않은 경우 보이는 버튼 다르게
            }
          })
          .catch(err=>{
            console.log(err)
          })
        })
        .catch(err => {
          alert("존재하지 않는 거래입니다")
          router.push({name: 'home'})
        })
    })

    const backHome = function () {
      router.push({name: 'home'})
    }

    const deleteDeal = function () {
      if (info.user) {
        store.dispatch('root/requestDeleteDeal', state.productId)
          .then(res=>{
            console.log("거래 삭제")
            alert("거래가 삭제되었습니다")
            router.push({name: 'home'})
          })
          .catch(err=>{
            console.log(err)
          })
      } else {
        alert("자신의 거래만 삭제할 수 있습니다")
      }
    }

    const likeDeal = function () {
      store.dispatch('root/requestLikeDeal', {productId: info.productId, userId: info.userId})
      .then(res=>{
        info.like = true
      })
    }

    const deletelikeDeal = function () {
      store.dispatch('root/requestDeleteLikeDeal', {productId: info.productId, userId: info.userId})
      .then(res=>{
        info.like = false
      })
    }

    return { info, backHome, deleteDeal, likeDeal, deletelikeDeal }
  }
}
</script>

<style>
  .deal-info {
    text-align: left;
    font-size: 20px;
    /* font-weight: 900; */
  }

  .deal-info > p > span {
    margin-left: 20px;
    font-weight: 900;
  }

  .soldout {
    width: 1000px;
    height: 100px;
    font-size: 50px;
    margin-top:20px;
    text-align:center;
  }
</style>
