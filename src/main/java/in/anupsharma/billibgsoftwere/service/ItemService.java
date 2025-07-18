package in.anupsharma.billibgsoftwere.service;

import in.anupsharma.billibgsoftwere.io.ItemRequest;
import in.anupsharma.billibgsoftwere.io.ItemResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {
   ItemResponse add(ItemRequest request, MultipartFile file);
 List<ItemResponse> fetchItems();
 void deleteItem(String itemId);
}
