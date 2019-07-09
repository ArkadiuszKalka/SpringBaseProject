package andrzej.appdemo.admin;

import andrzej.appdemo.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {

	Page<User> findAll(Pageable pageable);

}
