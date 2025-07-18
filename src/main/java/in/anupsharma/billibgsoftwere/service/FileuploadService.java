package in.anupsharma.billibgsoftwere.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileuploadService {
    String uploadFile(MultipartFile file);
  boolean  deleteFile(String imgUrl);
}
