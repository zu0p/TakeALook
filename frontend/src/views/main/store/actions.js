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

const BASE_URL = 'https://silent-goose-93.loca.lt/swagger-ui/'
const instanceWithAuth = createInstance()
const posts = createInstanceWithAuth('posts')

export function requestLogin ({ state, commit }, payload) {
  //console.log('requestLogin', state, payload)
  const url = BASE_URL+'/auth/login'
  let body = payload

  //return axios.post(url, body)
  return instanceWithAuth.post(url, body)
}

export function requestSignup({state}, payload){
  const url = BASE_URL+'/users'
  let body = payload

  return instanceWithAuth.post(url, body)
}

export function requestCheckDupl({state}, payload){
  //console.log(payload)
  const url = BASE_URL+`/users/${payload.id}`
  return instanceWithAuth.get(url)
}

// update-deal-form.vue의 onBeforeMount훅에서 dispatch method를 통해 requestUserInfo action 시작
export function requestUserInfo({commit}, payload){
  const url = BASE_URL+'/users/me'
  instanceWithAuth.get(url)
    .then(res=>{
      commit("SET_USER_INFO", res.data.userId)
    })
    .catch((err)=>{
      console.log(err)
    })
}
// mutations로 "SET_CONFERENCE_INFO" commit 요청
export function requestConferenceInfo({commit}, payload){
  commit("SET_CONFERENCE_INFO", res.data.conferenceId)

}

// 게시글 작성 요청 보내기
export function createPost(postData) {
  // const url = BASE_URL+`/create-deal-form/${userId}/${}`
  return posts.post('/', postData)
}
// 게시글 삭제 요청 보내기
export function deletePost(postId) {
  return posts.delete(postId);
}
