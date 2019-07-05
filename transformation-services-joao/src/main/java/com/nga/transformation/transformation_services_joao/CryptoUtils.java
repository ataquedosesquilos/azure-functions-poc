package com.nga.transformation.transformation_services_joao;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import javax.security.auth.x500.X500Principal;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMException;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.bouncycastle.util.encoders.Hex;

public class CryptoUtils
{
  private static final String ALGORITHM_SHA256WITHECDSA = "SHA256withECDSA";
  private static final String ALGORITHM_SHA256 = "SHA-256";
  private static final String ALGORITHM_X509 = "X.509";
  public static final String BEGIN_CERTIFICATE = "-----BEGIN CERTIFICATE-----\\n";
  public static final String END_CERTIFICATE = "\\n-----END CERTIFICATE-----";

  
  public CryptoUtils()
  {
    Security.addProvider(new BouncyCastleProvider());
  }
  
  public CryptoUtils(Provider provider)
  {
    Security.addProvider(provider);
  }
  
  public String hashSha256(String input)
  {
    return hashSha256(input.getBytes());
  }
  
  public String hashSha256(byte[] input)
  {
    try
    {
      MessageDigest md = MessageDigest.getInstance(ALGORITHM_SHA256);
      md.update(input);
      byte[] byteData = md.digest();
      return Hex.toHexString(byteData);
    }
    catch (NoSuchAlgorithmException e)
    {
      throw new IllegalArgumentException(e);
    }
  }
  
  public PrivateKey getPrivateKeyFromBytes(byte[] data)
    throws IOException, NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException
  {
    Reader pemReader = new StringReader(new String(data));
    
    PEMParser pemParser = new PEMParser(pemReader);Throwable localThrowable3 = null;
    PrivateKeyInfo pemPair;
    try
    {
      pemPair = ((PEMKeyPair)pemParser.readObject()).getPrivateKeyInfo();
    }
    catch (Throwable localThrowable1)
    {
     
      localThrowable3 = localThrowable1;throw localThrowable1;
    }
    finally
    {
      if (pemParser != null) {
        if (localThrowable3 != null) {
          try
          {
            pemParser.close();
          }
          catch (Throwable localThrowable2)
          {
            localThrowable3.addSuppressed(localThrowable2);
          }
        } else {
          pemParser.close();
        }
      }
    }
    return new JcaPEMKeyConverter().setProvider("BC").getPrivateKey(pemPair);
  }
  
  public PrivateKey getPrivateKeyFromFile(Path privateKeyFilePath)
    throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException, IOException
  {
    return getPrivateKeyFromBytes(IOUtils.toByteArray(new FileInputStream(privateKeyFilePath.toFile())));
  }
  
  public PKCS10CertificationRequest generateCertificationRequest(String subject, String organisation, KeyPair pair)
    throws OperatorCreationException
  {
    X500Principal principal = new X500Principal("C=GB, ST=England, L=London, O=Gospel Technology Ltd, OU=" + organisation + ", CN=" + subject);
    
    PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(principal, pair.getPublic());
    JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder(ALGORITHM_SHA256WITHECDSA);
    ContentSigner signer = csBuilder.build(pair.getPrivate());
    return p10Builder.build(signer);
  }
  
  public X509Certificate generateCertificate(X500Name issuer, BigInteger serial, Date notBefore, Date notAfter, PKCS10CertificationRequest csr, ContentSigner sigGen)
    throws InvalidKeyException, NoSuchAlgorithmException, CertificateException, PEMException
  {
    SubjectPublicKeyInfo pkInfo = csr.getSubjectPublicKeyInfo();
    JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
    PublicKey pubKey = converter.getPublicKey(pkInfo);
    
    X509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(issuer, serial, notBefore, notAfter, csr.getSubject(), pubKey);
    X509CertificateHolder certificateHolder = certificateBuilder.build(sigGen);
    return new JcaX509CertificateConverter().setProvider("BC").getCertificate(certificateHolder);
  }
  
  public String getCertificateThumbprint(X509Certificate cert)
    throws CertificateEncodingException
  {
    try
    {
      MessageDigest md = MessageDigest.getInstance("SHA-1");
      byte[] der = cert.getEncoded();
      md.update(der);
      byte[] digest = md.digest();
      String digestHex = DatatypeConverter.printHexBinary(digest);
      return digestHex.toLowerCase();
    }
    catch (NoSuchAlgorithmException e)
    {
      throw new IllegalArgumentException(e);
    }
  }
  
