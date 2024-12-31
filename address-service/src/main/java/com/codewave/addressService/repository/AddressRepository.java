package com.codewave.addressService.repository;

import com.codewave.addressService.entity.Address;
import com.codewave.addressService.entity.AddressKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT a FROM Address a WHERE a.addressKey.userId = :userId")
    List<Address> findByUserId(@Param("userId")Long userId);

    @Query("SELECT MAX (a.addressKey.addressId) FROM Address a WHERE a.addressKey.userId = :userId")
    Optional<Long> findMaxAddressId(@Param("userId") Long userId);

    Address findAddressByAddressKey(AddressKey addressKey);
}
