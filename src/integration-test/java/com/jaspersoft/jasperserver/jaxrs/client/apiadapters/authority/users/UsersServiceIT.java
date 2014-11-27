package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.common.ClientConfigurationFactory;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.config.ConfigType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Alexander Krasnyanskiy
 */
public class UsersServiceIT extends ClientConfigurationFactory {

    private Session session;

    @BeforeClass
    public void before() {
        session = getClientSession(ConfigType.YML);
    }


    @Test
    public void should_create_a_new_User() {
        ClientUser created = session.usersService()
                .username("Purushottama")
                .createOrUpdate(new ClientUser().setUsername("Purushottama"))
                .entity();
        assertNotNull(created);
    }

    @Test(dependsOnMethods = "should_create_a_new_User")
    public void should_retrieve_the_create_User() {
        ClientUser retrieved = session.usersService()
                .username("Purushottama")
                .get("Purushottama")
                .entity();
        assertNotNull(retrieved);
    }

    @Test(dependsOnMethods = "should_create_a_new_User")
    public void should_update_an_existed_User() {
        ClientUser user = session.usersService()
                .username("Purushottama")
                .get("Purushottama")
                .entity();
        user.setPassword("TheDarkSecret");
        ClientUser updated = session.usersService()
                .username("Purushottama")
                .createOrUpdate(user)
                .entity();
        assertNotNull(updated);
        assertTrue(updated.getPassword().equals("TheDarkSecret"));
    }

    @AfterClass
    public void after() {
        session.logout();
    }
}