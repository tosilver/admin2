import co.b4pay.admin.dao.DetailDataCardDao;
import co.b4pay.admin.entity.*;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.service.ConsumeService;
import co.b4pay.admin.service.TUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/spring-*.xml")
public class ServiceTest {

    @Autowired
    ConsumeService consumeService;

    @Autowired
    DetailDataCardDao detailDataCardDao;


    @Test
    public void listTest() {
        Consume consume = new Consume();
        Channel channel = new Channel();
        channel.setId("149");
        consume.setChannel(channel);
        List<Consume> list = consumeService.findList(consume);
        System.out.println(list);
    }

    @Test
    public void sumTest() {
        Double aDouble = consumeService.sumFzMoney(new Page<>());
        System.out.println(aDouble);
    }

    @Test
    public void findByBatchNo() {
        DetailDataCard batchNo = detailDataCardDao.findByBatchNo("10000000000003316751");
        System.out.println(batchNo);
    }

    @Test
    public void detailCardTest() {
        DetailDataCard detailDataCard = new DetailDataCard();
        detailDataCard.setStatus(1);
        detailDataCard.setMerchantId(100000000000056L);
        Params params = Params.create(detailDataCard);
        params.add("startDate", "2018-12-30 00:00:00");
        params.add("endDate", "2018-12-30 17:00:00");
        List<DetailDataCard> dataCardList = detailDataCardDao.findList(params);
        System.out.println(dataCardList.size());

    }

    @Autowired
    private TUserService tUserService;

    @Test
    public void updateStatusTest() {
       /* int i = tUserService.updateStatus("18672679757", 1);
        System.out.println(i);*/
        TUser tUser = tUserService.get("18672679757");
        System.out.println(tUser);
    }

    @Test
    public void findTest() {
        JobTrade jobTradeById = consumeService.findJobTradeById("20181119162501881625991266421414");
        System.out.println(jobTradeById);
    }

}
