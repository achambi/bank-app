package bo.com.mondongo.bankapp.repository;

import bo.com.mondongo.bankapp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("AccountRepository")
public interface AccountRepository extends JpaRepository<Account, Serializable> {
}
