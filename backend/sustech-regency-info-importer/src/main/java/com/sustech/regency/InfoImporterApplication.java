package com.sustech.regency;

import com.sustech.regency.db.dao.CityDao;
import com.sustech.regency.db.dao.ProvinceDao;
import com.sustech.regency.db.dao.RegionDao;
import com.sustech.regency.db.po.City;
import com.sustech.regency.db.po.Province;
import com.sustech.regency.db.po.Region;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@MapperScan("com.sustech.regency.db.dao")
@SpringBootApplication
public class InfoImporterApplication implements ApplicationRunner {
	@Autowired
	private ProvinceDao provinceDao;
	@Autowired
	private CityDao cityDao;
	@Autowired
	private RegionDao regionDao;

	public static void main(String[] args) {
		SpringApplication.run(InfoImporterApplication.class, args);
	}

	@Override
	@SuppressWarnings("SpellCheckingInspection")
	public void run(ApplicationArguments args) throws IOException {
		Document document = Jsoup.parse(Files.readString(Path.of("backend/sustech-regency-info-importer/src/main/resources/info.html")));
		Elements proviceDoms = document.select("#area > div.onebox > div.container");
		proviceDoms.remove(proviceDoms.size()-1); //去除最后的"海外"，剩余34个省
		for (Element proviceDom : proviceDoms) {
			Province province = new Province();
			province.setName(proviceDom.select("em").get(0).ownText());
			System.out.println("------------------"+province.getName()+" 开始------------------");
			provinceDao.insert(province);

			List<String> cityNames = proviceDom.select("div.twoBox > em.two").stream()
									.map(Element::ownText)
									.collect(Collectors.toList());
			Elements cityDoms = proviceDom.select("div.twoBox > div.thrBox");
			for(int i=0;i<cityNames.size();i++){
				City city = new City();
				city.setProvinceId(province.getId());
				city.setName(cityNames.get(i));
				if(i==0){ //第一个DOM为省会
					city.setIsProvincialCapital(true);
				}
				cityDao.insert(city);
				Element cityDom = cityDoms.get(i);

				Elements regionDoms = cityDom.select("em.thr");
				for (Element regionDom : regionDoms) {
					String resionName = regionDom.ownText();
					if(resionName.equals("其他区")||resionName.equals("其它区")){continue;}
					Region region = new Region();
					region.setCityId(city.getId());
					region.setName(resionName);
					regionDao.insert(region);
				}
				System.out.println(city.getName()+" 完成");
			}
		}
	}
}
