package in.anupsharma.billibgsoftwere.serviceImpl;

import in.anupsharma.billibgsoftwere.entity.CategoryEntity;
import in.anupsharma.billibgsoftwere.entity.ItemEntity;
import in.anupsharma.billibgsoftwere.io.ItemRequest;
import in.anupsharma.billibgsoftwere.io.ItemResponse;
import in.anupsharma.billibgsoftwere.repository.CategoryRepository;
import in.anupsharma.billibgsoftwere.repository.ItemRepository;
import in.anupsharma.billibgsoftwere.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final FileuploadServiceImpl fileuploadService;
  private final CategoryRepository categoryRepository;
  private  final ItemRepository itemRepository;
    @Override
    public ItemResponse add(ItemRequest request,MultipartFile file) {
      String imgUrl=fileuploadService.uploadFile(file);
      ItemEntity newItem=convertToEntity(request);
     CategoryEntity existingCategory= categoryRepository.findByCategoryId(request.getCategoryId())
                                                        .orElseThrow(()->new RuntimeException("category not found "+request.getCategoryId()));
      newItem.setCategory(existingCategory);
      newItem.setImgUrl(imgUrl);
      newItem=itemRepository.save(newItem);
      return convertTOResponse(newItem);

    }

  private ItemResponse convertTOResponse(ItemEntity newItem) {
      return ItemResponse.builder().itemId(newItem.getItemId()).
                  name(newItem.getName())
              .description(newItem.getDescription())
              .price(newItem.getPrice()).
                         imgUrl(newItem.getImgUrl()).
                  categoryName(newItem.getCategory().getName())
              .categoryId(newItem.getCategory().getCategoryId()).createdAt(newItem.getCreatedAt())
              .updatedAt(newItem.getUpdatedAt()).build();
  }

  private ItemEntity convertToEntity(ItemRequest request) {
 return ItemEntity.builder().itemId(UUID.randomUUID().toString()).name(request.getName())
           .description(request.getDescription()).price(request.getPrice()).build();
  }

  @Override
    public List<ItemResponse> fetchItems() {
      return
itemRepository.findAll().stream().map(itemEntity -> convertTOResponse(itemEntity)).collect(Collectors.toList());

    }

    @Override
    public void deleteItem(String itemId) {
      ItemEntity existingUser=
itemRepository.findByItemId(itemId).orElseThrow(()->new RuntimeException("item not found"));
        fileuploadService.deleteFile(existingUser.getImgUrl());
   itemRepository.delete(existingUser);
    }
}
