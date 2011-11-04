package org.scribe.model;

/**
 * Parameter object that groups OAuth config values
 * 
 * @author Pablo Fernandez
 */
public class OAuthConfig
{
  private final String apiKey;
  private final String apiSecret;
  private final String callback;
  private final SignatureType signatureType;
  private final String scope;
  private final String referenceId;
  
  public OAuthConfig(String key, String secret)
  {
    this(key, secret, null, null, null, null);
  }

  public OAuthConfig(String key, String secret, String callback, SignatureType type, String scope, String refID)
  {
    this.apiKey = key;
    this.apiSecret = secret;
    this.callback = callback != null ? callback : OAuthConstants.OUT_OF_BAND;
    this.signatureType = (type != null) ? type : SignatureType.Header;
    this.scope = scope;
    this.referenceId = refID;
  }

  public String getApiKey()
  {
    return apiKey;
  }

  public String getApiSecret()
  {
    return apiSecret;
  }

  public String getCallback()
  {
    return callback;
  }

  public SignatureType getSignatureType()
  {
    return signatureType;
  }

  public String getScope()
  {
    return scope;
  }

  public boolean hasScope()
  {
    return scope != null;
  }
  
  public String getReferenceId()
  {
	return referenceId;
  }
}