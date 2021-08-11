
import VueAxios from 'vue-axios'
import axios from 'axios'
// import config from '../config'

const BASE_URL = 'https://i5d101.p.ssafy.io:8080/api/v1'
// const BASE_URL = 'http://localhost:8080/api/v1'
const DEFAULT_ACCEPT_TYPE = 'application/json'

axios.defaults.baseURL = BASE_URL
axios.defaults.headers['Content-Type'] = DEFAULT_ACCEPT_TYPE

if(localStorage.getItem('accessToken'))
  axios.defaults.headers['Authorization'] = localStorage.getItem('accessToken')

export default { VueAxios, axios }
