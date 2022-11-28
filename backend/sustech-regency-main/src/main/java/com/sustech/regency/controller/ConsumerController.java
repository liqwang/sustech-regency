package com.sustech.regency.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sustech.regency.db.dao.ChatHistoryDao;
import com.sustech.regency.db.po.ChatHistory;
import com.sustech.regency.db.po.Room;
import com.sustech.regency.model.param.ReserveParam;
import com.sustech.regency.db.po.Order;
import com.sustech.regency.model.vo.HotelInfo;
import com.sustech.regency.model.vo.PayInfo;
import com.sustech.regency.model.vo.RoomInfo;
import com.sustech.regency.service.ConsumerService;
import com.sustech.regency.service.PublicService;
import com.sustech.regency.web.annotation.DateParam;
import com.sustech.regency.web.annotation.PathController;
import com.sustech.regency.web.vo.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.List;

@PathController("/consumer")
public class ConsumerController {
    @Resource
    private ConsumerService consumerService;

    @Resource
    private ChatHistoryDao chatHistoryDao;

    @Resource
    private PublicService publicService;

    @ApiOperation(value = "上传评论图片或视频", notes = "为指定的订单(orderId)上传评论图片(jpg,jpeg,png)或视频(mp4),返回文件上传成功后的获取url, 如https://quanquancho.com:8080/public/file/2022/09/30/2d02610787154be1af4816d5450b5ae8.jpg")
    @PostMapping("/comment/upload-media")
    public ApiResponse<Map> uploadCommentMedia(@ApiParam(required = true)
                                               @RequestParam MultipartFile media,

                                               @ApiParam(value = "订单id", required = true)
                                               @NotNull @RequestParam Long orderId) {
        String url = consumerService.uploadCommentMedia(media, orderId);

        return ApiResponse.success(Map.of("url", url));
    }

    @ApiOperation(value = "删除评论图片或视频", notes = "删除指定的订单评论(orderId)的图片或视频")
    @PostMapping("/comment/delete-media")
    public ApiResponse deleteCommentMedia(@ApiParam(required = true)
                                          @NotNull @RequestParam String mediaId,

                                          @ApiParam(value = "订单id", required = true)
                                          @NotNull @RequestParam Long orderId) {
        consumerService.deleteCommentMedia(mediaId, orderId);
        return ApiResponse.success();
    }


    @ApiOperation(value = "预订房间", notes = "返回支付二维码的Base64编码，过期时间15分钟")
    @PostMapping("/reserve-room")
    public ApiResponse<PayInfo> reserveRoom(@RequestBody @Validated ReserveParam reserveParam) {
        PayInfo payInfo = consumerService.reserveRoom(reserveParam.getRoomId(),
                reserveParam.getStartTime(),
                reserveParam.getEndTime());
        return ApiResponse.success(payInfo);
    }

    @ApiOperation(value = "订单退款")
    @PostMapping("/refund")
    public ApiResponse refund(@RequestParam @NotNull Long orderId) {
        consumerService.cancelOrder(orderId);
        return ApiResponse.success();
    }

    @ApiOperation("收藏酒店")
    @PostMapping("/like-hotel")
    public ApiResponse likeHotel(@ApiParam(value = "酒店Id", required = true)
                                 @NotNull @RequestParam Integer hotelId) {
        consumerService.like(hotelId);
        return ApiResponse.success();
    }

    @ApiOperation("移出收藏酒店")
    @PostMapping("/dislike-hotel")
    public ApiResponse dislikeHotel(@ApiParam(value = "酒店Id", required = true) @RequestParam Integer hotelId) {
        consumerService.dislike(hotelId);
        return ApiResponse.success();
    }

