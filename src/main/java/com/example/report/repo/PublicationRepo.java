package com.example.report.repo;

import com.example.report.entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationRepo extends JpaRepository<Publication,Long> {
}
