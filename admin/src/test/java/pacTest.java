import co.b4pay.admin.entity.City;
import co.b4pay.admin.entity.Province;
import co.b4pay.admin.entity.SysArea;
import co.b4pay.admin.service.CityService;
import co.b4pay.admin.service.ProvinceService;
import co.b4pay.admin.service.SysAreaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/spring-*.xml")
public class pacTest {

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private SysAreaService areaService;


    @Test
    public void save() {
        List<Province> provinceList = provinceService.findList();
        final String SEPARATOR = ",";
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        String convertedListStr = "";
        String citystr = "";
        String areastr = "";
        for (Province province : provinceList) {
            String province1 = province.getProvince();
            sb.append(province1);
            sb.append(SEPARATOR);
        }
        convertedListStr = sb.toString();
        String[] arr = convertedListStr.split(",");
        int index = getNum(0, arr.length - 1);
        String first = arr[index];
        System.out.println("省份:" + first);
        List<Province> province = provinceService.getByProvince(first);
        String provinceId = null;
        for (Province province1 : province) {
            provinceId = province1.getProvinceId();
        }

        List<City> cities = cityService.getByProvinceId(provinceId);
        for (City city : cities) {
            sb2.append(city.getCity());
            sb2.append(SEPARATOR);
        }
        citystr = sb2.toString();
        //System.out.println(citystr);
        String[] arr2 = citystr.split(",");
        int index2 = getNum(0, arr2.length - 1);
        String two = arr2[index2];
        System.out.println("市:" + two);
        List<City> city = cityService.getByCity(provinceId, two);
        String cityId = null;
        for (City city1 : city) {
            cityId = city1.getCityId();
        }

        List<SysArea> byCityId = areaService.getByCityId(cityId);
        for (SysArea area : byCityId) {
            sb3.append(area.getArea());
            sb3.append(SEPARATOR);
        }
        areastr = sb3.toString();
        //System.out.println("区选择:"+areastr);
        String[] arr3 = areastr.split(",");
        int index3 = getNum(0, arr3.length - 1);
        String tree = arr3[index3];
        System.out.println("区:" + tree);
    }


    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

}
