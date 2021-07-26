<template>
  <!-- 로그인한 경우 -->

    <!-- 거래가 끝난 경우 -->
    <el-card :body-style="{ padding: '0px' }" v-if="isLogin">
    <div class="image-wrapper enddeal">
      <el-skeleton style="width: 100%">
        <template #template>
          <!-- 내 거래인 경우 -->
          <!-- <a class="custom-icon2" style="color:red;"  @click="deleteDeal"><i class="el-icon-delete-solid"></i></a> -->
          <!-- 찜한 거래인 경우 -->
          <a class="custom-icon2" style="color:red;"  @click="deleteLike"><i class="el-icon-delete-solid"></i></a>
          <el-skeleton-item variant="image" style="width: 100%; height: 190px" />
        </template>
      </el-skeleton>
      </div>
    <div style="text-align:left; padding: 14px;">
      <span class="title enddeal">{{ title }}</span>
      <div class="loginbottom">
        <p style="margin-bottom:0;" class="enddeal">{{ price }}⠀|⠀{{ time }}</p>
        <div>
          <!-- 내가 생성한 거래인 경우 -->
          <!-- <div style="text-align:center">
            <el-button class="buyer" type="info" plain disabled style="margin-top:20px; text-align:center;" @click="deleteDeal">구매자: {{ buyer }}</el-button>
          </div> -->
          <!-- 찜한 거래인 경우 -->
          <div style="text-align:center">
            <el-button class="buyer" type="info" plain disabled style="margin-top:20px; text-align:center;">거래가 완료된 상품입니다</el-button>
          </div>
        </div>
      </div>
    </div>
  </el-card>
    <!-- 거래가 시작되지 않은 경우 -->
  <!-- <el-card :body-style="{ padding: '0px' }" shadow="hover">
    <div class="image-wrapper">
      <el-skeleton style="width: 100%">
        <template #template>
          <el-skeleton-item variant="image" style="width: 100%; height: 190px" />
        </template>
      </el-skeleton>
      </div>
    <div style="text-align:left; padding: 14px;">
      <span class="title">{{ title }}</span>
      <div class="loginbottom">
        <p style="margin-bottom:0;">{{ price }}⠀|⠀{{ time }}</p>
        <div> -->
          <!-- 내가 생성한 거래인 경우 -->
            <!-- 거래 시작 가능한 방 -->
          <!-- <div style="text-align:right">
            <el-button type="info" @click="updateDeal" style="margin-top:30px;" size="small">거래 수정</el-button>
            <el-button type="primary" @click="startDeal" style="margin-top:30px" size="small">거래 시작</el-button>
          </div> -->
          <!-- 내가 생성한 거래가 아닌 경우 -->
            <!-- 찜한 경우 -->
          <!-- <div style="text-align:right">
            <a class="custom-icon" @click="likeDeal" style="color:#ffd04b; margin-top:30px"><i class="el-icon-message-solid"></i></a>
          </div> -->
            <!-- 찜하지 않은 경우 -->
          <!-- <div style="text-align:right">
            <a class="custom-icon" @click="likeDeal" style="color:#ffd04b; margin-top:30px"><i class="el-icon-bell"></i></a>
          </div>
        </div>
      </div>
    </div>
  </el-card> -->
  <!-- 로그인을 하지 않은 경우 -->
  <el-card :body-style="{ padding: '0px' }" shadow="hover" v-else>
    <div class="image-wrapper">
      <el-skeleton style="width: 100%">
        <template #template>
          <el-skeleton-item variant="image" style="width: 100%; height: 190px" />
        </template>
      </el-skeleton>
      </div>
    <div style="text-align: center; padding: 14px;">
      <span class="title">{{ title }}</span>
      <div class="bottom">
        <span>{{ price }}⠀|⠀{{ time }}</span>
      </div>
    </div>
  </el-card>
</template>

<script>
// import { onMounted } from '@vue/runtime-core'

export default {
  name: 'Home',

  data: function() {
    return {
      isLogin: false
    }
  },

  mounted: function () {
    if (localStorage.accessToken) {
      this.isLogin = true
    }
  },

  props: {
    title: {
      type: String,
      default: '맥북2016-15인치'
    },
    //! 가격
    price: {
      type: String,
      default: "100000 원"
    },
    //! 시간
    time: {
      type: String,
      default: "10시 30분"
    },
    buyer: {
      type: String,
      default: "거래가 성사되지 않았습니다"
    }
  },

  setup () {
    const updateDeal = function () {
      console.log("거래 수정")
    }

    const startDeal = function (res) {
      console.log(res)
      console.log("거래 시작")
    }

    const deleteDeal = function () {
      console.log("거래 삭제")
      // router.push({
      //   name: 'keep-deal',
      // })
    }
  return { updateDeal, startDeal, deleteDeal }
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
  margin-left:115px;
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
