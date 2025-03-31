package com.auction.user.service;

import com.auction.user.entity.User;
import com.auction.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("john123", 25, "male", "Reading,Sports", "Pass@123");
    }

    @Test
    void testSignup_Success() {
        when(userRepository.findByUsername("john123")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.signup(user);

        assertNotNull(savedUser);
        assertEquals("john123", savedUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testSignup_UsernameExists() {
        when(userRepository.findByUsername("john123")).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.signup(user);
        });

        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLogin_Success() {
        when(userRepository.findByUsername("john123")).thenReturn(Optional.of(user));

        User loggedInUser = userService.login("john123", "Pass@123");

        assertNotNull(loggedInUser);
        assertEquals("john123", loggedInUser.getUsername());
    }

    @Test
    void testLogin_UserNotFound() {
        when(userRepository.findByUsername("john123")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.login("john123", "Pass@123");
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testLogin_InvalidPassword() {
        when(userRepository.findByUsername("john123")).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.login("john123", "WrongPass");
        });

        assertEquals("Invalid password", exception.getMessage());
    }
}
