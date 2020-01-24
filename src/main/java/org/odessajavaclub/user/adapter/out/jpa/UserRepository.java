package org.odessajavaclub.user.adapter.out.jpa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.odessajavaclub.user.adapter.out.jpa.mapper.UserEntityMapper;
import org.odessajavaclub.user.adapter.out.jpa.model.UserEntity;
import org.odessajavaclub.user.application.port.out.CreateUserPort;
import org.odessajavaclub.user.application.port.out.DeleteUserPort;
import org.odessajavaclub.user.application.port.out.LoadUsersPort;
import org.odessajavaclub.user.application.port.out.UpdateUserPort;
import org.odessajavaclub.user.domain.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository implements CreateUserPort,
                                       DeleteUserPort,
                                       LoadUsersPort,
                                       UpdateUserPort {

  private final UserJpaRepository userJpaRepository;

  private final UserEntityMapper userEntityMapper;

  @Override
  public User createUser(User user) {
    UserEntity userEntity = userEntityMapper.toUserEntity(user);
    UserEntity createdUser = userJpaRepository.save(userEntity);
    return userEntityMapper.toUser(createdUser);
  }

  @Override
  public boolean deleteUser(User.UserId userId) {
    return userJpaRepository.findById(userId.getValue())
                            .map(u -> {
                              userJpaRepository.delete(u);
                              return true;
                            })
                            .orElse(false);
  }

  @Override
  public List<User> loadAllUsers() {
    return StreamSupport.stream(userJpaRepository.findAll().spliterator(), false)
                        .map(userEntityMapper::toUser)
                        .collect(Collectors.toList());
  }

  @Override
  public List<User> loadAllUsers(int page, int size) {
    return StreamSupport
        .stream(userJpaRepository.findAll(PageRequest.of(page, size)).spliterator(), false)
        .map(userEntityMapper::toUser)
        .collect(Collectors.toList());
  }

  @Override
  public List<User> loadAllUsersByActive(boolean active) {
    return userJpaRepository.findAllByActive(active, Pageable.unpaged())
                            .stream()
                            .map(userEntityMapper::toUser)
                            .collect(Collectors.toList());
  }

  @Override
  public List<User> loadAllUsersByActive(boolean active, int page, int size) {
    return userJpaRepository.findAllByActive(active, PageRequest.of(page, size))
                            .stream()
                            .map(userEntityMapper::toUser)
                            .collect(Collectors.toList());
  }

  @Override
  public Optional<User> loadUser(User.UserId userId) {
    return userJpaRepository.findById(userId.getValue())
                            .map(userEntityMapper::toUser);
  }

  @Override
  public User updateUser(User updatedUser) {
    checkIdIsPresent(updatedUser);
    UserEntity userEntity = userEntityMapper.toUserEntity(updatedUser);
    userJpaRepository.save(userEntity);
    return userEntityMapper.toUser(userEntity);
  }

  private static void checkIdIsPresent(User user) {
    if (user.getId() == null) {
      throw new UserIdIsAbsentException(user);
    }
  }
}
