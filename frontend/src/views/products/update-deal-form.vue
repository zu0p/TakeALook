<template>
  <el-container class="update-deal-form">
    <!-- 이미지 업로드 폼 -->
    <form class="image-upload-form" method="post" enctype="multipart/form-data">
      <img class="wrapper" :src="state.imgUrl" alt="">
      <div class="button">
          <label for="chooseFile">
              👉 이곳을 눌러 사진을 업로드 하세요 👈
              <br>
              (다시 눌러 사진을 변경할 수 있습니다.)
          </label>
      </div>
      <input type="file" id="chooseFile" name="chooseFile" accept="image/*" :onchange="loadFile">
    </form>

    <!-- 거래 작성 폼 -->
    <el-form v-if="!state.loading" :model="state.form" :rules="state.rules" ref="updateDealForm" :label-position="state.form.align">
      <!-- 게시글 제목 -->
      <el-form-item prop="productName" label="제목" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.productName" placeholder="제목을 입력하세요" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="categories" label="상품 분류" :label-width="state.formLabelWidth">
        <el-select v-model="state.form.categories" placeholder="카테고리를 선택해주세요">
          <el-option label="선택하세요" value=""></el-option>
          <el-option label="디지털/가전" value="digital"></el-option>
          <el-option label="가구인테리어" value="furniture"></el-option>
          <el-option label="패션잡화" value="fashion"></el-option>
          <el-option label="미술품" value="art"></el-option>
        </el-select>
      </el-form-item>
      <!-- 가격 -->
      <!-- 숫자만 입력가능하다. -->
      <el-form-item prop="basePrice" label="가격" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.basePrice" placeholder="가격을 입력하세요 (단위: 원)" maxlength="12" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" autocomplete="off"></el-input>
      </el-form-item>
      <!-- 제품 판매 예약시간 -->
      <el-form-item prop="reserveTime" label="제품 판매 예약시간" :label-width="state.formLabelWidth">
        <el-col :span="30">
          <el-date-picker
            class="date_picker"
            v-model="state.form.reserveTime"
            type="datetime"
            placeholder="날자를 선택하세요"
            style="width: 100%;"
            :disabled-date="disabledDate"

            >
          </el-date-picker>
        </el-col>
      </el-form-item>
      <!-- 제품 설명 -->
      <el-form-item prop="description" label="제품 설명" :label-width="state.formLabelWidth">
        <el-input type="textarea" resize="none" :rows="5" v-model="state.form.description" placeholder="내용을 입력하세요" show-word-limit maxlength="300" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
  </el-container>

  <!-- 작성, 취소버튼 -->
  <hr>
  <el-form-item>
    <el-button type="primary" @click="clickUpdate">수정</el-button>
    <el-button type="danger" @click="clickCancel">취소</el-button>
  </el-form-item>
</template>

<script>
import { onMounted, ref, reactive, onBeforeMount, } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import axios from 'axios'

