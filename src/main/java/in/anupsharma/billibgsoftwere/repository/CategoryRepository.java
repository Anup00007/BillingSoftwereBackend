package in.anupsharma.billibgsoftwere.repository;

import in.anupsharma.billibgsoftwere.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    Optional<CategoryEntity> findByCategoryId(String categoryId);
}


