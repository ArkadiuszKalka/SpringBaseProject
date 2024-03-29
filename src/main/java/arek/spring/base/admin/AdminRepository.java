package arek.spring.base.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import arek.spring.base.user.User;

@Repository("adminRepository")
public interface AdminRepository extends JpaRepository<User, Integer> {

	User findUserById(int id);

	@Modifying
	@Query("UPDATE User u SET u.active = :intActive WHERE u.id = :id")
	void updateActivationUser(@Param("intActive")int active, @Param("id") int id);
	
	@Modifying
	@Query(value = "UPDATE user_role r SET r.role_id = :roleId WHERE r.user_id= :id", nativeQuery = true)
	void updateRoleUser(@Param("roleId") int nrRoli, @Param("id") int id);
	
	@Query(value = "SELECT u FROM User u WHERE u.name LIKE %:param% OR u.lastName LIKE %:param% OR u.email LIKE %:param%")
    Page<User> findAllSearch(@Param("param") String param, Pageable pageable);

	@Modifying
	@Query(value = "DELETE from user_role where user_id=:id", nativeQuery = true)
	void deleteFromUserRole(@Param("id")int id);

	@Modifying
	@Query(value = "DELETE from user where user_id=:id", nativeQuery = true)
	void deleteFromUser(@Param("id")int id);


}
