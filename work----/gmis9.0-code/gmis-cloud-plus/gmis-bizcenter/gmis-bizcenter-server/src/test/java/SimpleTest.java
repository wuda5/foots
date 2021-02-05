import com.cdqckj.gmis.installed.InstallRecordApi;
import com.cdqckj.gmis.installed.entity.InstallRecord;
import com.cdqckj.gmis.operation.OrderRecordApi;
import com.cdqckj.gmis.operation.entity.OrderRecord;
import com.cdqckj.gmis.operation.enumeration.InstallOrderType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTest {

    @Autowired
    InstallRecordApi installRecordApi;
    OrderRecordApi orderRecordApi;
    @Test
    public void doTest() {
        InstallRecord installRecord =new InstallRecord().setInstallNumber("111");

        List<OrderRecord> query = orderRecordApi.query(new OrderRecord().setBusinessOrderNumber(installRecord.getInstallNumber()).setWorkOrderType(InstallOrderType.SURVEY.getCode())).getData();
        System.out.println(query);
    }
}