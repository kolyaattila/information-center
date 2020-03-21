package com.information.center.topicservice.service.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

@RunWith(MockitoJUnitRunner.class)
public class CustomUserInfoTokenServicesTest {

  private static final String ACCESS_TOKEN = "accessToken";
  private static final String PATH = "https://127.0.0.1:8080/url";
  private static final String CLIENT_ID = "clientId";
  private static final String USER = "user";
  private static final String CREDENTIALS = "N/A";
  private CustomUserInfoTokenServices customUserInfoTokenServices;
  private OAuth2RestOperations restTemplate;
  private OAuth2ClientContext oAuth2ClientContext;
  private OAuth2AccessToken oAuth2AccessToken;
  private AuthoritiesExtractor authoritiesExtractor;

  @Before
  public void setUp() {
    restTemplate = mock(OAuth2RestOperations.class);
    oAuth2ClientContext = mock(OAuth2ClientContext.class);
    oAuth2AccessToken = mock(OAuth2AccessToken.class);
    authoritiesExtractor = mock(AuthoritiesExtractor.class);

    customUserInfoTokenServices = new CustomUserInfoTokenServices(PATH, "");
    customUserInfoTokenServices.setTokenType(DefaultOAuth2AccessToken.BEARER_TYPE);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void readAccessToken_expectException() {
    customUserInfoTokenServices.readAccessToken(ACCESS_TOKEN);
  }

  @Test(expected = InvalidTokenException.class)
  public void loadAuthentication_expectException() {
    customUserInfoTokenServices.loadAuthentication(ACCESS_TOKEN);
  }

  @Test
  public void loadAuthentication_ExpectOAuth2AuthenticationWithScope() {
    customUserInfoTokenServices.setRestTemplate(restTemplate);
    customUserInfoTokenServices.setAuthoritiesExtractor(authoritiesExtractor);
    Map<String, Object> mapWithScope = getMapWithScope();

    when(restTemplate.getOAuth2ClientContext()).thenReturn(oAuth2ClientContext);
    when(oAuth2ClientContext.getAccessToken()).thenReturn(oAuth2AccessToken);
    when(oAuth2AccessToken.getValue()).thenReturn(ACCESS_TOKEN);
    when(restTemplate.getForEntity(PATH, Map.class))
        .thenReturn(new ResponseEntity<>(mapWithScope, OK));
    when(authoritiesExtractor.extractAuthorities(mapWithScope))
        .thenReturn(Collections.singletonList(new SimpleGrantedAuthority("USER")));

    OAuth2Authentication response = customUserInfoTokenServices.loadAuthentication(ACCESS_TOKEN);

    assertNotNull(response);
    assertEquals(USER, response.getPrincipal());
    assertTrue(response.getAuthorities().contains(new SimpleGrantedAuthority("USER")));
    assertOAuth2Request(response);
    assertUserAuthentication(mapWithScope, response);
    assertTrue(
        response.getOAuth2Request().getScope().containsAll(Arrays.asList("scope1", "scope2")));
  }

  @Test
  public void loadAuthentication_ExpectOAuth2AuthenticationWithoutScope() {
    customUserInfoTokenServices.setRestTemplate(restTemplate);
    customUserInfoTokenServices.setAuthoritiesExtractor(authoritiesExtractor);
    Map<String, Object> mapWithoutScope = getMapWithoutScope();

    when(restTemplate.getOAuth2ClientContext()).thenReturn(oAuth2ClientContext);
    when(oAuth2ClientContext.getAccessToken()).thenReturn(oAuth2AccessToken);
    when(oAuth2AccessToken.getValue()).thenReturn(ACCESS_TOKEN);
    when(restTemplate.getForEntity(PATH, Map.class))
        .thenReturn(new ResponseEntity<>(mapWithoutScope, OK));
    when(authoritiesExtractor.extractAuthorities(mapWithoutScope))
        .thenReturn(Collections.singletonList(new SimpleGrantedAuthority("USER")));

    OAuth2Authentication response = customUserInfoTokenServices.loadAuthentication(ACCESS_TOKEN);

    assertNotNull(response);
    assertEquals(USER, response.getPrincipal());
    assertTrue(response.getAuthorities().contains(new SimpleGrantedAuthority("USER")));
    assertOAuth2Request(response);
    assertUserAuthentication(mapWithoutScope, response);
    assertFalse(
        response.getOAuth2Request().getScope().containsAll(Arrays.asList("scope1", "scope2")));
  }

  @Test
  public void loadAuthentication_ExpectOAuth2AuthenticationWithUnknownKey() {
    customUserInfoTokenServices.setRestTemplate(restTemplate);
    customUserInfoTokenServices.setAuthoritiesExtractor(authoritiesExtractor);
    Map<String, Object> mapWithoutPrincipalKeys = getMapWithoutPrincipalKeys();

    when(restTemplate.getOAuth2ClientContext()).thenReturn(oAuth2ClientContext);
    when(oAuth2ClientContext.getAccessToken()).thenReturn(oAuth2AccessToken);
    when(oAuth2AccessToken.getValue()).thenReturn(ACCESS_TOKEN);
    when(restTemplate.getForEntity(PATH, Map.class))
        .thenReturn(new ResponseEntity<>(mapWithoutPrincipalKeys, OK));
    when(authoritiesExtractor.extractAuthorities(mapWithoutPrincipalKeys))
        .thenReturn(Collections.singletonList(new SimpleGrantedAuthority("USER")));

    OAuth2Authentication response = customUserInfoTokenServices.loadAuthentication(ACCESS_TOKEN);

    assertNotNull(response);
    assertEquals("unknown", response.getPrincipal());
    assertTrue(response.getAuthorities().contains(new SimpleGrantedAuthority("USER")));
    assertOAuth2Request(response);
    assertUserAuthentication(mapWithoutPrincipalKeys, response);
    assertTrue(
        response.getOAuth2Request().getScope().containsAll(Arrays.asList("scope1", "scope2")));
  }

  private Map<String, Object> getMapWithScope() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("unknownKey", "value");
    map.put("user", "user");
    map.put("oauth2Request", getOauth2RequestMapWithScope());
    return map;
  }

