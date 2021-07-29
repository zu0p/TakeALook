export function setPlatform (state, isDesktop) {
  state.isDesktopPlatform = isDesktop
}

export function setMenuActive (state, index) {
	console.log('setMenuActive', state,index)
	const keys = Object.keys(state.menus)
	state.activeMenu = keys[index]
}

export function setMenuActiveMenuName (state, menuName) {
	state.activeMenu = menuName
}

export function SET_USER_INFO(state, userId){
  state.userId = userId
}
// actions에서 받은 SET_CONFERENCE_INFO commit 호출을 여기서 처리
export function SET_PRODUCT_INFO(state, data){
  // 여기에 있는 것들이 기존에 작성한 내용들
  // state.productId = productId
  // state.form.productName = productName
  // state.form.categories = categories
  // state.form.basePrice = basePrice
  // state.form.reserveTime = reserveTime
  // state.form.description = description
  // state.src.imageUrl = imageUrl
}

export function SET_ACCESSTOKEN(state, token){
  state.accessToken = token
}
