// API
import axios from 'axios'
import { setInterceptors } from '../../../common/interceptors'

function createInstance(){
  const instance = axios.create()
  return setInterceptors(instance)
}

// Token값과 특정 url을 붙여서 셋팅
function createInstanceWithAuth(url) {
  const instance = axios.create({
    baseURL: BASE_URL+`${url}`,
  })
  return setInterceptors(instance)
}

// const BASE_URL = 'https://silent-goose-93.loca.lt/swagger-ui/'
const BASE_URL = 'http://i5d101.p.ssafy.io:8080/api/v1'
// const BASE_URL = 'http://localhost:8080/api/v1'

const instanceWithAuth = createInstance()
// const posts = createInstanceWithAuth('posts')

export function requestLogin ({ state, commit }, payload) {
  //console.log('requestLogin', state, payload)
  const url = BASE_URL+'/auth/login'
  let body = payload

  //return axios.post(url, body)
  return instanceWithAuth.post(url, body)
}

export function requestSignup({state}, payload){
  const url = BASE_URL+'/user'
  let body = payload

  return instanceWithAuth.post(url, body)
}

export function requestCheckDupl({state}, payload){
  //console.log(payload)
  const url = BASE_URL+`/user/${payload.id}`
  return instanceWithAuth.get(url)
}

// update-deal-form.vue의 onBeforeMount훅에서 dispatch method를 통해 requestUserInfo action 시작
export function requestUserInfo({commit}, payload){
  const url = BASE_URL+'/user/me'
  return instanceWithAuth.get(url)
}

export function requestModifyUserInfo({state}, payload){
  const url = BASE_URL+`/user`
  return instanceWithAuth.patch(url, payload)
}

export function requestProductInfo({commit}, payload){
  console.log(payload)
  const url = BASE_URL+`/product/${payload}`
  //  instanceWithAuth: 인증 + 요청 함께 이뤄짐
  // 결국 onBeforeMount훅은 instanceWithAuth를 반환한다.
  return instanceWithAuth.get(url)
}
// 게시글 작성 요청 보내기
// 두번째 인자값에 내가 담아올 것을 담아옴
export function createPost({commit}, postData) {
  // console.log(postData)
  const url = BASE_URL+'/product'
  console.log(url)
  let body = postData
  // const url = BASE_URL+`/create-deal-form/${userId}/${}`
  return instanceWithAuth.post(url, body)
}
// 게시글 삭제 요청 보내기
export function deletePost(postId) {
  return posts.delete(postId);
}

export function requestDropoutUser({state}, payload){
  const url = BASE_URL+`/user`
  return instanceWithAuth.delete(url)
}

export function requestDealDetail({state}, payload){
  const url = BASE_URL+`/product/${payload}`
  return instanceWithAuth.get(url)
}

export function requestDeleteDeal({state}, payload){
  const url = BASE_URL+`/product/${payload}`
  return instanceWithAuth.delete(url)
}

export function requestLikeDealList({state}, payload){
  const url = BASE_URL+`/wish/${payload}`
  return instanceWithAuth.get(url)
}

export function requestLikeDeal({state}, payload){
  const url = BASE_URL+'/wish'
  let body = {
    "productId": payload,
  }
  return instanceWithAuth.post(url, body)
}

export function requestDeleteLikeDeal({state}, payload){
  const url = BASE_URL+`/wish/${payload}`
  return instanceWithAuth.delete(url)
}

export function requestWishList({state}){
  const url = BASE_URL+'/wish'
  return instanceWithAuth.get(url)
}

export function requestSellList({state}){
  const url = BASE_URL+'/trade/sell'
  return instanceWithAuth.get(url)
}

export function requestBuyList({state}){
  const url = BASE_URL+'/trade/buy'
  return instanceWithAuth.get(url)
}

export function requestDealList({state}){
  const url = BASE_URL+'/product/list'
  return instanceWithAuth.get(url)
}

export function requestCheckWish({state}, payload){
  const url = BASE_URL+`/wish/${payload}`
  return instanceWithAuth.get(url)
}

export function requestWishCount({state}, payload){
  const url = BASE_URL+`/wish/count/${payload}`
  return instanceWithAuth.get(url)
}
