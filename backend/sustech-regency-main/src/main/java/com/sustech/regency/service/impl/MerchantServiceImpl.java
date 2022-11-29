package com.sustech.regency.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.sustech.regency.db.dao.*;
import com.sustech.regency.db.po.*;
import com.sustech.regency.model.vo.HotelInfo;
import com.sustech.regency.model.vo.RoomInfo;
import com.sustech.regency.service.MerchantService;
import com.sustech.regency.service.PublicService;
import com.sustech.regency.service.RoomService;
import com.sustech.regency.util.FileUtil;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

import static com.sustech.regency.util.VerificationUtil.getUserId;
import static com.sustech.regency.web.util.AssertUtil.asserts;

@Service
public class MerchantServiceImpl implements MerchantService {
    @Resource
    private HotelDao hotelDao;

    @Resource
    private PublicService publicService;

    @Resource
    private OrderDao orderDao;

    @Resource
    private RoomService roomService;

    @Override
    public List<HotelInfo> getAllHotelInfos(Integer merchantId) {
        return hotelDao.selectJoinList(
                HotelInfo.class,
                new MPJLambdaWrapper<HotelInfo>()
                        .select(Hotel::getId, Hotel::getLatitude, Hotel::getLongitude, Hotel::getName, Hotel::getTel, Hotel::getAddress, Hotel::getDescription)
                        .selectAs(Province::getName, HotelInfo::getProvinceName)
                        .selectAs(City::getName, HotelInfo::getCityName)
                        .selectAs(Region::getName, HotelInfo::getRegionName)
                        .innerJoin(Region.class, Region::getId, Hotel::getRegionId)
                        .innerJoin(City.class, City::getId, Region::getCityId)
                        .innerJoin(Province.class, Province::getId, City::getProvinceId)
                        .eq(Hotel::getMerchantId, merchantId));
    }

    @Override
    public Boolean addNewHotel(Hotel hotel) {
        hotelDao.insert(hotel);
        return true;
    }

    @Override
    public Boolean deleteHotel(Integer merchantId, Integer hotelId) {
        QueryWrapper<Hotel> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id", merchantId);
        wrapper.eq("id", hotelId);
        Hotel query = hotelDao.selectOne(wrapper);
        //只有自己旗下的酒店才能删除
        if (query == null) {
            return false;
        }
        hotelDao.deleteById(hotelId);
        return true;
    }

