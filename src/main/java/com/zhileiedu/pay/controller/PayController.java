package com.zhileiedu.pay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.zhileiedu.pay.configuration.AlipayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: 王志雷
 * @Date: 2020/5/9 12:25
 */
@Slf4j
@Controller
public class PayController {


	/**
	 * 手机端h5支付
	 *
	 * @param out_trade_no
	 * @param total_amount
	 * @param subject
	 * @param body
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/mpay", produces = "text/html")
	private String mpay(String out_trade_no, String total_amount, String subject, String body) {

		// 设置支付超时时间
		String timeout_express = "2m";

		// 1、创建支付宝客户端
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
				AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key,
				AlipayConfig.sign_type);

		//  手机端支付
		AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		alipayRequest.setReturnUrl(AlipayConfig.return_url);

		// 商户订单号，商户网站订单系统中唯一订单号，必填
		// 付款金额，必填
		// 订单名称，必填
		// 商品描述，可空

		// 3 手机端
		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setBody(body);
		model.setTotalAmount(total_amount);
		model.setOutTradeNo(out_trade_no);
		model.setSubject(subject);
		model.setTimeoutExpress(timeout_express);
		model.setProductCode("142424");
		alipayRequest.setBizModel(model);

		String result = "";
		try {
			// 4、请求
			result = alipayClient.pageExecute(alipayRequest).getBody();
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}

		return result;// 支付跳转页的代码

	}


	/**
	 * 网页扫描支付
	 *
	 * @param out_trade_no
	 * @param total_amount
	 * @param subject
	 * @param body
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/pay", produces = "text/html")
	private String payOrder(String out_trade_no, String total_amount, String subject, String body) {

		// 1、创建支付宝客户端
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
				AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key,
				AlipayConfig.sign_type);

		// 2、创建一次支付请求
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

		// 商户订单号，商户网站订单系统中唯一订单号，必填
		// 付款金额，必填
		// 订单名称，必填
		// 商品描述，可空

		// 3、构造支付请求数据
		alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"total_amount\":\"" + total_amount
				+ "\"," + "\"subject\":\"" + subject + "\"," + "\"body\":\"" + body + "\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

		String result = "";
		try {
			// 4、请求
			result = alipayClient.pageExecute(alipayRequest).getBody();
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}

		return result;// 支付跳转页的代码

	}


}
