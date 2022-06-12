import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
    {
        path: '/about',
        name: 'about',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
    },
    {
        path: '/login',
        name: 'login',
        component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
    },
    {
        path: '/register',
        name: 'register',
        component: () => import('../views/Register.vue')
    },
    {
        path: '*',
        name: '404',
        component: () => import('../views/404.vue')
    },


]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export const setRoutes = () => {
    const storeMenus = localStorage.getItem("menus");
    if (storeMenus) {
        //碰撞动态路由
        const manageRoute = {path: '/', component: () => import('../views/Manage.vue'), redirect: "/home", children: []}
        const menus = JSON.parse(storeMenus)
        menus.forEach(item => {
            if (item.path) {
                let itemMenu = {
                    path: item.path.replace("/", ""),
                    name: item.name,
                    component: () => import('../views/' + item.pagePath + '.vue')
                }
                manageRoute.children.push(itemMenu)
            } else if (item.children.length) {
                item.children.forEach(item => {
                    let itemMenu = {
                        path: item.path.replace("/", ""),
                        name: item.name,
                        component: () => import('../views/' + item.pagePath + '.vue')
                    }
                    manageRoute.children.push(itemMenu)
                })
            }
        })

        // 获取当前的路由对象名称数组
        const currentRouteNames = router.getRoutes().map(v => v.name);
        if (!currentRouteNames.includes('Manage')) {

            //动态添加
            router.addRoute(manageRoute)
        }
    }
}

// router.addRoute({
//     path: '/',
//     name: 'Manage',
//     component: () => import('../views/Manage.vue'),
//     redirect: "/home",
//     children: [
//         {path: 'menu', name: 'Menu', component: () => import('../views/Menu.vue')},
//         {path: 'role', name: 'Role', component: () => import('../views/Role.vue')},
//         {path: 'home', name: 'Home', component: () => import('../views/Home.vue')},
//         {path: 'user', name: 'User', component: () => import('../views/User.vue')},
//         {path: 'profile', name: 'Profile', component: () => import('../views/Profile.vue')},
//         {path: 'file', name: 'File', component: () => import('../views/File.vue')},
//     ]
// })


setRoutes()

router.beforeEach((to, from, next) => {
    localStorage.setItem("currentPathName", to.name)//设置当前的路由名称，为了在Header组件中去使用
    store.commit("setPath")//除法store的数据跟新


    if (!to.matched.length) {
        const storeMenus = localStorage.getItem("menus")
        if (storeMenus) {
            next("/404")
        } else {
            next("/login")
        }
    }
    next()

})

export default router
