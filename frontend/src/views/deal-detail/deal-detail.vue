<template>
  <div style="margin-left: 50px; margin-right: 50px;">
    <h1 style="font-size: 40px; text-align:left; margin-bottom: 0;">상품 이름</h1>
    <hr style="margin-bottom: 50px;">
    <el-container>
      <el-carousel class="carousel-z" :interval="2000" arrow="always" style="width: 700px;" height="450px">
        <el-carousel-item v-for="item in 4" :key="item">
          <h3>{{ item }}</h3>
        </el-carousel-item>
      </el-carousel>
      <div class="deal-info" style="margin-left:50px;">
        <p style="margin-top:0;">판매자: </p>
        <p>카테고리: </p>
        <p>기준가: </p>
        <p>거래 시간: </p>
        <p>상세 설명: </p>
      </div>
    </el-container>
    <br><br>
    <!-- 내가 작성한 거래인 경우 -->
    <!-- <div>
      <el-button type="primary" style="margin-right:40px" @click="updateDeal">수정하기</el-button>
      <el-button type="danger" style="margin-left:40px; margin-right:40px;" @click="deleteDeal">삭제하기</el-button>
      <el-button type="info" style="margin-left:40px" @click="backHome">목록</el-button>
    </div> -->
    <!-- 내가 작성한 거래가 아닌 경우 -->
    <div>
      <el-button type="danger" style="margin-right:40px" @click="likeDeal">찜하기</el-button>
      <el-button type="info" style="margin-left:40px" @click="backHome">목록</el-button>
    </div>
  </div>
</template>

<script>
import { useRouter } from 'vue-router'

export default {
  name: "Deal-detail",

  setup () {
    const router = useRouter()

    const backHome = function () {
      router.push({name: 'home'})
    }

    return { backHome }
  }
}
</script>

<style>
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

  .deal-info {
    text-align: left;
    font-size: 20px;
    font-weight: 900;
  }

  .carousel-z {
    z-index: -5;
  }
</style>
