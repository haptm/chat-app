package com.haptm.chatapp.reposiroty;

import com.haptm.chatapp.model.FriendRequest;
import com.haptm.chatapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findBySender(User sender);
    List<FriendRequest> findByReceiver(User receiver);
}
