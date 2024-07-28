package com.haptm.chatapp.controller;

import com.haptm.chatapp.model.FriendRequest;
import com.haptm.chatapp.service.FriendRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Friend Request Resource",
        description = "CRUD REST APIs - Create Request, Update Request, Get Request, Delete Request"
)
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("api/v1/user")
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    @Operation(
            summary = "Get Friend Requests By Sender ID REST API",
            description = "Get Friend Requests By Sender ID REST API is used to get all requests of a user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping("/{senderId}/request-sent")
    public ResponseEntity<List<FriendRequest>> getFriendRequestBySenderId(@Valid @PathVariable Long senderId){
        return ResponseEntity.ok(friendRequestService.getFriendRequestsBySenderId(senderId));
    }

    @Operation(
            summary = "Get Friend Requests By Receiver ID REST API",
            description = "Get Friend Requests By Receiver ID REST API is used to get all requests to a user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping("/{receiverId}/request-received")
    public ResponseEntity<List<FriendRequest>> getFriendRequestByReceiverId(@Valid @PathVariable Long receiverId){
        return ResponseEntity.ok(friendRequestService.getFriendRequestsByReceiverId(receiverId));
    }

    @Operation(
            summary = "Create Friend Request REST API",
            description = "Create Friend Request REST API is used to save request in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @PostMapping("/request")
    public ResponseEntity<FriendRequest> createFriendRequest(@Valid @RequestBody FriendRequest friendRequest){
        return ResponseEntity.ok(friendRequestService.createFriendRequest(friendRequest));
    }

    @Operation(
            summary = "Update Friend Request REST API",
            description = "Update Friend Request REST API is used to accept request"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @PutMapping("/request-accepted/{requestId}")
    public ResponseEntity<FriendRequest> acceptFriendRequest(@Valid @PathVariable Long requestId){
        return ResponseEntity.ok(friendRequestService.acceptFriendRequest(requestId));
    }

    @Operation(
            summary = "Update Friend Request REST API",
            description = "Update Friend Request REST API is used to refuse request"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @PutMapping("/request-refused/{requestId}")
    public ResponseEntity<FriendRequest> refuseFriendRequest(@Valid @PathVariable Long requestId){
        return ResponseEntity.ok(friendRequestService.refuseFriendRequest(requestId));
    }

    @Operation(
            summary = "Delete Request REST API",
            description = "Delete Request REST API is used to delete a request from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @DeleteMapping("/request/{requestId}")
    public ResponseEntity<String> deleteFriendRequest(@Valid @PathVariable Long requestId){
        friendRequestService.deleteFriendRequest(requestId);
        return ResponseEntity.ok("Request deleted successfully");
    }
}
