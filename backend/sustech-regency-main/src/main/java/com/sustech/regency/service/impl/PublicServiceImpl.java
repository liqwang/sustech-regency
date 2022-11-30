package com.sustech.regency.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.sustech.regency.db.dao.*;
import com.sustech.regency.db.po.*;
import com.sustech.regency.db.po.Collection;
import com.sustech.regency.model.vo.Comment;
import com.sustech.regency.model.vo.HotelInfo;
import com.sustech.regency.model.vo.RoomInfo;
import com.sustech.regency.service.PublicService;
import com.sustech.regency.util.FileUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.sustech.regency.db.po.OrderStatus.COMMENTED;
import static com.sustech.regency.db.po.OrderStatus.NOT_COMMENTED;
import static com.sustech.regency.web.util.AssertUtil.asserts;

@Service
public class PublicServiceImpl implements PublicService {
    @Resource
    private HotelDao hotelDao;

    @Resource
    private RoomDao roomDao;

    @Resource
    private FileDao fileDao;

    @Resource
    private HotelExhibitionDao hotelExhibitionDao;

    @Resource
    private OrderDao orderDao;

    @Resource
    private UserDao userDao;

    @Resource
    private RoomTypeDao roomTypeDao;

    @Resource
    private RoomTypeExhibitionDao roomTypeExhibitionDao;

    @Resource
    private CollectionDao collectionDao;

    @Override
    public IPage<HotelInfo> getHotelsByLocation(String province, String city, String region, String hotelName, Integer pageNum, Integer pageSize) {
        MPJLambdaWrapper<HotelInfo> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(Hotel::getId, Hotel::getLatitude, Hotel::getLongitude, Hotel::getName, Hotel::getTel, Hotel::getAddress, Hotel::getStars)
                .selectAs(Province::getName, HotelInfo::getProvinceName)
                .selectAs(City::getName, HotelInfo::getCityName)
                .selectAs(Region::getName, HotelInfo::getRegionName)
                .selectAs(File::getId, HotelInfo::getCoverUrl);
        if (Strings.isNotEmpty(province)) wrapper.eq(Province::getName, province);
        if (Strings.isNotEmpty(city)) wrapper.eq(City::getName, city);
        if (Strings.isNotEmpty(region)) wrapper.eq(Region::getName, region);
        if (Strings.isNotEmpty(hotelName)) wrapper.like(Hotel::getName, hotelName);
        wrapper.innerJoin(Region.class, Region::getId, Hotel::getRegionId)
                .innerJoin(City.class, City::getId, Region::getCityId)
                .innerJoin(Province.class, Province::getId, City::getProvinceId)
                .innerJoin(File.class, File::getId, Hotel::getCoverId);
        IPage<HotelInfo> hotelInfoIPage = hotelDao.selectJoinPage(new Page<>(pageNum, pageSize), HotelInfo.class, wrapper);
        List<HotelInfo> hotelInfos = hotelInfoIPage.getRecords();
        for (HotelInfo a : hotelInfos) {
            LambdaQueryWrapper<File> fileLambdaQueryWrapper = new LambdaQueryWrapper<>();
            fileLambdaQueryWrapper.eq(File::getId, a.getCoverUrl());
            File file = fileDao.selectOne(fileLambdaQueryWrapper);
            if (file != null) {
                if (file.getDeleteTime() == null) a.setCoverUrl(FileUtil.getUrl(file));
            }
            a.setPictureUrls(getHotelPictureUrls(a.getId()));
            a.setVideoUrls(getHotelVideoUrls(a.getId()));
        }
        return hotelInfoIPage;
    }

