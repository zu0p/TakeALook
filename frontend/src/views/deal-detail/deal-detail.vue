<template>
  <div style="margin-left: 50px; margin-right: 50px;">
    <el-container>
      <img :src="info.imageUrl" alt="">
      <el-form ref="form" label-width="120px" style="text-align:left; margin-left:100px;" label-position="left">
        <h1 style="font-size:28px;">상품 이름</h1>
        <el-form-item label="판매자">
          <span style="margin-top:0; margin-left:20px; font-weight: 900;">{{ info.seller }}</span>
        </el-form-item>
        <el-form-item label="카테고리">
          <span style="margin-top:0; margin-left:20px; font-weight: 900;">{{ info.categories }}</span>
        </el-form-item>
        <el-form-item label="기준가">
         <span style="margin-top:0; margin-left:20px; font-weight: 900;">{{ info.basePrice }}</span>
        </el-form-item>
        <el-form-item label="거래 시간">
         <span style="margin-top:0; margin-left:20px; font-weight: 900;">{{ info.reserveTime }}</span>
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
          <!-- 내가 작성한 거래인 경우 -->
          <div v-if="info.user" style="text-align:center;">
            <el-button @click="updateDeal" type="primary" style="width:95px;" icon="el-icon-edit">수정</el-button>
            <el-button @click="deleteDeal" type="danger" style="width:95px;" icon="el-icon-delete">삭제</el-button>
            <br>
          </div>
          <!-- 내가 작성한 거래가 아닌 경우 -->
          <div v-else style="text-align:center">
            <el-button v-if="info.like" @click="deletelikeDeal" type="danger" style="width: 200px" icon="el-icon-message-solid">찜 취소</el-button>
            <el-button v-else @click="likeDeal" type="danger" style="width: 200px;" icon="el-icon-bell">찜</el-button>
            <br>
          </div>
        </div>
        <div style="text-align:center">
          <el-button @click="backHome" type="info" style="width: 200px; margin-top:5px;" icon="el-icon-menu">목록</el-button>
        </div>
      </el-form>
    </el-container>
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

    const info = reactive({
      productName:'',
      basePrice: '',
      categories: '',
      description: '',
      reserveTime: '',
      productId:'',
      imageUrl: '',
      seller: '',
      user: false,
      like: false,
      isSold: false,
    })

    onBeforeMount (() => {
      state.productId = route.params.productId
      store.dispatch('root/requestDealDetail', state.productId)
        .then(res=>{
          if(res.data.statusCode == 404){
            alert("존재하지 않는 거래입니다")
            // !이전 페이지 이동
            router.push({name: 'home'})
            store.commit('root/setMenuActiveMenuName', 'home')
          } else{
            info.productName = res.data.productName
            info.basePrice = res.data.basePrice
            info.categories = res.data.categories
            info.description = res.data.description
            info.reserveTime = res.data.reserveTime
            info.seller = res.data.seller
            info.productId = res.data.productId
            info.imageUrl = res.data.imageUrl
            info.isSold = res.data.isSold
            // 본인 등록 거래인 경우 수정, 삭제 버튼
            store.dispatch('root/requestUserInfo')
            .then(res=>{
              if (info.seller == res.data.userId) {
                console.log("본인 등록")
                info.user = true
              } else {
                console.log("본인 거래 아님")
                //! 이미 찜을 한 경우와 그렇지 않은 경우 보이는 버튼 다르게
                store.dispatch('root/requestWishList')
                .then(res=>{
                  console.log(res.data)
              })
            }
          })
          .catch(err=>{
            console.log(err)
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
      store.commit('root/setMenuActiveMenuName', 'home')
    }

    const deleteDeal = function () {
      if (info.user) {
        store.dispatch('root/requestDeleteDeal', {productId: state.productId})
          .then(res=>{
            console.log("거래 삭제")
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
      })
      .catch(err=>{
        console.log(err)
      })
    }

    const deletelikeDeal = function () {
      store.dispatch('root/requestDeleteLikeDeal', state.productId)
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
