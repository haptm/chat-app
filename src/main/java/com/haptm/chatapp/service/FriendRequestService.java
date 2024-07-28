package com.haptm.chatapp.service;

import com.haptm.chatapp.exception.FriendRequestNotFoundException;
import com.haptm.chatapp.model.FriendRequest;
import com.haptm.chatapp.model.User;
import com.haptm.chatapp.reposiroty.FriendRequestRepository;
import com.haptm.chatapp.utils.RequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;
    private final UserService userService;

    public List<FriendRequest> getAllFriendRequests() {
        return friendRequestRepository.findAll();
    }

    public List<FriendRequest> getFriendRequestsBySenderId(Long senderId) {
        User sender = userService.getUserById(senderId);
        return friendRequestRepository.findBySender(sender);
    }

    public List<FriendRequest> getFriendRequestsByReceiverId(Long receiverId) {
        User receiver = userService.getUserById(receiverId);
        return friendRequestRepository.findByReceiver(receiver);
    }

    public FriendRequest createFriendRequest(FriendRequest friendRequest) {
        return friendRequestRepository.save(friendRequest);
    }

    public FriendRequest acceptFriendRequest(Long requestId) {
        Optional<FriendRequest> friendRequestOptional = friendRequestRepository.findById(requestId);
        if (friendRequestOptional.isPresent()) {
            FriendRequest friendRequest = friendRequestOptional.get();
            friendRequest.setStatus(RequestStatus.accepted);
            User user = userService.addFriend(friendRequest.getSender().getUserId(), friendRequest.getReceiver().getUserId());
            return friendRequestRepository.save(friendRequest);
        }
        else {
            throw new FriendRequestNotFoundException("Friend request not found");
        }
    }

    public FriendRequest refuseFriendRequest(Long requestId) {
        Optional<FriendRequest> friendRequestOptional = friendRequestRepository.findById(requestId);
        if (friendRequestOptional.isPresent()) {
            FriendRequest friendRequest = friendRequestOptional.get();
            friendRequest.setStatus(RequestStatus.refused);
            return friendRequestRepository.save(friendRequest);
        }
        else {
            throw new FriendRequestNotFoundException("Friend request not found");
        }
    }

    public void deleteFriendRequest(Long requestId) {
        friendRequestRepository.deleteById(requestId);
    }
}
