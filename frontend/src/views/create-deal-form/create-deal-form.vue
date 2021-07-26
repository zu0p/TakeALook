<template>
  <!-- 사진 업로드 -->
  <el-container class="create-deal-form">
    <el-upload
      class="avatar-uploader"
      action="https://jsonplaceholder.typicode.com/posts/"
      :show-file-list="false"
      :on-success="handleAvatarSuccess"
      :before-upload="beforeAvatarUpload">
      <img v-if="imageUrl" :src="imageUrl" class="avatar">
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>

    <!-- 거래 작성 폼 -->
    <el-form v-if="!state.loading" v-model="state.form" :rules="state.rules" ref="createDealForm" :label-position="state.form.align">
      <!-- 게시글 제목 -->
      <el-form-item prop="title" label="제목" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.title" placeholder="제목을 입력하세요" maxlength="10" autocomplete="off" ></el-input>
      </el-form-item>
      <el-form-item prop="category" label="상품 분류" :label-width="state.formLabelWidth">
        <el-select v-model="state.form.category" placeholder="카테고리를 선택해주세요">
          <el-option label="전자기기" value="electronics"></el-option>
          <el-option label="의류" value="clothing"></el-option>
        </el-select>
      </el-form-item>
      <!-- 가격 -->
      <!-- 숫자만 입력가능하다. -->
      <el-form-item prop="price" label="가격" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.price" placeholder="가격을 입력하세요 (단위: 원)" maxlength="10" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" autocomplete="off"></el-input>
      </el-form-item>
      <!-- 제품 판매 예약시간 -->
      <el-form-item prop="reservation_date" label="제품 판매 예약시간" :label-width="state.formLabelWidth">
        <el-col :span="30">
          <el-date-picker
            type="datetime"
            placeholder="날자를 선택하세요"
            v-model="state.form.reservation_dateTime"
            style="width: 100%;"
            >
          </el-date-picker>
        </el-col>
      </el-form-item>
      <!-- 제품 설명 -->
      <el-form-item prop="desc" label="제품 설명" :label-width="state.formLabelWidth">
        <el-input type="textarea" :rows="5" v-model="state.form.desc" placeholder="내용을 입력하세요" show-word-limit maxlength="300" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
  </el-container>

  <!-- 작성, 취소버튼 -->
  <hr>
  <el-form-item>
    <el-button type="primary" @click="clickCreate">작성</el-button>
    <el-button type="danger" @click="clickCancel">취소</el-button>
  </el-form-item>
</template>

<script>
import { onMounted, ref, reactive, } from 'vue'
import { useStore } from 'vuex'
import { RouterLink } from 'vue-router'

export default {
  name: 'CreateDealform',

  setup () {
    const store = useStore()
    // 독립적인 반응형 값 생성 ref()
    const createDealForm = ref(null)

    const state = reactive({
      form: {
        title:'',
        category:'',
        price:'',
        reservation_dateTime:'',
        desc:'',
      },
      src: {
        imageUrl:'',
      },
      formValicate: false,
      loading: false,
      formLabelWidth: '130px',
    })

    const onSubmit = function () {
      state.src.imageUrl = ''
      state.form.title = ''
      state.form.category = ''
      state.form.price =''
      state.form.reservation_dateTime = ''
      state.form.desc = ''
    }


    // 페이지 진입시 불리는 훅
    onMounted (() => {
      // mutations의 setMenuActiveMenuName을 호출하고 setMenuActiveMenuName의 create-deal-form 인자를 받아온다.
      store.commit('root/setMenuActiveMenuName', 'create-deal-form')
    })

    const clickCreate = function () {
      console.log('게시글 작성 함수 실행')
      state.loading = true
      // 작성 클릭 시 validate 체크 후 그 결과 값에 따라, 게시글 작성 API 호출 또는 경고창 표시
      createDealForm.value.validate((valid) => {
        if (valid) {
          console.log('submit')
          store.dispatch('root/createPost', {
            imageUrl: state.src.imageUrl,
            title: state.form.title,
            category: state.form.category,
            price: state.form.price,
            reservation_dateTime: state.form.reservation_dateTime,
            desc: state.form.desc,
           })
            .then(res=>{
              //console.log(res.data)
              window.location="/"
            }).then(()=>{
              state.loading = false
            })
            .catch(err=>{
              state.loading = false
              console.log(err)
              alert("양식이 올바르지 않습니다!")
            })
        } else {
          state.loading = false
          alert('Validate error!')
        }
      });
    }

    const clickCancel = function () {
      console.log('go home')
      window.location="/"
    }

    return { createDealForm, state, onSubmit, clickCreate, clickCancel, }
  },
  // imageUrl 관련 method
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('Avatar picture must be JPG format!');
      }
      if (!isLt2M) {
        this.$message.error('사진 크기는 2MB를 초과할 수 없습니다!');
      }
      return isJPG && isLt2M;
    },
  },

}
</script>
<style>
  .create-deal-form {
    /* justify-content: center; */
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 40px;
    color: #8c939d;
    width: 300px;
    height: 300px;
    line-height: 50%;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
