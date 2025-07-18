package in.anupsharma.billibgsoftwere.io;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
@Data
@Builder

public class CategoryResponse {
    private  String categoryId;

    private String name;
    private String description;
    private String bgColor;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String imgUrl;

private Integer items;


}