export default {
  name: 'UpdateDealform',
  // setup 안에서 props를 사용하려면 여기서 정의를 해주어야 한다.
  props: {
    productId: [ String||Number ]
  },
  // props를 통해 productId값이 담겨온다.
  setup (props) {
    console.log(props.productId)
    const store = useStore()
    const router = useRouter()
    // 독립적인 반응형 값 생성 ref()
    const updateDealForm = ref(null)
    // let imgUrl = require(`@/assets/pimages/${props.productId}.jpg`)
    const state = reactive({
      imgUrl : require(`@/assets/pimages/${props.productId}.jpg`),
      form: {
        productName: '',
        categories: '',
        basePrice: '',
        reserveTime: '',
        description: '',
        state: '',
      },
      src: {
        imageUrl: '',
      },
      rules: {
        productName:[
          {required: true, message: '필수 입력 항목입니다.', trigger: 'blur'},
          {max:16, message: '최대 16자까지 입력 가능합니다.'}
        ],
        categories:[
          {required: true, message: '필수 선택 항목입니다.', trigger: 'blur'},
        ],
        basePrice:[
          {required: true, message: '필수 입력 항목입니다.', trigger: 'blur'},
        ],
        reserveTime:[
          {required: true, message: '필수 선택 항목입니다.', trigger: 'blur'},
        ],
        description:[
          {required: true, message: '필수 입력 항목입니다.', trigger: 'blur'},
        ],
      },
      formValicate: false,
      loading: false,
      formLabelWidth: '140px',
    })
    //  페이지 진입 전 불리는 훅
    onBeforeMount(()=>{
      console.log('before mount')
      // axios처리로 data불러오기
      axios({
        url: `/product/${props.productId}`
      })
      .then(res=>{
        console.log(1111111111)
        console.log(res)
        state.form.productName = res.data.productName
        state.form.categories = res.data.categories
        state.form.basePrice = res.data.basePrice
        state.form.reserveTime = res.data.reserveTime
        state.form.description = res.data.description
        state.src.imageUrl = res.data.imageUrl
      })
      // 게시글 정보 받아와서 폼의 prop로 보여주기
      // dispatch method로 requestProductInfo action 호출
      // props.productID가 payload에 담긴다.
      // store.dispatch('root/requestProductInfo', props.productId)
      //   .then(data=> {
      //     console.log(data)
      //   })
    })
    // 페이지 진입시 불리는 훅
    onMounted (() => {
      // mutations의 setMenuActiveMenuName을 호출하고 setMenuActiveMenuName의 create-deal-form 인자를 받아온다.
      store.commit('root/setMenuActiveMenuName', 'update-deal-form')
      //
    })

        // 이미지 파일 처리
    const loadFile = function (res) {
      //const tmp = URL.createObjectURL(res.path[0].files[0])
      // console.log(imgUrl)
      // state.src.imageUrl = imgUrl

      // let img = document.getElementById('chooseFile')
      // img.src = res.path[0].files[0] + new Date().getTime()

      const tmp = URL.createObjectURL(res.path[0].files[0])
      state.imgUrl = tmp
    }

        // reserveTime 의 타입은 String이다.
    const dateTimeToString = function () {
      // 단어별로 구분
      // reserveTime
      const array = state.form.reserveTime.toString().split(' ')
      // 그 단어의 1,2,3,4번째 배열만 쓸거다
      const month = ["","Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
      let res = `${array[3]}-`
      for(let i = 1; i<=12; i++){
        if(array[1]==month[i]){
          if(i<10)
            res+=`0${i}-`
          else
            res += `${i}-`
          break;
        }
      }
      res += `${array[2]}T${array[4]}.000+00:00`
      state.date = res
    }

    const saveFile = function (pid) {
      //console.log("saveFile func")
      let img = document.getElementById('chooseFile')

      let fd = new FormData()
      fd.append('imageFile', img.files[0])

      const req = {
        imageFile: fd,
        productId: pid
      }
      store.dispatch('root/requestUploadImage', req)
      .then(res => {
        //console.log(res)
        alert('상품 수정이 완료되었습니다!')
        router.push({name: 'home'})
      })
      .catch(err=>{
        console.log(err)
      })
    }

    const clickUpdate = function () {
      // console.log(state.form.categories)
      // console.log(state.form.reserveTime)
      dateTimeToString()
      state.loading = true
      // 작성 클릭 시 validate 체크 후 그 결과 값에 따라, 게시글 작성 API 호출 또는 경고창 표시
      updateDealForm.value.validate((valid) => {
        if (valid) {
          console.log('submit')
          store.dispatch('root/updatePost', {
            productId: props.productId,
            imageUrl: state.src.imageUrl,
            productName: state.form.productName,
            categories: state.form.categories,
            basePrice: parseInt(state.form.basePrice),
            reserveTime: state.form.reserveTime,
            description: state.form.description,
           })
          .then(res=>{
            //console.log(res.data)
            saveFile(props.productId)
          }).then(()=>{
            state.loading = false
          })
          .catch(err=>{
            state.loading = false
            alert("게시글 수정에 실패하였습니다.")
            console.log(err)
          })
        } else if(!valid){
          state.loading = false
          alert('필수 항목을 입력하세요.')
        }
      })
    }

    const clickCancel = function () {
      console.log('go home')
      window.location='/'
    }

    return { updateDealForm, state, clickUpdate, clickCancel, loadFile,  }
  },
  // imageUrl, el-date-picker 관련 method
  methods: {
    disabledDate(time) {
      return time && time.valueOf() < Date.now();
      // return time.getTime() < Date.now() - 8.64e7
    },
  },

}
</script>
<style>
  .update-deal-form {
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
  .el-picker-panel__footer .el-button--text.el-picker-panel__link-btn {
    display: none;
  }
</style>
