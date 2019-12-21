package com.wiredbrains.friends.service;

import com.wiredbrains.friends.model.Friend;
import org.springframework.data.repository.CrudRepository;

// The second parameter must be the id Type in CrudRepository<Friend, Integer>
public interface FriendService extends CrudRepository<Friend, Integer> {

    // Spring data will generate the code.
    Iterable<Friend> findByFirstNameAndLastName(String firstName, String lastName);
    Iterable<Friend> findByFirstName(String firstName);
    Iterable<Friend> findByLastName(String lastName);
}
