package in.anupsharma.billibgsoftwere.service;

import in.anupsharma.billibgsoftwere.io.CategoryRequest;
import in.anupsharma.billibgsoftwere.io.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
 CategoryResponse add(CategoryRequest request, MultipartFile file);
 List<CategoryResponse> read();
 void delete(String categoryId);
}
