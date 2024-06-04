package hr.oauth2.kakaologin.controller

import hr.oauth2.kakaologin.service.KakaoLoginService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView

@RequestMapping("/kakao")
@RestController
class KakaoLoginController(private val kakaoLoginService: KakaoLoginService) {

    @Value("\${kakao.client-id}")
    lateinit var clientId: String

    @Value("\${kakao.redirect-uri}")
    lateinit var redirectUri: String

    @GetMapping("/login")
    fun login():RedirectView{
        val uri = "https://kauth.kakao.com/oauth/authorize?client_id=$clientId&redirect_uri=$redirectUri&response_type=code"
        return RedirectView(uri)
    }

    @GetMapping("/callback")
    fun callback(@RequestParam code: String): ResponseEntity<String> {
        val accessToken = kakaoLoginService.getKakaoAccessToken(code)
        return ResponseEntity.ok(accessToken)
    }

}