    @Override
    public List<Room> getRoomsByHotel(Integer hotelId, Integer roomTypeId) {
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Room::getHotelId, hotelId);
        List<Room> rooms = roomDao.selectList(wrapper);
        List<Room> roomList = new ArrayList<>();
        if (roomTypeId != null) {
            for (Room r : rooms) {
                if (r.getTypeId().equals(roomTypeId)) {
                    roomList.add(r);
                }
            }
        }
        if (roomTypeId == null) {
            return rooms;
        }
        return roomList;
    }

    @Override
    public List<String> getHotelPictureUrls(Integer hotelId) {
        LambdaQueryWrapper<HotelExhibition> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HotelExhibition::getHotelId, hotelId);
        List<HotelExhibition> list = hotelExhibitionDao.selectList(wrapper);
        List<String> pictureList = new ArrayList<>();

        for (HotelExhibition he : list) {
            LambdaQueryWrapper<File> fileLambdaQueryWrapper = new LambdaQueryWrapper<>();
            fileLambdaQueryWrapper.eq(File::getId, he.getFileId());
            File file = fileDao.selectOne(fileLambdaQueryWrapper);
            if (file != null && !file.getSuffix().equals("mp4")) {
                if (file.getDeleteTime() == null) pictureList.add(FileUtil.getUrl(file));
            }
        }
        return pictureList;
    }


    @Override
    public List<String> getRoomTypeVideoUrls(Integer roomTypeId) {
        LambdaQueryWrapper<RoomTypeExhibition> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoomTypeExhibition::getRoomTypeId, roomTypeId);
        List<RoomTypeExhibition> list = roomTypeExhibitionDao.selectList(wrapper);
        List<String> videoList = new ArrayList<>();
        for (RoomTypeExhibition he : list) {
            LambdaQueryWrapper<File> fileLambdaQueryWrapper = new LambdaQueryWrapper<>();
            fileLambdaQueryWrapper.eq(File::getId, he.getFileId());
            File file = fileDao.selectOne(fileLambdaQueryWrapper);
            if (file != null && file.getSuffix().equals("mp4")) {
                if (file.getDeleteTime() == null) videoList.add(FileUtil.getUrl(file));
            }
        }
        return videoList;
    }

    @Override
    public List<String> getRoomTypePictureUrls(Integer roomTypeId) {
        LambdaQueryWrapper<RoomTypeExhibition> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoomTypeExhibition::getRoomTypeId, roomTypeId);
        List<RoomTypeExhibition> list = roomTypeExhibitionDao.selectList(wrapper);
        List<String> pictureList = new ArrayList<>();

        for (RoomTypeExhibition he : list) {
            LambdaQueryWrapper<File> fileLambdaQueryWrapper = new LambdaQueryWrapper<>();
            fileLambdaQueryWrapper.eq(File::getId, he.getFileId());
            File file = fileDao.selectOne(fileLambdaQueryWrapper);
            if (file != null && !file.getSuffix().equals("mp4")) {
                if (file.getDeleteTime() == null) pictureList.add(FileUtil.getUrl(file));
            }
        }
        return pictureList;
    }


    @Override
    public List<String> getHotelVideoUrls(Integer hotelId) {
        LambdaQueryWrapper<HotelExhibition> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HotelExhibition::getHotelId, hotelId);
        List<HotelExhibition> list = hotelExhibitionDao.selectList(wrapper);
        List<String> videoList = new ArrayList<>();
        for (HotelExhibition he : list) {
            LambdaQueryWrapper<File> fileLambdaQueryWrapper = new LambdaQueryWrapper<>();
            fileLambdaQueryWrapper.eq(File::getId, he.getFileId());
            File file = fileDao.selectOne(fileLambdaQueryWrapper);
            if (file != null && file.getSuffix().equals("mp4")) {
                if (file.getDeleteTime() == null) videoList.add(FileUtil.getUrl(file));
            }
        }
        return videoList;
    }

    @Override
    public Float getMinPriceOfHotel(Integer hotelId) {
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Room::getHotelId, hotelId);
        List<Room> roomList = roomDao.selectList(wrapper);
        asserts(!roomList.isEmpty(), "Hotel has no rooms available");
        float min_price = Float.MAX_VALUE;
        for (Room r : roomList) {
            float curr_price = r.getPrice() * r.getDiscount();
            if (curr_price < min_price) min_price = curr_price;
        }
        return min_price;
    }

    @Override
    public Integer getCommentsNumberByHotel(Integer hotelId) {
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Room::getHotelId, hotelId);
        List<Room> roomList = roomDao.selectList(wrapper);
        int cnt = 0;
        for (Room r : roomList) {
            int room_id = r.getId();
            LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
            orderLambdaQueryWrapper.eq(Order::getRoomId, room_id);
            List<Order> orderList = orderDao.selectList(orderLambdaQueryWrapper);
            cnt += orderList.size();
        }
        return cnt;
    }

    @Override
    public RoomInfo getRoomInfoByRoomId(Integer roomId) {
        Room room = roomDao.selectById(roomId);
        asserts(room != null, "RoomId dose not exist!");
        MPJLambdaWrapper<RoomInfo> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(Room::getId, Room::getRoomNum, Room::getPrice, Room::getFloor, Room::getTypeId, Room::getHotelId, Room::getIsAvailable, Room::getDiscount)
                .selectAs(RoomType::getName, RoomInfo::getRoomTypeName)
                .selectAs(RoomType::getCapacity, RoomInfo::getCapacity)
                .selectAs(RoomType::getToiletNum, RoomInfo::getToiletNum)
                .selectAs(RoomType::getHasLivingRoom, RoomInfo::getHasLivingRoom)
                .selectAs(File::getId, RoomInfo::getCoverUrl)
                .innerJoin(RoomType.class, RoomType::getId, Room::getTypeId);
        wrapper.eq(Room::getId, room.getId());
        RoomInfo roomInfo = roomDao.selectJoinOne(RoomInfo.class, wrapper);
        LambdaQueryWrapper<RoomType> roomTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomTypeLambdaQueryWrapper.eq(RoomType::getId, roomInfo.getTypeId());
        RoomType type = roomTypeDao.selectOne(roomTypeLambdaQueryWrapper);
        LambdaQueryWrapper<File> fileLambdaQueryWrapper = new LambdaQueryWrapper<>();
        fileLambdaQueryWrapper.eq(File::getId, type.getCoverId());
        File file = fileDao.selectOne(fileLambdaQueryWrapper);
        if (file != null) {
            if (file.getDeleteTime() == null) roomInfo.setCoverUrl(FileUtil.getUrl(file));
        }
        roomInfo.setPictureUrls(getRoomTypePictureUrls(roomInfo.getId()));
        roomInfo.setVideoUrls(getRoomTypeVideoUrls(roomInfo.getId()));


        return roomInfo;
    }

    @Override
    public List<RoomType> getRoomTypesByHotelId(Integer hotelId) {
        List<Room> rooms = getRoomsByHotel(hotelId, null);
        List<Integer> typeIds = new ArrayList<>();
        for (Room room : rooms) {
            if (!typeIds.contains(room.getTypeId())) typeIds.add(room.getTypeId());
        }
        List<RoomType> roomTypes = new ArrayList<>();
        for (Integer i : typeIds) {
            RoomType roomType = roomTypeDao.selectById(i);
            roomTypes.add(roomType);
        }
        return roomTypes;
    }

    @Override
    public String getMerchantUsernameByHotelId(Integer hotelId) {
        MPJLambdaWrapper<User> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(User::getName)
                .eq(Hotel::getId, hotelId)
                .leftJoin(Hotel.class, Hotel::getMerchantId, User::getId);
        User user = userDao.selectJoinOne(User.class, wrapper);
        if (user == null) {
            return null;
        }
        return user.getName();
    }

    @Override
    public List<Comment> getCommentsByHotelId(Integer hotelId) {
        MPJLambdaWrapper<Comment> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(Order::getCommentTime, Order::getComment, Order::getStars)
                .selectAs(User::getName, Comment::getUserName)
                .selectAs(Hotel::getName, Comment::getHotelName)
                .selectAs(RoomType::getName, Comment::getRoomType);
        wrapper.eq(Hotel::getId, hotelId)
                .and(query -> query.eq(Order::getStatus, NOT_COMMENTED)
                        .or() //未评论的变为默认好评
                        .eq(Order::getStatus, COMMENTED));
        wrapper.innerJoin(User.class, User::getId, Order::getPayerId)
                .innerJoin(Room.class, Room::getId, Order::getRoomId)
                .innerJoin(Hotel.class, Hotel::getId, Room::getHotelId)
                .innerJoin(RoomType.class, RoomType::getId, Room::getTypeId);
        List<Comment> comments = new ArrayList<>(orderDao.selectJoinList(Comment.class, wrapper)
                .stream()
                .peek(comment -> {
                    if (comment.getComment() == null) {
                        comment.setComment("系统默认好评");
                        comment.setStars(5.0f);
                    }
                }).toList());
        for (Comment comment : comments) {
            String userName = comment.getUserName();
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getName, userName);
            User user = userDao.selectOne(userLambdaQueryWrapper);
            LambdaQueryWrapper<File> fileLambdaQueryWrapper = new LambdaQueryWrapper<>();
            fileLambdaQueryWrapper.eq(File::getId, user.getHeadshotId());
            File file = fileDao.selectOne(fileLambdaQueryWrapper);
            if (file != null) {
                if (file.getDeleteTime() == null) comment.setHeadShotUrl(FileUtil.getUrl(file));
            }
        }
        comments.sort(Comparator.comparing(Comment::getCommentTime));
        return comments;
    }

    @Override
    public HotelInfo getOneHotelByHotelId(Integer hotelId) {
        MPJLambdaWrapper<HotelInfo> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(Hotel::getId, Hotel::getLatitude, Hotel::getLongitude, Hotel::getName, Hotel::getTel, Hotel::getAddress, Hotel::getStars, Hotel::getDescription)
                .selectAs(Province::getName, HotelInfo::getProvinceName)
                .selectAs(City::getName, HotelInfo::getCityName)
                .selectAs(Region::getName, HotelInfo::getRegionName)
                .selectAs(File::getId, HotelInfo::getCoverUrl);
        wrapper.eq(Hotel::getId, hotelId);
        wrapper.innerJoin(Region.class, Region::getId, Hotel::getRegionId)
                .innerJoin(City.class, City::getId, Region::getCityId)
                .innerJoin(Province.class, Province::getId, City::getProvinceId)
                .innerJoin(File.class, File::getId, Hotel::getCoverId);
        HotelInfo hotelInfo = hotelDao.selectJoinOne(HotelInfo.class, wrapper);
        if (hotelInfo == null) {
            return null;
        }

        LambdaQueryWrapper<File> fileLambdaQueryWrapper = new LambdaQueryWrapper<>();
        fileLambdaQueryWrapper.eq(File::getId, hotelInfo.getCoverUrl());
        File file = fileDao.selectOne(fileLambdaQueryWrapper);
        if (file != null) {
            if (file.getDeleteTime() == null) hotelInfo.setCoverUrl(FileUtil.getUrl(file));
        }
        hotelInfo.setPictureUrls(getHotelPictureUrls(hotelInfo.getId()));
        hotelInfo.setVideoUrls(getHotelVideoUrls(hotelInfo.getId()));
        return hotelInfo;
    }

    @Override
    public Integer getRoomIdByHotelWithRoomNum(Integer hotelId, Integer roomNum) {
        LambdaQueryWrapper<Room> roomLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomLambdaQueryWrapper.eq(Room::getRoomNum, roomNum);
        List<Room> rooms = roomDao.selectList(roomLambdaQueryWrapper);
        for (Room r : rooms) {
            if (Objects.equals(r.getHotelId(), hotelId)) {
                return r.getId();
            }
        }
        asserts(true, "Room does not exist!");
        return null;
    }

    @Override
    public List<RoomType> getRoomTypesByHotel(Integer hotelId) {
        LambdaQueryWrapper<Room> roomLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomLambdaQueryWrapper.eq(Room::getHotelId, hotelId);
        List<Room> rooms = roomDao.selectList(roomLambdaQueryWrapper);
        return rooms.stream().map(Room::getTypeId)
                .map(typeId -> roomTypeDao.selectById(typeId))
                .distinct()
                .collect(Collectors.toList());

//        HashSet<RoomType> roomTypes = new HashSet<>();
//        for (Room r :
//                rooms) {
//            if (!roomTypes.contains(r.getTypeId())) roomTypes.add(r.getTypeId());
//        }
    }

    @Override
    public Integer getLikesNumByHotelId(Integer hotelId) {
        LambdaQueryWrapper<Collection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collection::getHotelId, hotelId);
        List<Collection> list = collectionDao.selectList(wrapper);
        return list.size();
    }

}
