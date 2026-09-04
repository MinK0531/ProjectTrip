package com.mink.projecttrip.wishlist.repository;

import com.mink.projecttrip.wishlist.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUserIdOrderByIdDesc(long userId);
    public int countByUserId(long userId);
}
