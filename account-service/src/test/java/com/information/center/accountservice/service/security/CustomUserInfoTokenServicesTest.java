package com.information.center.accountservice.service.security;

import static org.mockito.Mockito.mock;

import java.net.URLEncoder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;

@RunWith(MockitoJUnitRunner.class)
public class CustomUserInfoTokenServicesTest {

  private CustomUserInfoTokenServices customUserInfoTokenServices;
  private OAuth2RestOperations oAuth2RestOperations;

  @Before
  public void setUp() {
    oAuth2RestOperations = mock(OAuth2RestOperations.class);

    customUserInfoTokenServices = new CustomUserInfoTokenServices(URLEncoder.encode("https://127.0.0.1:8080/url") ,"");
    customUserInfoTokenServices.setTokenType(DefaultOAuth2AccessToken.BEARER_TYPE);
//    customUserInfoTokenServices.setRestTemplate(oAuth2RestOperations);
  }


  @Test(expected = UnsupportedOperationException.class)
  public void readAccessToken(){
    customUserInfoTokenServices.readAccessToken("accessToken");
  }

//  @Test
//  public void loadAuthentication(){
//    customUserInfoTokenServices.loadAuthentication("accessToken");
//  }

}
