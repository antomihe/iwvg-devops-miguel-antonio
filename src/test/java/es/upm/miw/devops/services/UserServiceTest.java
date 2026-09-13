package es.upm.miw.devops.services;

import es.upm.miw.devops.data.repositories.UserRepository;
import es.upm.miw.devops.data.model.User;
import es.upm.miw.devops.rest.dto.ActiveDto;
import es.upm.miw.devops.rest.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User(1L, "Oscar", "oscar@example.com", "Oscar", "Perez",
                "12345678Z", "Calle Mayor 1", "Madrid", "Madrid", "28001");
        sampleUser.setActive(true);
    }

    @Test
    void testReadSuccess() {
        when(this.userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        UserDto dto = this.userService.read(1L);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Oscar", dto.getName());
        assertEquals("oscar@example.com", dto.getEmail());
        assertTrue(dto.getBillable());
        assertTrue(dto.getActive());
        verify(this.userRepository, times(1)).findById(1L);
    }

    @Test
    void testReadNotFound() {
        when(this.userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> this.userService.read(99L));
        verify(this.userRepository, times(1)).findById(99L);
    }

    @Test
    void testSearchFilters() {
        when(this.userRepository.findAll()).thenReturn(List.of(sampleUser));

        Stream<UserDto> result = this.userService.search("Oscar", "OSCAR@EXAMPLE.COM", true);
        List<UserDto> dtoList = result.toList();

        assertEquals(1, dtoList.size());
        assertEquals("Oscar", dtoList.get(0).getName());
    }

    @Test
    void testDeleteSuccess() {
        when(this.userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(this.userRepository).deleteById(1L);

        assertDoesNotThrow(() -> this.userService.delete(1L));
        verify(this.userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(this.userRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> this.userService.delete(99L));
        verify(this.userRepository, never()).deleteById(anyLong());
    }

    @Test
    void testUpdateActiveSuccess() {
        ActiveDto activeDto = new ActiveDto(false);
        when(this.userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(this.userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserDto updatedDto = this.userService.updateActive(1L, activeDto);

        assertFalse(updatedDto.getActive());
        verify(this.userRepository, times(1)).save(sampleUser);
    }
}