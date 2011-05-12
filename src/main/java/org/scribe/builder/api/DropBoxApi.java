package org.scribe.builder.api;

import org.scribe.model.*;

public class DropBoxApi extends DefaultApi10a
{
  @Override
  public String getAccessTokenEndpoint()
  {
    return "https://api.dropbox.com/0/oauth/access_token";
  }

  @Override
  public String getAuthorizationUrl(Token requestToken, OAuthConfig config)
  {
    return "https://www.dropbox.com/0/oauth/authorize?oauth_token="+requestToken.getToken();
  }

  @Override
  public String getRequestTokenEndpoint()
  {
    return "https://api.dropbox.com/0/oauth/request_token";
  }

}