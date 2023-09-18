package com.example.demo.Repo;

import com.example.demo.models.Billboard;
import com.example.demo.models.Post;
import com.example.demo.models.Prototype;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PrototypeRepo extends CrudRepository<Prototype, Long> {
//    @Query("SELECT b FROM Prototype b WHERE b.Address = :address")
//    Optional<Prototype> findByAddress(String address);
        Prototype findById(long id);
}
