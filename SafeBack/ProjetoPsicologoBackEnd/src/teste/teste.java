
package teste;
  
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
  
public class teste {
  
         public static void main(String args []) throws NoSuchAlgorithmException, 
         UnsupportedEncodingException {
                    
                   String senha = "felipe";
  
                   MessageDigest algorithm = MessageDigest.getInstance("MD5");
                   byte messageDigestSenha[] = algorithm.digest(senha.getBytes("UTF-8"));
                    
                   System.out.println(messageDigestSenha);
         }
          
}
