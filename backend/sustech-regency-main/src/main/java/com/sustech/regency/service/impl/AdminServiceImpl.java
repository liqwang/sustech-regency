package com.sustech.regency.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sustech.regency.db.dao.FileDao;
import com.sustech.regency.db.dao.RoomTypeDao;
import com.sustech.regency.db.dao.RoomTypeExhibitionDao;
import com.sustech.regency.db.po.RoomTypeExhibition;
import com.sustech.regency.service.AdminService;
import com.sustech.regency.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static com.sustech.regency.web.util.AssertUtil.asserts;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private FileUtil fileUtil;
    @Resource
    private RoomTypeDao roomTypeDao;
    @Resource
    private RoomTypeExhibitionDao roomTypeExhibitionDao;

    @Override
    public String uploadRoomTypeCover(MultipartFile picture, Integer roomTypeId) {
        return fileUtil.uploadDisplayCover(picture, roomTypeDao, roomTypeId);
    }

    @Override
    public String uploadRoomTypeMedia(MultipartFile media, Integer roomTypeId) {
        return fileUtil.uploadDisplayMedia(media, roomTypeExhibitionDao, new RoomTypeExhibition(),
                roomTypeId, roomTypeDao);
    }

    @Resource
    private FileDao fileDao;

    @Override
    public void deleteRoomTypeMedia(String mediaId, Integer roomTypeId) {
        asserts(fileDao.selectById(mediaId) != null, "该文件不存在");
        RoomTypeExhibition roomTypeExhibition =
                roomTypeExhibitionDao.selectOne(
                        new LambdaQueryWrapper<RoomTypeExhibition>()
                                .eq(RoomTypeExhibition::getRoomTypeId, roomTypeId)
                                .eq(RoomTypeExhibition::getFileId, mediaId)
                );
        asserts(roomTypeExhibition != null, "该文件不是该房型的展示图片或视频");
        fileUtil.deleteFile(mediaId);
    }
}
