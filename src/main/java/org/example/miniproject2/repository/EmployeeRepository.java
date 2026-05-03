package org.example.miniproject2.repository;

import org.example.miniproject2.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("""
   SELECT e FROM Employee e
    WHERE (:name IS NULL OR :name = '' OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%')))
    AND (:departmentId IS NULL OR e.department.id = :departmentId)
    AND (:minAge IS NULL OR e.age >= :minAge)
    AND (:maxAge IS NULL OR e.age <= :maxAge)
""")

    Page<Employee> search(
            @Param("name") String name,
            @Param("departmentId") Long departmentId,
            @Param("minAge") Integer minAge,
            @Param("maxAge") Integer maxAge,
            Pageable pageable
    );

    List<Employee> findByDepartmentId(Long id);
}