    @Override
    public Boolean updateHotel(Integer hotelId, Float latitude, Float longitude, Integer merchantId, String name, String tel, String address) {
        QueryWrapper<Hotel> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id", merchantId);
        wrapper.eq("id", hotelId);
        Hotel query = hotelDao.selectOne(wrapper);
        //只有自己旗下的酒店才能删除
        if (query == null) {
            return false;
        } else {
            Hotel hotel = new Hotel();
            if (hotelId != null) hotel.setId(hotelId);
            if (latitude != null) hotel.setLatitude(latitude);
            if (longitude != null) hotel.setLongitude(longitude);
            if (merchantId != null) hotel.setMerchantId(merchantId); //比如酒店转让
            if (name != null) hotel.setName(name);
            if (tel != null) hotel.setTel(tel);
            hotelDao.updateById(hotel);
        }
        return true;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public HotelInfo getOneHotel(Integer hotelId, Float latitude, Float longitude, Integer merchantId, String name, String tel) {
//        QueryWrapper<Hotel> wrapper = new QueryWrapper<>();
        MPJLambdaWrapper<HotelInfo> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(Hotel::getId, Hotel::getLatitude, Hotel::getLongitude, Hotel::getName, Hotel::getTel, Hotel::getAddress)
                .selectAs(Province::getName, HotelInfo::getProvinceName)
                .selectAs(City::getName, HotelInfo::getCityName)
                .selectAs(Region::getName, HotelInfo::getRegionName);
        if (hotelId != null) wrapper.eq(Hotel::getId, hotelId);
        if (latitude != null) wrapper.eq(Hotel::getLatitude, latitude);
        if (longitude != null) wrapper.eq(Hotel::getLongitude, longitude);
        if (name != null) wrapper.eq(Hotel::getName, name);
        if (tel != null) wrapper.eq(Hotel::getTel, tel);

        wrapper.innerJoin(Region.class, Region::getId, Hotel::getRegionId)
                .innerJoin(City.class, City::getId, Region::getCityId)
                .innerJoin(Province.class, Province::getId, City::getProvinceId)
                .eq(Hotel::getMerchantId, merchantId);
        return hotelDao.selectJoinOne(HotelInfo.class, wrapper);
    }

    @Override
    public List<Order> getOrders(Integer merchantId, Integer hotelId, Integer roomId, Integer cityId) {
        //晚些实现，到时候看看前端需要那些参数可以查订单
        return null;
    }

    @Resource
    private FileUtil fileUtil;
    @Resource
    private HotelExhibitionDao hotelExhibitionDao;

    @Override
    public String uploadHotelMedia(MultipartFile media, Integer hotelId) {
        checkHotelAndOwner(hotelId);
        return fileUtil.uploadDisplayMedia(media, hotelExhibitionDao, new HotelExhibition(hotelId, null));
    }

    @Resource
    private FileDao fileDao;

    @Override
    public String uploadHotelCover(MultipartFile picture, Integer hotelId) {
        Hotel hotel = checkHotelAndOwner(hotelId);
        return fileUtil.uploadDisplayCover(picture, hotelDao, hotel);
    }

    @Override
    public void deleteHotelMedia(String mediaId, Integer hotelId) {
        checkHotelAndOwner(hotelId);
        asserts(fileDao.selectById(mediaId) != null, "该文件不存在");
        HotelExhibition hotelExhibition = hotelExhibitionDao.selectOne(
                new LambdaQueryWrapper<HotelExhibition>()
                        .eq(HotelExhibition::getHotelId, hotelId)
                        .eq(HotelExhibition::getFileId, mediaId));
        asserts(hotelExhibition != null, "该文件不是该酒店的展示图片或视频");
        fileUtil.deleteFile(mediaId);
    }

    @Override
    public List<Float> getHotelHistoricalBills(Integer hotelId, Date startTime, Date EndTime, Integer roomType) {
        Float[] money = new Float[differentDays(startTime, EndTime)];
        List<Room> rooms = publicService.getRoomsByHotel(hotelId, null);
        for (Room room : rooms) {
            if (roomType != null) {
                if (!Objects.equals(room.getTypeId(), roomType)) {
                    continue;
                }
            }
            LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
            orderLambdaQueryWrapper.eq(Order::getRoomId, room.getId());
            List<Order> orders = orderDao.selectList(orderLambdaQueryWrapper);
            for (Order o : orders) {
                if (startTime.before(o.getDateEnd()) && EndTime.after(o.getDateEnd())) {
                    money[differentDays(startTime, o.getDateEnd())] = o.getFee();
                }

            }
        }
        return new ArrayList<>(Arrays.asList(money));
    }

    @Override
    public List<Order> selectCustomerOrders(Integer hotelId, Integer roomId, Boolean isComment, Date startTime, Date EndTime, Integer status) {
        List<Order> orderList = new ArrayList<>();
        List<Room> rooms = publicService.getRoomsByHotel(hotelId, null);
        for (Room room :
                rooms) {
            LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
            orderLambdaQueryWrapper.eq(Order::getRoomId, room.getId());
            List<Order> orders = orderDao.selectList(orderLambdaQueryWrapper);
            for (Order o : orders) {
                boolean judge = true;

                if (status != null) {
                    if (o.getStatus().ordinal() != status) {
                        judge = false;
                    }

                    if (status == 2) {
                        if (o.getStatus().ordinal() > status) {
                            judge = true;
                        }
                    }
                }

                if (startTime != null && EndTime != null) {
                    if (!(startTime.before(o.getDateEnd()) && EndTime.after(o.getDateEnd()))) {
                        judge = false;
                    }
                }
                if (roomId != null) {
                    if (!Objects.equals(o.getRoomId(), roomId)) {
                        judge = false;
                    }
                }
                if (isComment != null) {
                    if (o.getComment() == null) {
                        judge = false;
                    }
                }




                if (judge) {
                    orderList.add(o);
                }
            }
        }
        return orderList;
    }

    @Override
    public void notifySale(Integer hotelId, Integer roomType, Float saleRate) {
        List<Room> rooms = publicService.getRoomsByHotel(hotelId, roomType);
        for (Room room : rooms) {
            roomService.updateOneRoom(getUserId(), room.getId(), null, null, null, null, null, null, saleRate);
        }
    }

    /**
     * @return 该hotelId对应的酒店
     */
    private Hotel checkHotelAndOwner(Integer hotelId) {
        Hotel hotel = hotelDao.selectById(hotelId);
        asserts(hotel != null, "酒店不存在");
        asserts(getUserId().equals(hotel.getMerchantId()), "该酒店属于别人");
        return hotel;
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    private static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {//同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else {// 不同年
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }


}



