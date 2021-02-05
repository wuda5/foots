import com.cdqckj.gmis.InstalledServerApplication;
import com.cdqckj.gmis.installed.dao.InstallRecordMapper;
import com.cdqckj.gmis.installed.entity.InstallRecord;
import com.cdqckj.gmis.installed.service.InstallRecordService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试类
 *
 * @author gmis
 * @date 2019/06/26
 */


//@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InstalledServerApplication.class)
@Slf4j
public class TestResource {
    @Autowired
    InstallRecordMapper service;

    @Test
    public void testGet() {
//        User user = new User();
//        user.setEmail("nihao");
//        user.setMobile("nihao");
//        user.setStation(new RemoteData<>((Long) null));
//        user.setEducation(new RemoteData<>(null));
//        user.setPositionStatus(new RemoteData<>("12%3"));
//
//        List<User> users = userMapper.selectList(Wraps.<User>lbQ()
//                .eq(User::getOrg, user.getOrg()).eq(User::getStation, user.getStation())
//                .like(User::getEducation, user.getEducation()).like(User::getPositionStatus, user.getPositionStatus())
//        );
//        System.out.println(users.size());
        InstallRecord byId = service.selectById(9L);
        System.out.println(byId);
    }



}
