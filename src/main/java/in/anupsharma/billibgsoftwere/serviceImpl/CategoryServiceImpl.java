package in.anupsharma.billibgsoftwere.serviceImpl;

import in.anupsharma.billibgsoftwere.entity.CategoryEntity;
import in.anupsharma.billibgsoftwere.io.CategoryRequest;
import in.anupsharma.billibgsoftwere.io.CategoryResponse;
import in.anupsharma.billibgsoftwere.repository.CategoryRepository;
import in.anupsharma.billibgsoftwere.repository.ItemRepository;
import in.anupsharma.billibgsoftwere.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
private final FileuploadServiceImpl fileuploadService;
    @Override
    public CategoryResponse add(CategoryRequest request,MultipartFile file) {
String imgUrl=fileuploadService.uploadFile(file);
        CategoryEntity newCategory=convertToEntity(request);
        newCategory.setImgUrl(imgUrl);
     newCategory= categoryRepository.save(newCategory);

      return  convertToResponse(newCategory);


    }

    @Override
    public List<CategoryResponse> read() {
  return  categoryRepository.findAll().stream().
                          map(categoryEntity -> convertToResponse(categoryEntity)).
                          collect(Collectors.toList());

    }

    @Override
    public void delete(String categoryId) {
   CategoryEntity existingCategory=  categoryRepository.findByCategoryId(categoryId).
                       orElseThrow(()->new RuntimeException("Category not found"+ categoryId)
                       );
   fileuploadService.deleteFile(existingCategory.getImgUrl());
   categoryRepository.delete(existingCategory);
    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
    Integer itemsCount=   itemRepository.countByCategoryId(newCategory.getId());

       return CategoryResponse.builder().
                              categoryId(newCategory.getCategoryId()).
                              name(newCategory.getName()).
                              description(newCategory.getDescription()).
                              bgColor(newCategory.getBgColor()).
                              imgUrl(newCategory.getImgUrl()).
                              createdAt(newCategory.getCreatedAt()).
                              updatedAt(newCategory.getUpdatedAt()).
                              items(itemsCount).
                              build();

    }

    private CategoryEntity convertToEntity(CategoryRequest request) {
       return  CategoryEntity.builder().
                      categoryId(UUID.randomUUID().toString()).
                      name(request.getName()).
                      description(request.getDescription()).
                      bgColor(request.getBgColor()).
                      build();
    }
}
