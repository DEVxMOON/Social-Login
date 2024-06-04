package hr.oauth2.kakaologin.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate

@Service
class KakaoLoginService {

    @Value("\${kakao.client-id}")
    lateinit var clientId: String

    @Value("\${kakao.redirect-uri}")
    lateinit var redirectUri: String

    fun getKakaoAccessToken(code: String): String {
        val url = "https://kauth.kakao.com/oauth/token"
        val params = LinkedMultiValueMap<String, String>().apply {
            add("grant_type", "authorization_code")
            add("client_id", clientId)
            add("redirect_uri", redirectUri)
            add("code", code)
        }
        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val entity = HttpEntity(params, headers)
        val response: ResponseEntity<String> = restTemplate.postForEntity(url, entity, String::class.java)

        val jsonNode = ObjectMapper().readTree(response.body)
        return jsonNode.get("access_token").asText()
    }
}