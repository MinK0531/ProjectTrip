package com.mink.projecttrip.wishlist.repository;

import com.mink.projecttrip.wishlist.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
}
