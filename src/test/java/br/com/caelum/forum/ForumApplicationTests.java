package br.com.caelum.forum;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.caelum.forum.security.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ForumApplicationTests {
	
	
	@Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        assertThat(userService).isNotNull();
        assertThat(userService).isInstanceOf(UserDetailsService.class);
    }


}