  private Map<String, Object> getOauth2RequestMapWithScope() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("clientId", CLIENT_ID);
    map.put("scope", Arrays.asList("scope1", "scope2"));
    return map;
  }

  private void assertUserAuthentication(Map<String, Object> mapWithScope,
      OAuth2Authentication response) {
    assertEquals(CREDENTIALS, response.getUserAuthentication().getCredentials());
    assertTrue(response.getUserAuthentication().getAuthorities()
        .contains(new SimpleGrantedAuthority("USER")));
    assertTrue(response.getUserAuthentication().isAuthenticated());
    assertEquals(mapWithScope, response.getUserAuthentication().getDetails());
  }

  private void assertOAuth2Request(OAuth2Authentication response) {
    assertNull(response.getOAuth2Request().getRedirectUri());
    assertTrue(response.getOAuth2Request().getResourceIds().isEmpty());
    assertTrue(response.getOAuth2Request().getAuthorities().isEmpty());
    assertTrue(response.getOAuth2Request().getResponseTypes().isEmpty());
    assertEquals(CLIENT_ID, response.getOAuth2Request().getClientId());
  }

  private Map<String, Object> getMapWithoutScope() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("user", USER);
    map.put("oauth2Request", getOauth2RequestMapWithoutScope());
    return map;
  }

  private Map<String, Object> getOauth2RequestMapWithoutScope() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("clientId", CLIENT_ID);
    return map;
  }

  private Map<String, Object> getMapWithoutPrincipalKeys() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("unknownKey", "value");
    map.put("oauth2Request", getOauth2RequestMapWithScope());
    return map;
  }
}