    @ApiOperation("用户查看自己收藏的酒店")
    @GetMapping("/get-likes")
    public ApiResponse<List<HotelInfo>> getLikes() {
        List<HotelInfo> hotels = consumerService.getHotelInfoFromLikes();
        for (HotelInfo hotelInfo:
                hotels) {
            hotelInfo.setMinPrice(publicService.getMinPriceOfHotel(hotelInfo.getId()));
            hotelInfo.setCommentNum(publicService.getCommentsNumberByHotel(hotelInfo.getId()));
            hotelInfo.setLikes_num(publicService.getLikesNumByHotelId(hotelInfo.getId()));
        }

        return ApiResponse.success(hotels);
    }

    @ApiOperation("用户查看自己订单")
    @GetMapping("/get-orders")
    public ApiResponse<List<Order>> getOrders() {
        return ApiResponse.success(consumerService.getOrders());
    }

    @ApiOperation("用户多条件筛选某个酒店的订单")
    @GetMapping("/hotel/get-selected-orders")
    public ApiResponse<List<Order>> getSelectedOrders(@ApiParam(value = "是否有评论", required = false) @RequestParam(required = false) Boolean isComment,
                                                      @ApiParam(value = "开始时间", required = false) @RequestParam(required = false) @DateParam Date startTime,
                                                      @ApiParam(value = "结束时间", required = false) @RequestParam(required = false) @DateParam Date endTime,
                                                      @ApiParam(value = "订单状态", required = false) @RequestParam(required = false) Integer status) {

        return ApiResponse.success(consumerService.selectCustomerOrders(isComment, startTime, endTime, status));
    }

    @ApiOperation("用户获取与商家的所有聊天记录")
    @GetMapping("/hotel/get-chat-history")
    public ApiResponse<List<ChatHistory>> getChatHistories(@RequestParam String user1, @RequestParam String user2, @RequestParam Integer hotelId) {
        QueryWrapper<ChatHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("from_name", user1);
        queryWrapper.eq("to_name", user2);
        queryWrapper.eq("hotel_id", hotelId);
        List<ChatHistory> chatHistories = chatHistoryDao.selectList(queryWrapper);
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("from_name", user2);
        queryWrapper.eq("to_name", user1);
        queryWrapper.eq("hotel_id", hotelId);
        chatHistories.addAll(chatHistoryDao.selectList(queryWrapper));
        chatHistories.sort(Comparator.comparing(ChatHistory::getChatTime));
        return ApiResponse.success(chatHistories);
    }

    @ApiOperation("用户预定酒店时多参数筛选房间")
    @GetMapping("/hotel/consumer-select-rooms")
    public ApiResponse<List<Room>> getSelectedRooms(@ApiParam(value = "酒店Id", required = true) @RequestParam @NotNull Integer hotelId,
                                                    @ApiParam(value = "开始时间", required = false) @RequestParam(required = false) @DateParam Date startTime,
                                                    @ApiParam(value = "结束时间", required = false) @RequestParam(required = false) @DateParam  Date endTime,
                                                    @ApiParam(value = "最低价格", required = false) @RequestParam(required = false) Integer minPrice,
                                                    @ApiParam(value = "最高价格", required = false) @RequestParam(required = false) Integer maxPrice,
                                                    @ApiParam(value = "房型ID") @RequestParam(required = false) Integer roomTypeId) {

        return ApiResponse.success(consumerService.getRoomInfosByCustomerChoice(hotelId,startTime,endTime,minPrice,maxPrice,roomTypeId));
    }

    @ApiOperation("用户上传评论")
    @PostMapping("/upload-comment")
    public ApiResponse uploadComment(@ApiParam(value = "订单Id", required = true) @RequestParam @NotNull Long orderId,
                                     @ApiParam(value = "评论") @RequestParam(required = false)  String comment) {
        consumerService.uploadComment(orderId,comment);
        return ApiResponse.success();
    }
    @ApiOperation("用户上传评分星级")
    @PostMapping("/upload-comment-star")
    public ApiResponse uploadCommentStar(@ApiParam(value = "订单Id", required = true) @RequestParam @NotNull Long orderId,
                                     @ApiParam(value = "star") @RequestParam(required = false)  Float star) {
        consumerService.uploadCommentStar(orderId,star);
        return ApiResponse.success();
    }
}
