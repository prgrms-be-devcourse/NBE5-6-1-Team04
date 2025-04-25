package com.grepp.spring.app.model.order;

import java.io.IOException;
import java.util.Base64;
import org.springframework.web.multipart.MultipartFile;

public class ImageUtil {
  public static String encodeBase64(MultipartFile multipartFile) throws IOException{
    return Base64.getEncoder().encodeToString(multipartFile.getBytes());
  }

}
