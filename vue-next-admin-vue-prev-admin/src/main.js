import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';

import Particles from 'vue-particles';
import Element from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import '@/theme/index.scss';
import { i18n } from '@/i18n/index.js';
import { globalComponentSize } from '@/utils/componentSize.js';

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from "axios";
Vue.use(ElementUI);
//配置axios的全局基本路径
axios.defaults.baseURL='http://localhost:80'
//全局属性配置,任意位置使用this.$http使用axios对象
Vue.prototype.$http = axios;

Vue.use(Particles);
Vue.use(Element, { i18n: (key, value) => i18n.t(key, value), size: globalComponentSize });

Vue.config.productionTip = false;
Vue.prototype.bus = new Vue();

new Vue({
	router,
	store,
	i18n,
	render: (h) => h(App),
}).$mount('#app');
