package com.sustech.regency.controller;

import com.sustech.regency.db.po.Room;
import com.sustech.regency.service.RoomService;
import com.sustech.regency.web.vo.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Validated //单参数校验时必须加上该注解才会生效:https://developer.aliyun.com/article/786719
@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @ApiOperation("添加一个房间")
    @PostMapping("/room/add")
    public ApiResponse<Boolean> addNewRoom(@ApiParam(value = "房间号", required = true) @RequestParam Integer roomNum,
                                           @ApiParam(value = "价格", required = true) @RequestParam Float price,
                                           @ApiParam(value = "楼层", required = true) @RequestParam Integer floor,
                                           @ApiParam(value = "房间类型", required = true) @RequestParam Integer typeId,
                                           @ApiParam(value = "属于哪个酒店", required = true) @RequestParam Integer hotelId,
                                           @ApiParam(value = "是否空置中", required = true) @RequestParam Boolean isAvailable,
                                           @ApiParam(value = "折扣率", required = true) @RequestParam Float discount)
    {
        Room room = Room.builder().roomNum(roomNum)
                .price(price)
                .floor(floor)
                .typeId(typeId)
                .hotelId(hotelId)
                .isAvailable(isAvailable)
                .discount(discount)
                .build();
        return ApiResponse.success(roomService.addNewRoom(room));
    }

    @ApiOperation("商家删除一个酒店房间")
    @PostMapping("/room/delete")
    public ApiResponse<Boolean> deleteRoom(@ApiParam(value = "房间Id", required = true) @RequestParam Integer roomId) {
        int merchantId = (int) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.success(roomService.deleteRoom(merchantId, roomId));
    }

    @ApiOperation("商家更新一个酒店房间信息")
    @PostMapping("/room/updateOne")
    public ApiResponse<Boolean> updateOneRoom(@ApiParam(value = "房间Id", required = true) @RequestParam Integer roomId,
                                              @ApiParam(value = "房间号") @RequestParam(required = false) Integer roomNum,
                                              @ApiParam(value = "价格") @RequestParam(required = false) Float price,
                                              @ApiParam(value = "楼层") @RequestParam(required = false) Integer floor,
                                              @ApiParam(value = "房间类型") @RequestParam(required = false) Integer typeId,
                                              @ApiParam(value = "属于哪个酒店") @RequestParam(required = false) Integer hotelId,
                                              @ApiParam(value = "是否空置中") @RequestParam(required = false) Boolean isAvailable,
                                              @ApiParam(value = "折扣率") @RequestParam(required = false) Float discount){
        int merchantId = (int) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.success(roomService.updateOneRoom(merchantId,roomId,roomNum,price,floor,typeId,hotelId,isAvailable,discount));
    }

    @ApiOperation("商家更新一些房间信息")
    @PostMapping("/room/updateRooms")
    public ApiResponse<Boolean> updateOneRooms(@ApiParam(value = "价格") @RequestParam(required = false) Float price,
                                               @ApiParam(value = "房间类型") @RequestParam(required = false) Integer typeId,
                                               @ApiParam(value = "属于哪个酒店") @RequestParam(required = false) Integer hotelId,
                                               @ApiParam(value = "折扣率") @RequestParam(required = false) Float discount){
        int merchantId = (int) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.success(roomService.updateRooms(merchantId,hotelId,typeId,price,discount));
    }


}
