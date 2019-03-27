package co.b4pay.admin.task;

import co.b4pay.admin.dao.DetailDataCardDao;
import co.b4pay.admin.dao.YfbPayrollDao;
import co.b4pay.admin.entity.DetailDataCard;
import co.b4pay.admin.entity.base.Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public class PfQueryTask {

    @Autowired
    YfbPayrollDao yfbPayrollDao;

    @Autowired
    DetailDataCardDao detailDataCardDao;

    @Scheduled(cron = "0 0 * * * ?")
    public void pfQuery() {
        DetailDataCard detailDataCard = new DetailDataCard();
        detailDataCard.setStatus(1);
        List<DetailDataCard> dataCardList = detailDataCardDao.findList(Params.create(detailDataCard));
    }
}