  public PKCS10CertificationRequest convertPemToPKCS10CertificationRequest(String pem)
    throws IOException
  {
    PKCS10CertificationRequest csr = null;
    ByteArrayInputStream pemStream = null;
    pemStream = new ByteArrayInputStream(pem.getBytes("UTF-8"));
    
    Reader pemReader = new BufferedReader(new InputStreamReader(pemStream));
    PEMParser pemParser = new PEMParser(pemReader);
    
    Object parsedObj = pemParser.readObject();
    if ((parsedObj instanceof PKCS10CertificationRequest)) {
      csr = (PKCS10CertificationRequest)parsedObj;
    }
    pemParser.close();
    return csr;
  }
  
  public String convertCertificateToPEM(X509Certificate signedCertificate)
    throws IOException
  {
    StringWriter signedCertificatePEMDataStringWriter = new StringWriter();
    JcaPEMWriter pemWriter = new JcaPEMWriter(signedCertificatePEMDataStringWriter);
    pemWriter.writeObject(signedCertificate);
    pemWriter.close();
    return signedCertificatePEMDataStringWriter.toString();
  }
  
  public String convertPrivateKeyToPEM(PrivateKey privateKey)
  {
    StringWriter privateKeyPEMDataStringWriter = new StringWriter();
    JcaPEMWriter pemWriter = new JcaPEMWriter(privateKeyPEMDataStringWriter);
    try
    {
      pemWriter.writeObject(privateKey);
      pemWriter.close();
    }
    catch (IOException e)
    {
      throw new IllegalArgumentException(e);
    }
    return privateKeyPEMDataStringWriter.toString();
  }
  
  public  X509Certificate getX509CertificateFromFile(Path path)
    throws FileNotFoundException, CertificateException
  {
    CertificateFactory fact = CertificateFactory.getInstance(ALGORITHM_X509);
    FileInputStream is = new FileInputStream(path.toFile());
    return (X509Certificate)fact.generateCertificate(is);
  }
  
  public PrivateKey getPrivateKeyFromEcPem(Path path)
    throws IOException
  {
    String fileContent = new String(Files.readAllBytes(path));
    
    Reader pemReader = new StringReader(fileContent);
    PEMParser pemParser = new PEMParser(pemReader);Throwable localThrowable3 = null;
    PrivateKeyInfo pemPair;
    try
    {
      pemPair = ((PEMKeyPair)pemParser.readObject()).getPrivateKeyInfo();
    }
    catch (Throwable localThrowable1)
    {
      localThrowable3 = localThrowable1;throw localThrowable1;
    }
    finally
    {
      if (pemParser != null) {
        if (localThrowable3 != null) {
          try
          {
            pemParser.close();
          }
          catch (Throwable localThrowable2)
          {
            localThrowable3.addSuppressed(localThrowable2);
          }
        } else {
          pemParser.close();
        }
      }
    }
    Security.addProvider(new BouncyCastleProvider());
    return new JcaPEMKeyConverter().setProvider("BC").getPrivateKey(pemPair);
  }
  
  public KeyPair getKeyPairFromEcPem(String pem)
  {
    try
    {
      Reader pemReader = new StringReader(pem);
      PEMParser pemParser = new PEMParser(pemReader);Throwable localThrowable3 = null;
      PEMKeyPair keyPair;
      try
      {
        pemParser.readObject();
        keyPair = (PEMKeyPair)pemParser.readObject();
      }
      catch (Throwable localThrowable1)
      {
        
        localThrowable3 = localThrowable1;throw localThrowable1;
      }
      finally
      {
        if (pemParser != null) {
          if (localThrowable3 != null) {
            try
            {
              pemParser.close();
            }
            catch (Throwable localThrowable2)
            {
              localThrowable3.addSuppressed(localThrowable2);
            }
          } else {
            pemParser.close();
          }
        }
      }
      Security.addProvider(new BouncyCastleProvider());
      return new JcaPEMKeyConverter().setProvider("BC").getKeyPair(keyPair);
    }
    catch (IOException e)
    {
      throw new UnsupportedOperationException("Failed to load private key from file");
    }
  }
  
  public KeyPair generateKey(String encryptionName, String curveName)
    throws NoSuchAlgorithmException, InvalidAlgorithmParameterException
  {
    Security.addProvider(new BouncyCastleProvider());
    ECGenParameterSpec ecGenSpec = new ECGenParameterSpec(curveName);
    KeyPairGenerator g;
    try
    {
      g = KeyPairGenerator.getInstance(encryptionName, "BC");
    }
    catch (NoSuchProviderException e)
    {
      throw new UnsupportedOperationException(e.getMessage());
    }
    g.initialize(ecGenSpec, new SecureRandom());
    return g.generateKeyPair();
  }
}
