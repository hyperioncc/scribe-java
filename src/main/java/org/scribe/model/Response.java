package org.scribe.model;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.scribe.utils.*;

/**
 * Represents an HTTP Response.
 * 
 * @author Pablo Fernandez
 */
public class Response
{
  private static final String EMPTY = "";

  private int code;
  private String body;
  private InputStream stream;
  private Map<String, String> headers;

  Response(HttpURLConnection connection) throws IOException
  {
    try
    {
      if (connection.getURL().getProtocol().toLowerCase().contains("https"))
      {
    	try
    	{
    	  Response.trustAllHttpsCertificates();
    	} catch (Exception e) {} 
      }
      connection.connect();
      code = connection.getResponseCode();
      headers = parseHeaders(connection);
      stream = wasSuccessful() ? connection.getInputStream() : connection.getErrorStream();
    } catch (UnknownHostException e)
    {
      code = 404;
      body = EMPTY;
    }
  }

  private String parseBodyContents()
  {
    body = StreamUtils.getStreamContents(getStream());
    return body;
  }

  private Map<String, String> parseHeaders(HttpURLConnection conn)
  {
    Map<String, String> headers = new HashMap<String, String>();
    for (String key : conn.getHeaderFields().keySet())
    {
      headers.put(key, conn.getHeaderFields().get(key).get(0));
    }
    return headers;
  }

  private boolean wasSuccessful()
  {
    return getCode() >= 200 && getCode() < 400;
  }

  /**
   * Obtains the HTTP Response body
   * 
   * @return response body
   */
  public String getBody()
  {
    return body != null ? body : parseBodyContents();
  }

  /**
   * Obtains the meaningful stream of the HttpUrlConnection, either inputStream
   * or errorInputStream, depending on the status code
   * 
   * @return input stream / error stream
   */
  public InputStream getStream()
  {
    return stream;
  }

  /**
   * Obtains the HTTP status code
   * 
   * @return the status code
   */
  public int getCode()
  {
    return code;
  }

  /**
   * Obtains a {@link Map} containing the HTTP Response Headers
   * 
   * @return headers
   */
  public Map<String, String> getHeaders()
  {
    return headers;
  }

  /**
   * Obtains a single HTTP Header value, or null if undefined
   * 
   * @param header
   *          name
   * 
   * @return header value or null
   */
  public String getHeader(String name)
  {
    return headers.get(name);
  }

  HostnameVerifier hv = new HostnameVerifier() {
    public boolean verify(String urlHostName, SSLSession session) {
      return true;
    }
  };

  public static void trustAllHttpsCertificates() throws Exception {
    javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
    javax.net.ssl.TrustManager tm = new miTM();
    trustAllCerts[0] = tm;
    javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
    sc.init(null, trustAllCerts, null);
    javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
  }

  static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
      return null;
    }

    public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
      return true;
    }

    public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
      return true;
    }

    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException {
      return;
    }

    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException {
      return;
    }
  }

}