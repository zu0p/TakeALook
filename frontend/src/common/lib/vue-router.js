import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/home/home'
import ConferencesDetail from '@/views/conferences/conference-detail'
import PurchaseList from '@/views/purchase-list/purchase-list'
import SalesList from '@/views/sales-list/sales-list'
import CreateDealform from '@/views/create-deal-form/create-deal-form'

const fullMenu = require('@/views/main/menu.json')
// 리스트 이기 때문에 .map 사용
// key : menu.json의 key값
function makeRoutesFromMenu () {
  let routes = Object.keys(fullMenu).map((key) => {
    if (key === 'home') {
      return { path: fullMenu[key].path, name: key, component: Home  }
    } else if (key === 'purchase-list') {
      return { path: fullMenu[key].path, name: key, component: PurchaseList }
    } else if (key === 'sales-list') {
      return { path: fullMenu[key].path, name: key, component: SalesList }
    } else if (key === 'create-deal-form') {
      return { path: fullMenu[key].path, name: key, component: CreateDealform }
    } else {
      // menu.json 에 들어있는 로그아웃 메뉴
      return null
    }
  })
  // 로그아웃 파싱한 부분 제거
  routes = routes.filter(item => item)
  // menu 자체에는 나오지 않는 페이지 라우터에 추가(방 상세보기)
  routes.push({
    path: '/conferences/:conferenceId',
    name: 'conference-detail',
    component: ConferencesDetail,
  })
  // menu 자체에는 나오지 않는 페이지 라우터에 추가(게시글 상세보기))
  return routes
}

const routes = makeRoutesFromMenu()

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.afterEach((to) => {
  console.log(to)
})

export default router
