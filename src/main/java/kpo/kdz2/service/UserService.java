package kpo.kdz2.service;

import java.util.List;
import kpo.kdz2.domain.Order;
import kpo.kdz2.domain.Session;
import kpo.kdz2.domain.User;
import kpo.kdz2.model.UserDTO;
import kpo.kdz2.repos.OrderRepository;
import kpo.kdz2.repos.SessionRepository;
import kpo.kdz2.repos.UserRepository;
import kpo.kdz2.util.NotFoundException;
import kpo.kdz2.util.WebUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final OrderRepository orderRepository;

    public UserService(final UserRepository userRepository,
            final SessionRepository sessionRepository, final OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.orderRepository = orderRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map((user) -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Integer id) {
        return userRepository.findById(id)
                .map((user) -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Integer id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Integer id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPasswordHash(user.getPasswordHash());
        userDTO.setRole(user.getRole());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(userDTO.getPasswordHash());
        user.setRole(userDTO.getRole());
        user.setCreatedAt(userDTO.getCreatedAt());
        user.setUpdatedAt(userDTO.getUpdatedAt());
        return user;
    }

    public boolean usernameExists(final String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }

    public boolean emailExists(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }
    public boolean emailCorrect(final String email) {
        return !(email.contains("@") && (email.indexOf("@") < email.lastIndexOf(".")));
    }

    public String getReferencedWarning(final Integer id) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Session userSession = sessionRepository.findFirstByUser(user);
        if (userSession != null) {
            return WebUtils.getMessage("user.session.user.referenced", userSession.getId());
        }
        final Order userOrder = orderRepository.findFirstByUser(user);
        if (userOrder != null) {
            return WebUtils.getMessage("user.order.user.referenced", userOrder.getId());
        }
        return null;
    }

}
