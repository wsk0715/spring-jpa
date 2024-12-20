package com.example.springboot_jpa.oauth.kakao.controller;

import com.example.springboot_jpa.oauth.kakao.service.OAuthKakaoService;
import com.example.springboot_jpa.oauth.response.OAuthResponse;
import com.example.springboot_jpa.response.BaseResponse;
import com.example.springboot_jpa.user.domain.Nickname;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/kakao")
public class OAuthKakaoController implements OAuthKakaoControllerDocs {

	@Value("${server.baseUrl}")
	private String BASE_URL;

	@Value("${security.oauth.kakao.authUrl}")
	private String AUTHORIZATION_URL;

	@Value("${security.oauth.kakao.clientId}")
	private String CLIENT_ID;

	@Value("${security.oauth.kakao.redirectUri}")
	private String REDIRECT_URI;

	private final OAuthKakaoService OAuthKakaoService;


	@GetMapping("/")
	public ResponseEntity<BaseResponse> authentication() {
		String url = AUTHORIZATION_URL
					 + "?client_id=" + CLIENT_ID
					 + "&redirect_uri=" + REDIRECT_URI
					 + "&response_type=code";

		OAuthResponse res = OAuthResponse.redirect()
										 .title("카카오 인증 요청")
										 .description("targetUrl로 이동해 주세요.")
										 .targetUrl(url)
										 .build();
		return ResponseEntity.ok(res);
	}

	@GetMapping("/callback")
	public ResponseEntity<BaseResponse> callback(@RequestParam("code") String code) {
		if (!OAuthKakaoService.init(code)) {
			String url = BASE_URL + "/auth";

			OAuthResponse res = OAuthResponse.ok()
											 .title("최초 로그인")
											 .description("targetUrl에 nickname을 POST 요청해주세요.")
											 .data(new Nickname("example01"))
											 .targetUrl(url)
											 .build();
			return ResponseEntity.ok(res);
		}

		OAuthResponse res = OAuthResponse.ok()
										 .title("로그인 성공")
										 .description("메인 페이지로 이동해주세요.")
										 .build();
		return ResponseEntity.ok(res);
	}

}