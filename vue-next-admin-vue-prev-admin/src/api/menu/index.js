import request from '@/utils/request';

/**
 * webpack 的代理只是本地开发生效，打包后需要在部署环境 搭建 nginx 代理
 * 所以：
 * 开发环境，请求跨越的接口。为了配置跨越示例
 * 线上环境，请求目录下的 `json` 数据
 * 一般后端接口都会处理跨越问题，可根据具体情况进行修改
 * json 格式地址：https://gitee.com/lyt-top/vue-next-admin-images/tree/master/vue2
 * 本地菜单地址：public/xxx.json
 */
// 获取后端动态路由菜单(超级管理)
export function getMenuAdmin() {
	// 本地数据，路径：`/public/xxx.json`
	return request({
		url: './admin.json',
		method: 'get',
	});
	// 模拟跨域
	// return request({
	// 	url: '/gitee/lyt-top/vue-next-admin-images/raw/master/vue2/admin.json',
	// 	method: 'get',
	// });
}

// 获取后端动态路由菜单(普通用户)
export function getMenuTest() {
	// 本地数据，路径：`/public/xxx.json`
	return request({
		url: './test.json',
		method: 'get',
	});
	// 模拟跨域
	// return request({
	// 	url: '/gitee/lyt-top/vue-next-admin-images/raw/master/vue2/test.json',
	// 	method: 'get',
	// });
}
