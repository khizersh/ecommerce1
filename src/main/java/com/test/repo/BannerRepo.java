package com.test.repo;

import com.test.bean.banner.HomePageBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepo extends JpaRepository<HomePageBanner , Integer> {
}
